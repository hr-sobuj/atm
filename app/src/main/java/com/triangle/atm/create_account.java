package com.triangle.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class create_account extends AppCompatActivity {

    private EditText accnumber,accpin,accname;
    private Button btnsubmit;

    private ProgressDialog progressDialog;
    DatabaseReference databaseReference;
    String name,pin,number,valname,valnumber,valpin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        accname=(EditText) findViewById(R.id.accountname);
        accpin=(EditText) findViewById(R.id.accountpin);
        accnumber=(EditText) findViewById(R.id.accountnumber);

        databaseReference=FirebaseDatabase.getInstance().getReference("ATM");

        /*progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Processing..");*/

        btnsubmit=(Button) findViewById(R.id.registration);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=accname.getText().toString();
                pin=accpin.getText().toString();
                number=accnumber.getText().toString();

               /* Intent intent=new Intent(create_account.this,registration_validation.class);
                intent.putExtra("name",name);
                intent.putExtra("pin",pin);
                intent.putExtra("number",number);

                startActivity(intent);*/


                databaseReference.child(number).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                       /* progressDialog.dismiss();*/
                        valnumber=snapshot.child("usernumber").getValue(String.class);
                        valname=snapshot.child("username").getValue(String.class);
                        valpin=snapshot.child("userpin").getValue(String.class);

                        if(!number.equals(valnumber) || !valpin.equals(pin) || !valname.equals(name)){


                            dbref ref=new dbref(name,number,pin);
                            databaseReference.child(number).setValue(ref);
                            databaseReference.child(number).child("Balance").child("total_ammount").setValue("500");

                            Toast.makeText(create_account.this,"Registration Successful.",Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(create_account.this,login.class);
                            startActivity(intent);

                        }

                        else {

                            Toast.makeText(create_account.this,"Phone number already registered",Toast.LENGTH_LONG).show();


                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




/*                databaseReference.child(number).orderByChild(number).equalTo(number).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if(snapshot.exists()){


                            Toast.makeText(create_account.this,"Phone number already registered",Toast.LENGTH_LONG).show();
                        }
                        else {
                            dbref ref=new dbref(name,number,pin);
                            databaseReference.child(number).setValue(ref);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/







            }
        });

    }
}
