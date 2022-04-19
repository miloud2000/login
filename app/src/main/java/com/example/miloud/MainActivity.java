package com.example.miloud;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    private static final String apiurl="http://169.254.67.182:8081/register/login.php";
    EditText t1,t2;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void Go(View v) {
        //on creer une nouvelle intent on definit la class de depart ici this et la class d'arrivé ici SecondActivite
        Intent intent=new Intent(this,register.class);
        //on lance l'intent, cela a pour effet de stoper l'activité courante et lancer une autre activite ici SecondActivite
        startActivity(intent);


    }
    public void Back(View v) {
        //pour retourner a l’activite principale il suffit seulement d’appler la methode finish() qui vas tuer cette activite

        finish() ;

    }

    public void login_process(View view)
    {
        t1=(EditText)findViewById(R.id.t1);
        t2=(EditText)findViewById(R.id.t2);
        tv=(TextView)findViewById(R.id.tv);

        String qry="?t1="+t1.getText().toString().trim()+"&t2="+t2.getText().toString().trim();

        class dbprocess extends AsyncTask<String,Void,String>
        {
            @Override
            protected  void onPostExecute(String data)
            {
                if(data.equals("found"))
                {

                    startActivity(new Intent(getApplicationContext(),dashboard.class));
                }
                else
                {
                    t1.setText("");
                    t2.setText("");
                    tv.setTextColor(Color.parseColor("#8B0000"));
                    tv.setText(data);
                }
            }
            @Override
            protected String doInBackground(String... params)
            {
                String furl=params[0];

                try
                {
                    URL url=new URL(furl);
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    return br.readLine();

                }catch (Exception ex)
                {
                    return ex.getMessage();
                }
            }
        }

        dbprocess obj=new dbprocess();
        obj.execute(apiurl+qry);

    }

    public void checklogoutmsg(View view)
    {
        tv=(TextView) findViewById(R.id.tv);

        SharedPreferences sp=getSharedPreferences("credentials",MODE_PRIVATE);
        if(sp.contains("msg"))
        {
            tv.setText(sp.getString("msg", ""));
            SharedPreferences.Editor ed=sp.edit();
            ed.remove("msg");
            ed.commit();
        }
    }
}
