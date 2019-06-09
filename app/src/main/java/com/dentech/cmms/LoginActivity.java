package com.dentech.cmms;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dentech.cmms.admin.EmployeesActivity;
import com.dentech.cmms.common.CommonUtils;
import com.dentech.cmms.database.DatabaseHelper;
import com.dentech.cmms.navigationdrawer.EmployeeHomeActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
   private FancyButton signInButton;
   private MaterialEditText etEmail, etPassword;
   TextView tvSignup;
    private LinearLayout rootView;
    DatabaseHelper db;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //init the database objects
        db = new DatabaseHelper(this);

        //Building the progress dialog
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setView(R.layout.layout_loading_dialog);
        dialog = dialogBuilder.create();

        rootView = (LinearLayout) findViewById(R.id.login_layout);
        etEmail = (MaterialEditText) findViewById(R.id.etEmail);
        etPassword  = (MaterialEditText) findViewById(R.id.etPass);
       signInButton = (FancyButton) findViewById(R.id.email_sign_in_button);
        signInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
               //dialog.show();
            }
        });
        
    }

    /**
     * This methods logs in the employee
     */
    private void attemptLogin() {
        if(etEmail.getText().toString().isEmpty()) {
            etEmail.setError("You must fill in the phone number!");
            etEmail.requestFocus();
        }
        else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("You must fill in the password!");
            etPassword.requestFocus();
        }
        else {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            if (db.checkEmployee(email, password)){
                Intent loginIntent = new Intent(LoginActivity.this, EmployeeHomeActivity.class);
                loginIntent.putExtra("EMAIL", email);
                emptyEditText();
                startActivity(loginIntent);
                finish();
                CommonUtils.current_user_contact = email;
                dialog.dismiss();
            }
            else {
                dialog.dismiss();

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
        etEmail.setText(null);
        etPassword.setText(null);
    }


}

