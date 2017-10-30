package com.example.teproject.myattendance;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class ApplicationStatusActivity extends AppCompatActivity {

    EditText editText_id;
    Button button_Student;
    Button button_Teacher;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_status);

        editText_id = (EditText)findViewById(R.id.editText_id);
        button_Student = (Button)findViewById(R.id.button_Student);
        button_Teacher = (Button)findViewById(R.id.button_Teacher);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    public void OnClickStudentButton(View view)
    {
        String id = editText_id.getText().toString();
        if(id.length() != 7)
        {
            Toast.makeText(this, "Enter Valid ID!", Toast.LENGTH_SHORT).show();
            return;
        }
        String link = "https://000attendance-system.000webhostapp.com/AdministratorApp/Signup/application_status.php";
        phpApplicationStatus p = new phpApplicationStatus(link, "1", id);
        p.execute();
    }

    public void OnClickTeacherButton(View view)
    {
        String id = editText_id.getText().toString();
        if(id.length() != 6)
        {
            Toast.makeText(this, "Enter Valid ID!", Toast.LENGTH_SHORT).show();
            return;
        }
        String link = "https://000attendance-system.000webhostapp.com/AdministratorApp/Signup/application_status.php";
        phpApplicationStatus p = new phpApplicationStatus(link, "2", id);
        p.execute();
    }

    private class phpApplicationStatus extends AsyncTask<String,String,String>
    {
        String link;
        String choice;
        String id;

        phpApplicationStatus(String link, String choice, String id)
        {
            this.link = link;
            this.choice = choice;
            this.id = id;
        }

        @Override
        protected void onPreExecute() {
            editText_id.setEnabled(false);
            button_Teacher.setEnabled(false);
            button_Student.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            Toast.makeText(ApplicationStatusActivity.this, "Checking Status...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(String s) {
            editText_id.setEnabled(true);
            button_Teacher.setEnabled(true);
            button_Student.setEnabled(true);
            progressBar.setVisibility(View.GONE);
            android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(ApplicationStatusActivity.this).create();
            alertDialog.setTitle("Application Status");
            alertDialog.setMessage(s);
            alertDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);

                String data = URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                        URLEncoder.encode("choice","UTF-8")+"="+URLEncoder.encode(choice,"UTF-8");

                OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());
                out.write(data);
                out.flush();

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                StringBuffer sb = new StringBuffer("");

                while((line=br.readLine())!=null)
                {
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
}
