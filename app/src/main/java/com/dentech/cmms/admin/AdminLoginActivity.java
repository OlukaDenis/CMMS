package com.dentech.cmms.admin;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dentech.cmms.R;
import com.dentech.cmms.database.DatabaseHelper;
import com.rengwuxian.materialedittext.MaterialEditText;

import mehdi.sakout.fancybuttons.FancyButton;

public class AdminLoginActivity extends AppCompatActivity {
    // UI references.
    private FancyButton signInButton;
    private MaterialEditText etPhone, etPassword;
    TextView tvSignup;
    private LinearLayout rootView;
    DatabaseHelper db;
    private AlertDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        //init the database objects
        db = new DatabaseHelper(this);

        rootView = (LinearLayout) findViewById(R.id.admin_login_layout);
        etPhone = (MaterialEditText) findViewById(R.id.ad_phone);
        etPassword  = (MaterialEditText) findViewById(R.id.ad_password);
        tvSignup = (TextView) findViewById(R.id.ad_tvSignup);
        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                attemptSignup();
            }
        });
        signInButton = (FancyButton) findViewById(R.id.btn_ad_login);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
                //dialog.show();
            }
        });
    }

    /**
     * This method directs the employee to create a new account
     */
    private void attemptSignup() {
        startActivity(new Intent(AdminLoginActivity.this, AdminSignupActivity.class));
    }

    /**
     * This methods logs in the employee
     */
    private void attemptLogin() {
        if(etPhone.getText().toString().isEmpty()) {
            etPhone.setError("You must fill in the phone number!");
            etPhone.requestFocus();
        }
        else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("You must fill in the password!");
            etPassword.requestFocus();
        }
        else {
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (db.checkAdmin(phone, password)){
                Intent loginIntent = new Intent(AdminLoginActivity.this, MainActivity.class);
                loginIntent.putExtra("Phone", phone);
                emptyEditText();
                startActivity(loginIntent);
                finish();
            }
            else {
                Snackbar snackbar  = Snackbar.make(rootView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG);
                View snackbarView = snackbar.getView();
                snackbarView.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.red));
                snackbar.show();

            }

        }

    }

    /**
     * This method empties all the text fields
     */
    private void emptyEditText() {
        etPhone.setText(null);
        etPassword.setText(null);
    }
}
