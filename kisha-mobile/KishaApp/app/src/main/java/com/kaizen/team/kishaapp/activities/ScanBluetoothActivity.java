package com.kaizen.team.kishaapp.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaizen.team.kishaapp.R;
import com.kaizen.team.kishaapp.adapters.ScanBluetoothAdapter;
import com.kaizen.team.kishaapp.bluetooth.BluetoothManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;


/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class ScanBluetoothActivity extends BaseAppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1123;
    private static com.kaizen.team.kishaapp.bluetooth.BluetoothManager manager;
    private RecyclerView recyclerView;


    private List<String> pairedDeviceList = new ArrayList<>();

    private static BluetoothAdapter adapter;

    private ScanBluetoothAdapter recyclerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_bluetooth_activity);
        adapter = BluetoothAdapter.getDefaultAdapter();
        initRecyclerView();
        initOrUpdateAdapter();
        setUpBluetoothAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelDiscovery();
    }

    private void initOrUpdateAdapter() {
        if (recyclerAdapter == null) {
            recyclerAdapter = new ScanBluetoothAdapter(this, pairedDeviceList, getBluetoothNameClickListener());
            recyclerView.setAdapter(recyclerAdapter);
        } else {
            recyclerAdapter.setBluetoothDevices(pairedDeviceList);
        }
    }

    private View.OnClickListener getBluetoothNameClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bluetoothName = (String) view.getTag();
                if (pairedDeviceList.contains(bluetoothName)) {
                    String[] part = bluetoothName.split("\n");
                    Log.w("SELECTED BLUETOOTH", bluetoothName);
                    Log.w("SELECTED BLUETOOTH", part[0]);
                    Log.w("SELECTED BLUETOOTH", part[1]);
                    BluetoothDevice device = adapter.getRemoteDevice(part[1]);
                    UUID applicationUUID = UUID.fromString(device.getUuids()[0].toString());
                    manager = new BluetoothManager(device, true, adapter, Arrays.asList(applicationUUID));
                    finish();
                } else {
                    Log.w("SELECTED BLUETOOTH", "not available");
                }
            }
        };
    }

    public static boolean isManagagerReady() {
        return manager != null;
    }

    public static void sendData(String data) throws Exception {
        BluetoothManager.BluetoothSocketWrapper wrapper =  manager.connect();
        wrapper.getOutputStream().write(data.getBytes());
        wrapper.close();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void setUpBluetoothAdapter() {
        if (adapter == null) {
            showToast("Device does not support Bluetooth");
        } else {
            if (!adapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                getBondedDevices();
                initOrUpdateAdapter();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            showToast("Bluetooth has ben turned On.");
            getBondedDevices();
            initOrUpdateAdapter();
        }
    }

    private void getBondedDevices() {
        Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();

        if (pairedDevices != null && pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
                pairedDeviceList.add(deviceName + "\n" + deviceHardwareAddress);
            }
        } else {
            showToast("You haven't paired to any bluetooth device yet.");
        }
    }



    private void cancelDiscovery() {
        if (adapter != null) {
            adapter.cancelDiscovery();
        }
    }
}
