package com.example.myprojectapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Admin_Dashboard  extends AppCompatActivity {
    Toolbar tbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dashboard);

        tbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        Admin_Manage_book ob1=new Admin_Manage_book();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.addToBackStack(null);
        ft1.replace(R.id.frame,ob1);
        ft1.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.manage_book) {
            Admin_Manage_book ob1=new Admin_Manage_book();
            FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
            ft1.addToBackStack(null);
            ft1.replace(R.id.frame,ob1);
            ft1.commit();
            return true;
        } else if (item.getItemId() == R.id.manage_order) {
            Toast.makeText(this, "Manage order", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.generate_bill) {
            Toast.makeText(this, "Bills", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.view_report) {
            Toast.makeText(this, "Reports", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Admin_Dashboard.this, Login.class);
            startActivity(i);
            return true;
        }
        else {
            return true;
        }
    }

    private void logout() {
        Intent i=new Intent(Admin_Dashboard.this,Login.class);
        finish();
        startActivity(i);
    }

    public void setFragment1(Fragment f) {
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.addToBackStack(null);
        ft1.replace(R.id.frame,f);
        ft1.commit();
    }
}
