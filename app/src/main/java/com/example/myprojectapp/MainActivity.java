package com.example.myprojectapp;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        createDatabase();

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent i=new Intent(MainActivity.this,Login.class);
               startActivity(i);
           }
       },3000);
    }

    private void createDatabase() {
        Boolean checkdb=false;
        Toast.makeText(this, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), Toast.LENGTH_SHORT).show();
        try {
            db=SQLiteDatabase.openDatabase(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+"/book_app.db",null,SQLiteDatabase.OPEN_READWRITE);
            checkdb=true;
        }catch(Exception ex){
            Toast.makeText(this, "Error: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        if(checkdb){
            Toast.makeText(this, "Database Opened Successfully", Toast.LENGTH_SHORT).show();
        }else{
            db=openOrCreateDatabase(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+"/book_app.db", Context.MODE_PRIVATE,null);
            db.execSQL("create table if not Exists admin_login(_id integer primary key autoincrement,admin_id integer(20),email_id varchar(20),password varchar(10))");
            db.execSQL("create table if not Exists cust_regis(_id integer primary key autoincrement,cust_id integer(20),cust_name varchar(20),address varchar(50),city varchar(20),mobile_no integer(10),email_id varchar(20),password varchar(10))");
            db.execSQL("create table if not Exists book_detail(_id integer primary key autoincrement,book_id integer(20),book_name varchar(20),description varchar(60),author_name varchar(30),price integer(5),book_img varchar(50))");
            db.execSQL("create table if not exists cart_detail(_id integer primary key autoincrement,cart_d_id integer(20)  ,cart_id integer(5),book_id integer(10),qty integer(10),price integer(10))");
            db.execSQL("create table if not exists order_detail(_id integer primary key autoincrement,order_id integer(20)  ,order_date date,cart_id integer(5),cust_id integer,shipping_addr varchar(50),shipping_mno integer(10),total_amt integer(10))");
            db.execSQL("create table if not exists bill_detail(_id integer primary key autoincrement,bill_id integer(20)  ,bill_date date,order_id integer)");

            db.execSQL("insert into admin_login(admin_id,email_id,password) values(1,'bookworm_emporium@gmail.com','be1234')");
            Toast.makeText(this, "database created successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}