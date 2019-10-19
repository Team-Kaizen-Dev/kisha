package com.kaizen.team.kishaapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kaizen.team.kishaapp.R;
import com.kaizen.team.kishaapp.adapters.HazardAdapter;
import com.kaizen.team.kishaapp.hazard.HazardCategory;

public class MainActivity extends BaseAppCompatActivity {
    private static final long LOCATION_REFRESH_TIME = 0;
    private static final float LOCATION_REFRESH_DISTANCE = 1;
    private static final int LOCATION_PERMISSION_REQUEST = 123;
    private LocationManager locationManager;
    public static Location location;

    private RecyclerView recyclerView;
    private HazardAdapter recyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        recyclerView = findViewById(R.id.recyclerView);
        checkLocationPermission();
        initRecyclerView();
        initAdapter();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
    }

    private void initAdapter() {
        recyclerAdapter = new HazardAdapter(this, getOnHazardListener());
        recyclerView.setAdapter(recyclerAdapter);
    }

    private String getBluetoothHazardRequest(String userId, int hazardType, String coordinates, String message) {
        String request = "";
        request += userId;
        request += ("-" + hazardType);
        request += ("-" + coordinates);
        request += ("-" + message);
        return request;
    }

    private View.OnClickListener getOnHazardListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HazardCategory category = (HazardCategory) view.getTag();
                int hazardCategoryValue = getHazardCategoryValue(category);
                if (hazardCategoryValue == 11) {
                    showOtherDialog(hazardCategoryValue, getOtherSelectedListener());
                } else {
                    showDialog(hazardCategoryValue, getOnHazardCodeSelectedListener());
                }
            }
        };
    }

    private View.OnClickListener getOtherSelectedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String entryWithCategoryCode = (String) view.getTag();
                String[] entryWithCode = entryWithCategoryCode.split(":");
                String entry = entryWithCode[0];
                int code = Integer.parseInt(entryWithCode[1]);
                Log.w("SELECTED CODE AND ENTRY", code + "" + entry);
                if (location != null) {
                    String latLng = location.getLatitude() + "," + location.getLongitude();
                    //TODO add user id
                    //TODO add checker if there is network connection if true then call ws else send request via bluetooth
                    sendRequestViaBluetooth(entry, code, latLng);
                    //TODO send request here
                }
            }
        };
    }

    private void sendRequestViaBluetooth(String entry, int code, String latLng) {
        String request = getBluetoothHazardRequest("", code, latLng, entry);
        try {
            ScanBluetoothActivity.sendData(request);
        } catch (Exception e) {
            showToast("Bluetooth error, please select Bluetooth device");
            redirectToScanBluetooth();
        }
    }

    private void redirectToScanBluetooth() {
        startActivity(new Intent(this, ScanBluetoothActivity.class));
    }

    private View.OnClickListener getOnHazardCodeSelectedListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int code = (int) view.getTag();
                Log.w("SELECTED CODE", String.valueOf(code));

                if (location != null) {
                    String latLng = location.getLatitude() + "," + location.getLongitude();
                    //TODO add user id
                    //TODO add checker if there is network connection if true then call ws else send request via bluetooth
                    sendRequestViaBluetooth("", code, latLng);
                    //TODO send request here
                }
            }
        };
    }

    private int getHazardCategoryValue(HazardCategory category) {
        switch (category.getName()){
            case "FIRE":
                return -1;
            case "EARTHQUAKE":
                return 2;
            case "FLOOD":
                return 5;
            case "TYPHOON":
                return 8;
            case "OTHERS":
                return 11;
            default:
                return 0;
        }
    }

    private void showDialog(int hazardCodeToAppend, View.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View parentView = getLayoutInflater().inflate(R.layout.severity_level_selection, null);
        EditText entry = parentView.findViewById(R.id.otherEt);

        entry.setVisibility(View.GONE);
        builder.setView(parentView);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        Button lowBtn = parentView.findViewById(R.id.lowBtn);
        lowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setTag(hazardCodeToAppend + 1);
                onClickListener.onClick(view);
                alertDialog.dismiss();
            }
        });

        Button moderateBtn = parentView.findViewById(R.id.moderateBtn);
        moderateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setTag(hazardCodeToAppend + 2);
                onClickListener.onClick(view);
                alertDialog.dismiss();
            }
        });

        Button severeBtn = parentView.findViewById(R.id.severeBtn);
        severeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setTag(hazardCodeToAppend + 3);
                onClickListener.onClick(view);
                alertDialog.dismiss();
            }
        });
    }

    private void showOtherDialog(int hazardCodeToAppend, View.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View parentView = getLayoutInflater().inflate(R.layout.severity_level_selection, null);

        EditText entry = parentView.findViewById(R.id.otherEt);

        Button lowBtn = parentView.findViewById(R.id.lowBtn);

        lowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entry.getText().toString().isEmpty()) {
                    showToast("Please enter your concern.");
                    return;
                }
                view.setTag(entry.getText().toString() + ":" + hazardCodeToAppend + 1);
                onClickListener.onClick(view);
            }
        });

        Button moderateBtn = parentView.findViewById(R.id.moderateBtn);
        moderateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entry.getText().toString().isEmpty()) {
                    showToast("Please enter your concern.");
                    return;
                }
                view.setTag(entry.getText().toString() + ":" + hazardCodeToAppend + 2);
                onClickListener.onClick(view);
            }
        });

        Button severeBtn = parentView.findViewById(R.id.severeBtn);
        severeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (entry.getText().toString().isEmpty()) {
                    showToast("Please enter your concern.");
                    return;
                }
                view.setTag(entry.getText().toString() + ":" + hazardCodeToAppend + 3);
                onClickListener.onClick(view);
            }
        });

        builder.setView(parentView);
        builder.show();
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            showRequestLocationDialog();
        } else {
            requestPermission();
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
    }

    private void showRequestLocationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Attention!")
                .setMessage("Please enable location permission in order to continue.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        requestPermission();
                    }
                }).create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    //Request location updates:
                    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    } else {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_REFRESH_TIME,
                                LOCATION_REFRESH_DISTANCE, mLocationListener);
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_REFRESH_TIME,
                                LOCATION_REFRESH_DISTANCE, mLocationListener);
                    }
                }
            } else {
                showToast("Permission denied.");
            }
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            MainActivity.this.location = location;
            Log.w("Location Updates", location.getLatitude() + " " + location.getLongitude());
            showToast(location.getLatitude() + " " + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.w("Location Updates", "status changed " + s);
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.w("Location Updates", "provider enabled " + s);
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.w("Location Updates", "provider disabled " + s);
        }
    };
}
