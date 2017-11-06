package com.example.teproject.myattendance;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Created by vasantkapare on 28-10-2017.
 */

public class ViewTTforTeachers extends Fragment implements View.OnClickListener {
    @Nullable

    View view;
    Button vtt;

    Spinner spinner_dept;
    Spinner spinner_sem;
    Spinner spinner_class;
    Spinner spinner_batch;

    ArrayList<String> deptList;
    int deptCount;

    Bundle bundle;
    android.support.v4.app.Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.selectfortimetable,container,false);

        vtt=(Button)view.findViewById(R.id.ViewTTButton);
        vtt.setOnClickListener(this);

        spinner_batch = (Spinner)view.findViewById(R.id.BatchSpinner);
        spinner_class = (Spinner)view.findViewById(R.id.DivSpinner);
        spinner_dept = (Spinner)view.findViewById(R.id.DeptSpinner);
        spinner_sem = (Spinner)view.findViewById(R.id.SemSpinner);


        getDeptList();
        ArrayList<String> yearList = new ArrayList<>(5);
        yearList.add(0,"1");
        yearList.add(1,"2");
        yearList.add(2,"3");
        yearList.add(3,"4");
        yearList.add(4,"5");
        yearList.add(5,"6");
        yearList.add(6,"7");
        yearList.add(7,"8");

        ArrayList<String> classList = new ArrayList<>(5);

        classList.add(0,"A");
        classList.add(1, "B");


        ArrayList<String> batchList = new ArrayList<>(5);

        batchList.add(0,"1");
        batchList.add(1, "2");
        batchList.add(2, "3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,yearList);
        spinner_sem.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,batchList);
        spinner_batch.setAdapter(adapter);

        adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,classList);
        spinner_class.setAdapter(adapter);


        return view;
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
               // deptList.add(0,"-");

                for(int i=0 ; i<jsonArray.length() ; i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String str = jsonObject.getString("DEPT_NAME");
                    deptList.add(i,str);
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
        if(v.getId()==R.id.ViewTTButton)
        {

            bundle = new Bundle();
            bundle.putString("roll", "0");

            bundle.putString("dept", spinner_dept.getSelectedItem().toString());
            bundle.putString("sem", spinner_sem.getSelectedItem().toString());
            bundle.putString("div", spinner_class.getSelectedItem().toString());
            bundle.putString("batch", spinner_batch.getSelectedItem().toString());

            fragment = new viewTTFragment();
            fragment.setArguments(bundle);


                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.contents_frame, fragment);
                ft.commit();

        }
    }
}
