package com.example.user.firebase1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ActivityLogin extends AppCompatActivity {

    Bundle b = new Bundle();
    EditText email, contraseña;
    Button entrar, registrar;

    final int MainActivity1=1;
    final int ActivityGestion1=2;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.Email);
        contraseña = (EditText) findViewById(R.id.contraseña);
        entrar = (Button) findViewById(R.id.button);
        registrar = (Button) findViewById(R.id.button2);






        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String correo = email.getText().toString();
                String pass = contraseña.getText().toString();

                acceder(correo,pass);
            }
        });


        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String correo = email.getText().toString();
                String pass = contraseña.getText().toString();

                registrar(correo,pass);
            }
        });
    }


    private void registrar(String correo, String pass){

        mAuth = FirebaseAuth.getInstance();





        mAuth.createUserWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser User = mAuth.getCurrentUser();
                            Toast.makeText(ActivityLogin.this, "Authentication successfull. "+ User.getUid(),
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);

                            startActivityForResult(i, MainActivity1);
                        } else {


                            Toast.makeText(ActivityLogin.this, "Authentication failed. "+task.getException(),
                                    Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                });



    }

    private void acceder(String correo, String pass){

        mAuth = FirebaseAuth.getInstance();


        mAuth.signInWithEmailAndPassword(correo, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser User = mAuth.getCurrentUser();
                            Toast.makeText(ActivityLogin.this, "Signing successfull. "+ User.getUid(),
                                    Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), ActivityGestion.class);

                            startActivityForResult(i, ActivityGestion1);

                        } else {


                            Toast.makeText(ActivityLogin.this, "Signing failed. "+task.getException(),
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });



    }
}
