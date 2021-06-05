package com.sict.udn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sict.udn.Model.Cart;
import com.sict.udn.myshoes.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<Cart> carts;
    Context context;
    FirebaseAuth mAuth;

    public CartAdapter(ArrayList<Cart> carts, Context context) {
        this.carts = carts;
        this.context = context;
    }

    public CartAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custtom_cart,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txt_Cartname.setText( carts.get(position).getName());

        Picasso.get().load(carts.get(position).getImage()).into(holder.imagecart);

        String hiensl = String.valueOf(carts.get(position).getSoluong());
        String total = String.valueOf(carts.get(position).getTotal());
        int total1 = Integer.parseInt(total);
        String hienTotal = String.format("%,d",total1);
        holder.btnValue.setText( hiensl);
        holder.txt_Total.setText( "Total : "+hienTotal +"Đ");


        String price = carts.get(position).getPrice();
        int pri = Integer.parseInt(price);
        String hiengia = String.format("%,d",pri);
        String promotion = carts.get(position).getPromotional();

        holder.txt_CartPrice.setText("Price : "+hiengia +"Đ" );
        if (promotion != null){
            int pro = Integer.parseInt(promotion);
            String hienkm = String.format("%,d",pro);
            holder.txt_Promotion.setText("Promotion : "+hienkm +"Đ" );
        }

    holder.btnTru.setOnClickListener(new View.OnClickListener() {
        int Total = 0;
        @Override
        public void onClick(View v) {
            int soluongc = carts.get(position).getSoluong();
            if (soluongc<=1){
                Toast.makeText(context, "Delete this product!", Toast.LENGTH_SHORT).show();
                return;
            }
            int kq = soluongc - 1;
//            cập nhật lại total trong data
            String price = carts.get(position).getPrice();
            int pri = Integer.parseInt(price);
            String promotion = carts.get(position).getPromotional();
            if (promotion != null) {
                int pro = Integer.parseInt(promotion);
                Total = kq * pro;
            }else{
                Total = kq * pri;
            }
//            kết nối để get data
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GioHang");
//            geet data user qua hàm authentication
            mAuth = FirebaseAuth.getInstance();
            final String uid = mAuth.getCurrentUser().getUid();

//    trỏ đến giá trị soluong cập nhật giá trị
            reference.child(uid).child("SanPham").child(carts.get(position).getId()).child("SoLuong").setValue(kq);
            reference.child(uid).child("SanPham").child(carts.get(position).getId()).child("Total").setValue(Total);
//            load lại dử liệu
            notifyDataSetChanged();
        }
    });

        holder.btnplus.setOnClickListener(new View.OnClickListener() {
            int Total = 0;
            @Override
            public void onClick(View v) {

                int soluongc = carts.get(position).getSoluong();
                int kq = soluongc + 1;
//                cập nhật lại sản phẩm trong giỏ hàng
                String price = carts.get(position).getPrice();
                int pri = Integer.parseInt(price);
                String promotion = carts.get(position).getPromotional();
                if (promotion != null) {
                    int pro = Integer.parseInt(promotion);
                     Total = kq * pro;
                }else{
                    Total = kq * pri;
                }


                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GioHang");
                mAuth = FirebaseAuth.getInstance();
                final String uid = mAuth.getCurrentUser().getUid();
                reference.child(uid).child("SanPham").child(carts.get(position).getId()).child("SoLuong").setValue(kq);
                reference.child(uid).child("SanPham").child(carts.get(position).getId()).child("Total").setValue(Total);
                notifyDataSetChanged();
            }
        });
//        xóa sản phẩm trong giỏ hàng
       holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GioHang");
                mAuth = FirebaseAuth.getInstance();
                final String uid = mAuth.getCurrentUser().getUid();

                reference.child(uid).child("SanPham").child(carts.get(position).getId()).removeValue();
                Toast.makeText(context, "Delete successful product !", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_Cartname, txt_CartPrice, txt_Promotion, txt_Total;
        ImageView imagecart, delete;
        Button btnTru, btnValue, btnplus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_Total = (TextView) itemView.findViewById(R.id.CartTotal);
            delete = (ImageView) itemView.findViewById(R.id.CartDelete);
            txt_Cartname = (TextView) itemView.findViewById(R.id.CartNameProduct);
            txt_CartPrice = (TextView) itemView.findViewById(R.id.CartPrice);
            txt_Promotion = (TextView) itemView.findViewById(R.id.CartPromotion);
            btnplus = (Button) itemView.findViewById(R.id.cartbtnPlus);
            btnTru = (Button) itemView.findViewById(R.id.cartbtnTru);
            btnValue = (Button) itemView.findViewById(R.id.cartbtnValue);
            imagecart = (ImageView) itemView.findViewById(R.id.Cartimaview);

        }

    }
}
