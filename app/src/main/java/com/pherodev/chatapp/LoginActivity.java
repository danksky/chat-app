package com.pherodev.chatapp;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button loginButton;
    private ConstraintLayout registerConstraintLayout;
    private TextView createAccountButton;
    private TextView forgotPasswordButton;

    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.button_login_login);
        registerConstraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_register_fields);
        createAccountButton = (TextView) findViewById(R.id.text_view_login_create_account_button);
        forgotPasswordButton = (TextView) findViewById(R.id.text_view_login_forgot_password_button);

        usernameEditText = (EditText) findViewById(R.id.edit_text_login_username_field);
        firstNameEditText = (EditText) findViewById(R.id.edit_text_login_first_name_field);
        lastNameEditText = (EditText) findViewById(R.id.edit_text_login_last_name_field);
        emailEditText = (EditText) findViewById(R.id.edit_text_login_email_field);
        passwordEditText = (EditText) findViewById(R.id.edit_text_login_password_field);

        loginButton.setText(getText(R.string.login_activity_sign_in_label));
        loginButton.setOnClickListener(this);
        createAccountButton.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login_login:
                if (loginButton.getText().toString().equals(getText(R.string.login_activity_sign_in_label)))
                {}
                else
                    {}
                break;
            case R.id.text_view_login_create_account_button:
                convertUI();
                break;
            case R.id.text_view_login_forgot_password_button:
                Snackbar.make(v, getText(R.string.warning_feature_not_implemented).toString(), Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    private void convertUI() {
        if (createAccountButton.getText().toString().equals(getText(R.string.login_activity_create_account))) {
            loginButton.setText(getText(R.string.login_activity_register_label));
            createAccountButton.setText(R.string.login_activity_existing_account);
            registerConstraintLayout.setVisibility(View.VISIBLE);
        } else {
            loginButton.setText(getText(R.string.login_activity_sign_in_label));
            createAccountButton.setText(R.string.login_activity_create_account);
            registerConstraintLayout.setVisibility(View.GONE);
        }
    }

}
