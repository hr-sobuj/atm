package com.triangle.atm;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class firebase_adapter extends ArrayAdapter<db_cashout> {

    private Activity context;
    private List<db_cashout> databaseView;


    public firebase_adapter(Activity context, List<db_cashout> databaseView) {
        super(context, R.layout.firebase_data_show_layout, databaseView);
        this.context = context;
        this.databaseView = databaseView;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        LayoutInflater layoutInflater=context.getLayoutInflater();
        View vieww=layoutInflater.inflate(R.layout.firebase_data_show_layout,null,false);

        db_cashout firebasess=databaseView.get(position);

        TextView t1= vieww.findViewById(R.id.tk);
   /*     TextView t2= vieww.findViewById(R.id.Ufaculty);
        TextView t3= vieww.findViewById(R.id.Udept);
        TextView t4= vieww.findViewById(R.id.Useassion);*/
 /*       TextView t5= vieww.findViewById(R.id.Uaddress);*/

/*        t1.setText(firebasess.getName());
        t2.setText(firebasess.getFaculty());
        t3.setText(firebasess.getDepartment());
        t4.setText(firebasess.getSession());*/
      /*  t5.setText(firebasess.getPresent());*/
        /*t5.setText(firebasess.getMobile());
        t6.setText(firebasess.getId());*/
        t1.setText(firebasess.getAmmount());
        return vieww;
    }


}
