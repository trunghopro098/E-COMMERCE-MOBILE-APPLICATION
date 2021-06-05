package com.sict.udn.myshoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.sict.udn.Adapter.AdapterCheckOut;
import com.sict.udn.Adapter.CartAdapter;
import com.sict.udn.Model.Cart;
import com.sict.udn.Model.Users;

import java.util.ArrayList;
import java.util.HashMap;

public class CheckOut extends AppCompatActivity {
//    CartAdapter adapter;
    AdapterCheckOut adapter;
    ArrayList<Cart> arrayList;
    LinearLayoutManager layoutManager;
    Cart cart;
    Users users;
    ArrayList<Users> usersArrayList;
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    TextView textView, txtuserName,txtuserAddress, txtuserEmail,txtphone;
    Button btnCheckoutend;
    long tong;
    String phone,name,address,email,hientong;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);


        btnCheckoutend = findViewById(R.id.Checkoutend);
        txtuserAddress = (TextView) findViewById(R.id.txt_CheckoutAddress);
        txtuserEmail = (TextView) findViewById(R.id.txt_CheckoutEmail);
        txtuserName = (TextView) findViewById(R.id.txt_CheckoutName);
        txtphone = (TextView) findViewById(R.id.txt_CheckoutPhone);

        textView = (TextView) findViewById(R.id.txt_Checkout);
        recyclerView = (RecyclerView) findViewById(R.id.cartRcycalview);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String uid = user.getUid();
        arrayList = new ArrayList<>();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("GioHang").child(uid).child("SanPham");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    cart = data.getValue(Cart.class);

                    cart.setId(data.getKey());
                    arrayList.add(cart);
                }
                for(int i = 0 ;i<arrayList.size();i++){
                    long total = arrayList.get(i).getTotal();
                    tong = tong + total;

                }
                 hientong = String.format("%,d",tong);
                textView.setText("You need to pay us "+hientong+"Đ"+" on delivery. Thank you!");
//                adapter  = new CartAdapter(arrayList,getApplicationContext());
                adapter = new AdapterCheckOut(arrayList,getApplicationContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final DatabaseReference refuser = FirebaseDatabase.getInstance().getReference("Users");
        refuser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(uid).exists()) {
                    phone = dataSnapshot.child(uid).child("phone").getValue().toString();
                    address = dataSnapshot.child(uid).child("address").getValue().toString();
                    email = dataSnapshot.child(uid).child("email").getValue().toString();
                    name = dataSnapshot.child(uid).child("name").getValue().toString();
                }
              txtphone.setText(phone);
              txtuserAddress.setText(address);
              txtuserEmail.setText(email);
              txtuserName.setText(name);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btnCheckoutend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference refHoaDon =  FirebaseDatabase.getInstance().getReference("HoaDon");
//                tạo id tự động cho hd
                String idhoadon = refHoaDon.push().getKey();

                HashMap<String,Object> hoadon = new HashMap<>();
                for (int z = 0 ;z<arrayList.size();z++){
                    String idsanpham = arrayList.get(z).getId();
                    hoadon.put(idsanpham,arrayList.get(z));
                }
                refHoaDon.child(idhoadon).child("uid").setValue(uid);
                refHoaDon.child(idhoadon).child("uid").child("Phone").setValue(phone);
                refHoaDon.child(idhoadon).child("uid").child("Address").setValue(address);
                refHoaDon.child(idhoadon).child("uid").child("Name").setValue(name);
                refHoaDon.child(idhoadon).child("uid").child("Email").setValue(email);
                refHoaDon.child(idhoadon).child("uid").child("Total").setValue(hientong);
                refHoaDon.child(idhoadon).child("tinhtrang").setValue(false);
                refHoaDon.child(idhoadon).child("SanPham").updateChildren(hoadon).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        DatabaseReference refGiohang = FirebaseDatabase.getInstance().getReference("GioHang");
                        refGiohang.child(uid).removeValue();
                        Toast.makeText(CheckOut.this, "Your order is being processed...", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });

    }



}
