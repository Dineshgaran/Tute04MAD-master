package com.example.tute04mad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button ok, login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ok = (Button) findViewById(R.id.btnOk);
        login = (Button) findViewById(R.id.btnLogin);
        buttonClickActivity();

    }

    private void buttonClickActivity() {
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit Intent
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("Extra1", "Hello");
                intent.putExtra("Extra2", "World");
                startActivity(intent);

                // Implicit Intent
               /* Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.google.com/"));
                startActivity(intent);*/
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogin = new Intent(getApplicationContext(), Login.class);
                startActivity(intentLogin);
            }
        });
    }
}
