package munoz.alejandro.encuentrahipotenochas;

import android.graphics.drawable.Drawable;

public class Personaje {
    private String nombre;
    private Drawable imagen;

    public Personaje(String nombre, Drawable imagen) {
        setNombre(nombre);
        setImagen(imagen);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }
}

