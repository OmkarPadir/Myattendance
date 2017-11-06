package com.example.teproject.myattendance;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

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

/**
 * Created by vasantkapare on 29-10-2017.
 */

public class Default_page_frag extends Fragment {

    View view;
    String rollno;
    int total=0, totpresent=0;
    PieChart pieChart;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.default_page_students,container,false);

         pieChart= (PieChart) view.findViewById(R.id.piechart);



        if(getArguments()!=null)
        {

            TextView name = (TextView)view.findViewById(R.id.nameTV);
            name.setText("Roll no: "+getArguments().getString("rollno"));

            rollno=getArguments().getString("rollno");
            Log.i("temp",rollno);
        }

        getAttDetails();
        return view;
    }

    public void loadPieChart()
    {


        // pieChart.setUsePercentValues(true);

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(totpresent,0));
        yvalues.add(new Entry(total-totpresent, 1));


        PieDataSet dataSet = new PieDataSet(yvalues, "");

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add("Present");
        xVals.add("Absent");


        PieData data = new PieData(xVals, dataSet);

        // data.setValueFormatter(new PercentFormatter());



        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        pieChart.setData(data);

        pieChart.setDescription("Attendance Report");


        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);

        TextView totTV= (TextView) view.findViewById(R.id.totTV);
        totTV.setText("Total Lectures: "+Integer.toString(total)+"\n\n"+
                        "Overall Attendance: "+String.valueOf(totpresent)+"/"+String.valueOf(total)+"    "
                        +String.format("%.2f",((float) (totpresent*100)/total))+"%");

    }

    public void getAttDetails()
    {
        String link = "http://000attendance-system.000webhostapp.com/student_login/attendance.php";


       // final LinearLayout viewAttFrame = (LinearLayout)view.findViewById(R.id.viewAtt);
        class phpClass extends AsyncTask<String, String, String> {
            private final String link;
            JSONObject subjlist;
            JSONObject attendance;
            JSONObject obj;
            JSONArray subjcount;




            phpClass(String url) {
                link = url;
            }



            protected String doInBackground(String[] params) {
                try {

                    Log.i("temp","here i am total is: "+Integer.toString(total)+rollno);

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
                    Log.i("temp", "before assigning to json " + sb.toString());
                    // JSONArray jsonArray = new JSONArray(sb.toString());


                    obj = new JSONObject(sb.toString());
                    subjlist = obj.getJSONObject("subjlist");
                    attendance = obj.getJSONObject("attendance");
                    subjcount = obj.getJSONArray("subjcount");


                    Log.i("phpTEST", subjcount.toString());
                    String string = subjlist.toString() + "\n\n" + attendance.toString() + "\n\n";
                    return (string);

                } catch (Exception e) {
                    return (new String("EXCEPTION:" + e.getMessage()));
                }
            }

            protected void onPostExecute(String res) {

                disableProgressBar();
                ScrollView scrollView= (ScrollView) view.findViewById(R.id.defaultSV);
                scrollView.setVisibility(View.VISIBLE);
                try {


                for(int i=0;i<subjlist.length()-4;i++)
                {
                    total+=Integer.valueOf(subjcount.getJSONObject(i).getString("lecture_count"));

                }

                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub1")));
                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub2")));
                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub3")));
                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub4")));
                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub5")));
                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub6")));
                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub7")));
                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub8")));
                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub9")));
                    totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub10")));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                loadPieChart();

/*
                if(viewAttFrame.getChildCount()>0)
                {
                    viewAttFrame.removeAllViews();

                }

                ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                        ConstraintLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(10,10,10,10);


                LinearLayout.LayoutParams linlay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                linlay.setMargins(200,10,10,10);



                TextView tvSub[] = new TextView[subjlist.length()-3];
                TextView tvAtt[] = new TextView[subjlist.length()-3];
                TableRow tbr[]=new TableRow[subjlist.length()-3];
                for(int i=0;i<subjlist.length()-3;i++)
                {
                    TextView rowTvSub = new TextView(view.getContext());
                    TextView rowAtt = new TextView(view.getContext());
                    TableRow row = new TableRow(view.getContext());

                    TableRow.LayoutParams trlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(trlp);

                    rowAtt.setTextSize(24);
                    rowTvSub.setTextSize(24);

                    rowTvSub.setPadding(10,10,10,10);
                    rowAtt.setPadding(10,10,10,10);

                    View parent = (View)view.getParent();
                    int width = parent.getWidth();


                    TableRow.LayoutParams layparaminTR = new TableRow.LayoutParams(width/2-50, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layparaminTR.setMargins(10,10,10,10);
                    rowAtt.setLayoutParams(layparaminTR);
                    rowTvSub.setLayoutParams(layparaminTR);


                    rowTvSub.setBackgroundResource(R.drawable.border_for_tv);
                    rowAtt.setBackgroundResource(R.drawable.border_for_tv);


                    row.addView(rowTvSub);
                    row.addView(rowAtt);
                    viewAttFrame.addView(row);


                    tbr[i]=row;
                    tvSub[i]= rowTvSub;
                    tvAtt[i]= rowAtt;

                }

                Log.i("temp", "subj len = "+String.valueOf(subjlist.length()));

                int total=0, totpresent=0;
                try
                {
                    for(int i=0;i<subjlist.length()-4;i++)
                    {
                        total+=Integer.valueOf(subjcount.getJSONObject(i).getString("lecture_count"));
                        if(i==0)
                        {
                            tvSub[i].setText(subjlist.getString("sub1"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub1"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));

                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub1")));
                        }
                        if(i==1)
                        {
                            tvSub[i].setText(subjlist.getString("sub2"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub2"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));
                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub2")));
                        }
                        if(i==2)
                        {
                            tvSub[i].setText(subjlist.getString("sub3"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub3"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));
                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub3")));
                        }
                        if(i==3)
                        {
                            tvSub[i].setText(subjlist.getString("sub4"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub4"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));
                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub4")));
                        }
                        if(i==4)
                        {
                            tvSub[i].setText(subjlist.getString("sub5"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub5"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));
                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub5")));
                        }
                        if(i==5)
                        {
                            tvSub[i].setText(subjlist.getString("sub6"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub6"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));
                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub6")));
                        }
                        if(i==6)
                        {
                            tvSub[i].setText(subjlist.getString("sub7"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub7"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));
                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub7")));
                        }
                        if(i==7)
                        {
                            tvSub[i].setText(subjlist.getString("sub8"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub8"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));
                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub8")));
                        }
                        if(i==8)
                        {
                            tvSub[i].setText(subjlist.getString("sub9"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub9"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));
                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub9")));
                        }
                        if(i==9)
                        {
                            tvSub[i].setText(subjlist.getString("sub10"));
                            tvAtt[i].setText(attendance.getString(subjlist.getString("sub10"))+"/"+subjcount.getJSONObject(i).getString("lecture_count"));
                            totpresent+=Integer.parseInt(attendance.getString(subjlist.getString("sub10")));
                        }

                    }
                    tvSub[subjlist.length()-4].setText("Total");
                    tvAtt[subjlist.length()-4].setText(String.valueOf(totpresent)+"/"+String.valueOf(total)+"    "
                            +String.valueOf((float) (totpresent*100)/total)+"%");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

*/
            }
        }

        showProgressBar();

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
