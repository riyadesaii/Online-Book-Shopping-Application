package com.example.myprojectapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Customer_Dashboard extends AppCompatActivity {
    Toolbar tbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_dashboard);

        tbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tbar);

        Cutomer_View_Book ob1=new Cutomer_View_Book();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.addToBackStack(null);
        ft1.replace(R.id.frame,ob1);
        ft1.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.view_wishlist) {
            Toast.makeText(this, "Wishlist", Toast.LENGTH_SHORT).show();
            return true;
        } else if (item.getItemId() == R.id.view_cart) {
            Toast.makeText(this, "Cart", Toast.LENGTH_SHORT).show();
            return true;
        }
         else if (item.getItemId() == R.id.logout) {
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Customer_Dashboard.this, Login.class);
            startActivity(i);
            return true;
        }
        else {
            return true;
        }
    }

    private void logout() {
        Intent i=new Intent(Customer_Dashboard.this,Login.class);
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
