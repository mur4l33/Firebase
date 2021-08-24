package com.app.firebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class fbsignup extends AppCompatActivity {

    public EditText emailId, password;
    Button btnsignup;
    TextView tvsignUp;
    FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbsignup);
        mFirebaseAuth =FirebaseAuth.getInstance();
        emailId=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnsignup=findViewById(R.id.signup);
        tvsignUp=findViewById(R.id.textView2);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter emailid");
                    emailId.requestFocus();
                }else if(pass.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }else if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(fbsignup.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }else if(!(email.isEmpty() && pass.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(fbsignup.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(fbsignup.this,"Signup error, please Try Again",Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(new Intent(fbsignup.this,home.class));
                            }
                        }
                    });

                }else{
                    Toast.makeText(fbsignup.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(fbsignup.this, fblogin.class);
                startActivity(intent);
            }
        });

    }
    public void login(View view) {
        Intent intent =new  Intent(this,fblogin.class);
        startActivity(intent);
    }


}