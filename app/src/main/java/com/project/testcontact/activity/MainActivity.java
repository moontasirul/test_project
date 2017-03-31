package com.project.testcontact.activity;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.testcontact.R;
import com.project.testcontact.adapter.ContactNumberAdapter;
import com.project.testcontact.database.DatabaseHelper;
import com.project.testcontact.model.ContactNumber;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    Toolbar mToolbar;

    private ImageView ivProImage;
    private RecyclerView recyclerView;
    private ContactNumberAdapter mAdapter;
    private List<ContactNumber> mContactList;
    private DatabaseHelper mDBHelper;
    private static final int MAX_ROW_DISPLAY = 5;


    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar= (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ivProImage = (ImageView) findViewById(R.id.profile_image);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.moon);
        ivProImage.setImageBitmap(bitmap);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        mDBHelper = new DatabaseHelper(this);

        //Check exists database
        File dataBase= getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false==dataBase.exists()){
            mDBHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)) {
                Toast.makeText(this, "Copy database success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        mContactList=new ArrayList<>();


    }


    private  boolean copyDatabase(Context context){
        try{
            InputStream inputStream= context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName= DatabaseHelper.DBLOCATION +DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff= new byte[1024];
            int length= 0;
            while((length= inputStream.read(buff))> 0){
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("MainActivity","DB Copy");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
        mAdapter.notifyDataSetChanged();
        Log.d("Check resume","resume");
    }

    public void refreshAdapter(){
        mContactList = mDBHelper.getListContact();
        mAdapter= new ContactNumberAdapter(this,mContactList);
        recyclerView.setAdapter(mAdapter);
    }
}
