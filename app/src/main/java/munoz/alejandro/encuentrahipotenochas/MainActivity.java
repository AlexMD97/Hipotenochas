package munoz.alejandro.encuentrahipotenochas;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private int nceldas; //Para saber el numero de celdas en cada dificultad
    private GridLayout tabla;
    private Celda[][] celdas;
    private int nminas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nceldas = 8;
        nminas = 10;

        iniciajuego();
    }

    private void iniciajuego() {
        inicializarTabla();
        crearCeldas();
    }

    private void crearCeldas() {
        celdas = new Celda[nceldas][nceldas];
        inicializarCeldas();
        colocarMinas();

        for (int i = 0; i < nceldas; i++) {
            for (int j = 0; j < nceldas; j++) {
                if(celdas[i][j].getValor() == -1) {
                    for(int k = i-1; k<=i+1; k++) {
                        for(int l = j-1; l<=j+1; l++) {
                            try {
                                if(celdas[k][l].getValor() != -1) {
                                    celdas[k][l].incrementar();
                                }
                            } catch(ArrayIndexOutOfBoundsException ex) {
                                // Controla que no nos salgamos del array
                            }
                        }
                    }
                }
            }
        }

        /* TODO: TAREA 2:
            Elegir las hipotenochas y cambiar el menú
         */

        /* TODO: TAREA 3:
            Crear las instrucciones
            DONE
         */


    }

    private void inicializarCeldas() {
        System.out.println(nceldas);
        // Inicialializar
        for (int i = 0; i < nceldas; i++) {
            for (int j = 0; j < nceldas; j++) {
                System.out.println(i + "," + j);
                celdas[i][j] = new Celda(this, 0, i, j);
            }
        }
    }


    private void colocarMinas() {
        int contador = 0;
        while (contador < nminas) {
            int x = (int) Math.round(Math.random() * (nceldas-1));
            int y = (int) Math.round(Math.random() * (nceldas-1));

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
                break;
            case R.id.mnu_config:

                break;
            case R.id.mnu_person:
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
