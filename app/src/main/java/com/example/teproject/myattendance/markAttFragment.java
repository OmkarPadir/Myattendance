package com.example.teproject.myattendance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vasantkapare on 21-09-2017.
 */

public class markAttFragment extends Fragment implements View.OnClickListener{

    @Nullable
    View view;
    String uid;

    String division;
    String batch;

    int Count;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.mark_att_frag,container,false);


        if (getArguments() != null) {
            uid = getArguments().getString("roll");
        }

        Button loadsubs =(Button)view.findViewById(R.id.loadSubjButton);
        loadsubs.setOnClickListener(this);

        Button nextButton =(Button)view.findViewById(R.id.nextMarkAttButton);
        nextButton.setOnClickListener(this);

        TableRow attrow1 =(TableRow)view.findViewById(R.id.attrow1);
        TableRow attrow2 =(TableRow)view.findViewById(R.id.attrow2);
        TableRow attrow3 =(TableRow)view.findViewById(R.id.attrow3);
        TableRow attrow4 =(TableRow)view.findViewById(R.id.attrow4);
        TableRow attrow5 =(TableRow)view.findViewById(R.id.attrow5);
        TableRow attrow6 =(TableRow)view.findViewById(R.id.attrow6);
        TableRow attrow7 =(TableRow)view.findViewById(R.id.attrow7);
        TableRow attrow8 =(TableRow)view.findViewById(R.id.attrow8);
        TableRow attrow9 =(TableRow)view.findViewById(R.id.attrow9);
        TableRow attrow10 =(TableRow)view.findViewById(R.id.attrow10);
        TableRow attrow11 =(TableRow)view.findViewById(R.id.attrow11);
        TableRow attrow12 =(TableRow)view.findViewById(R.id.attrow12);
        TableRow attrow13 =(TableRow)view.findViewById(R.id.attrow13);
        TableRow attrow14 =(TableRow)view.findViewById(R.id.attrow14);
        TableRow attrow15 =(TableRow)view.findViewById(R.id.attrow15);
        TableRow attrow16 =(TableRow)view.findViewById(R.id.attrow16);
        TableRow attrow17 =(TableRow)view.findViewById(R.id.attrow17);
        TableRow attrow18 =(TableRow)view.findViewById(R.id.attrow18);
        TableRow attrow19 =(TableRow)view.findViewById(R.id.attrow19);
        TableRow attrow20 =(TableRow)view.findViewById(R.id.attrow20);

        attrow1.setOnClickListener(this);
        attrow2.setOnClickListener(this);
        attrow3.setOnClickListener(this);
        attrow4.setOnClickListener(this);
        attrow5.setOnClickListener(this);
        attrow6.setOnClickListener(this);
        attrow7.setOnClickListener(this);
        attrow8.setOnClickListener(this);
        attrow9.setOnClickListener(this);
        attrow10.setOnClickListener(this);
        attrow11.setOnClickListener(this);
        attrow12.setOnClickListener(this);
        attrow13.setOnClickListener(this);
        attrow14.setOnClickListener(this);
        attrow15.setOnClickListener(this);
        attrow16.setOnClickListener(this);
        attrow17.setOnClickListener(this);
        attrow18.setOnClickListener(this);
        attrow19.setOnClickListener(this);
        attrow20.setOnClickListener(this);

        Button uploadAtt = (Button)view.findViewById(R.id.uploadAttButton);
        uploadAtt.setOnClickListener(this);

        markAttendance();
        return view;
    }

    public void markrow1(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb1);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb2);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb3);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb4);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb5);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb6);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow7(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb7);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb8);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb9);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb10);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow11(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb11);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow12(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb12);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow13(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb13);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow14(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb14);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow15(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb15);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow16(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb16);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb17);
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
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb18);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow19(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb19);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }public void markrow20(View view)
    {
        CheckBox attcb =(CheckBox)view.findViewById(R.id.attcb20);
        if(attcb.isChecked())
        {
            attcb.setChecked(false);
        }
        else{
            attcb.setChecked(true);
        }
    }

    public void markAttendance() {


        makeLoadStudentsLayoutVisible();

        Button nextButton =(Button)view.findViewById(R.id.nextMarkAttButton);
        nextButton.setEnabled(false);


    }

    public void onNextClicked(View view)
    {

    }

    private void getStudentsList() {

        Spinner deptspinner =(Spinner) view.findViewById(R.id.deptspinner);
        Spinner semspinner =(Spinner) view.findViewById(R.id.semspinner);
        Spinner divspinner =(Spinner) view.findViewById(R.id.divisionspinner);
        Spinner subjectspinner =(Spinner) view.findViewById(R.id.subjectspinner);

        final String dept=deptspinner.getSelectedItem().toString();
        final String sem=semspinner.getSelectedItem().toString();
        final String div=divspinner.getSelectedItem().toString();
        final String subject=subjectspinner.getSelectedItem().toString();

        //final String division;
        //final String batch;

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


        String link="http://000attendance-system.000webhostapp.com/teacher_login/MarkAttendance/getstudents.php";
       // final ScrollView notSV =(ScrollView) view.findViewById(R.id.notSV);
        class phpClass extends AsyncTask<String, String, String> {

            private final String link;
            JSONArray studentsArray;

            TextView atttv1=(TextView)view.findViewById(R.id.atttv1);
            TextView atttv2=(TextView)view.findViewById(R.id.atttv2);
            TextView atttv3=(TextView)view.findViewById(R.id.atttv3);
            TextView atttv4=(TextView)view.findViewById(R.id.atttv4);
            TextView atttv5=(TextView)view.findViewById(R.id.atttv5);
            TextView atttv6=(TextView)view.findViewById(R.id.atttv6);
            TextView atttv7=(TextView)view.findViewById(R.id.atttv7);
            TextView atttv8=(TextView)view.findViewById(R.id.atttv8);
            TextView atttv9=(TextView)view.findViewById(R.id.atttv9);
            TextView atttv10=(TextView)view.findViewById(R.id.atttv10);

            TextView atttv11=(TextView)view.findViewById(R.id.atttv11);
            TextView atttv12=(TextView)view.findViewById(R.id.atttv12);
            TextView atttv13=(TextView)view.findViewById(R.id.atttv13);
            TextView atttv14=(TextView)view.findViewById(R.id.atttv14);
            TextView atttv15=(TextView)view.findViewById(R.id.atttv15);
            TextView atttv16=(TextView)view.findViewById(R.id.atttv16);
            TextView atttv17=(TextView)view.findViewById(R.id.atttv17);
            TextView atttv18=(TextView)view.findViewById(R.id.atttv18);
            TextView atttv19=(TextView)view.findViewById(R.id.atttv19);
            TextView atttv20=(TextView)view.findViewById(R.id.atttv20);



            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {
                LinearLayout markAttLinearLayout =(LinearLayout)view.findViewById(R.id.markAttLinearLayout);
               // disableProgressBar();
              //  notSV.setVisibility(View.VISIBLE);
                /*


                if(markAttLinearLayout.getChildCount()>0)
                {
                    markAttLinearLayout.removeAllViews();
                }


                final int N = studentsArray.length(); // total number of textviews to add

                ConstraintLayout.LayoutParams layoutParams=new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);
                ConstraintLayout.LayoutParams layoutParams2=new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);

                layoutParams.setMargins(10,10,10,10);
                layoutParams2.setMargins(5,5,5,5);

                final CheckBox[] cbs = new CheckBox[N];
                final TableRow[] trs = new TableRow[N];
                final TextView[] markAttTVS = new TextView[N];
                // create an empty array;
                for (int i = 0; i < N; i++) {
                    // create a new textview
                    final TextView rowTextView = new TextView(view.getContext());
                    final CheckBox row=new CheckBox(view.getContext());
                    final TableRow tbr=new TableRow(view.getContext());

                    // set some properties of rowTextView or something
                    try {
                        rowTextView.setText(studentsArray.getJSONObject(i).getString("rollno")
                                +" \n "+studentsArray.getJSONObject(i).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    rowTextView.setTextSize(24);
                    rowTextView.setBackgroundColor(Color.GREEN);
                    Log.i("temp", String.valueOf(((ColorDrawable)rowTextView.getBackground()).getColor()));


                   // rowTextView.setBackgroundResource(R.drawable.border_for_tv);
                    rowTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                    rowTextView.setLayoutParams(layoutParams);
                    rowTextView.setId(i);
                    rowTextView.setOnClickListener((View.OnClickListener) view.getContext());

                    tbr.setLayoutParams(layoutParams2);
                    tbr.setBackgroundResource(R.drawable.border_for_tv);

                   // tbr.addView(row);
                   // tbr.addView(rowTextView);


                    // add the textview to the linearlayout
                   // row.addView(rowTextView);
                    markAttLinearLayout.addView(rowTextView);
                    //markAttLinearLayout.addView(row);

                    // save a reference to the textview for later
                    markAttTVS[i] = rowTextView;
                    cbs[i]=row;
                    trs[i]=tbr;
                }
                //int i=studentsArray.length()-1;

                for(int j=0;j<studentsArray.length();j++)
                {
                    try {

                        markAttTVS[j].setText(studentsArray.getJSONObject(j).getString("rollno")
                                +" \n "+studentsArray.getJSONObject(j).getString("name"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
*/
                TableRow attrow1 =(TableRow)view.findViewById(R.id.attrow1);
                TableRow attrow2 =(TableRow)view.findViewById(R.id.attrow2);
                TableRow attrow3 =(TableRow)view.findViewById(R.id.attrow3);
                TableRow attrow4 =(TableRow)view.findViewById(R.id.attrow4);
                TableRow attrow5 =(TableRow)view.findViewById(R.id.attrow5);
                TableRow attrow6 =(TableRow)view.findViewById(R.id.attrow6);
                TableRow attrow7 =(TableRow)view.findViewById(R.id.attrow7);
                TableRow attrow8 =(TableRow)view.findViewById(R.id.attrow8);
                TableRow attrow9 =(TableRow)view.findViewById(R.id.attrow9);
                TableRow attrow10 =(TableRow)view.findViewById(R.id.attrow10);
                TableRow attrow11 =(TableRow)view.findViewById(R.id.attrow11);
                TableRow attrow12 =(TableRow)view.findViewById(R.id.attrow12);
                TableRow attrow13 =(TableRow)view.findViewById(R.id.attrow13);
                TableRow attrow14 =(TableRow)view.findViewById(R.id.attrow14);
                TableRow attrow15 =(TableRow)view.findViewById(R.id.attrow15);
                TableRow attrow16 =(TableRow)view.findViewById(R.id.attrow16);
                TableRow attrow17 =(TableRow)view.findViewById(R.id.attrow17);
                TableRow attrow18 =(TableRow)view.findViewById(R.id.attrow18);
                TableRow attrow19 =(TableRow)view.findViewById(R.id.attrow19);
                TableRow attrow20 =(TableRow)view.findViewById(R.id.attrow20);

                int k=studentsArray.length();
                Count=k;



              if(k<20) {
                    for (int j = 0; j < k; j++) {
                        try {

                            if (j == 0) {
                                attrow1.setVisibility(View.VISIBLE);
                                atttv1.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 1) {
                                attrow2.setVisibility(View.VISIBLE);
                                atttv2.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 2) {
                                attrow3.setVisibility(View.VISIBLE);
                                atttv3.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 3) {
                                attrow4.setVisibility(View.VISIBLE);
                                atttv4.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 4) {
                                attrow5.setVisibility(View.VISIBLE);
                                atttv5.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 5) {
                                attrow6.setVisibility(View.VISIBLE);
                                atttv6.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 6) {
                                attrow7.setVisibility(View.VISIBLE);
                                atttv7.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 7) {
                                attrow8.setVisibility(View.VISIBLE);
                                atttv8.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 8) {
                                attrow9.setVisibility(View.VISIBLE);
                                atttv9.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 9) {
                                attrow10.setVisibility(View.VISIBLE);
                                atttv10.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 10) {
                                attrow11.setVisibility(View.VISIBLE);
                                atttv11.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 11) {
                                attrow12.setVisibility(View.VISIBLE);
                                atttv12.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 12) {
                                attrow13.setVisibility(View.VISIBLE);
                                atttv13.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 13) {
                                attrow14.setVisibility(View.VISIBLE);
                                atttv14.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 14) {
                                attrow15.setVisibility(View.VISIBLE);
                                atttv15.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 15) {
                                attrow16.setVisibility(View.VISIBLE);
                                atttv16.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 16) {
                                attrow17.setVisibility(View.VISIBLE);
                                atttv17.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 17) {
                                attrow18.setVisibility(View.VISIBLE);
                                atttv18.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 18) {
                                attrow19.setVisibility(View.VISIBLE);
                                atttv19.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }
                            if (j == 19) {
                                attrow20.setVisibility(View.VISIBLE);
                                atttv20.setText(studentsArray.getJSONObject(j).getString("rollno")
                                        + "  " + studentsArray.getJSONObject(j).getString("name"));
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                  markAttLinearLayout.removeViewsInLayout(k-1,20-k);
                  /*  for(int p=0;p<20;p++)
                    {
                             Log.i("temp", "val of p: "+Integer.toString(p)+"  " +String.valueOf(markAttLinearLayout.getChildAt(p)));

                    }*/
               }

            }

            protected String doInBackground(String[] params) {
                try {


                    String data = URLEncoder.encode("dept", "UTF-8") + "=" +
                            URLEncoder.encode(dept, "UTF-8");
                    data += "&" + URLEncoder.encode("sem", "UTF-8") + "=" +
                            URLEncoder.encode(sem, "UTF-8");
                    data += "&" + URLEncoder.encode("class", "UTF-8") + "=" +
                            URLEncoder.encode(division, "UTF-8");
                    data += "&" + URLEncoder.encode("batch", "UTF-8") + "=" +
                            URLEncoder.encode(batch, "UTF-8");



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


                    studentsArray = new JSONArray(sb.toString());

                    Log.i("phpTEST", studentsArray.toString());
                    String string = studentsArray.toString();// + "\n\n" + attendance.toString() + "\n\n";
                    return (string);

                } catch (Exception e) {
                    return (new String("EXCEPTION:" + e.getMessage()));
                }
            }
        }


       // showProgressBar();
       // notSV.setVisibility(View.INVISIBLE);
        phpClass p = new phpClass(link);
        p.execute();
    }
    private void uploadAttendance() {
        Log.i("temp","hello " + Integer.toString(Count));

        Spinner deptspinner =(Spinner) view.findViewById(R.id.deptspinner);
        Spinner semspinner =(Spinner) view.findViewById(R.id.semspinner);
        Spinner divspinner =(Spinner) view.findViewById(R.id.divisionspinner);
        final Spinner subjectspinner =(Spinner) view.findViewById(R.id.subjectspinner);

        final String dept=deptspinner.getSelectedItem().toString();
        final String sem=semspinner.getSelectedItem().toString();
        final String div=divspinner.getSelectedItem().toString();
        final String subject=subjectspinner.getSelectedItem().toString();


/*
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
*/
        final CheckBox[] cbs = new CheckBox[20];
        final TableRow[] trs = new TableRow[20];
        final TextView[] atttv = new TextView[20];

        TextView atttv1=(TextView)view.findViewById(R.id.atttv1);atttv[0]=atttv1;
        TextView atttv2=(TextView)view.findViewById(R.id.atttv2);atttv[1]=atttv2;
        TextView atttv3=(TextView)view.findViewById(R.id.atttv3);atttv[2]=atttv3;
        TextView atttv4=(TextView)view.findViewById(R.id.atttv4);atttv[3]=atttv4;
        TextView atttv5=(TextView)view.findViewById(R.id.atttv5);atttv[4]=atttv5;
        TextView atttv6=(TextView)view.findViewById(R.id.atttv6);atttv[5]=atttv6;
        TextView atttv7=(TextView)view.findViewById(R.id.atttv7);atttv[6]=atttv7;
        TextView atttv8=(TextView)view.findViewById(R.id.atttv8);atttv[7]=atttv8;
        TextView atttv9=(TextView)view.findViewById(R.id.atttv9);atttv[8]=atttv9;
        TextView atttv10=(TextView)view.findViewById(R.id.atttv10);atttv[9]=atttv10;

        TextView atttv11=(TextView)view.findViewById(R.id.atttv11);atttv[10]=atttv11;
        TextView atttv12=(TextView)view.findViewById(R.id.atttv12);atttv[11]=atttv12;
        TextView atttv13=(TextView)view.findViewById(R.id.atttv13);atttv[12]=atttv13;
        TextView atttv14=(TextView)view.findViewById(R.id.atttv14);atttv[13]=atttv14;
        TextView atttv15=(TextView)view.findViewById(R.id.atttv15);atttv[14]=atttv15;
        TextView atttv16=(TextView)view.findViewById(R.id.atttv16);atttv[15]=atttv16;
        TextView atttv17=(TextView)view.findViewById(R.id.atttv17);atttv[16]=atttv17;
        TextView atttv18=(TextView)view.findViewById(R.id.atttv18);atttv[17]=atttv18;
        TextView atttv19=(TextView)view.findViewById(R.id.atttv19);atttv[18]=atttv19;
        TextView atttv20=(TextView)view.findViewById(R.id.atttv20);atttv[19]=atttv20;

        CheckBox attcb1=(CheckBox)view.findViewById(R.id.attcb1);cbs[0]=attcb1;
        CheckBox attcb2=(CheckBox)view.findViewById(R.id.attcb2);cbs[1]=attcb2;
        CheckBox attcb3=(CheckBox)view.findViewById(R.id.attcb3);cbs[2]=attcb3;
        CheckBox attcb4=(CheckBox)view.findViewById(R.id.attcb4);cbs[3]=attcb4;
        CheckBox attcb5=(CheckBox)view.findViewById(R.id.attcb5);cbs[4]=attcb5;
        CheckBox attcb6=(CheckBox)view.findViewById(R.id.attcb6);cbs[5]=attcb6;
        CheckBox attcb7=(CheckBox)view.findViewById(R.id.attcb7);cbs[6]=attcb7;
        CheckBox attcb8=(CheckBox)view.findViewById(R.id.attcb8);cbs[7]=attcb8;
        CheckBox attcb9=(CheckBox)view.findViewById(R.id.attcb9);cbs[8]=attcb9;
        CheckBox attcb10=(CheckBox)view.findViewById(R.id.attcb10);cbs[9]=attcb10;
        CheckBox attcb11=(CheckBox)view.findViewById(R.id.attcb11);cbs[10]=attcb11;
        CheckBox attcb12=(CheckBox)view.findViewById(R.id.attcb12);cbs[11]=attcb12;
        CheckBox attcb13=(CheckBox)view.findViewById(R.id.attcb13);cbs[12]=attcb13;
        CheckBox attcb14=(CheckBox)view.findViewById(R.id.attcb14);cbs[13]=attcb14;
        CheckBox attcb15=(CheckBox)view.findViewById(R.id.attcb15);cbs[14]=attcb15;
        CheckBox attcb16=(CheckBox)view.findViewById(R.id.attcb16);cbs[15]=attcb16;
        CheckBox attcb17=(CheckBox)view.findViewById(R.id.attcb17);cbs[16]=attcb17;
        CheckBox attcb18=(CheckBox)view.findViewById(R.id.attcb18);cbs[17]=attcb18;
        CheckBox attcb19=(CheckBox)view.findViewById(R.id.attcb19);cbs[18]=attcb19;
        CheckBox attcb20=(CheckBox)view.findViewById(R.id.attcb20);cbs[19]=attcb20;

        final JSONArray mainJsonArray= new JSONArray();


        for(int i=0;i<Count-1;i++)
        {
            JSONObject attObj= new JSONObject();
            try {
                if(cbs[i].isChecked()) {
                    attObj.put("rollno", atttv[i].getText().toString().substring(0,7));

                    Log.i("temp",attObj.toString());

                    mainJsonArray.put(attObj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


        String link="http://000attendance-system.000webhostapp.com/AdministratorApp/MarkAttendance/markattendance.php";
        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
           // JSONObject subjlist;

            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {

                Toast.makeText(view.getContext(),res,Toast.LENGTH_LONG).show();

            }

            protected String doInBackground(String[] params) {
               try {

                /*    HttpClient client = new DefaultHttpClient();
                    HttpResponse response;
                    try{
                        HttpPost post = new HttpPost(link);
                        List<NameValuePair> nVP = new ArrayList<NameValuePair>(2);
                        nVP.add(new BasicNameValuePair("json", mainJsonArray.toString()));  //studentJson is the JSON input

//student.Json.toString() produces the correct JSON [{"studentId":"2","class":"2a","dbname":"testDb"}]

                        post.setEntity(new UrlEncodedFormEntity(nVP));
                        response = client.execute(post);
                        if(response!=null){
//process data send from php
                        }
                    */
                    String data = URLEncoder.encode("dept", "UTF-8") + "=" +
                            URLEncoder.encode(dept, "UTF-8");

                    data += "&" + URLEncoder.encode("sem", "UTF-8") + "=" +
                            URLEncoder.encode(sem, "UTF-8");
                    Log.i("temp","before div");
                    data += "&" + URLEncoder.encode("class", "UTF-8") + "=" +
                            URLEncoder.encode(division, "UTF-8");
                    Log.i("temp","before batch");
                    data += "&" + URLEncoder.encode("batch", "UTF-8") + "=" +
                            URLEncoder.encode(batch, "UTF-8");
                    Log.i("temp","before uid"+uid);
                    data += "&" + URLEncoder.encode("uid", "UTF-8") + "=" +
                            URLEncoder.encode(uid, "UTF-8");
                    Log.i("temp","after uid "+uid);
                    data += "&" + URLEncoder.encode("subject", "UTF-8") + "=" +
                            URLEncoder.encode(subject, "UTF-8");



                    data += "&" + URLEncoder.encode("jsonarray", "UTF-8") + "=" +
                            URLEncoder.encode(mainJsonArray.toString(), "UTF-8");
                   Log.i("temp","hello " + mainJsonArray.toString());


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

                   // subjlist = new JSONObject(sb.toString());



                    Log.i("phpTEST", sb.toString());
                    String string = sb.toString();// + "\n\n" + attendance.toString() + "\n\n";
                    return (string);

                } catch (Exception e) {
                    return (new String("EXCEPTION:" + e.getMessage()));
                }
            }
        }

        phpClass p = new phpClass(link);
        p.execute();

        /**
         String data = URLEncoder.encode("dept", "UTF-8") + "=" +
         URLEncoder.encode(dept, "UTF-8");
         data += "&" + URLEncoder.encode("sem", "UTF-8") + "=" +
         URLEncoder.encode(sem, "UTF-8");
         data += "&" + URLEncoder.encode("class", "UTF-8") + "=" +
         URLEncoder.encode(division, "UTF-8");
         data += "&" + URLEncoder.encode("batch", "UTF-8") + "=" +
         URLEncoder.encode(batch, "UTF-8");
         data += "&" + URLEncoder.encode("uid", "UTF-8") + "=" +
         URLEncoder.encode(uid, "UTF-8");
         data += "&" + URLEncoder.encode("subject", "UTF-8") + "=" +
         URLEncoder.encode(subject, "UTF-8");

         data += "&" + URLEncoder.encode("jsonarray", "UTF-8") + "=" +
         URLEncoder.encode(mainJsonArray.toString(), "UTF-8");
         */



    }


    private void getSubjectsList() {
        Spinner deptspinner =(Spinner) view.findViewById(R.id.deptspinner);
        Spinner semspinner =(Spinner) view.findViewById(R.id.semspinner);
        Spinner divspinner =(Spinner) view.findViewById(R.id.divisionspinner);
        final Spinner subjectspinner =(Spinner) view.findViewById(R.id.subjectspinner);

        final String dept=deptspinner.getSelectedItem().toString();
        final String sem=semspinner.getSelectedItem().toString();
        final String div=divspinner.getSelectedItem().toString();

        Log.i("temp",dept+sem);
        String link="http://000attendance-system.000webhostapp.com/teacher_login/getsubjects.php";

        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject subjlist;

            phpClass(String url) {
                link = url;
            }

            protected void onPostExecute(String res) {


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


                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(view.getContext(),
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

        phpClass p = new phpClass(link);
        p.execute();

    }

   

    private void makeMarkAttendanceLayoutVisible() {

        makeLoadStudentsLayoutInvisible();
       

        RelativeLayout markAttendanceLayout =(RelativeLayout)view.findViewById(R.id.markAttendanceLayout);
        markAttendanceLayout.setVisibility(View.VISIBLE);
    }
    private void makeMarkAttendanceLayoutInvisible() {
        RelativeLayout markAttendanceLayout =(RelativeLayout)view.findViewById(R.id.markAttendanceLayout);
        markAttendanceLayout.setVisibility(View.INVISIBLE);
    }
    private void makeLoadStudentsLayoutVisible() {
        
        makeMarkAttendanceLayoutInvisible();

        RelativeLayout l =(RelativeLayout)view.findViewById(R.id.LoadStudentsLayout);
        l.setVisibility(View.VISIBLE);
    }
    private void makeLoadStudentsLayoutInvisible() {
        RelativeLayout l =(RelativeLayout)view.findViewById(R.id.LoadStudentsLayout);
        l.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.loadSubjButton :

                getSubjectsList();
                Button nextButton =(Button)view.findViewById(R.id.nextMarkAttButton);
                nextButton.setEnabled(true);

                break;
            case R.id.nextMarkAttButton :

                getStudentsList();
                makeMarkAttendanceLayoutVisible();

                break;
            case R.id.attrow1:
                markrow1(view);
                break;
            case R.id.attrow2:
                markrow2(view);
                break;
            case R.id.attrow3:
                markrow3(view);
                break;
            case R.id.attrow4:
                markrow4(view);
                break;
            case R.id.attrow5:
                markrow5(view);
                break;
            case R.id.attrow6:
                markrow6(view);
                break;
            case R.id.attrow7:
                markrow7(view);
                break;
            case R.id.attrow8:
                markrow8(view);
                break;
            case R.id.attrow9:
                markrow9(view);
                break;
            case R.id.attrow10:
                markrow10(view);
                break;
            case R.id.attrow11:
                markrow11(view);
                break;
            case R.id.attrow12:
                markrow12(view);
                break;
            case R.id.attrow13:
                markrow13(view);
                break;
            case R.id.attrow14:
                markrow14(view);
                break;
            case R.id.attrow15:
                markrow15(view);
                break;
            case R.id.attrow16:
                markrow16(view);
                break;
            case R.id.attrow17:
                markrow17(view);
                break;
            case R.id.attrow18:
                markrow18(view);
                break;
            case R.id.attrow19:
                markrow19(view);
                break;
            case R.id.attrow20:
                markrow20(view);
                break;

            case R.id.uploadAttButton:
                uploadAttendance();
                break;

        }
    }


}
