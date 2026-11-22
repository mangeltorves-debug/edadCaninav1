package es.davante.edadcanina.modelo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import es.davante.edadcanina.BR;

public class EdadCaninaModelo extends BaseObservable {
    private static final int FACTOR = 7;
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 30;

    private String edadTexto = "";
    private int edadHumana;
    private Validacion validacion = Validacion.SIN_CALCULAR;

    @Bindable
    public String getEdadTexto() { return edadTexto; }
    @Bindable
    public int getEdadHumana() { return edadHumana; }
    @Bindable
    public Validacion getValidacion() { return validacion; }

    public void setEdadTexto(String texto) {
        String nuevo = texto == null ? "" : texto;
        if (edadTexto.equals(nuevo)) return;
        edadTexto = nuevo;
        notifyPropertyChanged(BR.edadTexto);
        // Una edición deja sin vigencia tanto el resultado como el error anterior.
        setEdadHumana(0);
        setValidacion(Validacion.SIN_CALCULAR);
    }

    private void setEdadHumana(int nueva) {
        if (edadHumana == nueva) return;
        edadHumana = nueva;
        notifyPropertyChanged(BR.edadHumana);
    }

    private Validacion setValidacion(Validacion nueva) {
        if (validacion == nueva) return validacion;
        validacion = nueva;
        notifyPropertyChanged(BR.validacion);
        return validacion;
    }

    public Validacion calcular() {
        String entrada = edadTexto.trim();
        if (entrada.isEmpty()) return setValidacion(Validacion.VACIO);
        int edad;
        try {
            // La conversión decimal permite escribir 05 y +5 para indicar cinco años.
            edad = Integer.parseInt(entrada);
        } catch (NumberFormatException e) {
            return setValidacion(Validacion.NO_NUMERICO);
        }
        if (edad < EDAD_MINIMA || edad > EDAD_MAXIMA) {
            return setValidacion(Validacion.FUERA_DE_RANGO);
        }
        setEdadHumana(edad * FACTOR);
        return setValidacion(Validacion.OK);
    }

    public void limpiar() {
        setEdadTexto("");
        // También se puede limpiar el error de un campo que ya estaba vacío.
        setValidacion(Validacion.SIN_CALCULAR);
    }
}
