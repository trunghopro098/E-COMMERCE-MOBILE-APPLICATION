package com.sict.udn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.udn.Model.Cart;
import com.sict.udn.myshoes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCheckOut extends RecyclerView.Adapter<AdapterCheckOut.ViewHolder> {
    ArrayList<Cart> carts;
    Context context;

    public AdapterCheckOut(ArrayList<Cart> carts, Context context) {
        this.carts = carts;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.cusstom_rcv_checkout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txt_Name.setText( carts.get(position).getName());
        String tong = String.valueOf(carts.get(position).getTotal());
        int tong1 = Integer.parseInt(tong);
        String tongformat = String.format("%,d",tong1);
        holder.txt_Total.setText("Total : "+tongformat+"Đ");
        String sl = String.valueOf(carts.get(position).getSoluong());
        int sl1 = Integer.parseInt(sl);
        String slfm = String.format("%,d",sl1);
        holder.txt_soluong.setText("Quantity  :"+slfm);
        Picasso.get().load(carts.get(position).getImage()).into(holder.imgviewCheck);


    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgviewCheck;
        TextView txt_Name, txt_soluong, txt_Total;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgviewCheck = (ImageView) itemView.findViewById(R.id.Checkout_Cartimaview);
            txt_Name = (TextView) itemView.findViewById(R.id.CheckoutCartNameProduct);
            txt_soluong = (TextView) itemView.findViewById(R.id.cHeckoutSoLuong);
            txt_Total = (TextView) itemView.findViewById(R.id.CheckoutCartTotal);
        }
    }
}
