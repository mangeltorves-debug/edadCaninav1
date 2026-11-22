package es.davante.edadcanina.modelo;

public class EdadCaninaModelo {
    private static final int FACTOR = 7;
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 30;

    private String edadTexto = "";
    private int edadHumana;
    private Validacion validacion = Validacion.SIN_CALCULAR;

    public String getEdadTexto() { return edadTexto; }
    public int getEdadHumana() { return edadHumana; }
    public Validacion getValidacion() { return validacion; }

    public void setEdadTexto(String texto) {
        String nuevo = texto == null ? "" : texto;
        if (edadTexto.equals(nuevo)) return;
        edadTexto = nuevo;
        // Una edición deja sin vigencia tanto el resultado como el error anterior.
        setEdadHumana(0);
        setValidacion(Validacion.SIN_CALCULAR);
    }

    private void setEdadHumana(int nueva) {
        edadHumana = nueva;
    }

    private Validacion setValidacion(Validacion nueva) {
        validacion = nueva;
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
}
