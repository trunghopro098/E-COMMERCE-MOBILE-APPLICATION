package com.sict.udn.myshoes;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sict.udn.Adapter.ProductsAdapter;
import com.sict.udn.Adapter.Productsadapterver;
import com.sict.udn.Adapter.SliderAdapter;
import com.sict.udn.Model.Products;
import com.sict.udn.Model.Productvertical;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    private ViewPager viewPager;
    private ArrayList<Productvertical> arrayList;
    private Productsadapterver  productsadapterver;
    private Productvertical productvertical;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView, recyclerViewproduct;
    GridLayoutManager gridLayoutManager;
    private ProductsAdapter productsAdapter;


    int image[] = {R.drawable.salepanner1,R.drawable.salepanner2, R.drawable.salepannner3,R.drawable.salepanner4};
    int currentPageCuntr = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frapment_home, container, false);


//        ánh xạ
        viewPager = view.findViewById(R.id.viewpage);
        recyclerView = (RecyclerView) view.findViewById(R.id.rcv_productVertical);
        recyclerViewproduct = (RecyclerView) view.findViewById(R.id.rcv_product_doc);



        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewproduct.setLayoutManager(gridLayoutManager);

//slide
        viewPager.setAdapter(new SliderAdapter(image,getActivity()));
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPageCuntr == image.length){
                    currentPageCuntr = 0;

                }
                viewPager.setCurrentItem(currentPageCuntr++,true);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },4500,4500);

        //đổ dử liệu lên recycalview
        arrayList = new ArrayList();
        //    kết nối đén database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("dbproduct");
        //get dữu liệu từ firebase
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
            for (DataSnapshot data :  dataSnapshot.getChildren()){
                productvertical = data.getValue(Productvertical.class);
                productvertical.setId(data.getKey());
                arrayList.add(productvertical);

            }
                productsadapterver = new Productsadapterver(arrayList, getActivity());
                productsAdapter = new ProductsAdapter(arrayList,getActivity());
                recyclerView.setAdapter(productsadapterver);
                recyclerViewproduct.setAdapter(productsAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),"Load Data Falied"+databaseError.toString(),Toast.LENGTH_LONG).show();
                Log.d("MYTAG","onCalled"+databaseError.toString());
            }
        });


return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.search);

    }


}
