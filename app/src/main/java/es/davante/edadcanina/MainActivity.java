package com.example.edadcaninav1; // (Mantén tu paquete actual)

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Constantes para evitar números mágicos
    private static final int MULTIPLICADOR_EDAD = 7;
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 30;

    private EditText etEdadPerro;
    private Button btnCalcular;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazamos las vistas con el layout
        etEdadPerro = findViewById(R.id.etEdadPerro);
        btnCalcular = findViewById(R.id.btnCalcular);
        tvResultado = findViewById(R.id.tvResultado);

        btnCalcular.addListener(new View.OnClickListener() { // O btnCalcular.setOnClickListener según tu versión
            // Nota: En Java estándar es setOnClickListener
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcularEdadCanina();
            }
        });
    }

    private void calcularEdadCanina() {
        String textoInput = etEdadPerro.getText().toString().trim();

        // 1. Validar campo vacío
        if (textoInput.isEmpty()) {
            etEdadPerro.setError(getString(R.string.error_vacio));
            etEdadPerro.requestFocus();
            return;
        }

        int edadPerro;

        // 2. Control de errores con try/catch para evitar NumberFormatException
        try {
            edadPerro = Integer.parseInt(textoInput);
        } catch (NumberFormatException e) {
            etEdadPerro.setError(getString(R.string.error_no_numerico));
            etEdadPerro.requestFocus();
            return;
        }

        // 3. Validar rango de valores (ej: entre 1 y 30 años)
        if (edadPerro < EDAD_MINIMA || edadPerro > EDAD_MAXIMA) {
            etEdadPerro.setError(getString(R.string.error_rango));
            etEdadPerro.requestFocus();
            return;
        }

        // 4. Cálculo de la edad humana
        int edadHumana = edadPerro * MULTIPLICADOR_EDAD;

        // 5. Mostrar resultado usando strings.xml con varios parámetros (%1$d y %2$d)
        String mensajeResultado = getString(R.string.resultado_formato, edadPerro, edadHumana);
        tvResultado.setText(mensajeResultado);
    }
}