package com.example.miloud;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class dashboard extends AppCompatActivity
{
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        tv=(TextView)findViewById(R.id.tv);

        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("uname"))
            tv.setText(sp.getString("uname",""));
    }

    public void logout_process(View view)
    {
        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("uname"))
        {
            SharedPreferences.Editor editor=sp.edit();
            editor.remove("uname");

            editor.putString("msg","Logged out Successfully");
            editor.commit();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

    }
}
