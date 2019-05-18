package munoz.alejandro.encuentrahipotenochas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PersonajeAdapter extends BaseAdapter {
    private ArrayList<Personaje> personajes;
    private Context contexto;

    public PersonajeAdapter(ArrayList<Personaje> personajes, Context contexto) {
        this.personajes = personajes;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return personajes.size();
    }

    @Override
    public Object getItem(int i) {
        return personajes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=view;
        if(v==null){
            LayoutInflater inflater= (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.datos_spinner, null);
        }

        TextView txt=v.findViewById(R.id.txtNombrep);
        ImageView iv=v.findViewById(R.id.ivpersonaje);

        txt.setText(personajes.get(i).getNombre());
        iv.setImageDrawable(personajes.get(i).getImagen());


        return v;
    }
}
