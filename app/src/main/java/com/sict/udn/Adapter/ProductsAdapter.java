package com.sict.udn.Adapter;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sict.udn.Model.Products;
import com.sict.udn.Model.Productvertical;
import com.sict.udn.myshoes.DetailProducts;
import com.sict.udn.myshoes.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.zip.Inflater;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder>
{
    ArrayList<Productvertical> products;
    Context context;

    public ProductsAdapter(ArrayList<Productvertical> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
       View view = layoutInflater.inflate(R.layout.custom_rcv_product,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String price = products.get(position).getPrice();
        String pri = products.get(position).getPromotional();
        int price1 = Integer.parseInt(price);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txt_name.setText( products.get(position).getName());
        Picasso.get().load(products.get(position).getImage()).into(holder.imageView);
        holder.txt_price.setText("Price : " + decimalFormat.format(price1) + "Đ");
        if(pri != null){
            int p = Integer.parseInt(pri);
            holder.txtpromotion.setText("Promotion: " + decimalFormat.format(p) + "Đ");

        }

//        if(promotionprice > 0){

////            SpannableStringBuilder spnBuider = new SpannableStringBuilder(s);
////            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
////            spnBuider.setSpan(strikethroughSpan, 0,12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
////            holder.txt_price.setText(spnBuider);
//            holder.txt_price.setText("Price : " + decimalFormat.format(price1) + "Đ");
//
//            holder.txtpromotion.setText("Promotional: " + decimalFormat.format(promotionprice) + "Đ");
//        }else {
//            holder.txt_price.setText("Price : " + decimalFormat.format(price1) + "Đ");
//        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txt_price, txt_name,txtpromotion;
        Button btnDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.rcvimaproduct);
        txt_name = (TextView) itemView.findViewById(R.id.txt_nameGiay);
        txt_price = (TextView) itemView.findViewById(R.id.txt_price);
        txtpromotion = (TextView) itemView.findViewById(R.id.txt_pricepromotion) ;
        btnDetail = (Button) itemView.findViewById(R.id.btn_Detail);

            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context , DetailProducts.class);
                    intent.putExtra("PRODUCSTDETAIL",products.get(getPosition()) );
                    context.startActivity(intent);



                }
            });
        }
    }
}
