package com.example.teproject.myattendance;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by vasantkapare on 20-09-2017.
 */



public class viewTTFragment extends Fragment {
    @Nullable
    private static TextView timetabletv11 ;
    private static TextView timetabletv12 ;
    private static TextView timetabletv14 ;
    private static TextView timetabletv15 ;
    private static TextView timetabletv17 ;
    private static TextView timetabletv18 ;

    private static TextView timetabletv21 ;
    private static TextView timetabletv22 ;
    private static TextView timetabletv24 ;
    private static TextView timetabletv25 ;
    private static TextView timetabletv27 ;
    private static TextView timetabletv28 ;

    private static TextView timetabletv31 ;
    private static TextView timetabletv32 ;
    private static TextView timetabletv34 ;
    private static TextView timetabletv35 ;
    private static TextView timetabletv37 ;
    private static TextView timetabletv38 ;

    private static TextView timetabletv41 ;
    private static TextView timetabletv42 ;
    private static TextView timetabletv44 ;
    private static TextView timetabletv45 ;
    private static TextView timetabletv47 ;
    private static TextView timetabletv48 ;

    private static TextView timetabletv51 ;
    private static TextView timetabletv52 ;
    private static TextView timetabletv54 ;
    private static TextView timetabletv55 ;
    private static TextView timetabletv57 ;
    private static TextView timetabletv58 ;

    private static TextView timetabletv61 ;
    private static TextView timetabletv62 ;
    private static TextView timetabletv64 ;
    private static TextView timetabletv65 ;
    private static TextView timetabletv67 ;
    private static TextView timetabletv68 ;


    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.view_tt_frag,container,false);

         timetabletv11 =(TextView)view.findViewById(R.id.timetabletv11);
         timetabletv12 =(TextView)view.findViewById(R.id.timetabletv12);
         timetabletv14 =(TextView)view.findViewById(R.id.timetabletv14);
         timetabletv15 =(TextView)view.findViewById(R.id.timetabletv15);
         timetabletv17 =(TextView)view.findViewById(R.id.timetabletv17);
         timetabletv18 =(TextView)view.findViewById(R.id.timetabletv18);

         timetabletv21 =(TextView)view.findViewById(R.id.timetabletv21);
         timetabletv22 =(TextView)view.findViewById(R.id.timetabletv22);
         timetabletv24 =(TextView)view.findViewById(R.id.timetabletv24);
         timetabletv25 =(TextView)view.findViewById(R.id.timetabletv25);
         timetabletv27 =(TextView)view.findViewById(R.id.timetabletv27);
         timetabletv28 =(TextView)view.findViewById(R.id.timetabletv28);

         timetabletv31 =(TextView)view.findViewById(R.id.timetabletv31);
         timetabletv32 =(TextView)view.findViewById(R.id.timetabletv32);
         timetabletv34 =(TextView)view.findViewById(R.id.timetabletv34);
         timetabletv35 =(TextView)view.findViewById(R.id.timetabletv35);
         timetabletv37 =(TextView)view.findViewById(R.id.timetabletv37);
         timetabletv38 =(TextView)view.findViewById(R.id.timetabletv38);

         timetabletv41 =(TextView)view.findViewById(R.id.timetabletv41);
         timetabletv42 =(TextView)view.findViewById(R.id.timetabletv42);
         timetabletv44 =(TextView)view.findViewById(R.id.timetabletv44);
         timetabletv45 =(TextView)view.findViewById(R.id.timetabletv45);
         timetabletv47 =(TextView)view.findViewById(R.id.timetabletv47);
         timetabletv48 =(TextView)view.findViewById(R.id.timetabletv48);

         timetabletv51 =(TextView)view.findViewById(R.id.timetabletv51);
         timetabletv52 =(TextView)view.findViewById(R.id.timetabletv52);
         timetabletv54 =(TextView)view.findViewById(R.id.timetabletv54);
         timetabletv55 =(TextView)view.findViewById(R.id.timetabletv55);
         timetabletv57 =(TextView)view.findViewById(R.id.timetabletv57);
         timetabletv58 =(TextView)view.findViewById(R.id.timetabletv58);

         timetabletv61 =(TextView)view.findViewById(R.id.timetabletv61);
         timetabletv62 =(TextView)view.findViewById(R.id.timetabletv62);
         timetabletv64 =(TextView)view.findViewById(R.id.timetabletv64);
         timetabletv65 =(TextView)view.findViewById(R.id.timetabletv65);
         timetabletv67 =(TextView)view.findViewById(R.id.timetabletv67);
         timetabletv68 =(TextView)view.findViewById(R.id.timetabletv68);

        return view;
    }

    public void showTimeTable(final String rollno)
    {
        String link = "http://000attendance-system.000webhostapp.com/student_login/timetable.php";
        final HorizontalScrollView hsv = (HorizontalScrollView)view.findViewById(R.id.hsv);

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;


            JSONObject jobj;
            JSONArray jsonArray;



            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                disableProgressBar();
                hsv.setVisibility(View.VISIBLE);


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


        showProgressBar();
        hsv.setVisibility(View.INVISIBLE);
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
