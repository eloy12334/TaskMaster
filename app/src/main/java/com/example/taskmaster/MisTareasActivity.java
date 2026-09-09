package com.example.taskmaster;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MisTareasActivity extends AppCompatActivity {

    // Declaramos el TableLayout
    TableLayout tblTareas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mis_tareas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Java con el XML
        tblTareas = findViewById(R.id.tblTareas);

        // Cargamos las tareas guardadas
        cargarTareas();
    }

    // Método para cargar las tareas
    private void cargarTareas() {

        try {
            // Abrimos el almacenamiento donde guardamos las tareas
            SharedPreferences preferencias =
                    getSharedPreferences(
                            "tareas",
                            MODE_PRIVATE
                    );

            // Recuperamos la lista de tareas
            String tareasGuardadas =
                    preferencias.getString(
                            "listaTareas",
                            "[]"
                    );

            // Convertimos el texto a JSONArray
            JSONArray listaTareas =
                    new JSONArray(tareasGuardadas);

            // Comprobamos si existen tareas
            if (listaTareas.length() == 0) {

                TextView tvSinTareas = new TextView(this);

                tvSinTareas.setText("No hay tareas creadas");

                tblTareas.addView(tvSinTareas);
                return;
            }



            // Recorremos todas las tareas
            for (int i = 0; i < listaTareas.length();

                 i++) {
                // Obtenemos una tarea
                JSONObject tarea = listaTareas.getJSONObject(i);

                // Obtenemos sus datos
                String nombre = tarea.getString("nombre");

                String categoria = tarea.getString("categoria");

                String prioridad = tarea.getString("prioridad");

                boolean importante = tarea.getBoolean("importante");

                int progreso = tarea.getInt("progreso");

                // Creamos una fila
                TableRow fila = new TableRow(this);

                // Creamos un TextView para mostrar la información
                TextView tvTarea = new TextView(this);

                tvTarea.setText("Tarea: " + nombre + "\nCategoría: " + categoria
                        + "\nPrioridad: " + prioridad + "\nImportante: "
                        + (importante? "Sí" : "No")
                );

                // Creamos el ProgressBar
                ProgressBar pbProgreso = new ProgressBar(this,null, android.R.attr.progressBarStyleHorizontal);

                // Indicamos el progreso actual
                pbProgreso.setProgress(progreso);

                // El máximo es 100%
                pbProgreso.setMax(100);

                // Creamos el TextView para mostrar el porcentaje
                TextView tvProgreso = new TextView(this);

                tvProgreso.setText("Progreso: " + progreso + "%");

                // Creamos el botón
                Button btnProgreso = new Button(this);

                // Guardamos el índice de la tarea
                int indiceTarea = i;

                // Comprobamos si ya está completada
                if (progreso == 100) {

                    btnProgreso.setText("Completada");

                    btnProgreso.setEnabled(false);

                } else {

                    btnProgreso.setText("Avanzar 10%");
                }

                // Evento del botón
                btnProgreso.setOnClickListener((View view) -> {

                            // Obtenemos el progreso actual
                            int nuevoProgreso = pbProgreso.getProgress() + 10;

                            // Evitamos superar el 100%
                            if (nuevoProgreso > 100) {

                                nuevoProgreso = 100;
                            }

                            // Actualizamos el ProgressBar
                            pbProgreso.setProgress(nuevoProgreso);

                            // Actualizamos el porcentaje
                            tvProgreso.setText("Progreso: " + nuevoProgreso + "%");

                            // Si llegó al 100%
                            if (nuevoProgreso == 100) {

                                btnProgreso.setText("Completada");

                                btnProgreso.setEnabled(false);
                            }

                            // Guardamos el progreso
                            actualizarProgreso(indiceTarea, nuevoProgreso);
                        }
                );

                // Agregamos los componentes a la fila
                fila.addView(tvTarea);

                fila.addView(tvProgreso);

                fila.addView(pbProgreso);

                fila.addView(btnProgreso);

                // Agregamos la fila
                // al TableLayout
                tblTareas.addView(fila);
            }

        } catch (JSONException e) {

            // Mostramos un mensaje de error
            Toast.makeText(MisTareasActivity.this,"Error al cargar las tareas",Toast.LENGTH_SHORT
            ).show();
        }
    }

    // Método para actualizar el progreso de una tarea
    private void actualizarProgreso(int indice, int nuevoProgreso) {

        try {
            // Abrimos el almacenamiento
            SharedPreferences preferencias = getSharedPreferences("tareas", MODE_PRIVATE);

            // Recuperamos las tareas
            String tareasGuardadas = preferencias.getString("listaTareas", "[]");

            // Convertimos a JSONArray
            JSONArray listaTareas = new JSONArray(tareasGuardadas);

            // Obtenemos la tarea correspondiente
            JSONObject tarea = listaTareas.getJSONObject(indice);

            // Actualizamos el progreso
            tarea.put("progreso", nuevoProgreso);

            // Guardamos nuevamente las tareas
            preferencias.edit().putString("listaTareas", listaTareas.toString()).apply();

        } catch (JSONException e) {

            // Mostramos un mensaje de error
            Toast.makeText(MisTareasActivity.this, "Error al actualizar el progreso", Toast.LENGTH_SHORT).show();
        }
    }
}