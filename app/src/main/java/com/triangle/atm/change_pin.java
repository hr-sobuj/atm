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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class change_pin extends AppCompatActivity {

    private EditText pin;

    private Button btn;



    String user_identity,total_balance,cashout,cashin;
    DatabaseReference reference_balance,reference_cashout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);


        Bundle bundle=getIntent().getExtras();
        if(!bundle.isEmpty()){
            user_identity=bundle.getString("usernumber");
        }

        pin=findViewById(R.id.pinn);

        btn=findViewById(R.id.changepin);

        reference_balance= FirebaseDatabase.getInstance().getReference("ATM").child(user_identity);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String x=pin.getText().toString();


                reference_balance.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!x.isEmpty()) {
                            snapshot.getRef().child("userpin").setValue(x);

                            Toast.makeText(change_pin.this,"PIN change successfully.",Toast.LENGTH_LONG).show();
                        }




                        /*Toast.makeText(appmenu.this,cashin+cashout,Toast.LENGTH_LONG).show();*/

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


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
