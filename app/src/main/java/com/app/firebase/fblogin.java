package com.app.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class fblogin extends AppCompatActivity {
    public EditText emailId, password;
    TextView signup, signIn;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fblogin);
        mFirebaseAuth =FirebaseAuth.getInstance();
        emailId=findViewById(R.id.email);
        password=findViewById(R.id.password);
        signIn=findViewById(R.id.signin);
        signup=findViewById(R.id.signup);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null){
                    Toast.makeText(fblogin.this,"logged in", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(fblogin.this, home.class);
                    startActivity(i);
                }else{
                    Toast.makeText(fblogin.this,"Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

       signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter emailId");
                    emailId.requestFocus();
                }else if(pass.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }else if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(fblogin.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }else if(!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(fblogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(fblogin.this,"Login Error Occurred, Please Try again",Toast.LENGTH_SHORT).show();
                            }else{
                                Intent i =new Intent(fblogin.this, home.class);
                                startActivity(i);
                            }
                        }
                    });

                }else{
                    Toast.makeText(fblogin.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(fblogin.this, fbsignup.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    public void home(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}