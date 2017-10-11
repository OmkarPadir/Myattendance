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
/** GIT Trial After Edit**/

/** after creating trial branch*/

/*after second edit*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle logindata = getIntent().getExtras();
        tsflag = logindata.getInt("tsflagdata");
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

       /*Omkars 27/08/17 */


        View headerView = navigationView.getHeaderView(0);
        TextView emailtv = (TextView) headerView.findViewById(R.id.emailtv);
        final TextView nametv = (TextView) headerView.findViewById(R.id.nametv);

        rollno = logindata.getString("logindata");


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

        bundle = new Bundle();
        bundle.putString("roll", rollno);
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

        displaySelectedScreen(id);

        /*

        if (tsflag == 0) {
            if (id == R.id.nav_view_attendance) {

                internetErrorHandler();
                viewAttFragment attFragment = (viewAttFragment)getSupportFragmentManager().findFragmentById(R.id.viewAttfragment);
                attFragment.viewAttendance(rollno);
                makeAttendanceLayoutVisible();

              // viewAtt();
            } else if (id == R.id.nav_notices) {
                internetErrorHandler();
                makeNoticesVisible();
                viewNoticesFragment noticesFragment = (viewNoticesFragment) getSupportFragmentManager().findFragmentById(R.id.viewNoticesFragment);
                noticesFragment.showNotices(rollno);
            } else if (id == R.id.nav_timetable) {
                internetErrorHandler();
                makeTimetableVisible();
                viewTTFragment TTfragment = (viewTTFragment) getSupportFragmentManager().findFragmentById(R.id.viewTTfragment);
                TTfragment.showTimeTable(rollno);

            } else if (id == R.id.nav_profile) {
                internetErrorHandler();
                makeProfileLayoutVisible();
                viewProfileFragment viewProfileFragment =(viewProfileFragment)getSupportFragmentManager().findFragmentById(R.id.viewprofileFragment);
                viewProfileFragment.showProfile(rollno);

            } else if (id == R.id.nav_logout) {
                dbHandler dbh = new dbHandler(this, null, null, 1);
                dbh.deletedata();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        } else {
            if (id == R.id.nav_mark_attendance) {


                internetErrorHandler();

              //  makeMarkAttVisible();

                markAttFragment markAttFragment=(markAttFragment)getSupportFragmentManager().findFragmentById(R.id.markAttfrag);
                markAttFragment.markAttendance(rollno);

               // markAttendance();

            } else if (id == R.id.nav_teachers_notices) {

                internetErrorHandler();

                maketeachNoticesVisible();
                viewTeachNoticesFrag noticesFragment= (viewTeachNoticesFrag) getSupportFragmentManager().findFragmentById(R.id.teachNoticesfragment);
                noticesFragment.showNotices(rollno);



            } else if (id == R.id.nav_teachers_timetable) {
                internetErrorHandler();
                makeTeachTTVisible();


            }
            else if (id == R.id.nav_teachers_send_notices) {
                internetErrorHandler();
                makeSendNoticesVisible();

               // viewTeachNoticesFrag sendNoticesFrag= (viewTeachNoticesFrag) getSupportFragmentManager().findFragmentById(R.id.sendNotifFrag);



            }else if (id == R.id.nav_teachers_profile) {
                maketeachProfileVisible();
                internetErrorHandler();
                viewTeachProfileFrag frag= (viewTeachProfileFrag) getSupportFragmentManager().findFragmentById(R.id.teachProfFrag);
                frag.showteachProfile(rollno);



            } else if (id == R.id.nav_logout) {
                dbHandler dbh = new dbHandler(this, null, null, 1);
                dbh.deletedata();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
            }
        }
        DrawerLayout drawer;
        if (tsflag == 0) {
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        } else {
            drawer = (DrawerLayout) findViewById(R.id.teachers_drawer_layout);
        }
        drawer.closeDrawer(GravityCompat.START);

        */
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
            case R.id.nav_teachers_profile:
                fragment = new viewTeachProfileFrag();
                fragment.setArguments(bundle);
                break;


            case R.id.nav_logout:
                dbHandler dbh = new dbHandler(this, null, null, 1);
                dbh.deletedata();
                Intent i = new Intent(this, LoginActivity.class);
                startActivity(i);
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
        //return true;
    }

    /////////////////////////  Layouts Visible Invisible for Students side /////////////////////////
  /*  private void loadNotices() {
        makeNoticesVisible();
    }

    private void makeNoticesVisible() {
        makeAttendanceLayoutInvisible();
        makeProfileLayoutInvisible();
        makeProgressbarInvisible();
        makeTimetableInvisible();

        ConstraintLayout NoticesLayout = (ConstraintLayout)findViewById(R.id.NoticesLayout);
        NoticesLayout.setVisibility(View.VISIBLE);
    }

    private void makeNoticesInvisible()
    {
        ConstraintLayout NoticesLayout = (ConstraintLayout)findViewById(R.id.NoticesLayout);
        NoticesLayout.setVisibility(View.INVISIBLE);
    }



    public void makeTimetableVisible()
    {
        makeAttendanceLayoutInvisible();
        makeProfileLayoutInvisible();
        makeProgressbarInvisible();
        makeNoticesInvisible();


        RelativeLayout TimetaleLayout =(RelativeLayout)findViewById(R.id.TimeTableLayout);
        TimetaleLayout.setVisibility(View.VISIBLE);
    }

    public void makeTimetableInvisible()
    {
        RelativeLayout TimetaleLayout =(RelativeLayout)findViewById(R.id.TimeTableLayout);
        TimetaleLayout.setVisibility(View.INVISIBLE);
    }

    public void makeWelcomeInvisible()
    {
        TextView welcometv=(TextView)findViewById(R.id.welcometv);
        welcometv.setVisibility(View.INVISIBLE);
    }

    public void makeProgrssbarVisible()
    {
        makeAttendanceLayoutInvisible();
        makeProfileLayoutInvisible();
        makeTimetableInvisible();
        makeNoticesInvisible();

        TextView welcometv=(TextView)findViewById(R.id.welcometv);
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
        welcometv.setVisibility(View.VISIBLE);
        welcometv.setText("Loading...");
        progressBar.setVisibility(View.VISIBLE);
    }

    public void makeProgressbarInvisible()
    {
        TextView welcometv=(TextView)findViewById(R.id.welcometv);
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.progressBar);
        welcometv.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void makeAttendanceLayoutVisible()
    {
        makeProfileLayoutInvisible();
        makeTimetableInvisible();
        makeProgressbarInvisible();
        makeNoticesInvisible();

        ConstraintLayout constraintLayout=(ConstraintLayout)findViewById(R.id.AttendanceLayout);
        constraintLayout.setVisibility(View.VISIBLE);
    }
    public void makeAttendanceLayoutInvisible()
    {
        ConstraintLayout constraintLayout=(ConstraintLayout)findViewById(R.id.AttendanceLayout);
        constraintLayout.setVisibility(View.INVISIBLE);
    }
    public void makeProfileLayoutVisible() {

        makeProgressbarInvisible();
        makeTimetableInvisible();
        makeNoticesInvisible();
        makeAttendanceLayoutInvisible();

        RelativeLayout ProfileLayout =(RelativeLayout)findViewById(R.id.ProfileLayout);
        ProfileLayout.setVisibility(View.VISIBLE);

        EditText profileEmailet=(EditText)findViewById(R.id.profileEmailet);
        EditText profileAddet=(EditText)findViewById(R.id.profileAddet);
        EditText profileMobet=(EditText)findViewById(R.id.profileMobet);

        profileAddet.setEnabled(false);
        profileEmailet.setEnabled(false);
        profileMobet.setEnabled(false);
    }

    private void makeProfileLayoutInvisible()
    {
        RelativeLayout ProfileLayout =(RelativeLayout)findViewById(R.id.ProfileLayout);
        ProfileLayout.setVisibility(View.INVISIBLE);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////// functions for navigation items ////////////////////////////////////////////


    public void makeSendNoticesVisible()
    {

        makeTeachTTInvisible();
        maketeachNoticesInvisible();
        maketeachProfileInvisible();
        makeMarkAttInvisible();
        RelativeLayout sendNot =(RelativeLayout)findViewById(R.id.SendNotifLayout);
        sendNot.setVisibility(View.VISIBLE);
    }
    public void makeSendNoticesInvisible()
    {
        RelativeLayout sendNot =(RelativeLayout)findViewById(R.id.SendNotifLayout);
        sendNot.setVisibility(View.INVISIBLE);
    }

    public void makeMarkAttVisible()
    {
        makeTeachTTInvisible();
        maketeachNoticesInvisible();
        maketeachProfileInvisible();
        makeSendNoticesInvisible();

        RelativeLayout markAttFragLayout =(RelativeLayout)findViewById(R.id.markattFragLayout);
        markAttFragLayout.setVisibility(View.VISIBLE);
    }
    public void makeMarkAttInvisible()
    {
        RelativeLayout markAttFragLayout =(RelativeLayout)findViewById(R.id.markattFragLayout);
        markAttFragLayout.setVisibility(View.INVISIBLE);
    }
    public void makeTeachTTVisible()
    {
        makeMarkAttInvisible();
        maketeachNoticesInvisible();
        maketeachProfileInvisible();
        makeSendNoticesInvisible();

        RelativeLayout rl =(RelativeLayout)findViewById(R.id.TeachersTimeTableLayout);
        rl.setVisibility(View.VISIBLE);
    }
    public void makeTeachTTInvisible()
    {
        RelativeLayout rl =(RelativeLayout)findViewById(R.id.TeachersTimeTableLayout);
        rl.setVisibility(View.INVISIBLE);
    }
    public void maketeachNoticesVisible()
    {
        makeMarkAttInvisible();
        makeTeachTTInvisible();
        maketeachProfileInvisible();
        makeSendNoticesInvisible();

        RelativeLayout rl =(RelativeLayout)findViewById(R.id.teachNoticesLayout);
        rl.setVisibility(View.VISIBLE);
    }
    public void maketeachNoticesInvisible()
    {
        RelativeLayout rl =(RelativeLayout)findViewById(R.id.teachNoticesLayout);
        rl.setVisibility(View.INVISIBLE);
    }
    public void maketeachProfileVisible()
    {
        makeMarkAttInvisible();
        makeTeachTTInvisible();
        maketeachNoticesInvisible();
        makeSendNoticesInvisible();

        RelativeLayout rl =(RelativeLayout)findViewById(R.id.TeachersProfileLayout);
        rl.setVisibility(View.VISIBLE);
    }
    public void maketeachProfileInvisible()
    {
        RelativeLayout rl =(RelativeLayout)findViewById(R.id.TeachersProfileLayout);
        rl.setVisibility(View.INVISIBLE);
    }

    private void loadTimeTable() {
        makeTimetableVisible();

        String link = "http://000attendance-system.000webhostapp.com/student_login/timetable.php";

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;


            JSONObject jobj;
            JSONArray jsonArray;


            TextView timetabletv11 =(TextView)findViewById(R.id.timetabletv11);
            TextView timetabletv12 =(TextView)findViewById(R.id.timetabletv12);
            TextView timetabletv14 =(TextView)findViewById(R.id.timetabletv14);
            TextView timetabletv15 =(TextView)findViewById(R.id.timetabletv15);
            TextView timetabletv17 =(TextView)findViewById(R.id.timetabletv17);
            TextView timetabletv18 =(TextView)findViewById(R.id.timetabletv18);

            TextView timetabletv21 =(TextView)findViewById(R.id.timetabletv21);
            TextView timetabletv22 =(TextView)findViewById(R.id.timetabletv22);
            TextView timetabletv24 =(TextView)findViewById(R.id.timetabletv24);
            TextView timetabletv25 =(TextView)findViewById(R.id.timetabletv25);
            TextView timetabletv27 =(TextView)findViewById(R.id.timetabletv27);
            TextView timetabletv28 =(TextView)findViewById(R.id.timetabletv28);

            TextView timetabletv31 =(TextView)findViewById(R.id.timetabletv31);
            TextView timetabletv32 =(TextView)findViewById(R.id.timetabletv32);
            TextView timetabletv34 =(TextView)findViewById(R.id.timetabletv34);
            TextView timetabletv35 =(TextView)findViewById(R.id.timetabletv35);
            TextView timetabletv37 =(TextView)findViewById(R.id.timetabletv37);
            TextView timetabletv38 =(TextView)findViewById(R.id.timetabletv38);

            TextView timetabletv41 =(TextView)findViewById(R.id.timetabletv41);
            TextView timetabletv42 =(TextView)findViewById(R.id.timetabletv42);
            TextView timetabletv44 =(TextView)findViewById(R.id.timetabletv44);
            TextView timetabletv45 =(TextView)findViewById(R.id.timetabletv45);
            TextView timetabletv47 =(TextView)findViewById(R.id.timetabletv47);
            TextView timetabletv48 =(TextView)findViewById(R.id.timetabletv48);

            TextView timetabletv51 =(TextView)findViewById(R.id.timetabletv51);
            TextView timetabletv52 =(TextView)findViewById(R.id.timetabletv52);
            TextView timetabletv54 =(TextView)findViewById(R.id.timetabletv54);
            TextView timetabletv55 =(TextView)findViewById(R.id.timetabletv55);
            TextView timetabletv57 =(TextView)findViewById(R.id.timetabletv57);
            TextView timetabletv58 =(TextView)findViewById(R.id.timetabletv58);

            TextView timetabletv61 =(TextView)findViewById(R.id.timetabletv61);
            TextView timetabletv62 =(TextView)findViewById(R.id.timetabletv62);
            TextView timetabletv64 =(TextView)findViewById(R.id.timetabletv64);
            TextView timetabletv65 =(TextView)findViewById(R.id.timetabletv65);
            TextView timetabletv67 =(TextView)findViewById(R.id.timetabletv67);
            TextView timetabletv68 =(TextView)findViewById(R.id.timetabletv68);




            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                makeTimetableVisible();
                makeProgressbarInvisible();

                for(int i=0;i<jsonArray.length();i++) {
                    try {
                        jobj = jsonArray.getJSONObject(i);
                        Log.i("temp",jobj.getString("day"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    try {
                        if(jobj.getString("day").equals("MON")) {


                            timetabletv11.setText(jobj.getString("lec1"));
                            timetabletv12.setText(jobj.getString("lec2"));

                            timetabletv14.setText(jobj.getString("lec3"));
                            timetabletv15.setText(jobj.getString("lec4"));

                            timetabletv17.setText(jobj.getString("lec5"));
                            timetabletv18.setText(jobj.getString("lec6"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(jobj.getString("day").equals("TUE")) {
                            Log.i("temp",jobj.getString("day"));

                            timetabletv21.setText(jobj.getString("lec1"));
                            timetabletv22.setText(jobj.getString("lec2"));
                            Log.i("temp",jobj.getString("lec6"));

                            timetabletv24.setText(jobj.getString("lec3"));
                            timetabletv25.setText(jobj.getString("lec4"));

                            timetabletv27.setText(jobj.getString("lec5"));
                            timetabletv28.setText(jobj.getString("lec6"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(jobj.getString("day").equals("WED")) {

                            timetabletv31.setText(jobj.getString("lec1"));
                            timetabletv32.setText(jobj.getString("lec2"));

                            timetabletv34.setText(jobj.getString("lec3"));
                            timetabletv35.setText(jobj.getString("lec4"));

                            timetabletv37.setText(jobj.getString("lec5"));
                            timetabletv38.setText(jobj.getString("lec6"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(jobj.getString("day").equals("THU")) {

                            timetabletv41.setText(jobj.getString("lec1"));
                            timetabletv42.setText(jobj.getString("lec2"));

                            timetabletv44.setText(jobj.getString("lec3"));
                            timetabletv45.setText(jobj.getString("lec4"));

                            timetabletv47.setText(jobj.getString("lec5"));
                            timetabletv48.setText(jobj.getString("lec6"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(jobj.getString("day").equals("FRI")) {

                            timetabletv51.setText(jobj.getString("lec1"));
                            timetabletv52.setText(jobj.getString("lec2"));

                            timetabletv54.setText(jobj.getString("lec3"));
                            timetabletv55.setText(jobj.getString("lec4"));

                            timetabletv57.setText(jobj.getString("lec5"));
                            timetabletv58.setText(jobj.getString("lec6"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        if(jobj.getString("day").equals("SAT")) {

                            timetabletv61.setText(jobj.getString("lec1"));
                            timetabletv62.setText(jobj.getString("lec2"));

                            timetabletv64.setText(jobj.getString("lec3"));
                            timetabletv65.setText(jobj.getString("lec4"));

                            timetabletv67.setText(jobj.getString("lec5"));
                            timetabletv68.setText(jobj.getString("lec6"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

          protected String doInBackground(String[] params) {
                try {

                    String data = URLEncoder.encode("rollno", "UTF-8") + "=" +
                            URLEncoder.encode(rollno, "UTF-8");


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

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    Log.i("phpTEST", "before assigning to json " + sb.toString());
                     jsonArray = new JSONArray(sb.toString());





                    Log.i("phpTEST", jsonArray.toString());
                    String string = jsonArray.toString();// + "\n\n" + attendance.toString() + "\n\n";
                    return (string);

                } catch (Exception e) {
                    return (new String("EXCEPTION:" + e.getMessage()));
                }
            }
        }


        makeProgrssbarVisible();
        phpClass p = new phpClass(link);
        p.execute();

    }


    public void viewAtt() {


        String link = "http://000attendance-system.000webhostapp.com/student_login/attendance.php";

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject subjlist;
            JSONObject attendance;
            JSONObject obj;

            TextView tv1 =(TextView)findViewById(R.id.textView1);
            TextView tv2 =(TextView)findViewById(R.id.textView2);
            TextView tv3 =(TextView)findViewById(R.id.textView3);
            TextView tv4 =(TextView)findViewById(R.id.textView4);
            TextView tv5 =(TextView)findViewById(R.id.textView5);
            TextView tv6 =(TextView)findViewById(R.id.textView6);
            TextView tv7 =(TextView)findViewById(R.id.textView7);
            TextView tv8 =(TextView)findViewById(R.id.textView8);
            TextView tv9 =(TextView)findViewById(R.id.textView9);
            //TextView tv10 =(TextView)findViewById(R.id.textView10);

            TextView tv11 =(TextView)findViewById(R.id.textView11);
            TextView tv12 =(TextView)findViewById(R.id.textView12);
            TextView tv13 =(TextView)findViewById(R.id.textView13);
            TextView tv14 =(TextView)findViewById(R.id.textView14);
            TextView tv15 =(TextView)findViewById(R.id.textView15);
            TextView tv16 =(TextView)findViewById(R.id.textView16);
            TextView tv17 =(TextView)findViewById(R.id.textView17);
            TextView tv18 =(TextView)findViewById(R.id.textView18);
            TextView tv19 =(TextView)findViewById(R.id.textView19);

            LinearLayout AttendanceCol1 =(LinearLayout)findViewById(R.id.AttendanceCol1);
            LinearLayout AttendanceCol2 =(LinearLayout)findViewById(R.id.AttendanceCol2);


            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                makeAttendanceLayoutVisible();
                makeProgressbarInvisible();

                try {
                    tv1.setText(subjlist.getString("sub1"));
                    tv11.setText(attendance.getString(subjlist.getString("sub1")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tv2.setText(subjlist.getString("sub2"));
                    tv12.setText(attendance.getString(subjlist.getString("sub2")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tv3.setText(subjlist.getString("sub3"));
                    tv13.setText(attendance.getString(subjlist.getString("sub3")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tv4.setText(subjlist.getString("sub4"));
                    tv14.setText(attendance.getString(subjlist.getString("sub4")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tv5.setText(subjlist.getString("sub5"));
                    tv15.setText(attendance.getString(subjlist.getString("sub5")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tv6.setText(subjlist.getString("sub6"));
                    tv16.setText(attendance.getString(subjlist.getString("sub6")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tv7.setText(subjlist.getString("sub7"));
                    tv17.setText(attendance.getString(subjlist.getString("sub7")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tv8.setText(subjlist.getString("sub8"));
                    tv18.setText(attendance.getString(subjlist.getString("sub8")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tv9.setText(subjlist.getString("sub9"));
                    tv19.setText(attendance.getString(subjlist.getString("sub9")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            protected String doInBackground(String[] params) {
                try {

                    String data = URLEncoder.encode("rollno", "UTF-8") + "=" +
                            URLEncoder.encode(rollno, "UTF-8");

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

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    Log.i("phpTEST", "before assigning to json " + sb.toString());
                    // JSONArray jsonArray = new JSONArray(sb.toString());


                    obj = new JSONObject(sb.toString());
                    subjlist = obj.getJSONObject("subjlist");
                    attendance = obj.getJSONObject("attendance");


                    Log.i("phpTEST", subjlist.toString());
                    String string = subjlist.toString() + "\n\n" + attendance.toString() + "\n\n";
                    return (string);

                } catch (Exception e) {
                    return (new String("EXCEPTION:" + e.getMessage()));
                }
            }
        }

        makeProfileLayoutInvisible();
        makeProgrssbarVisible();
        phpClass p = new phpClass(link);
        p.execute();

    }



    private void loadProfile() {

        String link="http://000attendance-system.000webhostapp.com/student_login/profile.php";

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject profilejson;

            TextView nametv =(TextView)findViewById(R.id.nametv);
            TextView rollnotv=(TextView)findViewById(R.id.rollnotv);
            TextView yeartv =(TextView)findViewById(R.id.yeartv);
            TextView depttv =(TextView)findViewById(R.id.depttv);
            TextView semtv =(TextView)findViewById(R.id.semtv);
            TextView divtv =(TextView)findViewById(R.id.divtv);

            EditText profileEmailet=(EditText)findViewById(R.id.profileEmailet);
            EditText profileAddet=(EditText)findViewById(R.id.profileAddet);
            EditText profileMobet=(EditText)findViewById(R.id.profileMobet);

            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                makeProfileLayoutVisible();
                makeProgressbarInvisible();

                try {
                    nametv.setText(profilejson.getString("name"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    rollnotv.setText("Roll no: "+profilejson.getString("rollno"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    yeartv.setText("Year: "+profilejson.getString("year"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    depttv.setText("Dept: "+profilejson.getString("dept"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    semtv.setText("Sem: "+profilejson.getString("cur_sem"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    divtv.setText("Class: "+profilejson.getString("class")+profilejson.getString("batch"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    profileEmailet.setText(profilejson.getString("email"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    profileAddet.setText(profilejson.getString("address"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    profileMobet.setText(profilejson.getString("phoneno"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            protected String doInBackground(String[] params) {
                try {

                    String data = URLEncoder.encode("rollno", "UTF-8") + "=" +
                            URLEncoder.encode(rollno, "UTF-8");

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

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    Log.i("phpTEST", "before assigning to json " + sb.toString());
                    // JSONArray jsonArray = new JSONArray(sb.toString());


                    profilejson = new JSONObject(sb.toString());

                    Log.i("phpTEST", profilejson.toString());
                    String string = profilejson.toString();// + "\n\n" + attendance.toString() + "\n\n";
                    return (string);

                } catch (Exception e) {
                    return (new String("EXCEPTION:" + e.getMessage()));
                }
            }
        }


        makeProgrssbarVisible();
        phpClass p = new phpClass(link);
        p.execute();
    }

   /* public void onEditProfileClicked(View view)
    {
        EditText profileEmailet=(EditText)findViewById(R.id.profileEmailet);
        EditText profileAddet=(EditText)findViewById(R.id.profileAddet);
        EditText profileMobet=(EditText)findViewById(R.id.profileMobet);

        profileAddet.setEnabled(true);
        profileEmailet.setEnabled(true);
        profileMobet.setEnabled(true);
    }*/

  /*  public void onSaveProfileClicked(View view)
    {

        EditText profileEmailet=(EditText)findViewById(R.id.profileEmailet);
        EditText profileAddet=(EditText)findViewById(R.id.profileAddet);
        EditText profileMobet=(EditText)findViewById(R.id.profileMobet);

        profileAddet.setEnabled(false);
        profileEmailet.setEnabled(false);
        profileMobet.setEnabled(false);

        SaveProfileInformation(profileEmailet.getText().toString(),profileAddet.getText().toString(),
            profileMobet.getText().toString());

    }

    private void  SaveProfileInformation(final String email, final String address, final String phoneno)
    {



        String link="http://000attendance-system.000webhostapp.com/student_login/saveprofile.php";

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject profilejson;

            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                makeProgressbarInvisible();
                makeProfileLayoutVisible();

               // Toast.makeText(getApplicationContext(),"Before If",Toast.LENGTH_LONG).show();

                if(res.equals("1"))
                {
                    Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_LONG).show();
                }



            }

            protected String doInBackground(String[] params) {
                try {

                    String data = URLEncoder.encode("rollno", "UTF-8") + "=" +
                            URLEncoder.encode(rollno, "UTF-8");
                    data += "&" + URLEncoder.encode("email", "UTF-8") + "=" +
                            URLEncoder.encode(email, "UTF-8");
                    data += "&" + URLEncoder.encode("address", "UTF-8") + "=" +
                            URLEncoder.encode(address, "UTF-8");
                    data += "&" + URLEncoder.encode("phoneno", "UTF-8") + "=" +
                            URLEncoder.encode(phoneno, "UTF-8");

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

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    Log.i("phpTEST", "before assigning to json " + sb.toString());
                    // JSONArray jsonArray = new JSONArray(sb.toString());

                    String string = sb.toString();// + "\n\n" + attendance.toString() + "\n\n";
                    return (string);

                } catch (Exception e) {
                    return (new String("EXCEPTION:" + e.getMessage()));
                }
            }
        }

        makeAttendanceLayoutInvisible();
        makeProgrssbarVisible();
        phpClass p = new phpClass(link);
        p.execute();
    }

*/
    ////////////////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////

    //////////////////// TEACHERS SIDE CODE /////////////////////////////////////////////////////



    ////////////////////////// mark attendance FUNCTIONS  //////////////////////////////////////////
/*

    public void markrow1(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb1);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow2(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb2);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow3(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb3);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow4(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb4);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow5(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb5);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow6(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb6);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow7(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb7);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow8(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb8);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow9(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb9);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow10(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb10);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow11(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb11);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow12(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb12);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow13(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb13);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow14(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb14);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow15(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb15);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow16(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb16);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow17(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb17);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }
    public void markrow18(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb18);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow19(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb19);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow20(View view)
    {
        CheckBox attcb =(CheckBox)findViewById(R.id.attcb20);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }

    private void markAttendance() {

        makeTeachersProgressInvisible();
        makeLoadStudentsLayoutVisible();

        Button nextButton =(Button)findViewById(R.id.nextMarkAttButton);
        nextButton.setEnabled(false);


    }

    public void onNextClicked(View view)
    {
        getStudentsList();
        makeMarkAttendanceLayoutVisible();
    }

    private void getStudentsList() {

        Spinner deptspinner =(Spinner) findViewById(R.id.deptspinner);
        Spinner semspinner =(Spinner) findViewById(R.id.semspinner);
        Spinner divspinner =(Spinner) findViewById(R.id.divisionspinner);
        Spinner subjectspinner =(Spinner) findViewById(R.id.subjectspinner);

        final String dept=deptspinner.getSelectedItem().toString();
        final String sem=semspinner.getSelectedItem().toString();
        final String div=divspinner.getSelectedItem().toString();
        final String subject=subjectspinner.getSelectedItem().toString();

        String division;
        String batch;

        Log.i("temp",Integer.toString(div.length()));
        if(div.length()==2)
        {
            division=div.substring(0,1);
            batch=div.substring(1,2);
        }
        else
        {
            division=div;
            batch="0";
        }

        Log.i("temp",division);
        Log.i("temp",batch);
    }

    public void onLoadSubjectsClicked(View view)
    {

        getSubjectsList();

        Button nextButton =(Button)findViewById(R.id.nextMarkAttButton);
        nextButton.setEnabled(true);

    }

    private void getSubjectsList() {
        Spinner deptspinner =(Spinner) findViewById(R.id.deptspinner);
        Spinner semspinner =(Spinner) findViewById(R.id.semspinner);
        Spinner divspinner =(Spinner) findViewById(R.id.divisionspinner);
        final Spinner subjectspinner =(Spinner) findViewById(R.id.subjectspinner);

        final String dept=deptspinner.getSelectedItem().toString();
        final String sem=semspinner.getSelectedItem().toString();
        final String div=divspinner.getSelectedItem().toString();



        String link="http://000attendance-system.000webhostapp.com/teacher_login/getsubjects.php";

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject subjlist;

            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                makeTeachersProgressInvisible();
                makeLoadStudentsLayoutVisible();

                List<String> list = new ArrayList<String>();
                try {
                    list.add(subjlist.getString("sub1"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    list.add(subjlist.getString("sub2"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    list.add(subjlist.getString("sub3"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    list.add(subjlist.getString("sub4"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    list.add(subjlist.getString("sub5"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    list.add(subjlist.getString("sub6"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    list.add(subjlist.getString("sub7"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    list.add(subjlist.getString("sub8"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    list.add(subjlist.getString("sub9"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_spinner_item, list);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                subjectspinner.setAdapter(dataAdapter);

            }

            protected String doInBackground(String[] params) {
                try {

                    String data = URLEncoder.encode("dept", "UTF-8") + "=" +
                            URLEncoder.encode(dept, "UTF-8");

                    data += "&" + URLEncoder.encode("sem", "UTF-8") + "=" +
                            URLEncoder.encode(sem, "UTF-8");


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

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    Log.i("phpTEST", "before assigning to json " + sb.toString());

                    subjlist = new JSONObject(sb.toString());



                    Log.i("phpTEST", subjlist.toString());
                    String string = subjlist.toString();// + "\n\n" + attendance.toString() + "\n\n";
                    return (string);

                } catch (Exception e) {
                    return (new String("EXCEPTION:" + e.getMessage()));
                }
            }
        }
        makeLoadStudentsLayoutInvisible();
        makeTeachersProgressvisible();
        phpClass p = new phpClass(link);
        p.execute();
    }

    private void makeTeachersProgressvisible() {
        TextView welcometv=(TextView)findViewById(R.id.welcometv);
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.teachersprogressBar);
        welcometv.setVisibility(View.VISIBLE);
        welcometv.setText("Loading...");
        progressBar.setVisibility(View.VISIBLE);
    }
    private void makeTeachersProgressInvisible() {
        TextView welcometv=(TextView)findViewById(R.id.welcometv);
        ProgressBar progressBar=(ProgressBar)findViewById(R.id.teachersprogressBar);
        welcometv.setVisibility(View.INVISIBLE);
        welcometv.setText("Loading...");
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void makeMarkAttendanceLayoutVisible() {

        makeLoadStudentsLayoutInvisible();
        makeTeachersProgressInvisible();

        RelativeLayout markAttendanceLayout =(RelativeLayout)findViewById(R.id.markAttendanceLayout);
        markAttendanceLayout.setVisibility(View.VISIBLE);
    }
    private void makeMarkAttendanceLayoutInvisible() {
        RelativeLayout markAttendanceLayout =(RelativeLayout)findViewById(R.id.markAttendanceLayout);
        markAttendanceLayout.setVisibility(View.INVISIBLE);
    }
    private void makeLoadStudentsLayoutVisible() {
        makeTeachersProgressInvisible();
        makeMarkAttendanceLayoutInvisible();

        RelativeLayout l =(RelativeLayout)findViewById(R.id.LoadStudentsLayout);
        l.setVisibility(View.VISIBLE);
    }
    private void makeLoadStudentsLayoutInvisible() {
        RelativeLayout l =(RelativeLayout)findViewById(R.id.LoadStudentsLayout);
        l.setVisibility(View.INVISIBLE);
    }
*/
    ////////////////////////////////////////////////////////////////////////////////////////////////

}




