package es.davante.edadcanina;

import android.os.Bundle;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
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
        binding.btnCalc.setOnClickListener(v -> modelo.calcular());
    }
}
