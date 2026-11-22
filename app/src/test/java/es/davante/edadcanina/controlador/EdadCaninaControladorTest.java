package es.davante.edadcanina.controlador;

import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class EdadCaninaControladorTest {
    @Test
    public void calcularYLimpiarDesdeResultadoYError() {
        EdadCaninaModelo modelo = new EdadCaninaModelo();
        EdadCaninaControlador controlador = new EdadCaninaControlador(modelo);
        modelo.setEdadTexto("6");
        controlador.calcular();
        assertEquals(42, modelo.getEdadHumana());
        controlador.limpiar();
        assertEquals("", modelo.getEdadTexto());
        assertEquals(0, modelo.getEdadHumana());
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
        controlador.calcular();
        assertEquals(Validacion.VACIO, modelo.getValidacion());
        controlador.limpiar();
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
    }
}
