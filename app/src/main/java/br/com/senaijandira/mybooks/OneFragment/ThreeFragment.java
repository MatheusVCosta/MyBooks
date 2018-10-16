package br.com.senaijandira.mybooks.OneFragment;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.adapter.ParaLerAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.ParaLer;

public class ThreeFragment extends Fragment {
    ListView lstViewLivros;
    private MyBooksDataBase myBooksDB;
    ParaLerAdapter adapter;
    TextView txtTexto;

    public static ParaLer[] paraLer;
    public ThreeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Instanciando a variavel de acesso ao banco
        myBooksDB = Room.databaseBuilder(getContext(), MyBooksDataBase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        View v =  inflater.inflate(R.layout.fragment_three, container, false);

        lstViewLivros = v.findViewById(R.id.lstViewLivros);

        adapter = new ParaLerAdapter( getContext(),myBooksDB);

        lstViewLivros.setAdapter(adapter);

        return v;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            paraLer = myBooksDB.daoParaLer().selecionarTodosLivros();
            adapter.clear();
            adapter.addAll(paraLer);

        }
    }
}
