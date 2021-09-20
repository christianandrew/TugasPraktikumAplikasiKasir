package com.example.tugaspraktikum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity<firebaseDatabase, databaseReference> extends AppCompatActivity {
    EditText etNamaPelanggan,etjumlahBarang;
    String customerName;
    TextView namaBarang;
    String jumlahBarang;
    Button btnProses, btnHapus, btnKeluar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

//  Database listview
    ListView listView;
    ArrayList<String> productList = new ArrayList<>();
    ArrayAdapter<String> ArrayAdapter;





//    String namaPelanggan, namaBarang, jumlahBarang, hargaBarang, uangBayar;
    double jmlBarang, hrgBarang, total, kembalian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_new);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.aplikasi));
        }

//      Call Database
        db.collection("produk")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                productList.add(document.getData().get("namaBarang").toString());
                                System.out.println(document.getData().get("namaBarang").toString());
                            }
                        } else {
                            Log.w("Text", "Error getting documents.", task.getException());
                        }
                    }
                });
//      Spinner
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        CollectionReference subjectsRef = rootRef.collection("produk");
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("namaBarang");
                        subjects.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //EditText
        etNamaPelanggan = findViewById(R.id.etNamaPelanggan);
        etjumlahBarang = findViewById(R.id.etjumlahBarang);
        //TextView
//        customerName = findViewById(R.id.customerName);
//        tvnamaBarang = findViewById(R.id.namaBarang);
//        tvjumlahBarang = findViewById(R.id.jumlahBarang);

        //Button
        btnProses = findViewById(R.id.btnProses);
        btnHapus = findViewById(R.id.btnHapus);
        btnKeluar = findViewById(R.id.btnKeluar);

        //Proses
        btnProses.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                customerName = etNamaPelanggan.getText().toString().trim();
                jumlahBarang = etjumlahBarang.getText().toString().trim();
//                hargaBarang = etHarga.getText().toString().trim();
//                uangBayar = etJmlUang.getText().toString().trim();

//                jmlBarang = Double.parseDouble(String.valueOf(jumlahBarang));
//                hrgBarang = Double.parseDouble(hargaBarang);
//                uangByr = Double.parseDouble(uangBayar);
//                total = (jmlBarang * hrgBarang);
//                tvNamaPembeli.setText("Nama Pembeli : " + namaPelanggan);
//                tvNamaBarang.setText("Nama Barang : " + namaBarang);
//                tvJmlBarang.setText("Jumlah Barang : " + jumlahBarang);
//                tvHarga.setText("Harga Barang : " + hargaBarang);
//                tvUangBayar.setText("Uang bayar : " + uangBayar);

//                tvTotal.setText("Total Belanja " + total);
//                if (total >= 200000) {
//                    tvBonus.setText("Bonus : HardDisk 1TB");
//                } else if (total >= 50000) {
//                    tvBonus.setText("Bonus : Keyboard Gaming");
//                } else if (total >= 40000) {
//                    tvBonus.setText("Bonus : Mouse Gaming");
//                } else {
//                    tvBonus.setText("Bonus : Tidak ada bonus!");
//                }
//
//                kembalian = (uangByr - total);
//                if (uangByr < total) {
//                    tvKeterangan.setText("Keterangan : Uang bayar kurang Rp. " + (-kembalian));
//                    tvKembalian.setText("Uang Kembalian : Rp. 0");
//                } else {
//                    tvKeterangan.setText("Keterangan : Tunggu kembalian");
//                    tvKembalian.setText("Uang Kembalian : Rp. " + kembalian);
//                }

            }
        });

        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("namaBarang", "Kacang Sukro");
                user.put("hargaBarang", 50000);
                user.put("id", 005);

                // Add a new document with a generated ID
                db.collection("produk")
                        .add(user)
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
//                tvNamaPembeli.setText("");
//                tvNamaBarang.setText("");
//                tvJmlBarang.setText("");
//                tvHarga.setText("");
//                tvUangBayar.setText("");
//                tvKembalian.setText("");
//                tvKeterangan.setText("");
//                tvBonus.setText("");
//                tvTotal.setText("");

                Toast.makeText(getApplicationContext(), "Data sudah dihapus", Toast.LENGTH_SHORT).show();
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveTaskToBack(true);
            }
        });

    }
}