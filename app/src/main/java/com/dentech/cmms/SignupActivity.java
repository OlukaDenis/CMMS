package com.dentech.cmms;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.model.Employee;
import com.dentech.cmms.utils.Validations;
import com.rengwuxian.materialedittext.MaterialEditText;

import mehdi.sakout.fancybuttons.FancyButton;

public class SignupActivity extends AppCompatActivity {
    private TextView tvLogin;
    private MaterialEditText etName, etEmail, etPassword, etRepeatPassword;
    private FancyButton fbSignup;
    DatabaseHelper db;
    Validations validations;
    Employee employee;
    private LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //init objects
        db = new DatabaseHelper(this);
        validations = new Validations(this);
        employee = new Employee();

        //init views
        rootView = (LinearLayout) findViewById(R.id.signup_layout);
        etEmail = (MaterialEditText) findViewById(R.id.etEmail);
        etName = (MaterialEditText) findViewById(R.id.etUsername);
        etPassword = (MaterialEditText) findViewById(R.id.etPass);
        etRepeatPassword = (MaterialEditText) findViewById(R.id.etRepeatPass);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alreadyRegistered();
            }
        });
        fbSignup = (FancyButton) findViewById(R.id.signup_button);
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
        String email = etEmail.getText().toString();
        String pass = etPassword.getText().toString();
        String rPass = etRepeatPassword.getText().toString();
        if (name.isEmpty()) {
            etName.setError("Please fill in your name!");
            etName.requestFocus();
        } else if (email.isEmpty()) {
            etEmail.setError("Please provide an email address!");
            etEmail.requestFocus();
        } else if (pass.isEmpty()) {
            etPassword.setError("Please create a password!");
            etPassword.requestFocus();
        }
        if (!validations.isInputEditTextMatches(etPassword, etRepeatPassword,
                getString(R.string.error_password_match))) {
            return;
        }
        if (!db.checkUser(email)) {
            employee.setName(name);
            employee.setEmail(email);
            employee.setPassword(pass);

           // db.addUser(employee);
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

    /**
     * This method directs the employee to the login page
     */
    private void alreadyRegistered() {

        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
        finish();
    }

    /**
     * This method empties all the edit text fields
     */
    private void emptyEditText() {
        etName.setText(null);
        etEmail.setText(null);
        etPassword.setText(null);
        etRepeatPassword.setText(null);
    }
}
