package com.example.taskmaster;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.AlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TareaActivity extends AppCompatActivity {

    //componentes de la pantalla
    TextView tvTitulo;
    TextView tvNombre;
    EditText etNombre;
    TextView tvCategorias;
    Spinner spCategorias;
    TextView tvPrioridad;
    RadioGroup rgPrioridad;
    RadioButton rbAlta;
    RadioButton rbMedia;
    RadioButton rbBaja;
    CheckBox cbImportante;
    Button btnCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tarea);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // java con XML
        tvTitulo = findViewById(R.id.tvTitulo);
        //nombre tarea
        tvNombre = findViewById(R.id.tvNombre);
        etNombre = findViewById(R.id.etNombre);
        //categoria
        tvCategorias = findViewById(R.id.tvCategorias);
        spCategorias = findViewById(R.id.spCategorias);
        //prioridad
        tvPrioridad = findViewById(R.id.tvPrioridad);
        rgPrioridad = findViewById(R.id.rgPrioridad);
        rbAlta = findViewById(R.id.rbAlta);
        rbMedia = findViewById(R.id.rbMedia);
        rbBaja = findViewById(R.id.rbBaja);
        //checkbox
        cbImportante = findViewById(R.id.cbImportante);
        //boton crear
        btnCrear = findViewById(R.id.btnCrear);

        // Evento del botón Crear tarea
        btnCrear.setOnClickListener((View view) -> {

            // Obtenemos el nombre
            String nombre = etNombre.getText().toString().trim();

            // Obtenemos la categoría
            String categoria =
                    spCategorias.getSelectedItem().toString();

            // Obtenemos la prioridad seleccionada
            int prioridadSeleccionada =
                    rgPrioridad.getCheckedRadioButtonId();


            // Validamos el nombre
            if (nombre.isEmpty()) {

                Toast.makeText(
                        TareaActivity.this,
                        "Ingrese un nombre para la tarea",
                        Toast.LENGTH_SHORT
                ).show();

                // Validamos la categoría
            } else if (spCategorias.getSelectedItemPosition() == 0) {

                Toast.makeText(
                        TareaActivity.this,
                        "Seleccione una categoría",
                        Toast.LENGTH_SHORT
                ).show();

                // Validamos la prioridad
            } else if (prioridadSeleccionada == -1) {

                Toast.makeText(
                        TareaActivity.this,
                        "Seleccione una prioridad",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                // Obtenemos el RadioButton seleccionado
                RadioButton radioSeleccionado =
                        findViewById(prioridadSeleccionada);

                // Obtenemos el texto de la prioridad
                String prioridad =
                        radioSeleccionado.getText().toString();

                // Comprobamos si es importante
                boolean importante =
                        cbImportante.isChecked();


                // Guardamos la tarea
                guardarTarea(
                        nombre,
                        categoria,
                        prioridad,
                        importante
                );


                // Mostramos la ventana para decidir qué hacer
                new AlertDialog.Builder(TareaActivity.this)
                        .setTitle("Tarea creada")
                        .setMessage("¿Desea crear otra tarea?")
                        .setPositiveButton(
                                "Sí, maestro",
                                (dialog, which) -> {

                                    // Limpiamos el formulario
                                    etNombre.setText("");

                                    spCategorias.setSelection(0);

                                    rgPrioridad.clearCheck();

                                    cbImportante.setChecked(false);
                                })
                        .setNegativeButton(
                                "No, gracias",
                                (dialog, which) -> {

                                    // Volvemos al menú
                                    finish();
                                })
                        .show();
            }
        });
    }


    // Método para guardar una tarea
    private void guardarTarea(
            String nombre,
            String categoria,
            String prioridad,
            boolean importante) {

        try {

            // Abrimos las preferencias
            SharedPreferences preferencias =
                    getSharedPreferences(
                            "tareas",
                            MODE_PRIVATE
                    );

            // Recuperamos la lista de tareas guardada
            String tareasGuardadas =
                    preferencias.getString("listaTareas", "[]");

            // Convertimos el texto a JSONArray
            JSONArray listaTareas =
                    new JSONArray(tareasGuardadas);


            // Creamos una nueva tarea
            JSONObject tarea = new JSONObject();

            tarea.put("nombre", nombre);
            tarea.put("categoria", categoria);
            tarea.put("prioridad", prioridad);
            tarea.put("importante", importante);

            // Toda tarea nueva comienza en 0%
            tarea.put("progreso", 0);


            // Agregamos la tarea a la lista
            listaTareas.put(tarea);


            // Guardamos nuevamente la lista completa
            preferencias.edit()
                    .putString(
                            "listaTareas",
                            listaTareas.toString()
                    )
                    .apply();


            Toast.makeText(
                    TareaActivity.this,
                    "Tarea guardada correctamente",
                    Toast.LENGTH_SHORT
            ).show();


        } catch (JSONException e) {

            Toast.makeText(
                    TareaActivity.this,
                    "Error al guardar la tarea",
                    Toast.LENGTH_SHORT
            ).show();
            }


    }
}