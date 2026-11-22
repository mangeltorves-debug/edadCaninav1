package es.davante.edadcanina;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import es.davante.edadcanina.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Constantes para evitar números mágicos
    private static final int MULTIPLICADOR_EDAD = 7;
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 30;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btnCalc.setOnClickListener(v -> calcularEdadCanina());
    }

    private void calcularEdadCanina() {
        String textoInput = binding.ageInput.getText().toString().trim();

        // 1. Validar campo vacío
        if (textoInput.isEmpty()) {
            binding.ageInput.setError(getString(R.string.error_vacio));
            binding.ageInput.requestFocus();
            return;
        }

        int edadPerro;

        // 2. Control de errores con try/catch para evitar NumberFormatException
        try {
            edadPerro = Integer.parseInt(textoInput);
        } catch (NumberFormatException e) {
            binding.ageInput.setError(getString(R.string.error_no_numerico));
            binding.ageInput.requestFocus();
            return;
        }

        // 3. Validar rango de valores (ej: entre 1 y 30 años)
        if (edadPerro < EDAD_MINIMA || edadPerro > EDAD_MAXIMA) {
            binding.ageInput.setError(getString(R.string.error_rango));
            binding.ageInput.requestFocus();
            return;
        }

        // 4. Cálculo de la edad humana
        int edadHumana = edadPerro * MULTIPLICADOR_EDAD;

        // 5. Mostrar resultado usando strings.xml con varios parámetros (%1$d y %2$d)
        String mensajeResultado = getString(R.string.resultado_formato, edadPerro, edadHumana);
        binding.resultText.setText(mensajeResultado);
    }
}