package com.triangle.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class registration_validation extends AppCompatActivity {

    String name,pin,number,valname,valnumber,valpin;


    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_validation);

        databaseReference= FirebaseDatabase.getInstance().getReference("ATM");

        Bundle bundle=getIntent().getExtras();
        if(!bundle.isEmpty()){
            name=bundle.getString("name");
            pin=bundle.getString("pin");
            number=bundle.getString("number");

        }


        databaseReference.child(number).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                valnumber=snapshot.child("usernumber").getValue(String.class);
                valname=snapshot.child("username").getValue(String.class);
                valpin=snapshot.child("userpin").getValue(String.class);

                if(!number.equals(valnumber) || !valpin.equals(pin) || !valname.equals(name)){


                    dbref ref=new dbref(name,number,pin);
                    databaseReference.child(number).setValue(ref);

                    Toast.makeText(registration_validation.this,"Registration Successful."+valname+valpin+valnumber,Toast.LENGTH_LONG).show();

                }

                else {

                    Toast.makeText(registration_validation.this,"Phone number already registered",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(registration_validation.this,create_account.class);
                    startActivity(intent);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}
