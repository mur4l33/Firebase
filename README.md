Firebase Android Studio:

=>signup wih Firebase and go to console 
=>create a google-service.json file with your proper packagename 
=>download and keep your google-service.json in app folder of your app



1. Adding in build.gradle (Module):

	Add dependencies :
	implementation 'com.google.firebase:firebase-auth:11.8.0'
	implementation platform('com.google.firebase:firebase-bom:28.2.1')
	implementation 'com.google.firebase:firebase-analytics'

	Add plugin :
	plugins {
		id 'com.android.application'
	}


2. Create text boxes for username and password, Button for signup and Login:

Signup validation 
	1.Check whether text boxes are not empty 
		==>get string :
			String email=emailId.getText().toString();
			String pass=password.getText().toString();
			
		==>validate the string :
		
			if(email.isEmpty()){
				emailId.setError("Please enter emailid");
				emailId.requestFocus();
			}else if(pass.isEmpty()){
				password.setError("Please enter password");
				password.requestFocus();
			}else if(email.isEmpty() && pass.isEmpty()){
				Toast.makeText(act3.this,"Fields are empty",Toast.LENGTH_SHORT).show();
			}else if(!(email.isEmpty() && pass.isEmpty())){
			
		==>==>using firebase Auth createUserWithEmailAndPassword and addOnCompleteListener
		
				mFirebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(act3.this, new OnCompleteListener<AuthResult>() {
					@Override
					public void onComplete(Task<AuthResult> task) {
						if(!task.isSuccessful()){
							Toast.makeText(act3.this,"Signup error, please Try Again",Toast.LENGTH_SHORT).show();
						}else{
							startActivity(new Intent(act3.this,homeActivity.class));
						}
					}
				});

			}else{
				Toast.makeText(act3.this,"Error Occurred",Toast.LENGTH_SHORT).show();
			}
		}
			


2. For Login 
	declare mAuthStateListener Object
	
	private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fblogin);
        mFirebaseAuth =FirebaseAuth.getInstance();
        emailId=findViewById(R.id.email);
        password=findViewById(R.id.password);
        btnsignIn=findViewById(R.id.signin);
        tvsignIn=findViewById(R.id.textView2);
		
		
	//===>creating a Firebase Auth listener 
		
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
		// check for session using onAuthStateChanged 
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser=mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser!=null){
                    Toast.makeText(fblogin.this,"logged in", Toast.LENGTH_SHORT).show();
                    Intent i =new Intent(fblogin.this, homeActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(fblogin.this,"Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
		
		// signin Listener
		
        btnsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailId.getText().toString();
                String pass=password.getText().toString();
		//validating listener
                if(email.isEmpty()){
                    emailId.setError("Please enter emailid");
                    emailId.requestFocus();
                }else if(pass.isEmpty()){
                    password.setError("Please enter password");
                    password.requestFocus();
                }else if(email.isEmpty() && pass.isEmpty()){
                    Toast.makeText(fblogin.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }else if(!(email.isEmpty() && pass.isEmpty())){
		//signin with username and password
                    mFirebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(fblogin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(fblogin.this,"Login Error Occurred, Please Try again",Toast.LENGTH_SHORT).show();
                            }else{
                                Intent i =new Intent(fblogin.this, homeActivity.class);
                                startActivity(i);
                            }
                        }
                    });

                }else{
                    Toast.makeText(fblogin.this,"Error Occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(fblogin.this, fb.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }


Logout Script:
in some home page
signout=findViewById(R.id.signout);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i=new Intent(home.this, fblogin.class);
                startActivity(i);
            }
        });
