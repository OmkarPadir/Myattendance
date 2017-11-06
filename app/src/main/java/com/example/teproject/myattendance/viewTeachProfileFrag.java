package com.example.teproject.myattendance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
 * Created by vasantkapare on 25-09-2017.
 */

public class viewTeachProfileFrag extends Fragment implements View.OnClickListener{

    @Nullable

    View view;
    EditText profileEmailet;
    EditText profileAddet;
    EditText profileMobet;

    String uid;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.teach_profile_frag,container,false);
         profileEmailet=(EditText) view.findViewById(R.id.tpEmailET);
         profileAddet=(EditText) view.findViewById(R.id.tpAddET);
         profileMobet=(EditText) view.findViewById(R.id.tpMobET);

        profileAddet.setEnabled(false);
        profileEmailet.setEnabled(false);
        profileMobet.setEnabled(false);

        Button editButton =(Button)view.findViewById(R.id.tpEditButton);
        editButton.setOnClickListener( this);

        Button saveButton =(Button)view.findViewById(R.id.tpSaveButton);
        saveButton.setOnClickListener( this);

       // readBundle(getArguments());
        if (getArguments() != null) {
            uid = getArguments().getString("roll");
        }
        showteachProfile();

        return view;
    }


    public void showteachProfile()
    {


        String link="http://000attendance-system.000webhostapp.com/teacher_login/Profile/getProfile.php";
        final ConstraintLayout profLayout =(ConstraintLayout) view.findViewById(R.id.TeachProfileLayout);

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject profilejson;

            TextView tpNameTV= (TextView) view.findViewById(R.id.tpNameTV);
            TextView tpuidTV= (TextView) view.findViewById(R.id.tpuidTV);
            TextView tpDeptTV= (TextView) view.findViewById(R.id.tpDeptTV);


            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                disableProgressBar();
                profLayout.setVisibility(View.VISIBLE);


                try {
                    tpNameTV.setText(profilejson.getString("name"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tpuidTV.setText("UID: "+profilejson.getString("uid"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    tpDeptTV.setText("Dept: "+profilejson.getString("dept"));

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

                    String data = URLEncoder.encode("uid", "UTF-8") + "=" +
                            URLEncoder.encode(uid, "UTF-8");

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
        Log.i("temp","before");


        String link="http://000attendance-system.000webhostapp.com/teacher_login/Profile/saveProfile.php";

        final ConstraintLayout profLayout =(ConstraintLayout) view.findViewById(R.id.TeachProfileLayout);

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

                    String data = URLEncoder.encode("uid", "UTF-8") + "=" +
                            URLEncoder.encode(uid, "UTF-8");
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
            case R.id.tpEditButton :
                profileAddet.setEnabled(true);
                profileEmailet.setEnabled(true);
                profileMobet.setEnabled(true);
                break;
            case R.id.tpSaveButton :
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
