package com.triangle.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class cashout extends AppCompatActivity {

    private EditText tk;
    private Button btnout;

    DatabaseReference reference_balance,reference_cashout;

    private TextView txt;
    int cashout_tk,cashin_tk;
    String user_identity,total_balance,cashout,cashin,x;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashout);

        txt=(TextView) findViewById(R.id.balance);

        Bundle bundle=getIntent().getExtras();
        if(!bundle.isEmpty()){
            user_identity=bundle.getString("usernumber");
        }

        reference_cashout= FirebaseDatabase.getInstance().getReference("ATM").child(user_identity);
        reference_balance= FirebaseDatabase.getInstance().getReference("ATM").child(user_identity);

        tk=(EditText) findViewById(R.id.cashout_ammount);
        btnout=(Button) findViewById(R.id.cashoutbtn);



        reference_balance.child("Balance").child("total_ammount").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                if(snapshot.exists()){
                    total_balance=snapshot.getValue(String.class);

                    txt.setText("$"+total_balance);
                }


                /*Toast.makeText(appmenu.this,cashin+cashout,Toast.LENGTH_LONG).show();*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ammount=tk.getText().toString();

                cashin_tk=Integer.parseInt(ammount);

                reference_balance.child("Balance").child("total_ammount").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        if(snapshot.exists()){
                            total_balance=snapshot.getValue(String.class);

                            int tmp=Integer.parseInt(total_balance);
                            int total=tmp-cashin_tk;
                            x=String.valueOf(total);
                        }


                        /*Toast.makeText(appmenu.this,cashin+cashout,Toast.LENGTH_LONG).show();*/

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                reference_balance.child("Balance").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(!x.isEmpty()){
                            snapshot.getRef().child("total_ammount").setValue(x);

/*
                            Toast.makeText(cashout.this,x,Toast.LENGTH_LONG).show();*/
                        }




                        /*Toast.makeText(appmenu.this,cashin+cashout,Toast.LENGTH_LONG).show();*/

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                String key=reference_cashout.push().getKey();
                db_cashout dbBalance=new db_cashout(ammount);
                reference_cashout.child("Cashout").setValue(dbBalance);
                reference_cashout.child("Cashout_History").child(key).setValue(dbBalance);

                Toast.makeText(cashout.this,"Cash out Successful",Toast.LENGTH_LONG).show();


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
