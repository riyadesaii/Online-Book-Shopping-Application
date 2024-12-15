package com.example.myprojectapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase db;
    EditText edtemail,edtpwd;
    TextView txtregis;
    String mail,pwd;
    Button btnlogin;
    Cursor c;
    private static final Pattern EMAIL_PATTERN=Pattern.compile("[a-zA-Z0-9.-_]{1,100}@[a-zA-Z0-9.-_]{1,100}.([a-zA-Z]{2,4})");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        try {
            db=SQLiteDatabase.openDatabase(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+"/book_app.db",null,SQLiteDatabase.OPEN_READWRITE);

        }catch(Exception ex){
            Toast.makeText(this, "Error: "+ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

        edtemail=(EditText) findViewById(R.id.edtxt_email);
        edtpwd=(EditText) findViewById(R.id.edtxt_pwd);
        txtregis=(TextView) findViewById(R.id.textView_regis);
        btnlogin=(Button) findViewById(R.id.button_login);

        btnlogin.setOnClickListener(this);
        txtregis.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==txtregis){
            Intent i=new Intent(Login.this,Registration.class);
            startActivity(i);
        }
        if(view==btnlogin){
            mail=edtemail.getText().toString().trim();
            pwd=edtpwd.getText().toString().trim();
            if (mail.equals("")) {
                edtemail.setError("Please enter address");
                edtemail.setFocusable(true);
            }else if (!checkmail(mail)) {
                edtemail.setError("Enter only alphabets in the name");
                edtemail.setFocusable(true);
            }
            else if (pwd.equals("")) {
                edtpwd .setError("Please enter address");
                edtpwd.setFocusable(true);
            } else if (pwd.length()<6) {
                edtpwd .setError("Password should be more than 6 characters");
                edtpwd.setFocusable(true);
            }else if (pwd.length()>10) {
                edtpwd .setError("Password should be less than 10 characters");
                edtpwd.setFocusable(true);
            }else {
                login_cust();
            }
        }
    }

    private void login_cust() {
        c=db.rawQuery("select * from admin_login where email_id='"+mail+"' and password='"+pwd+"'",null);
        if(c.moveToFirst()){
            Toast.makeText(this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
            openadmindashboard();
        }else{
            c=db.rawQuery("select * from cust_regis where email_id='"+mail+"' and password='"+pwd+"'",null);
            if(c.moveToFirst()){
                Toast.makeText(this, "Customer Login Successful", Toast.LENGTH_SHORT).show();
                opencustdashboard();
            }else{
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void opencustdashboard() {
        Intent i=new Intent(Login.this,Customer_Dashboard.class);
        finish();
        startActivity(i);
    }


    private void openadmindashboard() {
        Intent i=new Intent(Login.this,Admin_Dashboard.class);
        finish();
        startActivity(i);
    }

    private boolean checkmail(String mail) {
        return EMAIL_PATTERN.matcher(mail).matches();
    }
}
