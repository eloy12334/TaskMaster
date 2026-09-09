package com.example.taskmaster;

// Importamos Bundle para trabajar con el ciclo de vida de la Activity
import android.content.Intent;
import android.os.Bundle;

//Importamos View para los eventos de los botones
import android.view.View;

// Importamos los componentes que vamos a utilizar
import android.widget.Button;
import android.widget.TextView;

// Importamos las clases necesarias para trabajar con la pantalla
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    // Declaramos los componentes del XML
    TextView tvTitulo;
    TextView tvBienvenida;

    Button btnTareas;
    Button btnNuevaTarea;
    Button btnSalir;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Relacionamos las variables Java con los componentes del XML
        tvTitulo = findViewById(R.id.tvTitulo);
        tvBienvenida = findViewById(R.id.tvBienvenida);

        btnTareas = findViewById(R.id.btnTareas);
        btnNuevaTarea = findViewById(R.id.btnNuevaTarea);
        btnSalir = findViewById(R.id.btnSalir);


        // botón Mis tareas
        btnTareas.setOnClickListener((View view) -> {

            // Creamos un Intent para ir a MisTareasActivity
            Intent intent = new Intent(
                    MenuActivity.this,
                    MisTareasActivity.class
            );

            // Abrimos la pantalla de tareas
            startActivity(intent);

        });


        // botón Nueva tarea
        btnNuevaTarea.setOnClickListener((View view) -> {

            // Creamos un Intent para ir a TareaActivity
            Intent intent = new Intent(
                    MenuActivity.this,
                    TareaActivity.class
            );

            // Abrimos la pantalla para crear una tarea
            startActivity(intent);


        });


        // Evento del botón Salir
        btnSalir.setOnClickListener((View view) -> {

            // Volvemos a la pantalla de Login
            finish();

        });

    }
}