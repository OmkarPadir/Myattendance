package com.example.teproject.myattendance;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by vasantkapare on 06-10-2017.
 */

public class SendNotification extends Fragment implements View.OnClickListener {

    Spinner spinner_dept;
    Spinner spinner_year;
    Spinner spinner_class;
    Spinner spinner_batch;

    View view;
    EditText editText_subject;
    EditText editText_body;
    Button button_send;

    ArrayList<String> deptList;
    int deptCount;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.send_notices_frag, container, false);

        button_send = (Button) view.findViewById(R.id.button_send);
        button_send.setOnClickListener(this);
        spinner_batch = (Spinner)view.findViewById(R.id.spinner_batch);
        spinner_class = (Spinner)view.findViewById(R.id.spinner_class);
        spinner_dept = (Spinner)view.findViewById(R.id.spinner_dept);
        spinner_year = (Spinner)view.findViewById(R.id.spinner_year);
        editText_body = (EditText)view.findViewById(R.id.editText_body);
        editText_subject = (EditText)view.findViewById(R.id.editText_subject);

        getDeptList();
        ArrayList<String> yearList = new ArrayList<>(5);
        yearList.add(0,"-");
        yearList.add(1,"FE");
        yearList.add(2, "SE");
        yearList.add(3,"TE");
        yearList.add(4,"BE");

        ArrayList<String> classList = new ArrayList<>(5);
        classList.add(0,"-");
        classList.add(1,"A");
        classList.add(2, "B");


        ArrayList<String> batchList = new ArrayList<>(5);
        batchList.add(0,"-");
        batchList.add(1,"1");
        batchList.add(2, "2");
        batchList.add(3, "3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,yearList);
        spinner_year.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,batchList);
        spinner_batch.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,classList);
        spinner_class.setAdapter(adapter);




        return view;


    }

    public void sendNotifToStudents() {

        String dept = spinner_dept.getSelectedItem().toString();
        String Class = spinner_class.getSelectedItem().toString();
        String year = spinner_year.getSelectedItem().toString();
        String batch = spinner_batch.getSelectedItem().toString();

        String subject = editText_subject.getText().toString();
        String body = editText_body.getText().toString();

        int choice=0;
        if(dept.equals("-"))
            choice=0;
        else
        {
            if(year.equals("-"))
                choice=1;
            else
            {
                if(Class.equals("-"))
                    choice=2;
                else
                {
                    if(batch.equals("-"))
                        choice=3;
                    else
                        choice=4;
                }
            }
        }

        String link = "https://000attendance-system.000webhostapp.com/AdministratorApp/Notifications/student_notifications.php";

        phpSendNotif p = new phpSendNotif(link,choice,dept,year,Class,batch,subject,body);
        p.execute();

    }

    private class phpSendNotif extends AsyncTask<String,String,String>
    {
        int choice;
        String link;
        String dept;
        String year;
        String Class;
        String batch;
        String subject;
        String body;

        phpSendNotif(String l, int ch, String d, String y, String c, String b, String s, String b1)
        {
            choice = ch;
            link = l;
            dept = d;
            year = y;
            Class = c;
            batch = b;
            subject = s;
            body = b1;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(view.getContext(), "Send notifications...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(view.getContext(), s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            try
            {
                String data="";
                switch(choice)
                {
                    case 0:
                        data = URLEncoder.encode("choice","UTF-8")+"="+URLEncoder.encode(Integer.toString(choice),"UTF-8")+"&"+
                                URLEncoder.encode("subject","UTF-8")+"="+URLEncoder.encode(subject,"UTF-8")+"&"+
                                URLEncoder.encode("body","UTF-8")+"="+URLEncoder.encode(body,"UTF-8");
                        break;
                    case 1:
                        data = URLEncoder.encode("choice","UTF-8")+"="+URLEncoder.encode(Integer.toString(choice),"UTF-8")+"&"+
                                URLEncoder.encode("subject","UTF-8")+"="+URLEncoder.encode(subject,"UTF-8")+"&"+
                                URLEncoder.encode("body","UTF-8")+"="+URLEncoder.encode(body,"UTF-8")+"&"+
                                URLEncoder.encode("dept","UTF-8")+"="+URLEncoder.encode(dept,"UTF-8");
                        break;
                    case 2:
                        data = URLEncoder.encode("choice","UTF-8")+"="+URLEncoder.encode(Integer.toString(choice),"UTF-8")+"&"+
                                URLEncoder.encode("subject","UTF-8")+"="+URLEncoder.encode(subject,"UTF-8")+"&"+
                                URLEncoder.encode("body","UTF-8")+"="+URLEncoder.encode(body,"UTF-8")+"&"+
                                URLEncoder.encode("dept","UTF-8")+"="+URLEncoder.encode(dept,"UTF-8")+"&"+
                                URLEncoder.encode("year","UTF-8")+"="+URLEncoder.encode(year,"UTF-8");
                        break;
                    case 3:
                        data = URLEncoder.encode("choice","UTF-8")+"="+URLEncoder.encode(Integer.toString(choice),"UTF-8")+"&"+
                                URLEncoder.encode("subject","UTF-8")+"="+URLEncoder.encode(subject,"UTF-8")+"&"+
                                URLEncoder.encode("body","UTF-8")+"="+URLEncoder.encode(body,"UTF-8")+"&"+
                                URLEncoder.encode("dept","UTF-8")+"="+URLEncoder.encode(dept,"UTF-8")+"&"+
                                URLEncoder.encode("year","UTF-8")+"="+URLEncoder.encode(year,"UTF-8")+"&"+
                                URLEncoder.encode("class","UTF-8")+"="+URLEncoder.encode(Class,"UTF-8");
                        break;
                    case 4:
                        data = URLEncoder.encode("choice","UTF-8")+"="+URLEncoder.encode(Integer.toString(choice),"UTF-8")+"&"+
                                URLEncoder.encode("subject","UTF-8")+"="+URLEncoder.encode(subject,"UTF-8")+"&"+
                                URLEncoder.encode("body","UTF-8")+"="+URLEncoder.encode(body,"UTF-8")+"&"+
                                URLEncoder.encode("dept","UTF-8")+"="+URLEncoder.encode(dept,"UTF-8")+"&"+
                                URLEncoder.encode("year","UTF-8")+"="+URLEncoder.encode(year,"UTF-8")+"&"+
                                URLEncoder.encode("class","UTF-8")+"="+URLEncoder.encode(Class,"UTF-8")+"&"+
                                URLEncoder.encode("batch","UTF-8")+"="+URLEncoder.encode(batch,"UTF-8");
                        break;

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

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                return sb.toString();

            }
            catch(Exception e)
            {
                return e.toString();
            }
        }
    }

    public void getDeptList()
    {
        String link = "https://000attendance-system.000webhostapp.com/AdministratorApp/Department/returndeptlist.php";

        phpDeptList p = new phpDeptList(link);
        p.execute();
    }

    private class phpDeptList extends AsyncTask<String,String,String>
    {
        String link;

        phpDeptList(String l)
        {
            link = l;
        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(view.getContext(), "Getting Departments...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item, deptList);
            spinner_dept.setAdapter(adapter);

            Toast.makeText(view.getContext(), "Departments Loaded...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... params) {
            try
            {
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line="";
                StringBuffer sb = new StringBuffer("");

                while((line=br.readLine())!=null)
                {
                    sb.append(line);
                    break;
                }

                JSONArray jsonArray = new JSONArray(sb.toString());
                deptCount = jsonArray.length();
                deptList = new ArrayList<>(deptCount);
                deptList.add(0,"-");

                for(int i=0 ; i<jsonArray.length() ; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String str = jsonObject.getString("DEPT_NAME");
                    deptList.add(i+1,str);
                }

                return "";
            }
            catch (Exception e)
            {
                return e.toString();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send:
                sendNotifToStudents();

                break;

        }
    }

}
