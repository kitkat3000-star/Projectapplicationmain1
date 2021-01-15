package com.example.projectapplicationmain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity {
    EditText LOG_username, LOG_password;
    Button SigninBtn;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        LOG_username = (EditText) findViewById(R.id.editTextloginemail);
        LOG_password = (EditText) findViewById(R.id.edittextloginPass);
        SigninBtn = (Button) findViewById(R.id.SignInBtn);
        mAuth = FirebaseAuth.getInstance();


        SigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEnteredUsername = LOG_username.getText().toString();
                String userEnteredPassword = LOG_password.getText().toString();

                mAuth.signInWithEmailAndPassword(userEnteredUsername,userEnteredPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(LoginActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                     //   FirebaseUser currentUser = mAuth.getCurrentUser();
                        Intent i = new Intent(LoginActivity.this, MainActivity4.class);
                        startActivity(i);
                        finish();


                    }
                });

            }
        });
    }



}