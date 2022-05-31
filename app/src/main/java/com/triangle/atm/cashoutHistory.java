package com.triangle.atm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class cashoutHistory extends AppCompatActivity {

    private EditText tk,accnum;
    private Button btnout;
    String user_identity;
    String old_ammount,total_ammount;

    DatabaseReference reference_balance,reference_cashout;

    int cashout_tk,cashin_tk;
    String total_balance,cashout,cashin,x;

    private ListView DataDisplay;
    private TextView txt;
    String val,val1;
    String stdid,stdname,stdfalculty,stddept,stdblood,stdgender,stdphn,stdmail,stdjob,stdpresent,stdpast;
    DatabaseReference reference;
    ProgressDialog progressDialog;

    private TextView sid,sname,sfaculty,sdept,sblood,sgender,sphn,smail,sjob,spresentad,spermanentad;
    private Button btn;

    private List<db_cashout> firebasess;
    private firebase_adapter firebaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashout_history);


        progressDialog=new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setProgress(10);
        progressDialog.setMax(10);
        progressDialog.setMessage("Loading..");
        progressDialog.setIndeterminate(true);


        Bundle bundle=getIntent().getExtras();
        if(!bundle.isEmpty()){
            user_identity=bundle.getString("usernumber");
        }


        reference_cashout= FirebaseDatabase.getInstance().getReference("ATM").child(user_identity).child("Cashout_History");

        firebasess=new ArrayList<>();

        firebaseAdapter=new firebase_adapter(cashoutHistory.this,firebasess);


        DataDisplay=findViewById(R.id.firebaseDataListView);




        reference_cashout.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    progressDialog.dismiss();

                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(cashoutHistory.this,"Data not found :(",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(cashoutHistory.this,MainActivity.class);
                    startActivity(intent);


                }

                firebasess.clear();
                int count=0;


                for(DataSnapshot i:dataSnapshot.getChildren()){


                    progressDialog.dismiss();

                    db_cashout firesdata=i.getValue(db_cashout.class);

                    firebasess.add(firesdata);

                }
                DataDisplay.setAdapter(firebaseAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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



            Intent intent= new Intent(this, transaction.class);
            intent.putExtra("usernumber",user_identity);
            startActivity(intent);
            finish();


        }


        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed(){


        Intent intent=new Intent(this,transaction.class);
        intent.putExtra("usernumber",user_identity);
        startActivity(intent);
        finish();

    }

}
