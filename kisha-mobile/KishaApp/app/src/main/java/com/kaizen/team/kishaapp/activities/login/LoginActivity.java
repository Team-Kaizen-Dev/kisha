package com.kaizen.team.kishaapp.activities.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.kaizen.team.kishaapp.R;
import com.kaizen.team.kishaapp.activities.BaseAppCompatActivity;
import com.kaizen.team.kishaapp.activities.main.MainActivity;
import com.kaizen.team.kishaapp.core.logic.LogicFactory;
import com.kaizen.team.kishaapp.user.UserPreferences;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class LoginActivity extends BaseAppCompatActivity {

    private EditText userNameEt, passwordEt;
    private Button loginBtn;
    private TextView registerTv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        startMain();
        setupViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMain();
    }

    private void startMain() {
        if(UserPreferences.getInstance().getAccountId() != 0){
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    private void setupViews() {
        userNameEt = findViewById(R.id.userNameEt);
        passwordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.loginBtn);
        setUpLoginClick();
        setUpRegisterClick();
    }

    private void setUpRegisterClick() {
        findViewById(R.id.registerTv).setOnClickListener(view -> {
            startActivity(new Intent(this, RegistrationActivity.class));
        });

    }

    private void setUpLoginClick() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameEt.getText().toString().trim();
                String password = passwordEt.getText().toString().trim();

                if (userName.isEmpty()) {
                    userNameEt.setError("Field is required");
                    return;
                }
                if (password.isEmpty()) {
                    passwordEt.setError("Field is required");
                    return;
                }

                displayProgressDialog("Logging in...");
                new Thread(() -> {
                    try {
                        LogicFactory.getAuthLogic().loginUser(userName, password);
                        runOnUiThread(() -> {
                            dismissProgressDialog();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        });
                    } catch (Exception e) {
                        runOnUiThread(() -> {
                            dismissProgressDialog();
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        });
                    }
                }).start();
            }
        });

    }


}
