package com.example.tugaspraktikum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText etnamaPelanggan,etjumlahBarang;
    String customerName;
    int index;
    Button btnProses, btnHapus, btnKeluar;
    Spinner spinner;
    ArrayList<String> namaProduk;
    ArrayList<Pair<String,Product>> listproduk;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private RecyclerView firestorelist;
    private FirestoreRecyclerAdapter<ProductModel, ProductViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_new);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.aplikasi));
        }

        spinner = findViewById(R.id.spinner);

//      Database Call
        db.collection("produk")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                System.out.println(document.getData().get("namaBarang").toString());
                            }
                        } else {
                            Log.w("Text", "Error getting documents.", task.getException());
                        }
                    }
                });
//      Database End

//      Spinner Start
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = rootRef.collection("produk");
        Spinner spinner = findViewById(R.id.spinner);

        namaProduk = new ArrayList<>();
        listproduk = new ArrayList<>();

        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        listproduk.add(new Pair<>(document.getId(), new Product(document.getData().get("namaBarang").toString(), Integer.parseInt(document.getData().get("hargaBarang").toString()))));
                        namaProduk.add(document.getData().get("namaBarang").toString());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, namaProduk);
                    spinner.setAdapter(adapter);
                }
            }
        });
//      Spinner End

//      Recycler View
//        firestorelist = findViewById(R.id.recyclerView);
//
//
//        Query query = db.collection("produk");
//
//        FirestoreRecyclerOptions<ProductModel> options = new FirestoreRecyclerOptions.Builder<ProductModel>()
//                .setQuery(query, ProductModel.class)
//                .build();
//
//        adapter = new FirestoreRecyclerAdapter<ProductModel, ProductHolder>(options) {
//            @Override
//            public void onBindViewHolder(ProductHolder holder, int position, ProductModel model) {
//                holder.list_name.setText(model.getNamaBarang());
//                holder.list_price.setText(model.getHargaBarang());
//            }
//            @Override
//            public ProductHolder onCreateViewHolder(ViewGroup group, int i) {
//                View view = LayoutInflater.from(group.getContext())
//                        .inflate(R.layout.list_item, group, false);
//                return new ProductHolder(view);
//            }
//            @Override
//            public void onError(FirebaseFirestoreException e) {
//                Log.e("error", e.getMessage());
//            }
//        };
//        firestorelist.setHasFixedSize(true);
//        firestorelist.setLayoutManager(new LinearLayoutManager(this));
//        firestorelist.setAdapter(adapter);
//      End Recview

//       Test Recview
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Query query = db.collection("users");
        FirestoreRecyclerOptions<ProductModel> options = new FirestoreRecyclerOptions.Builder<ProductModel>()
                .setQuery(query, ProductModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<ProductModel, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull ProductModel model) {
                holder.setProductName(model.getNamaPelanggan());
                holder.setProductPrice(model.getTotalBelanja());
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new ProductViewHolder(view);
            }
        };
        recyclerView.setAdapter(adapter);



        //EditText
        etnamaPelanggan = findViewById(R.id.etnamaPelanggan);
        etjumlahBarang = findViewById(R.id.etjumlahBarang);

        //Button
        btnProses = findViewById(R.id.btnProses);
        btnHapus = findViewById(R.id.btnHapus);
        btnKeluar = findViewById(R.id.btnKeluar);


        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etnamaPelanggan.setText("");
                etjumlahBarang.setText("");
                Toast.makeText(getApplicationContext(), "Data sudah dihapus", Toast.LENGTH_SHORT).show();
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String spinner_data = spinner.getSelectedItem().toString();
                index = namaProduk.indexOf(spinner_data);
//                System.out.println(index);

                String product_id = listproduk.get(index).first;
                Integer hargabarang = listproduk.get(index).second.getHargaBarang();

                customerName = etnamaPelanggan.getText().toString().trim();
                int value=Integer.parseInt(etjumlahBarang.getText().toString());

                UserCart push = new UserCart(customerName,product_id,value,hargabarang * value);

                // Add a new document with a generated ID
                db.collection("users")
                        .add(push)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
//                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_SHORT).show();
//                                Log.w(TAG, "Error adding document", e);
                            }
                        });
                Toast.makeText(getApplicationContext(), "Data sudah di input", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private class ProductViewHolder extends RecyclerView.ViewHolder {
        private View view;

        ProductViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setProductName(String namaPelanggan) {
            TextView textView = view.findViewById(R.id.list_namaPelanggan);
            textView.setText(namaPelanggan);
        }
        void setProductPrice(Integer totalBelanja) {
            TextView textView = view.findViewById(R.id.list_totalBelanja);
            textView.setText(String.valueOf(totalBelanja));
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }
}