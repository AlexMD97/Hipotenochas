package munoz.alejandro.encuentrahipotenochas;

import android.app.AlertDialog;
import android.content.Context;

public class DialogInstrucciones extends AlertDialog.Builder {
    public DialogInstrucciones(Context context, String titulo, String contenido, String btn) {
        super(context);
        setTitle(titulo);
        setMessage(contenido);
        setPositiveButton(btn, null);
    }
}
