package es.davante.edadcanina;

import android.os.Bundle;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;
import androidx.databinding.DataBindingUtil;
import es.davante.edadcanina.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final EdadCaninaModelo modelo = new EdadCaninaModelo();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModelo(modelo);
        binding.btnCalc.setOnClickListener(v -> mostrarError(modelo.calcular()));
    }

    private void mostrarError(Validacion estado) {
        int mensaje;
        switch (estado) {
            case VACIO: mensaje = R.string.error_vacio; break;
            case NO_NUMERICO: mensaje = R.string.error_no_numerico; break;
            case FUERA_DE_RANGO: mensaje = R.string.error_rango; break;
            default:
                binding.ageInput.setError(null);
                return;
        }
        binding.ageInput.setError(getString(mensaje));
        binding.ageInput.requestFocus();
    }
}
