package munoz.alejandro.encuentrahipotenochas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;

@SuppressLint("AppCompatCustomView")

public class Celda extends Button {
    private int valor;
    private boolean descubierto;
    private int posicionx, posiciony;

    public Celda(Context context, int valor, int x, int y) {
        super(context);
        setValor(valor);
        setPosicionx(x);
        setPosiciony(y);
    }

    public void incrementar() {
        this.valor++;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isDescubierto() {
        return descubierto;
    }

    public void setDescubierto(boolean descubierto) {
        this.descubierto = descubierto;
        this.setClickable(false);
    }

    public int getPosicionx() {
        return posicionx;
    }

    public void setPosicionx(int posicionx) {
        this.posicionx = posicionx;
    }

    public int getPosiciony() {
        return posiciony;
    }

    public void setPosiciony(int posiciony) {
        this.posiciony = posiciony;
    }
}
