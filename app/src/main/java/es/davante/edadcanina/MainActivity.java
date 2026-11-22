package es.davante.edadcanina;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.controlador.EdadCaninaControlador;
import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

public class MainActivity extends AppCompatActivity {

    private static final String CLAVE_TEXTO = "edad.texto";
    private static final String CLAVE_CALCULADO = "edad.calculado";

    private final EdadCaninaModelo modelo = new EdadCaninaModelo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            modelo.setEdadTexto(savedInstanceState.getString(CLAVE_TEXTO, ""));
            if (savedInstanceState.getBoolean(CLAVE_CALCULADO)) modelo.calcular();
        }
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModelo(modelo);
        binding.setControlador(new EdadCaninaControlador(modelo));
        // Aplicamos el estado antes de que Android restaure el foco y el cursor.
        binding.executePendingBindings();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle estado) {
        super.onSaveInstanceState(estado);
        // El texto y la última acción bastan para reconstruir aciertos y errores.
        estado.putString(CLAVE_TEXTO, modelo.getEdadTexto());
        estado.putBoolean(CLAVE_CALCULADO, modelo.getValidacion() != Validacion.SIN_CALCULAR);
    }
}
