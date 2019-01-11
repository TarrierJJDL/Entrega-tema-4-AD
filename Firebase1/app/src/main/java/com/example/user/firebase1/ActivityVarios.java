package com.example.user.firebase1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.firebase1.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityVarios extends AppCompatActivity {

    Button modificar, cerrar;
    ListView lista;
    EditText nomuser, direc, nomyape;

    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_varios);



        cerrar = (Button) findViewById(R.id.cerrar);
        modificar = (Button) findViewById(R.id.modi);
        nomuser = (EditText) findViewById(R.id.nomuser);
        nomyape = (EditText) findViewById(R.id.nomyape);
        direc = (EditText) findViewById(R.id.direc);
        lista = (ListView)findViewById(R.id.listView);

        bbdd = FirebaseDatabase.getInstance().getReference("usuarios");

        bbdd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ArrayAdapter<String> adaptador;
                ArrayList<String> listado = new ArrayList<String>();

                for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                    Usuario user = datasnapshot.getValue(Usuario.class);


                    String nomyape = user.getNomyape();
                    String direccion = user.getDireccion();
                    listado.add(nomyape);
                    listado.add(direccion);

                }
                adaptador = new ArrayAdapter<String>(ActivityVarios.this,android.R.layout.simple_list_item_1,listado);
                lista.setAdapter(adaptador);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        modificar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                String nomus = nomuser.getText().toString();

                if(!TextUtils.isEmpty(nomus)){
                    Query q=bbdd.orderByChild(getString(R.string.n_nu)).equalTo(nomus);

                    q.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                                String clave=datasnapshot.getKey();
                                bbdd.child(clave).child(getString(R.string.n_nya)).setValue(nomyape.getText().toString());
                                bbdd.child(clave).child(getString(R.string.n_dir)).setValue(direc.getText().toString());
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    Toast.makeText(ActivityVarios.this, "Los nombres y direccion se ha modificado con éxito", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ActivityVarios.this, "Debes de introducir un nombre de ususario", Toast.LENGTH_LONG).show();
                }

            }
        });


        cerrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                finish();
            }
        });


    }
}
