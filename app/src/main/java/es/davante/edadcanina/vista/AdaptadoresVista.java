package es.davante.edadcanina.vista;

import android.widget.EditText;
import androidx.databinding.BindingAdapter;
import es.davante.edadcanina.R;
import es.davante.edadcanina.modelo.Validacion;

public final class AdaptadoresVista {
    private AdaptadoresVista() {}

    // El contexto del campo elige el idioma del mensaje que corresponde al estado.
    @BindingAdapter("errorValidacion")
    public static void mostrarError(EditText campo, Validacion estado) {
        if (estado == null) {
            campo.setError(null);
            return;
        }
        int mensaje;
        switch (estado) {
            case VACIO: mensaje = R.string.error_vacio; break;
            case NO_NUMERICO: mensaje = R.string.error_no_numerico; break;
            case FUERA_DE_RANGO: mensaje = R.string.error_rango; break;
            default:
                campo.setError(null);
                return;
        }
        campo.setError(campo.getContext().getString(mensaje));
        campo.requestFocus();
    }
}
