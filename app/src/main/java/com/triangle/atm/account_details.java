package com.triangle.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class account_details extends AppCompatActivity {

    private TextView name,num,balance;


    String user_identity,total_balance,names,nums,balances;
    DatabaseReference reference_balance,reference_cashout;
    private TextView balancetxt;

    int cashout_tk,cashin_tk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        name=(TextView) findViewById(R.id.uname);
        num=(TextView) findViewById(R.id.unumber);
        balance=(TextView) findViewById(R.id.ubalance);

        Bundle bundle=getIntent().getExtras();
        if(!bundle.isEmpty()){
            user_identity=bundle.getString("usernumber");
        }


        reference_balance= FirebaseDatabase.getInstance().getReference("ATM").child(user_identity);


        reference_balance.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){

                    names=snapshot.child("username").getValue(String.class);
                    nums=snapshot.child("usernumber").getValue(String.class);
                    balances=snapshot.child("Balance").child("total_ammount").getValue(String.class);



                    name.setText(names);
                    num.setText(nums);
                    balance.setText(balances);
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
        menuInflater.inflate(R.menu.back_layout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.backId){



            Intent intent= new Intent(this, appmenu.class);
            intent.putExtra("usernumber",user_identity);
            startActivity(intent);
            finish();


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        Intent intent=new Intent(this,appmenu.class);
        intent.putExtra("usernumber",user_identity);
        startActivity(intent);
        finish();

    }

}
