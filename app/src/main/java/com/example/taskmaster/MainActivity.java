package com.example.taskmaster;

// Importamos Intent para poder cambiar de Activity
import android.content.Intent;

// Importamos Bundle, necesario para el método onCreate
import android.os.Bundle;

// Importamos View para trabajar con los eventos de los botones
import android.view.View;

// Importamos los componentes que utilizaremos
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

// Importamos las clases necesarias de Android
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    // Declaramos las variables que representan los componentes del XML
    TextView tvTitulo;
    EditText etCorreo;
    EditText etPassword;
    TextView tvCorreo;
    TextView tvPassword;
    TextView tvRegistro;
    Button btnIngresar;
    Button btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Relacionamos las variables Java con los componentes del XML
        tvTitulo = findViewById(R.id.tvTitulo);
        tvCorreo = findViewById(R.id.tvCorreo);
        etCorreo = findViewById(R.id.etCorreo);
        tvPassword = findViewById(R.id.tvPassword);
        etPassword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        tvRegistro = findViewById(R.id.tvRegistro);
        btnRegistro = findViewById(R.id.btnRegistro);

        // Evento del botón Ingresar
        btnIngresar.setOnClickListener((View view) -> {

            // Obtenemos el texto del correo
            String correo = etCorreo.getText().toString();

            // Obtenemos el texto de la contraseña
            String password = etPassword.getText().toString();

            // Validamos los datos
            if (correo.equals("admin@gmail.com") && password.equals("123456")) {

                // Si los datos son correctos, vamos al menú principal
                Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                startActivity(intent);

            } else {

                // Si los datos son incorrectos, mostramos un mensaje
                Toast.makeText(
                        MainActivity.this,
                        "Correo o contraseña incorrectos",
                        Toast.LENGTH_SHORT
                ).show();
            }
        });

        // Evento del botón Registro
        btnRegistro.setOnClickListener((View view) -> {

            // Vamos a la pantalla de crear cuenta
            Intent intent = new Intent(MainActivity.this, RegistroActivity.class);
            startActivity(intent);

        });
    }
}