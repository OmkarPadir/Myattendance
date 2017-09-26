package com.example.teproject.myattendance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by vasantkapare on 19-09-2017.
 */

public class viewAttFragment extends Fragment {
    @Nullable

    private static TextView tv1;
    private static TextView tv2;
    private static TextView tv3;
    private static TextView tv4;
    private static TextView tv5;
    private static TextView tv6;
    private static TextView tv7;
    private static TextView tv8;
    private static TextView tv9;

    private static TextView tv11;
    private static TextView tv12;
    private static TextView tv13;
    private static TextView tv14;
    private static TextView tv15;
    private static TextView tv16;
    private static TextView tv17;
    private static TextView tv18;
    private static TextView tv19;



    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.view_att_frag,container,false);



          tv1 =(TextView)view.findViewById(R.id.textView1);
          tv2 =(TextView)view.findViewById(R.id.textView2);
          tv3 =(TextView)view.findViewById(R.id.textView3);
          tv4 =(TextView)view.findViewById(R.id.textView4);
          tv5 =(TextView)view.findViewById(R.id.textView5);
          tv6 =(TextView)view.findViewById(R.id.textView6);
          tv7 =(TextView)view.findViewById(R.id.textView7);
          tv8 =(TextView)view.findViewById(R.id.textView8);
          tv9 =(TextView)view.findViewById(R.id.textView9);


          tv11 =(TextView)view.findViewById(R.id.textView11);
          tv12 =(TextView)view.findViewById(R.id.textView12);
          tv13 =(TextView)view.findViewById(R.id.textView13);
          tv14 =(TextView)view.findViewById(R.id.textView14);
          tv15 =(TextView)view.findViewById(R.id.textView15);
          tv16 =(TextView)view.findViewById(R.id.textView16);
          tv17 =(TextView)view.findViewById(R.id.textView17);
          tv18 =(TextView)view.findViewById(R.id.textView18);
          tv19 =(TextView)view.findViewById(R.id.textView19);

      /*  LinearLayout AttendanceCol1 =(LinearLayout)view.findViewById(R.id.AttendanceCol1);
        LinearLayout AttendanceCol2 =(LinearLayout)view.findViewById(R.id.AttendanceCol2);*/



        return  view;
    }


    public void viewAttendance(final String rollno)
    {
        String link = "http://000attendance-system.000webhostapp.com/student_login/attendance.php";
        final LinearLayout AttendanceCol1 =(LinearLayout)view.findViewById(R.id.AttendanceCol1);
        final LinearLayout AttendanceCol2 =(LinearLayout)view.findViewById(R.id.AttendanceCol2);
        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject subjlist;
            JSONObject attendance;
            JSONObject obj;




            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                disableProgressBar();

                AttendanceCol1.setVisibility(View.VISIBLE);
                AttendanceCol2.setVisibility(View.VISIBLE);


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

        showProgressBar();
        AttendanceCol1.setVisibility(View.INVISIBLE);
        AttendanceCol2.setVisibility(View.INVISIBLE);
        phpClass p = new phpClass(link);
        p.execute();


    }

    private void showProgressBar() {
        ProgressBar pb =(ProgressBar)view.findViewById(R.id.progbar);
        pb.setVisibility(View.VISIBLE);

        TextView tv = (TextView)view.findViewById(R.id.loadingtv);
        tv.setVisibility(View.VISIBLE);
    }

    private void disableProgressBar()
    {

        ProgressBar pb =(ProgressBar)view.findViewById(R.id.progbar);
        pb.setVisibility(View.INVISIBLE);

        TextView tv = (TextView)view.findViewById(R.id.loadingtv);
        tv.setVisibility(View.INVISIBLE);

    }


}
