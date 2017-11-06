package com.example.teproject.myattendance;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by vasantkapare on 24-09-2017.
 */

public class viewTeachNoticesFrag extends Fragment{


    @Nullable
    View view;
    String uid;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_notices_frag,container,false);

        if (getArguments() != null) {
            uid = getArguments().getString("roll");
        }
        showNotices();
        return  view;
    }

    public void showNotices( )
    {
        Log.i("temp",uid);

        String link="http://000attendance-system.000webhostapp.com/teacher_login/Notices/teachNotices.php";

        final ScrollView notSV =(ScrollView) view.findViewById(R.id.notSV);

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONArray noticesArray;

            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                disableProgressBar();
                notSV.setVisibility(View.VISIBLE);

                LinearLayout NoticesLinearLayout=(LinearLayout)view.findViewById(R.id.NoticesLinearLayout);
                if(NoticesLinearLayout.getChildCount()>0)
                {
                    NoticesLinearLayout.removeAllViews();
                }


                final int N = noticesArray.length(); // total number of textviews to add

                ConstraintLayout.LayoutParams layoutParams=new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.MATCH_PARENT);

                layoutParams.setMargins(10,10,10,10);



                final TextView[] NoticesTVS = new TextView[N]; // create an empty array;
                for (int i = 0; i < N; i++) {
                    // create a new textview
                    final TextView rowTextView = new TextView(view.getContext());

                    // set some properties of rowTextView or something
                    rowTextView.setText("This is row #" + i);

                    rowTextView.setTextSize(24);

                    rowTextView.setBackgroundResource(R.drawable.border_for_tv);
                    rowTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                    rowTextView.setLayoutParams(layoutParams);


                    // add the textview to the linearlayout
                    NoticesLinearLayout.addView(rowTextView);

                    // save a reference to the textview for later
                    NoticesTVS[i] = rowTextView;
                }
                int i=noticesArray.length()-1;

                for(int j=0;j<noticesArray.length();j++)
                {
                    try {
                        NoticesTVS[j].setText(noticesArray.getJSONObject(i).getString("subject")
                                +"\n"+noticesArray.getJSONObject(i).getString("text_body"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i--;



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


                    noticesArray = new JSONArray(sb.toString());

                    Log.i("phpTEST", noticesArray.toString());
                    String string = noticesArray.toString();// + "\n\n" + attendance.toString() + "\n\n";
                    return (string);

                } catch (Exception e) {
                    return (new String("EXCEPTION:" + e.getMessage()));
                }
            }
        }


        showProgressBar();
        notSV.setVisibility(View.INVISIBLE);
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
