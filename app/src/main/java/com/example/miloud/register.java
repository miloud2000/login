package com.example.miloud;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class register extends AppCompatActivity {
private EditText username,email,password,c_password;
private Button regbtn ;
//private progrssbar
private static String URL_REGIST="http://169.254.45.209:8081/register/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        regbtn=findViewById(R.id.regbtn);
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Regist();
            }
        });
    }



    private void Regist(){
        regbtn.setVisibility(View.GONE);
        final String username =this.username.getText().toString().trim();
        final String email =this.email.getText().toString().trim();
        final String password =this.password.getText().toString().trim();
        StringRequest stringRequest =new StringRequest(Request.Method.POST,URL_REGIST, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             try{
                 JSONObject jsonObject =new JSONObject(response);
                 String success= (String) jsonObject.get("SUCCESS");
                 if(success.equals("1")) {
                     Toast.makeText(register.this, "REGISTER SUCCESS ", Toast.LENGTH_SHORT).show();
                 }

             } catch (JSONException e) {
                 e.printStackTrace();
                 Toast.makeText(register.this, "REGISTER ERROR " +e.toString(), Toast.LENGTH_SHORT).show();
                  regbtn.setVisibility(View.VISIBLE);
             }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(register.this, "REGISTER ERROR " + error.toString(), Toast.LENGTH_SHORT).show();
                regbtn.setVisibility(View.VISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                6000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
          }
}