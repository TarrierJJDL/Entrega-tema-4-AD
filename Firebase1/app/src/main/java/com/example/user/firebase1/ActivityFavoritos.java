package com.example.user.firebase1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.firebase1.model.Productos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityFavoritos extends AppCompatActivity {

    Spinner spin;
    Button marcar, atras;
    ListView list;
    private FirebaseAuth mAuth;
    DatabaseReference bbdd, bbdd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        spin = (Spinner) findViewById(R.id.spinner);
        marcar = (Button) findViewById(R.id.marcar);
        atras = (Button) findViewById(R.id.cerrar);
        list = (ListView)findViewById(R.id.listView);


        bbdd = FirebaseDatabase.getInstance().getReference("usuarios");
        bbdd2 = FirebaseDatabase.getInstance().getReference("productos");
        bbdd2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();


                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()) {
                    Productos pro = datasnapshot.getValue(Productos.class);

                    String n = pro.getNombre();
                    listado.add(n);

                }adaptador = new ArrayAdapter<String>(ActivityFavoritos.this,android.R.layout.simple_list_item_1,listado);
                spin.setAdapter(adaptador);


                mAuth = FirebaseAuth.getInstance();
                FirebaseUser User = mAuth.getCurrentUser();
                String uid = User.getUid();
                String fav = "true"+uid;

                Query q=bbdd2.orderByChild(getString(R.string.n_f)).equalTo(fav);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayAdapter<String> adaptad;
                        ArrayList<String> lista = new ArrayList<String>();

                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            Productos pro = datasnapshot.getValue(Productos.class);

                            String n = pro.getNombre();
                            lista.add(n);

                        } adaptad = new ArrayAdapter<String>(ActivityFavoritos.this,android.R.layout.simple_list_item_1,lista);
                        list.setAdapter(adaptad);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        marcar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String nomus = spin.getSelectedItem().toString();

                if(!TextUtils.isEmpty(nomus)){

                    Query q=bbdd2.orderByChild(getString(R.string.n_n)).equalTo(nomus);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                mAuth = FirebaseAuth.getInstance();
                                FirebaseUser User = mAuth.getCurrentUser();
                                String uid = User.getUid();
                                String clave=datasnapshot.getKey();
                                bbdd2.child(clave).child(getString(R.string.n_f)).setValue("true"+uid);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(ActivityFavoritos.this, "El producto se ha marcado con éxito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ActivityFavoritos.this, "Debes seleccionar un producto", Toast.LENGTH_LONG).show();
                }

            }
        });

        atras.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });


    }
}
