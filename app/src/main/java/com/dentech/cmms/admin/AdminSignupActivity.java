package com.dentech.cmms.admin;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dentech.cmms.LoginActivity;
import com.dentech.cmms.R;
import com.dentech.cmms.SignupActivity;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Admin;
import com.dentech.cmms.model.Employee;
import com.dentech.cmms.utils.Validations;
import com.rengwuxian.materialedittext.MaterialEditText;

import mehdi.sakout.fancybuttons.FancyButton;

public class AdminSignupActivity extends AppCompatActivity {
    private TextView tvLogin;
    private MaterialEditText etName, etPhone, etPassword;
    private FancyButton fbSignup;
    DatabaseHelper db;
    Admin admin;
    private LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);

        //init objects
        db = new DatabaseHelper(this);
        admin = new Admin();

        //init views
        rootView = (LinearLayout) findViewById(R.id.admin_signup_layout);
        etPhone = (MaterialEditText) findViewById(R.id.adm_phone);
        etName = (MaterialEditText) findViewById(R.id.ad_name);
        etPassword = (MaterialEditText) findViewById(R.id.adm_password);
        tvLogin = (TextView) findViewById(R.id.tv_ad_Login);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyRegistered();
            }
        });
        fbSignup = (FancyButton) findViewById(R.id.ad_signup);
        fbSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignup();
            }
        });
    }

    /**
     * This method creates a new employee account
     */
    private void attemptSignup() {
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String pass = etPassword.getText().toString();
        if (name.isEmpty()) {
            etName.setError("Please fill in your name!");
            etName.requestFocus();
        } else if (phone.isEmpty()) {
            etPhone.setError("Please provide an phone number!");
            etPhone.requestFocus();
        } else if (pass.isEmpty()) {
            etPassword.setError("Please create a password!");
            etPassword.requestFocus();
        }
        else {
            if (!db.checkAdmin(phone)) {
                admin.setName(name);
                admin.setPhone(phone);
                admin.setPassword(pass);

                db.addAdmin(admin);
                // Snack Bar to show success message that record saved successfully
                Snackbar snackbar  = Snackbar.make(rootView, getString(R.string.success_message), Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.green));
                snackbar.show();
                emptyEditText();
            } else {
                // Snack Bar to show error message that record already exists
                Snackbar snackbar  = Snackbar.make(rootView, getString(R.string.error_email_exists), Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.red));
                snackbar.show();
            }
        }



    }

    /**
     * This method directs the employee to the login page
     */
    private void alreadyRegistered() {

        startActivity(new Intent(AdminSignupActivity.this, AdminLoginActivity.class));
        finish();
    }

    /**
     * This method empties all the edit text fields
     */
    private void emptyEditText() {
        etName.setText(null);
        etPhone.setText(null);
        etPassword.setText(null);
    }
}
