package es.davante.edadcanina.modelo;

import androidx.databinding.Observable;
import es.davante.edadcanina.BR;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EdadCaninaModeloTest {
    private final EdadCaninaModelo modelo = new EdadCaninaModelo();

    @Test
    public void vacio() {
        modelo.setEdadTexto("   ");
        assertEquals(Validacion.VACIO, modelo.calcular());
        assertEquals(0, modelo.getEdadHumana());
    }

    @Test
    public void letras() {
        modelo.setEdadTexto("abc");
        assertEquals(Validacion.NO_NUMERICO, modelo.calcular());
        assertEquals(0, modelo.getEdadHumana());
    }

    @Test
    public void cero() {
        modelo.setEdadTexto("0");
        assertEquals(Validacion.FUERA_DE_RANGO, modelo.calcular());
        assertEquals(0, modelo.getEdadHumana());
    }

    @Test
    public void treintaYUno() {
        modelo.setEdadTexto("31");
        assertEquals(Validacion.FUERA_DE_RANGO, modelo.calcular());
        assertEquals(0, modelo.getEdadHumana());
    }

    @Test
    public void seis() {
        modelo.setEdadTexto("6");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(42, modelo.getEdadHumana());
    }

    @Test
    public void minimo() {
        modelo.setEdadTexto("1");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(7, modelo.getEdadHumana());
    }

    @Test
    public void maximo() {
        modelo.setEdadTexto("30");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(210, modelo.getEdadHumana());
    }

    @Test
    public void ceroInicial() {
        modelo.setEdadTexto("05");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(35, modelo.getEdadHumana());
    }

    @Test
    public void signoPositivo() {
        modelo.setEdadTexto("+5");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(35, modelo.getEdadHumana());
    }

    @Test
    public void soloAvisaCuandoCambianLosDatos() {
        modelo.setEdadTexto("6");
        List<Integer> avisos = new ArrayList<>();
        modelo.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable origen, int propiedad) {
                avisos.add(propiedad);
            }
        });
        modelo.calcular();
        modelo.setEdadTexto("6");
        modelo.calcular();
        assertEquals(Arrays.asList(BR.edadHumana, BR.validacion), avisos);

        modelo.setEdadTexto("7");
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
        assertEquals(0, modelo.getEdadHumana());
    }
}
