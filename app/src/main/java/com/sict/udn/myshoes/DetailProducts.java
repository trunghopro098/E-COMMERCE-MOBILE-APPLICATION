package com.sict.udn.myshoes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.sict.udn.Model.Productvertical;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.HashMap;

public class DetailProducts extends AppCompatActivity {
        Toolbar toolbarDetail;
        ImageView imageViewDetail, imageViewDetail1,imageViewDetail2 ;
        TextView txtNameDetail, txtPriceDetail, txtDescriptionDetail,txtDesName,txtpromotion;
        Spinner spinner;
        Button btnAddCart;
      Productvertical productvertical;
      FirebaseAuth mAuth;

    String id;
    String name;
    String image;
    String price;
    String description;
    String imagedes1;
    String imagedes2;
    String promotional;
    int soluong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_products);
        AX();

        GetinforProduct();
        SetSpiner();
        Actiontoobar();
        mAuth = FirebaseAuth.getInstance();
        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Themgiohang();
//                Intent intent = new Intent(DetailProducts.this, Fragment.class);
//                startActivity(intent);
//                Intent intent = new Intent(DetailProducts.this,CartFragment.class);
//                startActivity(intent);
                finish();

            }
        });
    }


    public void SetSpiner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    public void GetinforProduct() {
        productvertical =  (Productvertical) getIntent().getSerializableExtra("PRODUCSTDETAIL");
        if(productvertical != null) {
            id = productvertical.getId();
            name = productvertical.getName();
            image = productvertical.getImage();
            price = productvertical.getPrice();
            description = productvertical.getDescription();
            imagedes1 = productvertical.getImagedes1();
            imagedes2 = productvertical.getImagedes2();
            promotional = productvertical.getPromotional();

            txtNameDetail.setText(name);
            txtDesName.setText(name);
            int i = Integer.parseInt(price);
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtPriceDetail.setText("Price : " + decimalFormat.format(i) + "Đ");
            txtDescriptionDetail.setText(description);
            Picasso.get().load(image).into(imageViewDetail);
            Picasso.get().load(imagedes1).into(imageViewDetail1);
            Picasso.get().load(imagedes2).into(imageViewDetail2);
            if (promotional!=null){
                int pro = Integer.parseInt(promotional);
                txtpromotion.setText("Promotion : " + decimalFormat.format(pro) + "Đ");
            }

        }else {
            Toast.makeText(this,"Load data false",Toast.LENGTH_SHORT).show();

        }
    }

    private void Actiontoobar(){
       setSupportActionBar(toolbarDetail);
//       getActionBar().setDisplayHomeAsUpEnabled(true);
//       toolbarDetail.setNavigationOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View v) {
//               finish();
//           }
//       });
   }
   private void AX(){
        txtDesName = findViewById(R.id.namedesprodetai);
        toolbarDetail = (Toolbar)findViewById(R.id.detailtoolbar);
        imageViewDetail = findViewById(R.id.imaview_detail);
        imageViewDetail1 = findViewById(R.id.imageDetail1);
       imageViewDetail2 = findViewById(R.id.imageDetail2);
       txtNameDetail = findViewById(R.id.nameDetail);
       txtPriceDetail = findViewById(R.id.priceDetail);
       txtDescriptionDetail = findViewById(R.id.descriptionDetai);
       spinner = findViewById(R.id.spiner);
       btnAddCart = findViewById(R.id.addCart);
       txtpromotion = findViewById(R.id.promotionDetail);

   }

    private void Themgiohang() {
        soluong = Integer.parseInt(spinner.getSelectedItem().toString());
        FirebaseUser user = mAuth.getCurrentUser();
        String uid = user.getUid();
        String id = productvertical.getId();
        String price = productvertical.getPrice();
        int pr = Integer.parseInt(price);
        String promotion = productvertical.getPromotional();
        long  Total = 0;
        if(promotion != null){
            int promo = Integer.parseInt(promotion);
            Total = promo * soluong;
        } else
        if(promotion == null){
            Total = pr * soluong;
        }

        DatabaseReference refGiohang = FirebaseDatabase.getInstance().getReference("GioHang");
        HashMap<String,Object> giohang = new HashMap<>();
        giohang.put(id,productvertical);
        refGiohang.child(uid).child("SanPham").updateChildren(giohang).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(DetailProducts.this, "Add cart success !", Toast.LENGTH_SHORT).show();
            }
        });
        refGiohang.child(uid).child("SanPham").child(id).child("SoLuong").setValue(soluong);
        refGiohang.child(uid).child("SanPham").child(id).child("Total").setValue(Total);
    }


}
