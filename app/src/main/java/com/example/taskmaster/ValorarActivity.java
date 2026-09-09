package com.example.taskmaster;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ValorarActivity extends AppCompatActivity {

    // Declaramos los componentes
    RatingBar ratingApp;
    Button btnValorar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_valorar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Relacionamos Java con XML
        ratingApp = findViewById(R.id.ratingApp);

        btnValorar = findViewById(R.id.btnValorar);



        // Evento del botón Valorar App
        btnValorar.setOnClickListener((View view) -> {

            // Obtenemos la cantidad de estrellas
            float estrellas = ratingApp.getRating();

            // Comprobamos que haya una valoración
            if (estrellas == 0) {

                Toast.makeText(ValorarActivity.this, "Seleccione una valoración",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                Toast.makeText(ValorarActivity.this,
                        "Gracias por valorar TaskMaster con " + estrellas + " estrellas",
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
}
