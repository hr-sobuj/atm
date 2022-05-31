package com.triangle.atm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {



    private ProgressBar progressBar;
    private int process;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        progressBar=(ProgressBar) findViewById(R.id.progressbar);

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                progression();
                startNewWindow();
            }
        });
        thread.start();

    }
    public void progression()
    {
        for (process=30;process<=90;process+=30){
            try {
                Thread.sleep(1100);
                progressBar.setProgress(process);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public void startNewWindow()
    {

        Intent intent=new Intent(MainActivity.this,login.class);
        startActivity(intent);
        finish();

    }
}
