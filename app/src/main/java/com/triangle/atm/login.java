package com.triangle.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    private Button btnlogin;
    private TextView txtregistration;

    private EditText accnumber,accpin;
    String name,pin,number,valname,valnumber,valpin;
    private ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        accnumber=(EditText) findViewById(R.id.lognumber);
        accpin=(EditText) findViewById(R.id.logpin);

        databaseReference= FirebaseDatabase.getInstance().getReference("ATM");

        btnlogin=(Button) findViewById(R.id.adminloginic);
        txtregistration=(TextView) findViewById(R.id.textreg);
        txtregistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(login.this,create_account.class);
                startActivity(intent);
            }
        });


        if(isOnline()){


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(login.this,appmenu.class);
                startActivity(intent);*/

                number=accnumber.getText().toString();
                pin=accpin.getText().toString();




                databaseReference.child(number).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        valnumber=snapshot.child("usernumber").getValue(String.class);
                        valname=snapshot.child("username").getValue(String.class);
                        valpin=snapshot.child("userpin").getValue(String.class);



                   /*     assert valpin != null;*/
                        if(number.equals(valnumber) && valpin.equals(pin) ){

                            /*Toast.makeText(login.this,"x."+valname+valpin+valnumber,Toast.LENGTH_LONG).show();*/

                            Intent intent=new Intent(login.this,appmenu.class);
                            intent.putExtra("usernumber",number);
                            startActivity(intent);


                        }

                        else {


                            Toast.makeText(login.this,"Log in Failed.",Toast.LENGTH_LONG).show();



                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



            }
        });

        }
        else {


            Toast.makeText(login.this,"Check your internet connection.",Toast.LENGTH_LONG).show();
        }


    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
