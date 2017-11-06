package com.example.teproject.myattendance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.io.BufferedReader;

public class TeacherSignupActivity extends AppCompatActivity {




    Button button_SubmitButton;
    LinearLayout linearLayout;
    TextView textView_name;
    TextView textView_email;
    TextView textView_address;
    TextView textView_phone1;
    TextView textView_phone2;
    TextView textView_department;
    TextView textView_subjects;

    EditText editText_name;
    EditText editText_email;
    EditText editText_address;
    EditText editText_phone1;
    EditText editText_phone2;

    Spinner spinner_department;
    Spinner spinner_subjects;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_signup);

        button_SubmitButton = (Button)findViewById(R.id.button_SubmitButton);
        linearLayout = (LinearLayout)findViewById(R.id.linearLayout);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        SetLinearLayout();
    }

    public void SetLinearLayout()
    {
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);

        textView_name = new TextView(this);
        textView_name.setText("Name:");
        textView_name.setGravity(Gravity.LEFT);
        textView_name.setTextSize(22);
        textView_name.setLayoutParams(params);
        linearLayout.addView(textView_name);

        editText_name = new EditText(this);
        editText_name.setGravity(Gravity.LEFT);
        editText_name.setLayoutParams(params);
        linearLayout.addView(editText_name);

        textView_email = new TextView(this);
        textView_email.setText("Email Address:");
        textView_email.setGravity(Gravity.LEFT);
        textView_email.setTextSize(22);
        textView_email.setLayoutParams(params);
        linearLayout.addView(textView_email);

        editText_email = new EditText(this);
        editText_email.setGravity(Gravity.LEFT);
        editText_email.setLayoutParams(params);
        linearLayout.addView(editText_email);

        textView_address = new TextView(this);
        textView_address.setText("Address:");
        textView_address.setGravity(Gravity.LEFT);
        textView_address.setTextSize(22);
        textView_address.setLayoutParams(params);
        linearLayout.addView(textView_address);

        editText_address = new EditText(this);
        editText_address.setGravity(Gravity.LEFT);
        LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400, 1);
        editText_address.setLayoutParams(params1);
        linearLayout.addView(editText_address);

        textView_phone1 = new TextView(this);
        textView_phone1.setText("Phone 1:");
        textView_phone1.setTextSize(22);
        textView_phone1.setGravity(Gravity.LEFT);
        textView_phone1.setLayoutParams(params);
        linearLayout.addView(textView_phone1);

        editText_phone1 = new EditText(this);
        editText_phone1.setGravity(Gravity.LEFT);
        editText_phone1.setLayoutParams(params);
        linearLayout.addView(editText_phone1);

        textView_phone2 = new TextView(this);
        textView_phone2.setGravity(Gravity.LEFT);
        textView_phone2.setText("Phone 2:");
        textView_phone2.setTextSize(22);
        textView_phone2.setLayoutParams(params);
        linearLayout.addView(textView_phone2);

        editText_phone2 = new EditText(this);
        editText_phone2.setGravity(Gravity.LEFT);
        editText_phone2.setLayoutParams(params);
        linearLayout.addView(editText_phone2);

        textView_department = new TextView(this);
        textView_department.setText("Departments: ");
        textView_department.setGravity(Gravity.LEFT);
        textView_department.setTextSize(22);
        textView_department.setLayoutParams(params);
        linearLayout.addView(textView_department);

        spinner_department = new Spinner(this);
        spinner_department.setGravity(Gravity.CENTER);
        spinner_department.setLayoutParams(params);
        linearLayout.addView(spinner_department);

        textView_subjects = new TextView(this);
        textView_subjects.setText("Subject: ");
        textView_subjects.setTextSize(22);
        textView_subjects.setGravity(Gravity.LEFT);
        textView_subjects.setLayoutParams(params);
        linearLayout.addView(textView_subjects);

        spinner_subjects = new Spinner(this);
        spinner_subjects.setGravity(Gravity.CENTER);
        spinner_subjects.setLayoutParams(params);
        linearLayout.addView(spinner_subjects);

        String l = "https://000attendance-system.000webhostapp.com/AdministratorApp/Department/returndeptlist.php";

        phpReceiveList p = new phpReceiveList(l);
        p.execute();

        spinner_department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String link = "https://000attendance-system.000webhostapp.com/AdministratorApp/Subjects/return_deptsubjects.php?";

                phpReceiveSubjects sub = new phpReceiveSubjects(link, (String)spinner_department.getSelectedItem());
                sub.execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void onClickSubmitButton(View view)
    {
        String name = (editText_name.getText().toString()).trim();
        String email = editText_email.getText().toString().trim();
        String address = editText_address.getText().toString().trim();
        String phone1 = editText_phone1.getText().toString().trim();
        String phone2 = editText_phone2.getText().toString().trim();
        String dept = spinner_department.getSelectedItem().toString().trim();
        String subject = spinner_subjects.getSelectedItem().toString().trim();

        if(name.length()==0 && email.length()==0 && address.length()==0 && phone1.length()==0)
        {
            Toast.makeText(this, "Enter the complete form!", Toast.LENGTH_SHORT).show();
            return;
        }

        String link = "https://000attendance-system.000webhostapp.com/AdministratorApp/Signup/teacher_signup.php";

        phpUploadForm form = new phpUploadForm(link,dept,subject,name,email,address,phone1,phone2);
        form.execute();
    }

    private class phpReceiveList extends AsyncTask<String, String, String>
    {
        String link;
        ArrayList<String> deptList = new ArrayList<>();
        int deptCount = 0;

        phpReceiveList(String l)
        {
            link = l;
        }

        @Override
        protected void onPreExecute() {
            editText_phone1.setEnabled(false);
            editText_address.setEnabled(false);
            editText_email.setEnabled(false);
            editText_name.setEnabled(false);
            editText_phone2.setEnabled(false);
            button_SubmitButton.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            Toast.makeText(TeacherSignupActivity.this, "Loading Departments...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            editText_phone1.setEnabled(true);
            editText_address.setEnabled(true);
            editText_email.setEnabled(true);
            editText_name.setEnabled(true);
            editText_phone2.setEnabled(true);
            button_SubmitButton.setEnabled(true);
            progressBar.setVisibility(View.GONE);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TeacherSignupActivity.this, android.R.layout.simple_spinner_dropdown_item, deptList);
            spinner_department.setAdapter(adapter);
            Toast.makeText(TeacherSignupActivity.this, "Departments Loaded!", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.i("DEPTLIST" , "doInBackground");
            try
            {
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = null;
                StringBuffer sb = new StringBuffer("");

                while((line=br.readLine())!=null)
                {
                    sb.append(line);
                    break;
                }

                JSONArray jsonArray = new JSONArray(sb.toString());

                deptCount = jsonArray.length();
                //deptList = new ArrayList<>(deptCount);

                for(int i=0 ; i<deptCount ; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String str = jsonObject.getString("DEPT_NAME");
                    deptList.add(i,str);
                }

                return "";
            }
            catch(Exception e)
            {
                Log.i("DEPTLIST", e.toString());
                return e.toString();
            }
        }
    }

    private class phpReceiveSubjects extends AsyncTask<String, String, String>
    {
        String link;
        String dept;

        ArrayList<String> subjectsList = new ArrayList<>();

        phpReceiveSubjects(String link, String dept)
        {
            this.link = link;
            this.dept = dept;
        }

        @Override
        protected void onPreExecute() {
            editText_address.setEnabled(false);
            editText_name.setEnabled(false);
            editText_email.setEnabled(false);
            editText_phone1.setEnabled(false);
            editText_phone2.setEnabled(false);
            spinner_department.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            Toast.makeText(TeacherSignupActivity.this, "Loading Subjects...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            editText_phone1.setEnabled(true);
            editText_address.setEnabled(true);
            editText_email.setEnabled(true);
            editText_name.setEnabled(true);
            editText_phone2.setEnabled(true);
            progressBar.setVisibility(View.GONE);
            spinner_department.setEnabled(true);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(TeacherSignupActivity.this, android.R.layout.simple_spinner_dropdown_item, subjectsList);
            spinner_subjects.setAdapter(adapter);

            Toast.makeText(TeacherSignupActivity.this, s, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                String data = URLEncoder.encode("dept","UTF-8") + "=" + URLEncoder.encode(dept, "UTF-8");

                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(data);
                out.flush();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                StringBuffer sb = new StringBuffer("");

                while((line = br.readLine())!=null)
                {
                    sb.append(line);
                    break;
                }

                JSONArray jsonArray = new JSONArray(sb.toString());
                //Log.i("DEPTLIST", sb.toString());
                int subjectCount = 0;
                String sub = "";

                for(int i=0 ; i<jsonArray.length() ; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    for(int j=1 ; j<=10 ; j++)
                    {
                        sub = "sub" + Integer.toString(j);
                        //Log.i("DEPTLIST", sub);
                        String temp = jsonObject.getString(sub);
                        if(temp.length()==1 || temp.length()==0)
                        {
                            //Log.i("DEPTLIST","*");
                            continue;
                        }
                        subjectsList.add(subjectCount, temp);
                        //Log.i("DEPTLIST" , temp);
                        subjectCount++;
                    }
                }

                return "Subjects Loaded!";
            }
            catch(Exception e)
            {
                //Log.i("DEPTLIST", e.toString());
                return e.toString();
            }
        }
    }

    private class phpUploadForm extends AsyncTask<String, String, String>
    {
        String link;
        String name;
        String email;
        String address;
        String phone1;
        String phone2;
        String dept;
        String subject;

        phpUploadForm(String link, String dept, String subject, String name, String email, String address, String phone1, String phone2)
        {
            this.link = link;
            this.dept = dept;
            this.subject = subject;
            this.name = name;
            this.email = email;
            this.address = address;
            this.phone1 = phone1;
            this.phone2 = phone2;
        }

        @Override
        protected void onPreExecute() {
            editText_email.setEnabled(false);
            editText_name.setEnabled(false);
            editText_phone1.setEnabled(false);
            editText_address.setEnabled(false);
            editText_phone2.setEnabled(false);
            spinner_department.setEnabled(false);
            spinner_subjects.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);

            Toast.makeText(TeacherSignupActivity.this, "Submitting form...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            editText_email.setEnabled(true);
            editText_name.setEnabled(true);
            editText_phone1.setEnabled(true);
            editText_address.setEnabled(true);
            editText_phone2.setEnabled(true);
            spinner_department.setEnabled(true);
            spinner_subjects.setEnabled(true);
            progressBar.setVisibility(View.GONE);

            AlertDialog alertDialog = new AlertDialog.Builder(TeacherSignupActivity.this).create();
            alertDialog.setTitle("Application ID");
            alertDialog.setMessage("Your Application ID is:"+s+".\nRemember this ID.");
            alertDialog.show();
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")+"&"+
                        URLEncoder.encode("phone1","UTF-8")+"="+URLEncoder.encode(phone1,"UTF-8")+"&"+
                        URLEncoder.encode("phone2","UTF-8")+"="+URLEncoder.encode(phone2,"UTF-8")+"&"+
                        URLEncoder.encode("dept","UTF-8")+"="+URLEncoder.encode(dept,"UTF-8")+"&"+
                        URLEncoder.encode("subject","UTF-8")+"="+URLEncoder.encode(subject,"UTF-8");

                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(data);
                out.flush();

                BufferedReader br = new BufferedReader((new InputStreamReader(conn.getInputStream())));
                String line = null;
                StringBuffer sb = new StringBuffer("");

                while((line=br.readLine())!=null)
                {
                    sb.append(line);
                    break;
                }

                Log.i("DEPTLIST", sb.toString());

                return sb.toString();
            }
            catch(Exception e)
            {
                Log.i("DEPTLIST", e.toString());
                return e.toString();
            }
        }
    }
}
