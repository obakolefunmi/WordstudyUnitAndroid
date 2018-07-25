package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cuwordstudy.solomolaiye.wordstudyunit.Adapters.MembersAdapter;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Profile;
import com.cuwordstudy.solomolaiye.wordstudyunit.Helpers.HttpDataHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditProfile_Activity extends AppCompatActivity {

    TextView firstname,lastname,usermail,gender,editprofilepull;
    EditText room_number,phone_number;
    Spinner Hall_spinnes;
    ScrollView editprofile_holder;
    List <Profile>profiles =  new ArrayList<>();
    Profile profileselected ;
    ArrayAdapter<String> HallAdapter;
    ProgressBar editprofilepgb;
    String [] malehalls = {"Peter Hall", "Paul Hall", "John Hall", "Joseph Hall", "Daniel Hall"};
    String [] femalehalls = {"Esther Hall", "Mary Hall", "Deborah Hall", "Lydia Hall", "Dorcas Hall "};

    String persongender,hall,oid;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_item_send: {
                if (TextUtils.isEmpty(phone_number.getText().toString().trim())||TextUtils.isEmpty(room_number.getText().toString().trim())) {
                    phone_number.setError("Required");
                    phone_number.setError("Required");

                } else {
                    //edit the profile.
                    new EditProfileData(hall,room_number.getText().toString().trim(), phone_number.getText().toString().trim(),EditProfile_Activity.this,profileselected).execute(Common.getAddressSingleProfile(profileselected));
                Intent intent = new Intent(EditProfile_Activity.this,MainActivity.class);
                startActivity(intent);
                finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_);
        firstname = findViewById(R.id.editfirstname);
        lastname= findViewById(R.id.editlastname);
        usermail= findViewById(R.id.editmail);
        gender= findViewById(R.id.editgender);
        editprofilepull= findViewById(R.id.editprofilepull);
        room_number = findViewById(R.id.editroomnumber);
        phone_number =  findViewById(R.id.editphone);
        Hall_spinnes = findViewById(R.id.editspinnerhall);
        editprofile_holder = findViewById(R.id.editprofil_holder);
        editprofilepgb = findViewById(R.id.edit_profilepgb);

        String themail ="\""+MainActivity.currmail+"\"";
        new GetmemDataSingle(EditProfile_Activity.this).execute(Common.getAddresApiSpecificProfile(themail));

        Hall_spinnes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (persongender.equals("Male"))
                {
                    hall =malehalls[i];
                }
                else
                {
                    hall = femalehalls[i];
                }
                Toast.makeText(EditProfile_Activity.this,"You are in "+hall,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public class GetmemDataSingle extends AsyncTask<String, Void, String> {
        private EditProfile_Activity activity;

        public GetmemDataSingle(EditProfile_Activity activity) {
            this.activity = activity;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.editprofilepgb.setVisibility(View.VISIBLE);
            activity.editprofile_holder.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!=null){
                try {
                    Type listtype = new TypeToken<List<Profile>>(){}.getType();
                    activity.profiles = new Gson().fromJson(s, listtype);
                    activity.profileselected = activity.profiles.get(0);
                    activity.firstname.setText(activity.profiles.get(0).fitst_name);
                    activity.lastname.setText(activity.profiles.get(0).last_name);
                    activity.usermail.setText(activity.profiles.get(0).user_email);
                    activity.gender.setText(activity.profiles.get(0).user_gender);
                    activity.persongender = activity.profiles.get(0).user_gender;
                    activity.room_number.setText(activity.profiles.get(0).room_number);
                    activity.phone_number.setText(activity.profiles.get(0).user_phone_number);
                    activity.oid = profiles.get(0)._id.getOid();
                    if (persongender.equals("Male"))
                    {
                        HallAdapter = new ArrayAdapter<String>(EditProfile_Activity.this,android.R.layout.simple_spinner_item,malehalls);
                        HallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Hall_spinnes.setAdapter(HallAdapter);
                    }
                    else
                    {
                        HallAdapter = new ArrayAdapter<String>(EditProfile_Activity.this,android.R.layout.simple_spinner_item,femalehalls);
                        HallAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        Hall_spinnes.setAdapter(HallAdapter);
                    }

                    activity.editprofilepgb.setVisibility(View.GONE) ;
                    activity.editprofilepull.setVisibility(View.GONE) ;
                    activity.editprofile_holder.setVisibility(View.VISIBLE) ;

                } catch (Exception ex) {
                    activity.editprofilepull.setVisibility(View.VISIBLE) ;
                }}else
                activity.editprofilepull.setVisibility(View.VISIBLE) ;


        }

        @Override
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            stream = http.GetHttpData(urlString);
            return stream;        }

    }
    public class EditProfileData extends AsyncTask<String, java.lang.Void, String> {
        String newHall = "",newroom="",newphone = "";

        EditProfile_Activity activity = new EditProfile_Activity();
        Profile profileselected = null;

        public EditProfileData(String newHall, String newroom, String newphone, EditProfile_Activity activity, Profile profileselected) {
            this.newHall = newHall;
            this.newroom = newroom;
            this.newphone = newphone;
            this.activity = activity;
            this.profileselected = profileselected;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            activity.editprofilepgb.setVisibility(View.VISIBLE) ;
            activity.editprofile_holder.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            activity.editprofilepgb.setVisibility(View.GONE);
            activity.editprofile_holder.setVisibility(View.VISIBLE);
            Toast.makeText(EditProfile_Activity.this ,"Your profile has been updated",Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String url = strings[0];
            HttpDataHandler http = new HttpDataHandler();
            profileselected.hall_of_residence = newHall;
            profileselected.room_number = newroom;
            profileselected.user_phone_number = newphone;
            String json = new Gson().toJson(profileselected);
            http.PutHttpData(url, json);
            return "";        }


    }

}
