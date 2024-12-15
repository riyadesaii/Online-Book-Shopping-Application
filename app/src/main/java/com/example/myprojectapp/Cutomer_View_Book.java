package com.example.myprojectapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Cutomer_View_Book extends Fragment {
    ListView lv1;
    SQLiteDatabase db;
    Cursor c;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.customer_view_book,container,false);
        try {
            db=SQLiteDatabase.openDatabase(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath()+"/book_app.db",null,SQLiteDatabase.OPEN_READWRITE);

        }catch(Exception ex){
            Toast.makeText(getActivity(), "Error: "+ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
        lv1=(ListView) v.findViewById(R.id.listview1);
        loadbookdetail();


        return v;
    }

    private void loadbookdetail() {
        c=db.rawQuery("select * from book_detail",null);
        Customer_View_Book_clv ob=new Customer_View_Book_clv(getActivity(),c,0);
        lv1.setAdapter(ob);
    }
}
