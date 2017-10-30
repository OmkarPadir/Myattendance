package com.example.teproject.myattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {



    Button button_TeacherSignup;
    Button button_StudentSignup;
    Button button_ApplicationStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        button_TeacherSignup = (Button)findViewById(R.id.button_TeacherSignup);
        button_StudentSignup = (Button)findViewById(R.id.button_StudentSignup);
        button_ApplicationStatus = (Button)findViewById(R.id.button_ApplicationStatus);
    }

    public void onClickTeacherSignupButton(View view)
    {
        Intent intent = new Intent(this.getApplicationContext(), TeacherSignupActivity.class);
        getApplicationContext().startActivity(intent);
    }

    public void onClickStudentSignupButton(View view)
    {
        Intent intent = new Intent(this.getApplicationContext(), StudentSignupActivity.class);
        getApplicationContext().startActivity(intent);
    }

    public void onClickApplicationStatusButton(View view)
    {
        Intent intent = new Intent(this.getApplicationContext(), ApplicationStatusActivity.class);
        getApplicationContext().startActivity(intent);
    }

   /* private class InternetHandler extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }*/

}
