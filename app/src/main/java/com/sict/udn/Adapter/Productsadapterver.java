package com.sict.udn.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.udn.Model.Productvertical;
import com.sict.udn.myshoes.DetailProducts;
import com.sict.udn.myshoes.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Productsadapterver extends RecyclerView.Adapter<Productsadapterver.ViewHolder> {
    ArrayList<Productvertical> productverticals;
    Context context;

    public Productsadapterver(ArrayList<Productvertical> productverticals, Context context) {
        this.productverticals = productverticals;
        this.context = context;
    }

    @NonNull
    @Override
//    onCreateViewHolder : phương thức này dùng để tạo view mới cho RecyclerView.
//    Nếu RecyclerView đã cached lại View thì phương thức này sẽ không gọi.

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.custtom_rcv_productvertical, parent, false);
        return new ViewHolder(view);


    }
    //            onBindViewHolder : phương thức này dùng để gắn data và view.
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String price;
        price = productverticals.get(position).getPrice();
        int price1 = Integer.parseInt(price);
//        holder.txt_name.setText( products.get(position).getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_price.setText("Price : " + decimalFormat.format(price1) + "Đ");
        Picasso.get().load(productverticals.get(position).getImage()).into(holder.img_anh);
    }

    @Override
    public int getItemCount() {
        return productverticals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_anh;
        TextView txt_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_anh = (ImageView) itemView.findViewById(R.id.rcvImaviewVertical);
            txt_price = (TextView) itemView.findViewById(R.id.rcvtextprice);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , DetailProducts.class);
                    intent.putExtra("PRODUCSTDETAIL",productverticals.get(getPosition()) );
                    context.startActivity(intent);



                }
            });

        }
    }
}
