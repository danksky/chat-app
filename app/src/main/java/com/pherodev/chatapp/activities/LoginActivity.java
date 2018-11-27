package com.pherodev.chatapp.activities;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pherodev.chatapp.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = "Login";

    // Firebase
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    // Views and ViewGroups
    private TextView usernameLabelTextView;
    private Button loginButton;
    private ConstraintLayout registerConstraintLayout;
    private TextView createAccountButton;
    private TextView forgotPasswordButton;

    private EditText usernameEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;

    private ConstraintLayout responseConstraintLayout;
    private TextView responseStatusTextView;
    private TextView responseDetailsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        // Initialize Views and ViewGroups
        usernameLabelTextView = (TextView) findViewById(R.id.text_view_login_username_label);
        loginButton = (Button) findViewById(R.id.button_login_login);
        registerConstraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_register_fields);
        createAccountButton = (TextView) findViewById(R.id.text_view_login_create_account_button);
        forgotPasswordButton = (TextView) findViewById(R.id.text_view_login_forgot_password_button);

        usernameEditText = (EditText) findViewById(R.id.edit_text_login_username_field);
        firstNameEditText = (EditText) findViewById(R.id.edit_text_login_first_name_field);
        lastNameEditText = (EditText) findViewById(R.id.edit_text_login_last_name_field);
        emailEditText = (EditText) findViewById(R.id.edit_text_login_email_field);
        passwordEditText = (EditText) findViewById(R.id.edit_text_login_password_field);

        responseConstraintLayout = (ConstraintLayout) findViewById(R.id.constraint_layout_login_response);
        responseStatusTextView = (TextView) findViewById(R.id.text_view_login_response_status);
        responseDetailsTextView = (TextView) findViewById(R.id.text_view_login_response_details);

        // Set OnClickListeners
        loginButton.setText(getText(R.string.login_activity_sign_in_label));
        loginButton.setOnClickListener(this);
        createAccountButton.setOnClickListener(this);
        forgotPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login_login:
                if (loginButton.getText().toString().equals(getText(R.string.login_activity_sign_in_label))) {
                    if (validateLogin(
                            usernameEditText.getText().toString(),
                            passwordEditText.getText().toString()))
                    {
                        firebaseLogin(usernameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                    }

                } else {
                    if (validateRegistration(
                            usernameEditText.getText().toString(),
                            emailEditText.getText().toString(),
                            lastNameEditText.getText().toString(),
                            firstNameEditText.getText().toString(),
                            passwordEditText.getText().toString()))
                    {
                        // TODO: Actually, first verify that username doesn't already exist.
                        // Note: Firebase kind of already does this for email / password.
                        // I just need to write my own preliminary check.
                        firebaseRegisterUser(usernameEditText.getText().toString(),
                                emailEditText.getText().toString(),
                                firstNameEditText.getText().toString(),
                                lastNameEditText.getText().toString(),
                                passwordEditText.getText().toString());
                    }
                }
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
            usernameLabelTextView.setText(getText(R.string.login_activity_register_username_label));
            loginButton.setText(getText(R.string.login_activity_register_label));
            createAccountButton.setText(R.string.login_activity_existing_account);
            registerConstraintLayout.setVisibility(View.VISIBLE);
            responseConstraintLayout.setVisibility(View.GONE);
        } else {
            usernameLabelTextView.setText(getText(R.string.login_activity_login_username_label));
            loginButton.setText(getText(R.string.login_activity_sign_in_label));
            createAccountButton.setText(R.string.login_activity_create_account);
            registerConstraintLayout.setVisibility(View.GONE);
            responseConstraintLayout.setVisibility(View.GONE);
        }
    }

    private boolean validateLogin(String username, String password) {
        // TODO: Make sure that if you put in the optional first or last name, both fields are there
        boolean valid = true;

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Required.");
            valid = false;
        } else {
            usernameEditText.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }

    private boolean validateRegistration(String username, String email, String first, String last, String password) {
        boolean valid = true;

        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Required.");
            valid = false;
        } else {
            usernameEditText.setError(null);
        }

        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Required.");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        if (!TextUtils.isEmpty(first) && TextUtils.isEmpty(last)) {
            lastNameEditText.setError("Cannot have first without last.");
            valid = false;
        } else {
            lastNameEditText.setError(null);
        }

        if (!TextUtils.isEmpty(last) && TextUtils.isEmpty(first)) {
            firstNameEditText.setError("Cannot have last without first.");
            valid = false;
        } else {
            firstNameEditText.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required.");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        return valid;
    }

    private void firebaseLogin(String usernameOrEmail, final String password) {
        if (usernameOrEmail.contains("@")) {
            // email
            firebaseEmailLogin(usernameOrEmail, password);
            return;
        }
        OnCompleteListener<DocumentSnapshot> fetchEmailListener = new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "fetchEmail:success");
                    firebaseEmailLogin(task.getResult().get("email").toString(), password);
                } else {
                    Log.e(TAG, "fetchEmail:" + task.getException());
                }
            }
        };
        firebaseFirestore.collection("usernames").document(usernameOrEmail).get().addOnCompleteListener(fetchEmailListener);
    }

    private void firebaseEmailLogin(String email, String password) {
        Log.d(TAG, "firebaseEmailLogin:" + email + " " + password);
        OnCompleteListener<AuthResult> passwordLoginListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    updateResponseUI(true, "firebaseEmailLogin", "Login success.", task.getResult().getUser().getEmail());
                } else {
                    updateResponseUI(false, "firebaseEmailLogin", "Login failed.", task.getException().getMessage());
                }
            }
        };
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(passwordLoginListener);
    }

    // TODO: Check that the username doesn't exist first.
    private void firebaseRegisterUser(final String username, final String email, final String first, final String last, final String password) {
        OnCompleteListener<DocumentSnapshot> registerListener = new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (!task.getResult().exists()) {
                        updateResponseUI(true, "fetchUsername", "Username is unique", null);
                        firebaseRegisterUnique(username, email, first, last, password);
                    } else {
                        updateResponseUI(false, "fetchUsername", "Username already exists.", username + " exists as " + task.getResult().getString("email"));
                    }
                } else {
                    Log.e(TAG, "fetchUsername:" + task.getException());
                    updateResponseUI(false, "fetchUsername", "Unable to preform username fetch.", task.getException().getMessage());
                }
            }
        };
        firebaseFirestore.collection("usernames").document(username).get().addOnCompleteListener(registerListener);
    }

    private void firebaseRegisterUnique(String username, String email, String first, String last, String password) {
        Log.d(TAG, email + " " + password);
        OnCompleteListener<AuthResult> registerListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    updateResponseUI(true, "firebaseRegister", "Successfully registered new user.", task.getResult().getUser().getEmail());
                    // update the username document with: username > email, userId
                    // update the users document with all person's details
                    // update UI
                } else {
                    updateResponseUI(true, "firebaseRegister", "Registration failed.", task.getException().getMessage());
                    // do not proceed, obviously
                    // update UI
                }
            }
        };
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(registerListener);
    }

    private void updateResponseUI(String methodName, @NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Log.d(TAG, methodName + ":success");
            responseConstraintLayout.setVisibility(View.VISIBLE);
            responseConstraintLayout.setBackgroundTintList(getResources().getColorStateList(R.color.color_status_good));
            responseStatusTextView.setText("User " +  task.getResult().getUser().getEmail() +
                    " is verified: " + task.getResult().getUser().isEmailVerified());
            responseDetailsTextView.setText(task.getResult().getUser().getUid());
        } else {
            Log.e(TAG, methodName + ":" + task.getException());
            responseConstraintLayout.setVisibility(View.VISIBLE);
            responseConstraintLayout.setBackgroundTintList(getResources().getColorStateList(R.color.color_status_bad));
            responseStatusTextView.setText(task.getException().getMessage());
            responseDetailsTextView.setText(null);
        }
    }
    private void updateResponseUI(boolean success, String methodName, String status, String details) {
        if (success) {
            Log.d(TAG, methodName + ":success (" + status + ")");
            responseConstraintLayout.setVisibility(View.VISIBLE);
            responseConstraintLayout.setBackgroundTintList(getResources().getColorStateList(R.color.color_status_good));
            responseStatusTextView.setText(status);
            responseDetailsTextView.setText(details);
        } else {
            Log.e(TAG, methodName + ":" + status);
            responseConstraintLayout.setVisibility(View.VISIBLE);
            responseConstraintLayout.setBackgroundTintList(getResources().getColorStateList(R.color.color_status_bad));
            responseStatusTextView.setText(status);
            responseDetailsTextView.setText(details);
        }
    }
}
