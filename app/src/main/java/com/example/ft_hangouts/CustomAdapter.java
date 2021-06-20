package com.example.ft_hangouts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Activity activity;
    Context context;
    ArrayList contactId,
            contactName,
            contactPhone,
            contactEmail,
            contactStreet,
            contactPostalCode;

    CustomAdapter(Activity activity,
                  Context context,
                  ArrayList contactId,
                  ArrayList contactName,
                  ArrayList contactPhone,
                  ArrayList contactEmail,
                  ArrayList contactStreet,
                  ArrayList contactPostalCode) {
        this.activity = activity;
        this.context = context;
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.contactStreet = contactStreet;
        this.contactPostalCode = contactPostalCode;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.contact, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(String.valueOf(contactName.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            int p = position;
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CreateContact.class);
                intent.putExtra("id", String.valueOf(contactId.get(p)));
                intent.putExtra("name", String.valueOf(contactName.get(p)));
                intent.putExtra("phone", String.valueOf(contactPhone.get(p)));
                intent.putExtra("email", String.valueOf(contactEmail.get(p)));
                intent.putExtra("street", String.valueOf(contactStreet.get(p)));
                intent.putExtra("postal_code", String.valueOf(contactPostalCode.get(p)));
                context.startActivity(intent);
            }
        });
        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sms(position);
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call(position);
            }
        });
    }

    public void sms(int position) {
        String num = String.valueOf(contactPhone.get(position));
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:" + num));
        intent.putExtra("sms_body", "");
        context.startActivity(intent);
    }

    public void call(int position) {
        String num = String.valueOf(contactPhone.get(position));;
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + num));
        final int REQUEST_PHONE_CALL = 1;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
            }
            else
            {
                context.startActivity(intent);
            }
        }
        else
        {
            context.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return contactId.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        Button message, call;

        // list of contacts created Adapter
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.et_name);
            message = itemView.findViewById(R.id.message);
            call = itemView.findViewById(R.id.call);
        }
    }
}
