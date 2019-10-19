package com.kaizen.team.kishaapp.activities.login;


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
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.kaizen.team.kishaapp.R;
import com.kaizen.team.kishaapp.activities.BaseAppCompatActivity;
import com.kaizen.team.kishaapp.core.logic.LogicFactory;
import com.kaizen.team.kishaapp.user.UserPreferences;
import com.kaizen.team.kishaapp.user.data.User;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class RegistrationActivity extends BaseAppCompatActivity {

    private static final long LOCATION_REFRESH_TIME = 0;
    private static final float LOCATION_REFRESH_DISTANCE = 1;
    private static final int LOCATION_PERMISSION_REQUEST = 123;
    private LocationManager locationManager;
    public static Location location;

    private TextInputEditText nameEt, userNameEt, phoneNumberEt, passwordEt, addressEt;
    private Button registerBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        enableBackButton();
        initLayouts();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkLocationPermission();
    }


    private void initLayouts() {
        nameEt = findViewById(R.id.nameEt);
        userNameEt = findViewById(R.id.userNameEt);
        passwordEt = findViewById(R.id.passwordEt);
        phoneNumberEt = findViewById(R.id.phoneNumberEt);
        registerBtn = findViewById(R.id.registerBtn);
        addressEt = findViewById(R.id.addressEt);
        registerClickListener();

    }

    private void registerClickListener() {
        registerBtn.setOnClickListener(v -> {
            String name = nameEt.getText().toString().trim();
            String userName = userNameEt.getText().toString().trim();
            String password = passwordEt.getText().toString().trim();
            String phoneNumber = phoneNumberEt.getText().toString().trim();
            String address = addressEt.getText().toString().trim();

            if (name.isEmpty()) {
                nameEt.setError("Field is required");
                return;
            }
            if (userName.isEmpty()) {
                userNameEt.setError("Field is required");
                return;
            }
            if (password.isEmpty()) {
                passwordEt.setError("Field is required");
                return;
            }
            if (phoneNumber.isEmpty()) {
                phoneNumberEt.setError("Field is required");
                return;
            }
            if(location == null){
                showToast("Failed retrieving location");
                return;
            }
            User user = new User();
            user.setFullName(name);
            user.setAddress(address);
            user.setContactNumber(phoneNumber);
            user.setPassword(password);
            user.setUsername(userName);
            user.setLat(location.getLatitude());
            user.setLng(location.getLongitude());
            displayProgressDialog("Creating User...");

            new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                        User createdUser = LogicFactory.getAuthLogic().createUser(user);
                        UserPreferences.getInstance().setAccountId(createdUser.getId());
                        runOnUiThread(() -> {
                            dismissProgressDialog();
                            showToast("User successfully created");
                            finish();
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            dismissProgressDialog();
                            showToast(e.getMessage());
                        });
                    }
                }
            }.start();

        });
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
            RegistrationActivity.this.location = location;
            Log.w("Location Updates", location.getLatitude() + " " + location.getLongitude());
//            showToast(location.getLatitude() + " " + location.getLongitude());
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
