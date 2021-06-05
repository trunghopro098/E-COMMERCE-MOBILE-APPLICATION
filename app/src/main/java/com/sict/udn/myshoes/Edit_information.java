package com.sict.udn.myshoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Edit_information extends AppCompatActivity {

    EditText editName,editPhone,editAdress;
    TextView txtEmail;
    Button btnBack,btnSend;
    FirebaseAuth mAuth;
    String phone,adress,email,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);

        editName = (EditText) findViewById(R.id.EdittextName);
        editAdress = (EditText) findViewById(R.id.EdittextAddress);
        editPhone = (EditText) findViewById(R.id.EdittextPhone);
        txtEmail = (TextView) findViewById(R.id.EdittextEmail);
        btnBack = (Button)findViewById(R.id.btnBACK);
        btnSend = (Button)findViewById(R.id.btnSend);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String uid = user.getUid();

        final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Users");
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(uid).exists()) {
                    phone = dataSnapshot.child(uid).child("phone").getValue().toString();
                    adress = dataSnapshot.child(uid).child("address").getValue().toString();
                    email = dataSnapshot.child(uid).child("email").getValue().toString();
                    name = dataSnapshot.child(uid).child("name").getValue().toString();
                }
                editName.setText(name);
                editAdress.setText(adress);
                editPhone.setText(phone);
                txtEmail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editName.getText().toString();
                adress = editAdress.getText().toString();
                phone = editPhone.getText().toString();

                HashMap<String,Object> user = new HashMap<>();
                user.put("name",name);
                user.put("address",adress);
                user.put("phone",phone);

                dataref.child(uid).updateChildren(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Edit_information.this, "Update success !", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
