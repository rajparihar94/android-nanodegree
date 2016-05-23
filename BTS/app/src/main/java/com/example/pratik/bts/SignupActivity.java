package com.example.pratik.bts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    EditText editTextUserName,editTextPassword,branch,enrollment_number,year;
    Button btnCreateAccount;
    private RequestQueue requestQueue;
    private static String URL = Constants.URL_PATH+"index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        //editTextPassword.setTransformationMethod(new AsteriskPasswordTransformationMethod());
        branch=(EditText)findViewById(R.id.branch);
        enrollment_number=(EditText)findViewById(R.id.enroll_number);
        year=(EditText)findViewById(R.id.year);

        btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);

        requestQueue = BTS_Volley.getInstance(getApplicationContext()).getRequestQueue();

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String userName=editTextUserName.getText().toString();
                final String br=branch.getText().toString();
                final String yr=year.getText().toString();
                final String password=editTextPassword.getText().toString();
                final String en=enrollment_number.getText().toString();
                /*if (!isValidBranch(br)) {
                    branch.setError("Invalid Branch");
                    }

                if (!isValidYear(yr)) {
                    year.setError("Invalid Year");
                }

                if (!isValidPassword(password)) {
                    editTextPassword.setError("Invalid Password");
                }

                if (!isValidEnrollmentNumber(en)) {
                    enrollment_number.setError("Invalid Enrollment Number");
                }*/
                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||br.equals("")||en.equals("")||yr.equals(""))
                {
                    Toast.makeText(getApplicationContext(), "Field Vaccant", Toast.LENGTH_LONG).show();
                    return;
                }
// check if both password matches

                else
                {
                    signup(editTextUserName.getText().toString(),enrollment_number.getText().toString(),branch.getText().toString(),year.getText().toString(),editTextPassword.getText().toString());
// Save the Data in Database
//loginDataBaseAdapter.insertEntry(userName,en,br,yr,password);
                    /*String enrollment=loginDataBaseAdapter.getSinlgeEntry(en);
                    if(en.equals(enrollment))
                    {
                        Toast.makeText(getApplicationContext(), "Account Already Created ", Toast.LENGTH_LONG).show();
                    }

                    else{
                        loginDataBaseAdapter.insertEntry(userName,en,br,yr,password);
                        Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                    }*/

                }
            }
        });
    }
    private boolean isValidPassword(String password) {
        // TODO Auto-generated method stub
        String PASSWORD_PATTERN = "^[A_Z a-z 0-9 !+]";


        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (password != null && password.length() <9) {
            return matcher.matches();
        }else
            return false;

    }
    private boolean isValidYear(String yr) {
        // TODO Auto-generated method stub
        if(yr.length()>4)
        { return false; }
        else
        return true;
    }
    private boolean isValidBranch(String br) {
        // TODO Auto-generated method stub
        String BRANCH_PATTERN="[A-Z]";
        Pattern pattern = Pattern.compile(BRANCH_PATTERN);
        Matcher matcher = pattern.matcher(br);
        if(br.length()>2)
        { return matcher.matches();}
        else
        return false;
    }
    protected boolean isValidEnrollmentNumber(String en) {

        // TODO Auto-generated method stub
        String ENROLLMENT_PATTERN = "^[0802+]+(\\.[_A-Z]+)"
                + "[0-9]";

        Pattern pattern = Pattern.compile(ENROLLMENT_PATTERN);
        Matcher matcher = pattern.matcher(en);
        if (en != null && en.length() <=12) {
            return matcher.matches();
        }
        else
            return false;
    }


    public void signup(final String name, final String enrollment, final String branch, final String year, final String password){
        try {
            StringRequest r = new StringRequest(Request.Method.POST,
                    URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Log.e("url : ",url);
                            Log.e("User"," user:- "+name +" Pass:- "+ password + " enroll:- "+ enrollment +" branch:- "+ branch +" year:- "+ year);
                            try {
                                int jsonStart = response.indexOf("{");
                                int jsonEnd = response.lastIndexOf("}");

                                if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                                    response = response.substring(jsonStart, jsonEnd + 1);
                                } else {
                                    // deal with the absence of JSON content here
                                }

                                JSONObject jsonObject = new JSONObject(response);
                                Log.d("Data coming : ",jsonObject.toString());
                                switch (jsonObject.getString("status")) {
                                    case "success":

                                        Toast.makeText(SignupActivity.this, " Registration Done", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(i);
                                        finish();
                                        break;
                                    case "fail":
                                        Toast.makeText(SignupActivity.this, jsonObject.getString("status")+" Fail", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.v("Connection url ",url);
                            Toast.makeText(SignupActivity.this, "Something went wrong: " + error, Toast.LENGTH_SHORT).show();
                        }
                    }
            )   {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> m = new HashMap<>();
                    m.put(Constants.ATTRIBUTE_STUDENT_NAME, name);
                    m.put(Constants.ATTRIBUTE_STUDENT_ENROLLMENT, enrollment);
                    m.put(Constants.ATTRIBUTE_STUDENT_BRANCH, branch);
                    m.put(Constants.ATTRIBUTE_STUDENT_YEAR, year);
                    m.put(Constants.ATTRIBUTE_STUDENT_PASSWORD, password);
                    m.put(Constants.CLIENT_REQUEST,Constants.REQUEST_SIGNUP);
                    return m;
                }
            };


            requestQueue.add(r);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Something went wrong: " + ex, Toast.LENGTH_LONG).show();
        }

    }
}
