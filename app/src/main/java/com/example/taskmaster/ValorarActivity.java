package com.example.taskmaster;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class ValorarActivity extends AppCompatActivity {

    // Declaramos los componentes
    RatingBar ratingApp;
    Button btnValorar;

    TextToSpeech tts;


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

        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.ITALY);
            }
        });


        btnValorar.setOnClickListener((View view) -> {

            // Obtenemos la cantidad de estrellas
            float estrellas = ratingApp.getRating();

            // Comprobamos que haya una valoración
            if (estrellas == 0) {

                Toast.makeText(
                        ValorarActivity.this,
                        "Seleccione una valoración",
                        Toast.LENGTH_SHORT
                ).show();

                // Configuración de la voz
                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                // Mensaje hablado
                tts.speak(
                        "Seleccione una valoración",
                        TextToSpeech.QUEUE_ADD,
                        null,
                        "TTS_MSG_ID"
                );

            } else {

                String mensaje = "Gracias por valorar TaskMaster con "
                        + estrellas
                        + " estrellas";

                Toast.makeText(
                        ValorarActivity.this,
                        mensaje,
                        Toast.LENGTH_LONG
                ).show();

                // Configuración de la voz
                tts.setPitch(1.0f);
                tts.setSpeechRate(1.0f);

                // Mensaje hablado
                tts.speak(
                        mensaje,
                        TextToSpeech.QUEUE_ADD,
                        null,
                        "TTS_MSG_ID"
                );
            }
        });
    }
}