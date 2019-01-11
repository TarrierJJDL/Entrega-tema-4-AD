package com.example.user.firebase1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.firebase1.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    Bundle b = new Bundle();
    EditText nomuser, nomyape, direc;
    Button añadir;
    private FirebaseAuth mAuth;
    int cont=0;
    DatabaseReference bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nomuser = (EditText) findViewById(R.id.nomuser);
        nomyape = (EditText) findViewById(R.id.nomyape);
        direc = (EditText) findViewById(R.id.direc);
        añadir = (Button) findViewById(R.id.añadir);


        bbdd = FirebaseDatabase.getInstance().getReference("usuarios");

        añadir.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser User = mAuth.getCurrentUser();

                String nomus = nomuser.getText().toString();
                String nya = nomyape.getText().toString();
                String dir = direc.getText().toString();
                String correo = User.getEmail();

                Query q=bbdd.orderByChild(getString(R.string.n_nu)).equalTo(nomus);

                q.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            cont++;

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if (cont==0){
                    Usuario u = new Usuario(nomus,nya,correo,dir);

                    String clave = bbdd.push().getKey();



                    bbdd.child(clave).setValue(u);
                    Toast.makeText(MainActivity.this, "Registro hecho",
                            Toast.LENGTH_SHORT).show();



                    finish();

                }

                else{
                    Toast.makeText(MainActivity.this, "El nombre de usuario ya existe",
                        Toast.LENGTH_SHORT).show();

                }


            }



        });

    }
}

