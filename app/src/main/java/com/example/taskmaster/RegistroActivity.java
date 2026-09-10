package com.example.taskmaster;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import android.util.Patterns;

public class RegistroActivity extends AppCompatActivity {

    TextView tvCorreo;
    EditText etCorreo;
    TextView tvPassword;
    EditText etPassword;
    Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvCorreo = findViewById(R.id.tvCorreo);
        etCorreo = findViewById(R.id.etCorreo);
        tvPassword = findViewById(R.id.tvPassword);
        etPassword = findViewById(R.id.etPassword);
        btnRegistro = findViewById(R.id.btnRegistro);

        btnRegistro.setOnClickListener((View view) -> {

            // Obtenemos el correo ingresado
            String correo = etCorreo.getText().toString();

            // Obtenemos la contraseña ingresada
            String password = etPassword.getText().toString();

            // Validamos que los campos estén completos
            // y que el correo tenga un formato válido
            if (!correo.isEmpty()
                    && !password.isEmpty()
                    && password.length() >= 6
                    && Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {


                // Abrimos SharedPreferences para guardar los datos
                SharedPreferences preferencias =
                        getSharedPreferences("usuarios", MODE_PRIVATE);

                // Editamos los datos que vamos a guardar
                SharedPreferences.Editor editor = preferencias.edit();

                // Guardamos correo y contraseña
                editor.putString("correo", correo);
                editor.putString("password", password);

                // Confirmamos el guardado
                editor.apply();

                // Mostramos mensaje
                Toast.makeText(
                        RegistroActivity.this,
                        "Cuenta creada correctamente",
                        Toast.LENGTH_SHORT
                ).show();

                // Volvemos al Login
                Intent intent = new Intent(
                        RegistroActivity.this,
                        MainActivity.class
                );

                startActivity(intent);

            } else {

                // Validar correo
                if (etCorreo.getText().toString().trim().isEmpty()) {
                    etCorreo.setError("Ingrese un correo");
                } else if (!Patterns.EMAIL_ADDRESS.matcher(
                        etCorreo.getText().toString().trim()
                ).matches()) {
                    etCorreo.setError("Ingrese un correo válido (..@..com)");
                }

                // Validar contraseña
                if (etPassword.getText().toString().trim().isEmpty()) {
                    etPassword.setError("Ingrese una contraseña minimo 6 caracteres");
                }

                Toast.makeText(
                        RegistroActivity.this,
                        "Complete los campos correctamente",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

    }
}