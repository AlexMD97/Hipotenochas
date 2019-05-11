package munoz.alejandro.encuentrahipotenochas;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    private int nceldas; //Para saber el numero de celdas en cada dificultad
    private GridLayout tabla;
    private Celda[][] celdas;
    private int nminas;
    private int descubiertas;
    private Drawable hipotenocha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nceldas = 8;
        nminas = 10;
        hipotenocha = this.getDrawable(R.mipmap.personaje1);

    }

    private void iniciajuego() {
        descubiertas = 0;
        crearCeldas();
        inicializarTabla();
    }

    private void crearCeldas() {
        celdas = new Celda[nceldas][nceldas];
        inicializarCeldas();
        colocarMinas();
        recalcularValores();

        /* TODO: TAREA 2:
            Elegir las hipotenochas y cambiar el menú
         */

        /* TODO: TAREA 3:
            Crear las instrucciones
            DONE
         */


    }

    private void recalcularValores() {
        for (int i = 0; i < nceldas; i++) {
            for (int j = 0; j < nceldas; j++) {
                if (celdas[i][j].getValor() == -1) {
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            try {
                                if (celdas[k][l].getValor() != -1) {
                                    celdas[k][l].incrementar();
                                }
                            } catch (ArrayIndexOutOfBoundsException ex) {
                                // Controla que no nos salgamos del array
                            }
                        }
                    }
                }
            }
        }
    }

    private void inicializarCeldas() {
        // Inicialializar
        for (int i = 0; i < nceldas; i++) {
            for (int j = 0; j < nceldas; j++) {
                celdas[i][j] = new Celda(this, 0, i, j);
                celdas[i][j].setOnClickListener(this);
                celdas[i][j].setOnLongClickListener(this);
            }
        }
    }

    private void colocarMinas() {
        int contador = 0;
        while (contador < nminas) {
            int x = (int) Math.round(Math.random() * (nceldas - 1));
            int y = (int) Math.round(Math.random() * (nceldas - 1));

            if (celdas[x][y].getValor() != -1) {
                celdas[x][y].setValor(-1);
                contador++;
            }
        }
    }

    private void inicializarTabla() {
        ConstraintLayout principal = findViewById(R.id.layoutPrincipal);
        int alto = principal.getHeight();
        int ancho = principal.getWidth();

        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.setMargins(0, 0, 0, 0);
        params.width = ancho;
        params.height = alto;

        tabla = new GridLayout(this);
        tabla.setColumnCount(nceldas);
        tabla.setRowCount(nceldas);

        tabla.setLayoutParams(params);

        principal.removeAllViews();
        principal.addView(tabla);

        int bancho = ancho / nceldas;
        int balto = alto / nceldas;

        for (int i = 0; i < nceldas; i++) {
            for (int j = 0; j < nceldas; j++) {
                tabla.addView(celdas[i][j], bancho, balto);
                //System.out.printf("%d ", celdas[i][j].getValor());
            }
            //System.out.println('\n');
        }
    }

    private void clickEnCelda(Celda celda) {
        if(!celda.isDescubierto()) {
            switch (celda.getValor()) {
                case -1:
                    celda.setBackground(hipotenocha);
                    derrota(celda, "Pisaste una mina. Has perdido!!");
                    break;
                case 0:
                    celda.setBackgroundColor(Color.BLUE);
                    descubrirRecursivo(celda);
                    break;
                default:
                    celda.setDescubierto(true);
                    celda.setText(String.valueOf(celda.getValor()));
            }
        }
    }

    private void derrota(Celda celda, String mensaje) {
        desactivarTodas();
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show();
    }

    private void descubrirRecursivo(Celda celda) {
        celda.setDescubierto(true);
        for (int i = celda.getPosicionx() - 1; i <= celda.getPosicionx() + 1; i++) {
            for (int j = celda.getPosiciony() - 1; j <= celda.getPosiciony() + 1; j++) {
                try {
                    if(!celdas[i][j].isDescubierto()) {
                        if(celdas[i][j].getValor() == 0) {
                            celdas[i][j].setBackgroundColor(Color.BLUE);
                            descubrirRecursivo(celdas[i][j]);
                        } else {
                            celdas[i][j].setText(String.valueOf(celdas[i][j].getValor()));
                            celdas[i][j].setDescubierto(true);
                        }
                    }
                } catch(ArrayIndexOutOfBoundsException ex) {

                }
            }
        }
    }

    private void desactivarTodas() {
        for (int i = 0; i < nceldas; i++) {
            for (int j = 0; j < nceldas; j++) {
                celdas[i][j].setEnabled(false);
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mnuppal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_inst:
                DialogInstrucciones ins = new DialogInstrucciones(this,
                        getString(R.string.titulo_ins),
                        getString(R.string.contenido_ins),
                        getString(R.string.btn_ins));
                ins.create();
                ins.show();
                break;
            case R.id.mnu_nuevo:
                iniciajuego();
                break;
            case R.id.mnu_config:

                break;
            case R.id.mnu_person:
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Celda) {
            clickEnCelda((Celda) v);
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (v instanceof Celda) {
            Celda pulsada = (Celda) v;

            if(pulsada.getValor() == -1) {
                descubiertas++;
                pulsada.setBackground(hipotenocha);
                pulsada.setDescubierto(true);
                Toast.makeText(this, String.format("Mina descubierta!!. Te quedan %d.", nminas - descubiertas), Toast.LENGTH_LONG).show();
            } else {
                pulsada.setText(String.valueOf(pulsada.getValor()));
                derrota(pulsada, "Ahí no había ninguna mina. Has perdido!!");
            }
        }
        return false;
    }
}
