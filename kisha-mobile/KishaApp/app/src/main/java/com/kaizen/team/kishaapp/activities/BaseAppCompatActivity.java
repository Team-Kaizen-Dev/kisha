package com.kaizen.team.kishaapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
