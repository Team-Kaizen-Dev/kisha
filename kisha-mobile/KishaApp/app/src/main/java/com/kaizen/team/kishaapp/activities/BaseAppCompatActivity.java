package com.kaizen.team.kishaapp.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kaizen.team.kishaapp.R;


/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public abstract class BaseAppCompatActivity extends AppCompatActivity {
    void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private Dialog progressDialog;

    protected void enableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    public void displayProgressDialog(String message) {
        if (progressDialog == null || !progressDialog.isShowing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LinearLayout view = (LinearLayout) View.inflate(this, R.layout.custom_progress_layout, null);
            builder.setView(view);
            ((TextView) view.findViewById(R.id.message_tv)).setText(message);
            progressDialog = builder.create();
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else if (progressDialog != null && progressDialog.isShowing()) {
            ((TextView) progressDialog.findViewById(R.id.message_tv)).setText(message);
        }
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
