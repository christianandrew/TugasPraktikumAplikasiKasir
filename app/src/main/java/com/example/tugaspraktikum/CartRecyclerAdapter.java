package com.example.tugaspraktikum;

import android.app.Dialog;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.MyViewHolder>{

    private Context context;
    private List<Pair<String,OrderedProduct>> orderedProductsList;
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();



    public CartRecyclerAdapter(Context context, List<Pair<String,OrderedProduct>> productsList) {
        this.context = context;
        this.orderedProductsList = productsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_per_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartRecyclerAdapter.MyViewHolder holder, int position) {

        OrderedProduct orderedProduct = orderedProductsList.get(position).second;
        Product product = new Product();
        firestore.collection("/produk").document(orderedProduct.getProductId()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){

                product.getNamaBarang(task.getResult().getData().get("namaBarang").toString());
                product.getHargaBarang(Integer.parseInt(task.getResult().getData().get("hargaBarang").toString()));
                holder.nama_barang.setText(product.getNamaBarang(task.getResult().getData().get("namaBarang").toString()));
//                holder.jumlah_kuantitas.setText(String.valueOf(orderedProduct.getQuantity()));
                holder.nama_pembeli.setText(orderedProduct.getUser());
//                holder.total_harga.setText(String.valueOf(orderedProduct.getQuantity() * product.getHargaBarang(Integer.parseInt(task.getResult().getData().get("totalBelanja").toString()))));
                holder.detail_btn.setOnClickListener(view -> {

                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    DetailFragment detailFragment = DetailFragment.newInstance(orderedProduct.getUser());
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.cartList,detailFragment).addToBackStack(null).commit();

                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderedProductsList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView nama_pembeli;
        TextView jumlah_kuantitas;
        TextView nama_barang;
        TextView total_harga;
        Button detail_btn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nama_pembeli = (TextView) itemView.findViewById(R.id.nama_pembeli);
            jumlah_kuantitas = (TextView) itemView.findViewById(R.id.jumlah_kuantitas_barang);
            nama_barang = (TextView) itemView.findViewById(R.id.nama_barang);
            total_harga = (TextView) itemView.findViewById(R.id.total_harga);
            detail_btn = (Button) itemView.findViewById(R.id.detail);
        }

    }
}
