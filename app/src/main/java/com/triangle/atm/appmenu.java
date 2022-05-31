package com.triangle.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class appmenu extends AppCompatActivity implements View.OnClickListener {

    private Button btncashout,btncashin,btnaccountdetals,btntransaction,btnlogout,btnchangepin;

    String user_identity,total_balance,cashout,cashin;
    DatabaseReference reference_balance,reference_cashout;
    private TextView balancetxt;

    int cashout_tk,cashin_tk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appmenu);




        Bundle bundle=getIntent().getExtras();
        if(!bundle.isEmpty()){
            user_identity=bundle.getString("usernumber");
        }



        reference_balance= FirebaseDatabase.getInstance().getReference("ATM").child(user_identity);




        btncashout=(Button) findViewById(R.id.cashout);
/*        btnaccountdetals=(Button) findViewById(R.id.accountdetails);*/
        btnlogout=(Button) findViewById(R.id.logout);
        btntransaction=(Button) findViewById(R.id.transaction);
        btncashin=(Button) findViewById(R.id.cashin);
        btnchangepin=(Button) findViewById(R.id.changepin);

        btncashout.setOnClickListener(this);
        btntransaction.setOnClickListener(this);
/*        btnaccountdetals.setOnClickListener(this);*/
        btnlogout.setOnClickListener(this);
        btncashin.setOnClickListener(this);
        btnchangepin.setOnClickListener(this);

        balancetxt=(TextView) findViewById(R.id.balance);




        reference_balance.child("Cashout").child("ammount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    cashout=snapshot.getValue(String.class);

                    cashout_tk=Integer.parseInt(cashout);
                }
                /*else {
                    cashout_tk=0;
                }*/



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        reference_balance.child("Cashin").child("ammount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    cashin=snapshot.getValue(String.class);

                    cashin_tk=Integer.parseInt(cashin);
                }
                /*else {
                    cashin_tk=0;
                }*/




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference_balance.child("Balance").child("total_ammount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){
                    total_balance=snapshot.getValue(String.class);
/*
                    *//*int tmp=Integer.parseInt(total_balance);
                    int total=(tmp+cashin_tk)-cashout_tk;
                    String x=String.valueOf(total);*//*
                    snapshot.getRef().setValue(total_balance);*/
                    balancetxt.setText("$"+total_balance);
                }


                /*Toast.makeText(appmenu.this,cashin+cashout,Toast.LENGTH_LONG).show();*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.profile_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.profile){



            Intent intent= new Intent(appmenu.this, account_details.class);
            intent.putExtra("usernumber",user_identity);
            startActivity(intent);
            finish();


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        Intent intent=new Intent(appmenu.this,login.class);
        startActivity(intent);
        finish();

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


    @Override
    public void onClick(View v) {

        if(v==btncashout){
            Intent intent=new Intent(appmenu.this,cashout.class);
            intent.putExtra("usernumber",user_identity);
            startActivity(intent);
        }
        if(v==btntransaction){
            Intent intent=new Intent(appmenu.this,transaction.class);

            intent.putExtra("usernumber",user_identity);
            startActivity(intent);
        }
        if(v==btnchangepin){
            Intent intent=new Intent(appmenu.this,change_pin.class);
            intent.putExtra("usernumber",user_identity);
            startActivity(intent);
        }

        if(v==btncashin){
            Intent intent=new Intent(appmenu.this,cashin.class);
            intent.putExtra("usernumber",user_identity);
            startActivity(intent);
        }

        if(v==btnlogout){
            Intent intent=new Intent(appmenu.this,login.class);
            intent.putExtra("usernumber",user_identity);
            startActivity(intent);
            finish();
        }



    }
}
