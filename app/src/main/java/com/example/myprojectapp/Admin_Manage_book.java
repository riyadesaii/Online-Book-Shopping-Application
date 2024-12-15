package com.example.myprojectapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

public class Admin_Manage_book extends Fragment implements View.OnClickListener {
    SQLiteDatabase db;
    EditText edtname,edtdec,edtauthor,edtprice;
    Button btnupload,btnsave,btnview;
    String name,desc,author,price,imgpath="x";
    Bitmap bmap;
    Cursor c2;

    private static final Pattern PRICE_PATTERN=Pattern.compile("[0-9]{1,3}");
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.admin_manage_book,container,false);

        try {
            db=SQLiteDatabase.openDatabase(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+"/book_app.db",null,SQLiteDatabase.OPEN_READWRITE);

        }catch(Exception ex){
            Toast.makeText(getActivity(), "Error: "+ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

        edtname=(EditText) v.findViewById(R.id.edtxt_bkname);
        edtdec=(EditText) v.findViewById(R.id.edtxt_desc);
        edtauthor=(EditText) v.findViewById(R.id.edtxt_aname);
        edtprice=(EditText) v.findViewById(R.id.edtxt_price);
        btnupload=(Button) v.findViewById(R.id.button_upload);
        btnsave=(Button) v.findViewById(R.id.button_save);
        btnview=(Button) v.findViewById(R.id.button_view);

        btnsave.setOnClickListener(this);
        btnupload.setOnClickListener(this);
        btnview.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View view) {
        if(view==btnupload){
            Intent photo=new Intent(Intent.ACTION_PICK);
            photo.setType("image/*");
            startActivityForResult(photo,101);
        }
        if(view==btnsave){
            name=edtname.getText().toString().trim();
            desc=edtdec.getText().toString().trim();
            author=edtauthor.getText().toString().trim();
            price=edtprice.getText().toString().trim();
            if(name.equals("")){
                edtname.setError("Enter book name");
                edtname.setFocusable(true);
            } else if (desc.equals("")) {
                edtdec.setError("Enter description");
                edtdec.setFocusable(true);
            }else if (author.equals("")) {
                edtauthor.setError("Enter author name");
                edtauthor.setFocusable(true);
            }else if (price.equals("")) {
                edtprice.setError("Enter price");
                edtprice.setFocusable(true);
            }else if ((Integer.parseInt(price))<=0) {
                edtprice.setError("Price cannot be 0");
                edtprice.setFocusable(true);
            }
            else if (!checkprice(price)) {
                edtprice.setError("Enter only digits in price");
                edtprice.setFocusable(true);
            } else if (imgpath.equals("x")) {
                Toast.makeText(getActivity(), "Please select book image", Toast.LENGTH_SHORT).show();
            }else{
                save_book();
            }
        }
        if(view==btnview){
            Fragment ob=new Admin_View_Book();
            ((Admin_Dashboard)getActivity()).setFragment1(ob);
        }
    }

    private void save_book() {
        int bid=0;
        c2=db.rawQuery("select max(book_id) from book_detail",null);
        c2.moveToFirst();
        if(c2.getString(0)!=null){
            bid=Integer.parseInt(c2.getString(0))+1;
        }else{
            bid=1;
        }
        try {
            db.execSQL("insert into book_detail(book_id,book_name,description,author_name,price,book_img)values(" + bid + ",'" + name + "','" + desc + "','" + author + "'," + price + ",'" + imgpath + "')");
            Toast.makeText(getActivity(), "Book Detail Saved Successful", Toast.LENGTH_SHORT).show();
            Fragment f=new Admin_Manage_book();
            ((Admin_Dashboard)getActivity()).setFragment1(f);
        }catch (Exception ex){
            Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkprice(String price) {
        return PRICE_PATTERN.matcher(price).matches();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && data!=null && data.getData()!=null){
            Uri filepath=data.getData();
            try {
                bmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),filepath);
                File myfile=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/book_img/");
                imgpath=System.currentTimeMillis() + ".jpg";
                File file=new File(myfile,imgpath);
                FileOutputStream opstream=new FileOutputStream(file);
                bmap.compress(Bitmap.CompressFormat.JPEG,100,opstream);
                Toast.makeText(getActivity(), "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
