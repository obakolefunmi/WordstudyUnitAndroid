package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import android.widget.ListView;
import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Profile;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.topic;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Signup extends AppCompatActivity {
    EditText usrname, usrmail, usrpass, userpassretype, firstname,lastname,phone_number,room_number;
    Spinner genderspinner, hallspinner;
    Button signupbutt;
    TextView logintxtvw;
    ProgressBar pgb;
    FirebaseAuth auth;
    String [] genders = {"Male","Female"};
    String [] malehalls = {"Peter Hall", "Paul Hall", "John Hall", "Joseph Hall", "Daniel Hall"};
    String [] femalehalls = {"Esther Hall", "Mary Hall", "Deborah Hall", "Lydia Hall", "Dorcas Hall "};

    String gender,hall;
    ArrayAdapter<String> genderAdapter,HallAdapter;

    private void Signup(String mail, String password)
    {
        if (!TextUtils.isEmpty(usrname.getText().toString().trim())
                &&!TextUtils.isEmpty( firstname.getText().toString().trim())
                &&!TextUtils.isEmpty( lastname.getText().toString().trim())
                &&!TextUtils.isEmpty( usrmail.getText().toString().trim())
                &&!TextUtils.isEmpty( phone_number.getText().toString().trim())
                &&!TextUtils.isEmpty( room_number.getText().toString().trim())
                && !TextUtils.isEmpty(usrpass.getText().toString().trim())
                && usrpass.getText().toString().equals(userpassretype.getText().toString())
                && usrmail.getText().toString().contains("@stu.cu.edu.ng")){

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
                                auth.getCurrentUser().sendEmailVerification();
                                UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(firstname.getText().toString().trim()+" "+ lastname.getText().toString().trim())
                                        .build();
                                auth.getCurrentUser().updateProfile(userProfileChangeRequest);
                                //ADD the profile to the db;
                                new SendNewProfile(firstname.getText().toString().trim(),lastname.getText().toString().trim(),usrmail.getText().toString().trim(),
                                        phone_number.getText().toString().trim(),hall,room_number.getText().toString().trim(),gender,Signup.this).execute(Common.getAddresApiProfile());

                                usrname.setVisibility(View.VISIBLE);
                                pgb.setVisibility(View.GONE);
                                signupbutt.setVisibility(View.VISIBLE);
                                usrmail.setVisibility(View.VISIBLE);
                                usrpass.setVisibility(View.VISIBLE);
                                userpassretype.setVisibility(View.VISIBLE);
                                logintxtvw.setVisibility(View.VISIBLE);

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
        {   if (TextUtils.isEmpty(firstname.getText().toString().trim() ))
            {
                firstname.setError("Required");
                firstname.requestFocus();

            }
            else if (TextUtils.isEmpty(lastname.getText().toString().trim() ))
            {
                lastname.setError("Required");
                lastname.requestFocus();

            }
            else if (TextUtils.isEmpty(usrname.getText().toString().trim()))
            {
                usrname.setError("Required");
                usrname.requestFocus();
            }
            else if (TextUtils.isEmpty(phone_number.getText().toString().trim()))
            {
                phone_number.setError("Required");
                phone_number.requestFocus();
            }
            else if (TextUtils.isEmpty(room_number.getText().toString().trim()))
            {
            room_number.setError("Required");
            room_number.requestFocus();
            }

                else if (TextUtils.isEmpty(usrmail.getText().toString().trim() ))
            {
                usrmail.setError("Required");
                usrmail.requestFocus();

            }
            else if (!usrmail.getText().toString().contains("@stu.cu.edu.ng"))
            {
                usrmail.setError("Email Not Valid please use your CU mail");
                usrmail.requestFocus();
            }
            else if (TextUtils.isEmpty(usrpass.getText().toString().trim()))
            {
                usrpass.setError("Required");
                usrpass.requestFocus();

            }
            else if (!usrpass.getText().toString().equals(userpassretype.getText().toString()))
            {
                userpassretype.setError("Did not match password");
                usrpass.setError("Did not match password");
                userpassretype.requestFocus();


            }
            else
            {   firstname.setError("Required");
                lastname.setError("Required");
                usrname.setError("Required");
                phone_number.setError("Required");
                room_number.setError("Required");
                usrmail.setError("Required");
                usrpass.setError("Required");
                userpassretype.setError("Required");

            }
        }



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
        firstname =  findViewById(R.id.signupfirstname);
        lastname =  findViewById(R.id.signuplastname);
        genderspinner = findViewById(R.id.signupspinnergender);
        hallspinner= findViewById(R.id.signupspinnerhall);
        phone_number=findViewById(R.id.signupphone);
        room_number = findViewById(R.id.signuproomnumber);
        pgb = findViewById(R.id.pgb);
        userpassretype = findViewById(R.id.signuppassretype);
        logintxtvw = findViewById(R.id.logintxtvw);
        genderAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,genders);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspinner.setAdapter(genderAdapter);
        genderspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = genders[i];
                if (gender.equals("Male"))
                {
                    HallAdapter = new ArrayAdapter<String>(Signup.this,android.R.layout.simple_spinner_item,malehalls);
                    HallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    hallspinner.setAdapter(HallAdapter);
                }
                else
                    {
                        HallAdapter = new ArrayAdapter<String>(Signup.this,android.R.layout.simple_spinner_item,femalehalls);
                        HallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        hallspinner.setAdapter(HallAdapter);
                    }
              }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        hallspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (gender.equals("Male"))
                {
                   hall =malehalls[i];
                }
                else
                {
                  hall = femalehalls[i];
                }
                Toast.makeText(Signup.this,"You are in "+hall,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
    private class SendNewProfile extends AsyncTask<String, Void, String>
    {
         String fitst_name;
         String last_name;
         String user_email;
         String user_phone_number;
         String hall_of_residence;
         String room_number;
         String user_gender;

        Signup activity = new Signup();
        public SendNewProfile( String fitst_name, String last_name, String user_email, String user_phone_number, String hall_of_residence, String room_number, String user_gender ,Signup activity )
        {
            this.fitst_name  = fitst_name;
            this.last_name = last_name;
            this.user_email=user_email;
            this.user_phone_number=user_phone_number;
            this.hall_of_residence=hall_of_residence;
            this.room_number=room_number;
            this.user_gender=user_gender;

            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // activity.newtopicpgb.setVisibility(View.VISIBLE);
            //activity.newtopicHolder.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
          //  activity.newtopicpgb.setVisibility(View.GONE);
            //activity.newtopicHolder.setVisibility(View.VISIBLE);
            //Intent intent = new Intent(activity,MainActivity.class);
           // activity.startActivity(intent);
               }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            Profile profile = new Profile();
            profile.fitst_name  = fitst_name;
            profile.last_name = last_name;
            profile.user_email=user_email;
            profile.user_phone_number=user_phone_number;
            profile.hall_of_residence=hall_of_residence;
            profile.room_number=room_number;
            profile.user_gender=user_gender;

            String json = new Gson().toJson(profile);
            http.PostHttpData(url, json);
            return "";
        }





    }

}

