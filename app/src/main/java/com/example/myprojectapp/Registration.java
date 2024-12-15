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

public class Registration extends AppCompatActivity implements View.OnClickListener {

    SQLiteDatabase db;
    EditText edtname,edtaddress,edtcity,edtmobno,edtemail,edtpwd;
    String name,addr,city,mno,mail,pwd;
    Button btnregis;
    Cursor c,c2;
    private static final Pattern NAME_PATTERN=Pattern.compile("[a-zA-Z]{2,40}");
    private static final Pattern MNO_PATTERN=Pattern.compile("[0-9]{10,10}");
    private static final Pattern EMAIL_PATTERN=Pattern.compile("[a-zA-Z0-9.-_]{1,100}@[a-zA-Z0-9.-_]{1,100}.([a-zA-Z]{2,4})");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        try {
            db=SQLiteDatabase.openDatabase(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+"/book_app.db",null,SQLiteDatabase.OPEN_READWRITE);

        }catch(Exception ex){
            Toast.makeText(this, "Error: "+ex.getMessage(), Toast.LENGTH_SHORT).show();

        }


        edtname=(EditText) findViewById(R.id.edtxt_name);
        edtaddress=(EditText) findViewById(R.id.edtxt_address);
        edtcity=(EditText) findViewById(R.id.edtxt_city);
        edtmobno=(EditText) findViewById(R.id.edtxt_mno);
        edtemail=(EditText) findViewById(R.id.edtxt_email);
        edtpwd=(EditText) findViewById(R.id.edtxt_pwd);
        btnregis=(Button) findViewById(R.id.button_regis);

        btnregis.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view==btnregis){
            name=edtname.getText().toString().trim();
            addr=edtaddress.getText().toString().trim();
            city=edtcity.getText().toString().trim();
            mno=edtmobno.getText().toString().trim();
            mail=edtemail.getText().toString().trim();
            pwd=edtpwd.getText().toString().trim();

            if(name.equals("")){
                edtname.setError("Please enter name");
                edtname.setFocusable(true);
            } else if (!checkname(name)) {
                edtname.setError("Enter only alphabets in the name");
                edtname.setFocusable(true);
            } else if (addr.equals("")) {
                edtaddress .setError("Please enter address");
                edtaddress.setFocusable(true);
            }
            else if (city.equals("")) {
                edtcity .setError("Please enter address");
                edtcity.setFocusable(true);
            }
            else if (mno.equals("")) {
                edtmobno .setError("Please enter address");
                edtmobno.setFocusable(true);
            }else if (!checkmno(mno)) {
                edtname.setError("Enter only alphabets in the name");
                edtname.setFocusable(true);
            }
            else if (mail.equals("")) {
                edtemail.setError("Please enter address");
                edtemail.setFocusable(true);
            }else if (!checkmail(mail)) {
                edtemail.setError("Enter valid mail");
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
            } else{
              regis_customer();
            }
        }

    }

    private void regis_customer() {
        c=db.rawQuery("select * from cust_regis where email_id='"+mail+"'",null);
        int custid=0;
        if(c.getCount()==0)
        {
            c2=db.rawQuery("select max(cust_id) from cust_regis",null);
            c2.moveToFirst();
            if(c2.getString(0)!=null){
                custid=Integer.parseInt(c2.getString(0))+1;
            }else{
                custid=1;
            }
            try {
                db.execSQL("insert into cust_regis(cust_id,cust_name,address,city,mobile_no,email_id,password)values(" + custid + ",'" + name + "','" + addr + "','" + city + "'," + mno + ",'" + mail + "','" + pwd + "')");
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(Registration.this,Customer_Dashboard.class);
                startActivity(i);
            }catch (Exception ex){
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "EmailId Already Exists", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkmail(String mail) {
        return EMAIL_PATTERN.matcher(mail).matches();
    }

    private boolean checkmno(String mno) {
        return MNO_PATTERN.matcher(mno).matches();
    }

    private boolean checkname(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }
}
