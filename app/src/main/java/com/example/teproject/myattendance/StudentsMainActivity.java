package com.example.teproject.myattendance;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class StudentsMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int tsflag;
    String rollno;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle logindata = getIntent().getExtras();
        tsflag = logindata.getInt("tsflagdata");
        rollno = logindata.getString("logindata");
        Log.i("temp", String.valueOf(tsflag));

        Toolbar toolbar;

        if (tsflag == 0) {
            setContentView(R.layout.activity_students_main);
            toolbar = (Toolbar) findViewById(R.id.students_toolbar);
            setSupportActionBar(toolbar);
        } else {
            setContentView(R.layout.activity_teachers_main);
            toolbar = (Toolbar) findViewById(R.id.teachers_toolbar);
            setSupportActionBar(toolbar);
        }


        DrawerLayout drawer;
        if (tsflag == 0) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        } else {
            drawer = (DrawerLayout) findViewById(R.id.teachers_drawer_layout);
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView;
        if (tsflag == 0) {
            navigationView = (NavigationView) findViewById(R.id.students_nav_view);
        } else {
            navigationView = (NavigationView) findViewById(R.id.teachers_nav_view);
        }
        navigationView.setNavigationItemSelectedListener(this);

        bundle = new Bundle();
        bundle.putString("roll", rollno);
        bundle.putString("tsflag",Integer.toString(tsflag));

        android.support.v4.app.Fragment f = null;
        Bundle defbun = new Bundle();
        defbun.putString("rollno",rollno);
      //  defbun.putString("tsflag",Integer.toString(tsflag));
        if(tsflag==0) {
            f = new Default_page_frag();

            f.setArguments(defbun);

            FragmentTransaction fft = getSupportFragmentManager().beginTransaction();
            fft.replace(R.id.contents_frame,f);
            fft.commit();
        }



        View headerView = navigationView.getHeaderView(0);
        TextView emailtv = (TextView) headerView.findViewById(R.id.emailtv);
        final TextView nametv = (TextView) headerView.findViewById(R.id.nametv);


        final String[] name = {null};

        {

            emailtv.setText(rollno);

            String link;
            if (tsflag==0){ link="http://000attendance-system.000webhostapp.com/student_login/profile.php";}
            else{ link="http://000attendance-system.000webhostapp.com/teacher_login/Profile/getProfile.php";}

            class phpClass extends AsyncTask<String, String, String> {
                private final String link;
                JSONObject profilejson;


                phpClass(String url) {
                    link = url;
                }

                protected void onPostExecute(String res) {


                    try {
                        nametv.setText(profilejson.getString("name"));




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                protected String doInBackground(String[] params) {
                    try {
                        String data;
                        if (tsflag==0) {
                             data = URLEncoder.encode("rollno", "UTF-8") + "=" +
                                    URLEncoder.encode(rollno, "UTF-8");
                        }
                        else {
                            data = URLEncoder.encode("uid", "UTF-8") + "=" +
                                    URLEncoder.encode(rollno, "UTF-8");
                        }

                        URL url = new URL(link);
                        URLConnection conn = url.openConnection();

                        conn.setDoOutput(true);
                        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                        wr.write(data);
                        wr.flush();

                        BufferedReader reader = new BufferedReader(new
                                InputStreamReader(conn.getInputStream()));

                        StringBuilder sb = new StringBuilder();


                        String line = null;


                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                            break;
                        }
                        Log.i("phpTEST", "before assigning to json " + sb.toString());

                        profilejson = new JSONObject(sb.toString());

                        Log.i("phpTEST", profilejson.toString());
                        String string = profilejson.toString();
                        return (string);

                    } catch (Exception e) {
                        return (new String("EXCEPTION:" + e.getMessage()));
                    }
                }
            }
           if(isConnectedToInternet())
            {
                 phpClass p = new phpClass(link);
                 p.execute();
            }
            else
            {
               showAlert();

            }

        }



    }
    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }

   public void showAlert()
   {
       AlertDialog alertDialog = new AlertDialog.Builder(this).create();
       alertDialog.setTitle("Alert");
       alertDialog.setMessage("Please connect to internet!!!");
       alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
               new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                       getApplicationContext().startActivity(new Intent(Settings.ACTION_SETTINGS));
                   }
               });
       alertDialog.show();
   }

   public void internetErrorHandler()
   {
       if(!isConnectedToInternet())
       {
           showAlert();
       }
   }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer;
        if (tsflag == 0) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        } else {
            drawer = (DrawerLayout) findViewById(R.id.teachers_drawer_layout);
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        if (tsflag == 0)
            getMenuInflater().inflate(R.menu.students_main, menu);
        else if (tsflag == 1)
            getMenuInflater().inflate(R.menu.teachers_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        displaySelectedScreen(id);
        //noinspection SimplifiableIfStatement
      return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.


        int id = item.getItemId();
        //new InternetHandler(this.getBaseContext());
        internetErrorHandler();
        displaySelectedScreen(id);

        return true;
    }


    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_view_attendance:
                fragment = new viewAttFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_notices:
                fragment = new viewNoticesFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_timetable:
                fragment = new viewTTFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_profile:
                fragment = new viewProfileFragment();
                fragment.setArguments(bundle);
                break;

            case R.id.nav_mark_attendance:
                fragment = new markAttFragment();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_teachers_notices:
                fragment = new viewTeachNoticesFrag();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_teachers_send_notices:
                fragment = new SendNotification();
                fragment.setArguments(bundle);
                break;
            case R.id.nav_teachers_timetable:
                fragment = new ViewTTforTeachers();
                break;
            case R.id.nav_teachers_profile:
                fragment = new viewTeachProfileFrag();
                fragment.setArguments(bundle);
                break;

            case R.id.action_LogOut:
            case R.id.nav_logout:
                dbHandler dbh = new dbHandler(this, null, null, 1);
                dbh.deletedata();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;

            case R.id.action_changePassword:
                fragment = new ChangePassword();
                fragment.setArguments(bundle);
                break;


        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contents_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer;
        if (tsflag == 0) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        } else {
            drawer = (DrawerLayout) findViewById(R.id.teachers_drawer_layout);
        }
        drawer.closeDrawer(GravityCompat.START);

    }



}




