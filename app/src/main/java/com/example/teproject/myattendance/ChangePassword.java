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
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by vasantkapare on 30-10-2017.
 */

public class ChangePassword extends Fragment implements View.OnClickListener{

    View view;
    String rollno,currPass,newPass;
    int mtsflag;
    Button spButton;

    EditText curPassET;
    EditText newPassET;
    EditText confPassET;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.change_password_frag,container,false);

        spButton= (Button)view.findViewById(R.id.spButton);
        spButton.setOnClickListener(this);

        curPassET = (EditText)view.findViewById(R.id.currPassET);
        newPassET = (EditText)view.findViewById(R.id.newPassET);
        confPassET= (EditText)view.findViewById(R.id.confPassET);

        if(getArguments()!=null)
        {
            rollno=getArguments().getString("roll");
            mtsflag=Integer.parseInt(getArguments().getString("tsflag"));
            Log.i("temp","ooo boi"+rollno+Integer.toString(mtsflag));

        }

        TextView changePassTV =(TextView)view.findViewById(R.id.changPassTV);
        changePassTV.setText("ID: "+rollno);


        return view;
    }


    @Override
    public void onClick(View v) {
        currPass=curPassET.getText().toString();
        newPass = newPassET.getText().toString();
        if(!(newPassET.getText().toString().equals(confPassET.getText().toString())))
        {
            newPassET.setText("");
            confPassET.setText("");

            Toast.makeText(view.getContext(),"Passwords dont match",Toast.LENGTH_LONG).show();

            newPassET.requestFocus();
        }
        else
        {
            checkPass cp = new checkPass();
            cp.execute();

            Toast.makeText(view.getContext(),"Uploading Please Wait",Toast.LENGTH_LONG).show();
        }







}
   private class checkPass extends AsyncTask <Void,Void,Boolean>
    {

        @Override
        protected Boolean doInBackground(Void... params) {

            String link;
            {   if(mtsflag==0) {
                link= "http://000attendance-system.000webhostapp.com/student_login/login.php";
            }
            else{
                link= "http://000attendance-system.000webhostapp.com/teacher_login/login.php";
            }

                String res = null;
                try{



                    String data  = URLEncoder.encode("rollno", "UTF-8") + "=" +
                            URLEncoder.encode((rollno), "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                            URLEncoder.encode(currPass, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();


                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    res=sb.toString();

                    Log.i("phpTEST",res);
                } catch(Exception e){
                    Log.i("phpTEST","Exception: " + e.getMessage());
                }

                if(res.equals("INVALID_ROLLNO"))
                {

                    // Toast.makeText(getApplicationContext(),"Roll no invalid!",Toast.LENGTH_LONG).show();
                    return false;
                }
                else if(res.equals("INVALID_PASSWORD"))
                {

                    // Toast.makeText(getApplicationContext(),"Password invalid!",Toast.LENGTH_LONG).show();
                    return false;
                }
                else if(res.equals("VALID_LOGIN")){

                    return true;
                }

            }


            return false;

        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {

            if(aBoolean)
            {
                uploadPass u = new uploadPass();
                u.execute();
            }
            else
            {
                Toast.makeText(view.getContext(),"Invalid Password",Toast.LENGTH_LONG).show();
            }
        }
    }

   private class uploadPass extends AsyncTask <Void,Void,String>
    {
        @Override
        protected String doInBackground(Void... params) {
            String res = null;
            String link;
            {   if(mtsflag==0) {
                link= "http://000attendance-system.000webhostapp.com/student_login/changePass.php";
            }
            else{
                link= "http://000attendance-system.000webhostapp.com/teacher_login/changePass.php";
            }


                try{



                    String data  = URLEncoder.encode("rollno", "UTF-8") + "=" +
                            URLEncoder.encode((rollno), "UTF-8");
                    data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
                            URLEncoder.encode(newPass, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write( data );
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();


                    String line = null;

                    // Read Server Response
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }

                    res=sb.toString();

                    Log.i("phpTEST",res);
                } catch(Exception e){
                    Log.i("phpTEST","Exception: " + e.getMessage());
                }

            }
            return res;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(view.getContext(),s,Toast.LENGTH_LONG).show();
        }
    }
}
