package com.cuwordstudy.solomolaiye.wordstudyunit;

import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.cuwordstudy.solomolaiye.wordstudyunit.Class.Common;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    public static FirebaseAuth auth;
    public static String currname,currmail  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        currname = auth.getCurrentUser().getDisplayName();

        currmail = auth.getCurrentUser().getEmail();
        Common.currentToken = FirebaseInstanceId.getInstance().getToken();
        FirebaseMessaging.getInstance().subscribeToTopic("Meeting");

        if (auth.getCurrentUser().getEmail().equals("wordstudycu@gmail.com"))
        {
            FirebaseMessaging.getInstance().subscribeToTopic("Prayer");
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem topic = menu.findItem(R.id.newtopic);
        MenuItem announce = menu.findItem(R.id.newannouncement);
        MenuItem prpoint = menu.findItem(R.id.newprayerpoint);
        MenuItem viewPr = menu.findItem(R.id.menue_prayerrequest);
        MenuItem sendPr = menu.findItem(R.id.sendprayerrequest);

        if (auth.getCurrentUser().getEmail().equals("wordstudycu@gmail.com"))
        {
            topic.setVisible(true);
            announce.setVisible(true);
            prpoint.setVisible(true);
            viewPr.setVisible(true);
            sendPr.setVisible(false);
        }
        else
        {
            topic.setVisible(false);
            announce.setVisible(false);
            prpoint.setVisible(false);
            viewPr.setVisible(false);
            sendPr.setVisible(true);


        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.gotobible:
                Intent intent = new Intent(MainActivity.this,BooksActivity.class);
                startActivity(intent);
                //  StartActivity(typeof(BooksActivity));
                break;

            case R.id.meeting:
                Intent intent0 = new Intent(MainActivity.this,Discussion.class);
                startActivity(intent0);
                //  StartActivity(typeof(BooksActivity));
                break;
            case R.id.menu_notepad:
                Intent intent8 = new Intent(MainActivity.this,Notepadactivity.class);
                startActivity(intent8);
                //  StartActivity(typeof(BooksActivity));
                break;
            case R.id.menue_prayerrequest:
                Intent intent7 = new Intent(MainActivity.this,PrayerRequest.class);
                startActivity(intent7);
                //  StartActivity(typeof(BooksActivity));
                break;
            case R.id.signout:
                auth.signOut();
                FirebaseMessaging.getInstance().unsubscribeFromTopic("Prayer");
                FirebaseMessaging.getInstance().unsubscribeFromTopic("Meeting");
                Intent intent1 = new Intent(MainActivity.this,Login_Activity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.sendprayerrequest:
                Intent intent2 = new Intent(MainActivity.this,SendRequestActivity.class);
                startActivity(intent2);
                // StartActivity(typeof(SendRequestActivity));
                break;
            case R.id.newtopic:
                 Intent intent3 = new Intent(MainActivity.this,NewTopicActivity.class);
                startActivity(intent3);
                //  StartActivity(typeof(NewTopicActivity));
                break;
            case R.id.newprayerpoint:
                   Intent intent4 = new Intent(MainActivity.this,NewPrayerPointActivity.class);
                 startActivity(intent4);
                //   StartActivity(typeof(NewPrayerPointActivity));
                break;
            case R.id.newannouncement:
                 Intent intent5 = new Intent(MainActivity.this,NewAnnouncementActivity.class);
                startActivity(intent5);
                //      StartActivity(typeof(NewAnnouncementActivity));
                break;
            case R.id.changePassword:
                 Intent intent6 = new Intent(MainActivity.this,changepassword.class);
                startActivity(intent6);
                break;


        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
 /*   public static class PlaceholderFragment */

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.

            return 5;
        }
    }
}
