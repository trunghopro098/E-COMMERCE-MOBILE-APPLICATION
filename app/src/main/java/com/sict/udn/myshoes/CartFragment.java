package com.sict.udn.myshoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sict.udn.Adapter.CartAdapter;
import com.sict.udn.Model.Cart;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private Cart cart;
    private ArrayList<Cart> arrayList;
    private CartAdapter cartAdapter;
    private LinearLayoutManager  linearLayoutManager;
    private RecyclerView recyclerView;
    FirebaseAuth mAuth;
    private TextView txt_cv,tongtien,txt_pro;
    long tong = 0;
    Button payment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frapment_cart, container, false);

        payment = (Button) view.findViewById(R.id.Thanhtoangiohang);
        recyclerView = (RecyclerView) view.findViewById(R.id.cartRcycalview);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(cartAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        txt_cv = (TextView) view.findViewById(R.id.txt_cv_cart);
        tongtien = (TextView) view.findViewById(R.id.txt_totalcar);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        arrayList = new ArrayList<>();
//    kết nối đến note GH-SP để get data gán vào arraylist
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("GioHang").child(uid).child("SanPham");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                xóa dử liệu trong mảng
                arrayList.clear();
                for (DataSnapshot data : dataSnapshot.getChildren()){
//                    get data qua id
                 cart = data.getValue(Cart.class);
                 cart.setId(data.getKey());
                 arrayList.add(cart);
                }
//                ẩn tview
                if (arrayList.size()>0){
                    txt_cv.setVisibility(View.INVISIBLE);
                }
                tong = 0;

//              duyệt mảng  tính tổng tiền cảu giỏ hàng
                for(int i = 0 ;i<arrayList.size();i++){
                    long total = arrayList.get(i).getTotal();
                    tong = tong + total;

                }
                String hientong = String.format("%,d",tong);
                tongtien.setText(hientong+" Đ");
                cartAdapter = new CartAdapter(arrayList,getContext());
                recyclerView.setAdapter(cartAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList.size()>0){
                    Intent intent = new Intent(getContext(),CheckOut.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), "Add products to your cart!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}
