package com.example.myprojectapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Customer_View_Book_clv extends CursorAdapter {
    SQLiteDatabase db;
    public Customer_View_Book_clv(Context context, Cursor c, int flags) {
        super(context, c, flags);

        try {
            db=SQLiteDatabase.openDatabase(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+"/book_app.db",null,SQLiteDatabase.OPEN_READWRITE);

        }catch(Exception ex){
            Toast.makeText(context, "Error: "+ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.customer_view_book_clv,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView iv=(ImageView) view.findViewById(R.id.imageView3);
        TextView txtbid=(TextView) view.findViewById(R.id.tvbid);
        TextView txtname=(TextView) view.findViewById(R.id.bname);
        TextView txtdesc=(TextView) view.findViewById(R.id.desc);
        TextView txtaname=(TextView) view.findViewById(R.id.aname);
        TextView txtprice=(TextView) view.findViewById(R.id.price);
        Button btncart=(Button) view.findViewById(R.id.button_cart);


        String bid=cursor.getString(cursor.getColumnIndexOrThrow("book_id"));
        String bname=cursor.getString(cursor.getColumnIndexOrThrow("book_name"));
        String desc=cursor.getString(cursor.getColumnIndexOrThrow("description"));
        String aname=cursor.getString(cursor.getColumnIndexOrThrow("author_name"));
        String price=cursor.getString(cursor.getColumnIndexOrThrow("price"));
        String img=cursor.getString(cursor.getColumnIndexOrThrow("book_img"));

        txtbid.setText(bid);
        txtname.setText(bname);
        txtdesc.setText(desc);
        txtaname.setText(aname);
        txtprice.setText(price);

        Bitmap bmap= BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/book_img/"+img);
        iv.setImageBitmap(bmap);



        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
