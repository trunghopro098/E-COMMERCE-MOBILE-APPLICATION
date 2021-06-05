package com.sict.udn.myshoes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sict.udn.Model.Users;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    private TextView txtNamepro,txtEmailpro,txtAddresspr,txtPhonepro;
    Button btnprofile;
    FirebaseAuth  mAuth;
    String name,phone,email,address;
    ArrayList<Users> users;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.frapment_profile, container, false);

         txtNamepro = (TextView) view.findViewById(R.id.protextName);
         txtAddresspr = (TextView) view.findViewById(R.id.protextAddress);
         txtEmailpro = (TextView) view.findViewById(R.id.protextEmail);
         txtPhonepro = (TextView) view.findViewById(R.id.protextPhone);
         btnprofile = (Button) view.findViewById(R.id.btn_profile);




        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String uid = user.getUid();
        final DatabaseReference dataref = FirebaseDatabase.getInstance().getReference("Users");
        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(uid).exists()) {
                    phone = dataSnapshot.child(uid).child("phone").getValue().toString();
                    address = dataSnapshot.child(uid).child("address").getValue().toString();
                    email = dataSnapshot.child(uid).child("email").getValue().toString();
                    name = dataSnapshot.child(uid).child("name").getValue().toString();
                }
                txtAddresspr.setText(address);
                txtEmailpro.setText(email);
                txtNamepro.setText(name);
                txtPhonepro.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Edit_information.class);
                startActivity(intent);
            }
        });





        return view;
    }
}
