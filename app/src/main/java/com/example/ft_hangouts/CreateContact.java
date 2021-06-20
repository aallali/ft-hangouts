package com.example.ft_hangouts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class CreateContact extends AppCompatActivity {

    ContactData contactData;
    EditText name, phone, email, street, postal_code;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        name = findViewById(R.id.et_name);
        phone = findViewById(R.id.et_phone);
        email = findViewById(R.id.et_email);
        street = findViewById(R.id.et_street);
        postal_code = findViewById(R.id.et_postal_code);

        contactData = new ContactData(this);
        getIntentData();
    }

    void getIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("phone") && getIntent().hasExtra("email") && getIntent().hasExtra("street") && getIntent().hasExtra("postal_code")) {
            id = getIntent().getStringExtra("id");
            String v_name = getIntent().getStringExtra("name");
            String v_phone = getIntent().getStringExtra("phone");
            String v_email = getIntent().getStringExtra("email");
            String v_street = getIntent().getStringExtra("street");
            String v_postal_code = getIntent().getStringExtra("postal_code");


            name.setText(v_name);
            phone.setText(v_phone);
            email.setText(v_email);
            street.setText(v_street);
            postal_code.setText(v_postal_code);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save, menu);
        if (!TextUtils.isEmpty(id))
            menu.findItem(R.id.delete).setVisible(true);
        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:

                if(TextUtils.isEmpty( name.getText().toString())) {
                    Toast.makeText(this, "name is required", Toast.LENGTH_LONG).show();
                    return true;
                }

                if(TextUtils.isEmpty( phone.getText().toString())) {
                    Toast.makeText(this, "phone is required", Toast.LENGTH_LONG).show();
                    return true;
                }
                if(!isValidEmail(email.getText().toString().trim())) {
                    Toast.makeText(this, "invalid email", Toast.LENGTH_LONG).show();
                    return true;
                }
                if(phone.getText().toString().length() < 10) {
                    Toast.makeText(this, "phone must be 10 digits", Toast.LENGTH_LONG).show();
                    return true;
                }

                if (!TextUtils.isEmpty(id)) {
                    contactData.updateContact(id,
                            name.getText().toString(),
                            phone.getText().toString(),
                            email.getText().toString(),
                            street.getText().toString(),
                            postal_code.getText().toString());
                    finish();
                    return true;
                }

                contactData.addContact(
                        name.getText().toString(),
                        phone.getText().toString(),
                        email.getText().toString(),
                        street.getText().toString(),
                        postal_code.getText().toString());
                finish();
                return true;
            case R.id.delete:
                if (!TextUtils.isEmpty(id)) {
                    contactData.deleteContact(id);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}