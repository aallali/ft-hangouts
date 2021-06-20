package com.example.ft_hangouts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ContactData contactData;
    ArrayList<String> contactId, contactName, contactPhone, contactEmail, contactStreet, contactPostalCode;
    RecyclerView recyclerView;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerview);

        contactData = new ContactData(this);
        contactId = new ArrayList<>();
        contactName = new ArrayList<>();
        contactPhone = new ArrayList<>();
        contactEmail = new ArrayList<>();
        contactStreet = new ArrayList<>();
        contactPostalCode = new ArrayList<>();


        customAdapter = new CustomAdapter(this,this,
                contactId,
                contactName,
                contactPhone,
                contactEmail,
                contactStreet,
                contactPostalCode);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createContactIntent = new Intent(MainActivity.this, CreateContact.class);
                startActivity(createContactIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContacts();
        customAdapter.notifyDataSetChanged();
    }

    void getContacts() {
        Cursor cursor = contactData.getAllContact();
        contactId.clear();
        contactName.clear();
        contactPhone.clear();
        contactEmail.clear();
        contactStreet.clear();
        contactPostalCode.clear();

        if (cursor.getCount() == 0)
            Log.v(this.getClass().getName(), "No Contacts");
        else {
            while (cursor.moveToNext()) {
                contactId.add(cursor.getString(0));
                contactName.add(cursor.getString(1));
                contactPhone.add(cursor.getString(2));
                contactEmail.add(cursor.getString(3));
                contactStreet.add(cursor.getString(4));
                contactPostalCode.add(cursor.getString(5));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.color, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.green:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4CAF50")));
                return true;
            case R.id.blue:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#2196F3")));
                return true;
            case R.id.red:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#F44336")));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}