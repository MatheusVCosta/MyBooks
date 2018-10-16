package br.com.senaijandira.mybooks.OneFragment;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.adapter.LivroAdapter;
import br.com.senaijandira.mybooks.adapter.LivroLidosAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;


public class TwoFragment extends Fragment {
    ListView lstViewLivros;
    private MyBooksDataBase myBooksDB;
    LivroLidosAdapter adapter;

    public static LivrosLidos[] Livros;
    public TwoFragment() {

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



        View v =  inflater.inflate(R.layout.fragment_two, container, false);
        lstViewLivros = v.findViewById(R.id.lstViewLivros);

        adapter = new LivroLidosAdapter( getContext(),myBooksDB);


        lstViewLivros.setAdapter(adapter);

        return v;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser){

            Livros = myBooksDB.daoLivroLidos().selecionarTodosLivros();
            adapter.clear();
            adapter.addAll(Livros);

        }
    }


}
