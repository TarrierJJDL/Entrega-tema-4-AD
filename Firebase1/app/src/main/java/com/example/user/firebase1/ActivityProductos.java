package com.example.user.firebase1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.firebase1.model.Productos;
import com.example.user.firebase1.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityProductos extends AppCompatActivity {

    EditText nombre, categoria, descripcion, precio;
    Button añadir, atras, borrar, modi, most;
    Spinner spin, spinp, spinl;
    ListView li;
    private FirebaseAuth mAuth;
    DatabaseReference bbdd, bbdd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);

        nombre = (EditText) findViewById(R.id.nombre);
        descripcion = (EditText) findViewById(R.id.descripcion);
        precio = (EditText) findViewById(R.id.precio);
        spin = (Spinner) findViewById(R.id.spinner);
        spinp = (Spinner) findViewById(R.id.spinnerproductos);
        spinl = (Spinner) findViewById(R.id.spinnerlista);
        borrar = (Button) findViewById(R.id.borrar);
        atras = (Button) findViewById(R.id.cerrar);
        añadir = (Button) findViewById(R.id.anyadir);
        modi = (Button) findViewById(R.id.mod);
        most = (Button) findViewById(R.id.mos);
        li = (ListView)findViewById(R.id.listView);


        bbdd = FirebaseDatabase.getInstance().getReference("usuarios");
        bbdd2 = FirebaseDatabase.getInstance().getReference("productos");
        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                ArrayAdapter<String> adapt;
                ArrayList<String> lista = new ArrayList<String>();

                    String tec = "Tecnologia";
                    String co = "Coches";
                    String ho = "Hogar";

                    listado.add(tec);
                    listado.add(co);
                    listado.add(ho);
                    lista.add(tec);
                    lista.add(co);
                    lista.add(ho);

                adaptador = new ArrayAdapter<String>(ActivityProductos.this,android.R.layout.simple_list_item_1,listado);
                spin.setAdapter(adaptador);

                adapt = new ArrayAdapter<String>(ActivityProductos.this,android.R.layout.simple_list_item_1,lista);
                spinl.setAdapter(adapt);




                mAuth = FirebaseAuth.getInstance();
                FirebaseUser User = mAuth.getCurrentUser();
                String uid = User.getUid();

                Query q=bbdd2.orderByChild(getString(R.string.n_uid)).equalTo(uid);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayAdapter<String> adaptad;
                        ArrayList<String> list = new ArrayList<String>();

                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            Productos pro = datasnapshot.getValue(Productos.class);

                            String n = pro.getNombre();
                            list.add(n);

                        } adaptad = new ArrayAdapter<String>(ActivityProductos.this,android.R.layout.simple_list_item_1,list);
                        spinp.setAdapter(adaptad);


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





        most.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String cate = spinl.getSelectedItem().toString();
                Query u=bbdd2.orderByChild(getString(R.string.n_cat)).equalTo(cate);

                u.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        ArrayAdapter<String> adaptado;
                        ArrayList<String> lis = new ArrayList<String>();

                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            Productos pro = datasnapshot.getValue(Productos.class);

                            String n = pro.getNombre();
                            lis.add(n);

                        } adaptado = new ArrayAdapter<String>(ActivityProductos.this,android.R.layout.simple_list_item_1,lis);
                        li.setAdapter(adaptado);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });


        añadir.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser User = mAuth.getCurrentUser();
                String nom = nombre.getText().toString();
                String cat = spin.getSelectedItem().toString();
                String des = descripcion.getText().toString();
                String pre = precio.getText().toString();
                String uid = User.getUid();
                String fa = null;

                if(!TextUtils.isEmpty(nom)){
                    Productos p = new Productos(nom,des,cat,pre,uid,fa);



                    String clave = bbdd2.push().getKey();

                    bbdd2.child(clave).setValue(p);

                    Toast.makeText( ActivityProductos.this, "Producto añadido", Toast.LENGTH_LONG).show();

                }
                else {
                    Toast.makeText(ActivityProductos.this, "Debes de introducir un nombre", Toast.LENGTH_LONG).show();
                }

            }
        });

        borrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String nom = spinp.getSelectedItem().toString();

                if(!TextUtils.isEmpty(nom)){
                    Query q=bbdd2.orderByChild(getString(R.string.n_n)).equalTo(nom);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();
                                DatabaseReference ref = bbdd2.child(clave);

                                ref.removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(ActivityProductos.this, "El Producto "+nom+" se ha borrado con éxito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ActivityProductos.this, "Debes seleccionar un producto", Toast.LENGTH_LONG).show();
                }

            }
        });

        modi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String nomus = spinp.getSelectedItem().toString();

                if(!TextUtils.isEmpty(nomus)){

                    Query q=bbdd2.orderByChild(getString(R.string.n_n)).equalTo(nomus);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();
                                bbdd2.child(clave).child(getString(R.string.n_nya)).setValue(nombre.getText().toString());
                                bbdd2.child(clave).child(getString(R.string.n_p)).setValue(precio.getText().toString());
                                bbdd2.child(clave).child(getString(R.string.n_cat)).setValue(spin.getSelectedItem().toString());
                                bbdd2.child(clave).child(getString(R.string.n_d)).setValue(descripcion.getText().toString());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(ActivityProductos.this, "El producto se ha modificado con éxito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ActivityProductos.this, "Debes seleccionar un producto", Toast.LENGTH_LONG).show();
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
