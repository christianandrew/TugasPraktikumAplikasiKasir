package com.example.tugaspraktikum;

import android.os.Bundle;
import android.util.Pair;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        RecyclerView rvProducts;
        CartRecyclerAdapter adapter;
        rvProducts = findViewById(R.id.recyclerView2);
        ArrayList<Pair<String, OrderedProduct>> cart = new ArrayList<>();

        adapter = new CartRecyclerAdapter(CartActivity.this, cart);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));


        //RecyclerView
        firestore.collection("/users").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                    cart.add(new Pair<>(
                            documentSnapshot.getId(),
                            new OrderedProduct(documentSnapshot.getData().get("namaPelanggan").toString(),
                                    documentSnapshot.getData().get("productId").toString(),
                                    Integer.parseInt(documentSnapshot.getData().get("jumlahBarang").toString()))));
                }
            }
            adapter.notifyDataSetChanged();
        });

    }
}