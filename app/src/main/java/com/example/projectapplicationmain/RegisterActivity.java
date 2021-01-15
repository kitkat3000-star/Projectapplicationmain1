package com.example.projectapplicationmain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends Activity {
    EditText regName;
    EditText regEmail;
    EditText regPass;
    EditText regPhoneNo;
    EditText regLicenseNo;
    Button Signin_Btn;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    FirebaseFirestore FirestoreNode = FirebaseFirestore.getInstance();
    String userID;
    DocumentReference dREF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        mAuth = FirebaseAuth.getInstance();

        regName = (EditText) findViewById(R.id.editTextName);
        regEmail = (EditText) findViewById(R.id.editTextloginemail);
        regPass = (EditText) findViewById(R.id.edittextloginPass);
        regPhoneNo = (EditText) findViewById(R.id.editTextPhone);
        regLicenseNo = (EditText) findViewById(R.id.editTextLicense);
        Signin_Btn = (Button) findViewById(R.id.SignInBtn);

        Signin_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String txt_email = regEmail.getText().toString();
                String txt_password = regPass.getText().toString();
                String REGISTER_name = regName.getText().toString();
                String REGISTER_phone_Num = regPhoneNo.getText().toString();
                String REGISTER_license_Num = regLicenseNo.getText().toString();
                mAuth.createUserWithEmailAndPassword(txt_email, txt_password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                    userID = mAuth.getCurrentUser().getUid();
                                    dREF = FirestoreNode.collection("Users").document(userID);
                                    Map<String, String> USERS = new HashMap<String, String>();
                                    USERS.put("email", txt_email);
                                    USERS.put("licensePlate", REGISTER_license_Num);
                                    USERS.put("name", REGISTER_name);
                                    USERS.put("phoneNumber", REGISTER_phone_Num);

                                    dREF.set(USERS);
                                    Intent i = new Intent(RegisterActivity.this, MainActivity4.class);
                                    startActivity(i);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Registration unsucessfull", Toast.LENGTH_SHORT).show();

                                }

                            }
                        });


            }
        });


    }
}