package com.example.tugaspraktikum;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
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
import com.google.firebase.firestore.auth.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.security.auth.Subject;

public class MainActivity<firebaseDatabase, databaseReference, x> extends AppCompatActivity {
    EditText etnamaPelanggan,etjumlahBarang;
    String customerName;
    String namaBarang;
    int index;
    double jumlahBarang;
    Button btnProses, btnHapus, btnKeluar;
    Spinner spinner;


    ArrayList<String> namaProduk;
    ArrayList<Pair<String,Product>> listproduk;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
//                                productList.add(document.getData().get("namaBarang").toString());
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
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

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
                System.out.println(index);

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
}