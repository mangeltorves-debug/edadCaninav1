package es.davante.edadcanina;

import android.os.Bundle;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.controlador.EdadCaninaControlador;
import androidx.databinding.DataBindingUtil;
import es.davante.edadcanina.databinding.ActivityMainBinding;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final EdadCaninaModelo modelo = new EdadCaninaModelo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModelo(modelo);
        binding.setControlador(new EdadCaninaControlador(modelo));
    }
}
