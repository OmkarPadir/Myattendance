package com.example.teproject.myattendance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class viewProfileFragment extends Fragment implements View.OnClickListener {

    @Nullable

    private static TextView nametv;
    private static TextView rollnotv;
    private static TextView yeartv;
    private static TextView depttv;
    private static TextView semtv;
    private static TextView divtv;

    private static EditText profileEmailet;
    private static EditText profileAddet;
    private static EditText profileMobet;

    private static Button EditButton;
    private static Button SaveButton;

    String rollno;

    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_profile_fragment,container,false);

         nametv =(TextView)view.findViewById(R.id.nametv);
         rollnotv=(TextView)view.findViewById(R.id.rollnotv);
         yeartv =(TextView)view.findViewById(R.id.yeartv);
         depttv =(TextView)view.findViewById(R.id.depttv);
         semtv =(TextView)view.findViewById(R.id.semtv);
         divtv =(TextView)view.findViewById(R.id.divtv);

         profileEmailet=(EditText)view.findViewById(R.id.profileEmailet);
         profileAddet=(EditText)view.findViewById(R.id.profileAddet);
         profileMobet=(EditText)view.findViewById(R.id.profileMobet);

        EditButton=(Button)view.findViewById(R.id.editProfileButton);
        EditButton.setOnClickListener(this);

        SaveButton=(Button)view.findViewById(R.id.saveProfileButton);
        SaveButton.setOnClickListener(this);

        return view;
    }

    public void showProfile(final String roll)
    {
        rollno=roll;
        String link="http://000attendance-system.000webhostapp.com/student_login/profile.php";
        final RelativeLayout profLayout =(RelativeLayout)view.findViewById(R.id.profLayout);
        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject profilejson;



            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                disableProgressBar();
                profLayout.setVisibility(View.VISIBLE);

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


        showProgressBar();
        profLayout.setVisibility(View.INVISIBLE);
        phpClass p = new phpClass(link);
        p.execute();
    }



    private void  SaveProfileInformation(final String email, final String address, final String phoneno)
    {



        String link="http://000attendance-system.000webhostapp.com/student_login/saveprofile.php";

        final RelativeLayout profLayout =(RelativeLayout)view.findViewById(R.id.profLayout);

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject profilejson;

            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                disableProgressBar();
                profLayout.setVisibility(View.VISIBLE);

                // Toast.makeText(getApplicationContext(),"Before If",Toast.LENGTH_LONG).show();

                if(res.equals("1"))
                {
                    Toast.makeText(view.getContext(),"Saved",Toast.LENGTH_LONG).show();
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

        showProgressBar();
        profLayout.setVisibility(View.INVISIBLE);
        phpClass p = new phpClass(link);
        p.execute();
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.editProfileButton :
                profileAddet.setEnabled(true);
                profileEmailet.setEnabled(true);
                profileMobet.setEnabled(true);
                break;
            case R.id.saveProfileButton :
                profileAddet.setEnabled(false);
                profileEmailet.setEnabled(false);
                profileMobet.setEnabled(false);

                SaveProfileInformation(profileEmailet.getText().toString(),profileAddet.getText().toString(),
                        profileMobet.getText().toString());


                break;
            // similarly for other buttons
        }


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
