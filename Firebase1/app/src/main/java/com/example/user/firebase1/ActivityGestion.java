package com.example.user.firebase1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ActivityGestion extends AppCompatActivity {


    Button LO,GP,GU,GF;
    final int ActivityProductos1=3;
    final int ActivityVarios1=4;
    final int ActivityFavoritos1=5;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);

        LO = (Button) findViewById(R.id.LO);
        GP = (Button) findViewById(R.id.GP);
        GU = (Button) findViewById(R.id.GU);
        GF = (Button) findViewById(R.id.GF);


        LO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        GP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityProductos.class);

                startActivityForResult(i, ActivityProductos1);
            }
        });


        GU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityVarios.class);

                startActivityForResult(i, ActivityVarios1);
            }
        });

        GF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ActivityFavoritos.class);

                startActivityForResult(i, ActivityFavoritos1);
            }
        });


    }
}
