package com.project.testcontact.adapter;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.testcontact.database.DatabaseHelper;
import com.project.testcontact.model.ContactNumber;
import com.project.testcontact.R;


import java.util.List;

public class ContactNumberAdapter extends RecyclerView.Adapter<ContactNumberAdapter.MyViewHolder> {


    private List<ContactNumber> contactList;

    private Context context;
    DatabaseHelper dbHelper;




    public ContactNumberAdapter(Context mContext, List<ContactNumber> contactList) {
        context = mContext;
        this.contactList = contactList;
        dbHelper = new DatabaseHelper(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, number;
        private LinearLayout titleLayout;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            number = (TextView) view.findViewById(R.id.number);


            titleLayout = (LinearLayout) itemView.findViewById(R.id.title_layout);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.contact_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ContactNumber contactNumber = contactList.get(position);

        holder.title.setText(contactNumber.getTitle());
        holder.number.setText(contactNumber.getNumber());


       if( contactNumber.getId()==26){
           Toast.makeText(context, "No More data found", Toast.LENGTH_SHORT).show();
       }


        holder.titleLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setMessage("Do wants to call " + contactNumber.getTitle());
                alert.setCancelable(true);
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + contactNumber.getNumber()));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        context.startActivity(callIntent);
                    }
                });
                alert.setNegativeButton("No ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                alert.create();
                alert.show();
            }
        });


    }




    @Override
    public int getItemCount() {
        if(contactList==null){
            return 0;
        }



        return contactList.size();
    }

    @Override
    public long getItemId(int position) {


        return contactList.get(position).getId();
    }



}
