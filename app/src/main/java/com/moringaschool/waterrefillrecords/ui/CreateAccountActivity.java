package com.moringaschool.waterrefillrecords.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.moringaschool.waterrefillrecords.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.createUserButton) Button mCreateUserButton;
    @BindView(R.id.nameEditText) EditText mNameEditText;
    @BindView(R.id.shopNameEditText) EditText mShopNameEditText;
    @BindView(R.id.emailEditText) EditText mEmailEditText;
    @BindView(R.id.passwordEditText) EditText mPasswordEditText;
    @BindView(R.id.confirmPasswordEditText) EditText mConfirmPasswordEditText;
    @BindView(R.id.loginTextView) TextView mLoginTextView;

    public static final String TAG = CreateAccountActivity.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mLoginTextView.setOnClickListener(this);
        mCreateUserButton.setOnClickListener(this);
        createAuthStateListener();
    }

    @Override
    public void onClick(View v) {
        if(v == mLoginTextView){
            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        if (v == mCreateUserButton) {
            createNewUser();
        }
    }

    public void createNewUser(){
        final String name = mNameEditText.getText().toString().trim();
        final String shopName = mShopNameEditText.getText().toString().trim();
        final String email = mEmailEditText.getText().toString().trim();
        final String password = mPasswordEditText.getText().toString().trim();
        final String confirmPassword = mConfirmPasswordEditText.getText().toString().trim();
        boolean validEmail = isValidEmail(email);
        boolean validName = isValidName(name);
        boolean validShopName = isValidShopName(shopName);
        boolean validPassword = isValidPassword(password, confirmPassword);

        if (!validEmail || !validName || !validShopName || !validPassword) return;

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()){
                Log.d(TAG, "Authentication successful");
            }else {
                Toast.makeText(CreateAccountActivity.this,"Authentication failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isValidEmail(String email){
        boolean isGoodEmail = (email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        if(!isGoodEmail){
            mEmailEditText.setError("Please enter a valid email address");
            return false;
        }else {
            return isGoodEmail;
        }
    }

    public boolean isValidName(String name){
        if(name.equals("")){
            mNameEditText.setError("Please enter your name");
            return false;
        }else {
            return true;
        }
    }

    public boolean isValidShopName(String shopName){
        if(shopName.equals("")){
            mShopNameEditText.setError("Please enter your shop name");
            return false;
        }
        return true;
    }

    private boolean isValidPassword(String password, String confirmPassword){
        if(password.length() < 6){
            mPasswordEditText.setError("Please create a password containing at least 6 characters");
            return false;
        }else if(!password.equals(confirmPassword)){
            mPasswordEditText.setError("Passwords do not match!");
            return false;
        }else {
            return true;
        }
    }

    private void createAuthStateListener() {
        mAuthListener = firebaseAuth -> {
            final FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null){
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}