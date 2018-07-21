package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {
    EditText usrname, usrmail, usrpass, userpassretype;
    Button signupbutt;
    TextView logintxtvw;
    ProgressBar pgb;
    FirebaseAuth auth;



    private void Signup(String mail, String password)
    {
        if (!TextUtils.isEmpty(usrname.getText().toString().trim())
                &&!TextUtils.isEmpty( usrmail.getText().toString().trim())
                && !TextUtils.isEmpty(usrpass.getText().toString().trim())
                && usrpass.getText().toString().equals(userpassretype.getText().toString())
                && usrmail.getText().toString().contains("@stu.cu.edu.ng"))
        {

            usrname.setVisibility(View.GONE);
            pgb.setVisibility(View.VISIBLE);
            signupbutt.setVisibility(View.GONE);
            usrmail.setVisibility(View.GONE);
            usrpass.setVisibility(View.GONE);
            userpassretype.setVisibility(View.GONE);
            logintxtvw.setVisibility(View.GONE);
            auth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                // add the profile stuff here
                                usrname.setVisibility(View.VISIBLE);
                                pgb.setVisibility(View.GONE);
                                signupbutt.setVisibility(View.VISIBLE);
                                usrmail.setVisibility(View.VISIBLE);
                                usrpass.setVisibility(View.VISIBLE);
                                userpassretype.setVisibility(View.VISIBLE);
                                logintxtvw.setVisibility(View.VISIBLE);
                                auth.getCurrentUser().sendEmailVerification();
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(usrname.getText().toString().trim())
                                        .build();
                                auth.getCurrentUser().updateProfile(userProfileChangeRequest);

                                Intent gotologin = new Intent(Signup.this, MainActivity.class);
                                startActivity(gotologin);
                                finish();

                            }
                            else
                            {

                                usrname.setVisibility(View.VISIBLE);
                                signupbutt.setVisibility(View.VISIBLE);
                                pgb.setVisibility(View.GONE);
                                usrmail.setVisibility(View.VISIBLE);
                                usrpass.setVisibility(View.VISIBLE);
                                userpassretype.setVisibility(View.VISIBLE);
                                logintxtvw.setVisibility(View.VISIBLE);

                                Toast.makeText(Signup.this, "Sign Up Not Succesfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else
        {
            if (TextUtils.isEmpty(usrname.getText().toString().trim()))
            {
                usrname.setError("Required");
            }
            else if (TextUtils.isEmpty(usrmail.getText().toString().trim() ))
            {
                usrmail.setError("Required");

            }
            else if (!usrmail.getText().toString().contains("@stu.cu.edu.ng"))
            {
                usrmail.setError("Email Not Valid please use your CU mail");
            }
            else if (TextUtils.isEmpty(usrpass.getText().toString().trim()))
            {
                usrpass.setError("Required");

            }
            else if (!usrpass.getText().toString().equals(userpassretype.getText().toString()))
            {
                userpassretype.setError("Did not match password");
                usrpass.setError("Did not match password");


            }
            else
            {
                usrname.setError("Required");
                usrmail.setError("Required");
                usrpass.setError("Required");
                userpassretype.setError("Required");

            }
        }



    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();

        signupbutt = findViewById(R.id.sigupbutton);
        usrname = findViewById(R.id.signupusername);
        usrmail = findViewById(R.id.signupemail);
        usrpass = findViewById(R.id.signuppass);
        pgb = findViewById(R.id.pgb);
        userpassretype = findViewById(R.id.signuppassretype);
        logintxtvw = findViewById(R.id.logintxtvw);
        signupbutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signup(usrmail.getText().toString(), usrpass.getText().toString());

            }
        });
        logintxtvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this,Login_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        //signupbutt.Click += Signupbutt_Click;


    }

    }

