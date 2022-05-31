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

public class transaction extends AppCompatActivity {

    private Button cashin,cashout;

    String user_identity,total_balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        Bundle bundle=getIntent().getExtras();
        if(!bundle.isEmpty()){
            user_identity=bundle.getString("usernumber");
        }

        cashout=findViewById(R.id.cashout_history);
        cashin=findViewById(R.id.cashin_history);

        cashin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(transaction.this,cashinHistory.class);

                intent.putExtra("usernumber",user_identity);
                startActivity(intent);
            }
        });

        cashout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(transaction.this,cashoutHistory.class);

                intent.putExtra("usernumber",user_identity);
                startActivity(intent);
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
