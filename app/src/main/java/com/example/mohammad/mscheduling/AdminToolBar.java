package com.example.mohammad.mscheduling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class AdminToolBar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        AddNewLab.OnFragmentInteractionListener,
        EditLab.OnFragmentInteractionListener,
        LabsList.OnFragmentInteractionListener,
        StudentLecturerList.OnFragmentInteractionListener,
        StudentLecDetails.OnFragmentInteractionListener,
        StudentProfile.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_tool_bar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        final SharedPreferences prefs = this.getSharedPreferences(
                "pref", Context.MODE_PRIVATE);
        TextView tName=(TextView) header.findViewById(R.id.textView2);
        TextView tEmail=(TextView) header.findViewById(R.id.textView);
        tName.setText(prefs.getString("name","name").toUpperCase());
        tEmail.setText(prefs.getString("username","name"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = null;
        fragmentClass = LabsList.class;
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        final int commit = fragmentManager.beginTransaction().replace(R.id.navmain, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.admin_tool_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        android.support.v4.app.Fragment fragment = null;
        Class fragmentClass = null;


        if (id == R.id.nav_AddLab) {
            fragmentClass = AddNewLab.class;
        } else if (id == R.id.nav_EditLab) {
            fragmentClass = LabsList.class;
        } else if (id == R.id.nav_LecList) {
            fragmentClass = StudentLecturerList.class;
        } else if (id == R.id.nav_pro) {
            fragmentClass=StudentProfile.class;
        } else if (id == R.id.nav_so) {
            this.finish();
            return true;
        }
        else if(id==R.id.nav_chat)
        {
            Intent adIntent = new Intent(this, StudentChatList.class);
            this.startActivity(adIntent);
            return true;
        }

        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        final int commit = fragmentManager.beginTransaction().replace(R.id.navmain, fragment).commit();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
