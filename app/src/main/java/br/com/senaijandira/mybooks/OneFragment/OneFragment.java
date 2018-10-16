package br.com.senaijandira.mybooks.OneFragment;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.adapter.LivroAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.Livro;

public class OneFragment extends Fragment {
    ListView lstViewLivros;
    private MyBooksDataBase myBooksDB;
    LivroAdapter adapter;

    public static Livro[] Livros;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    //on createViewé usada para inflar um layout ou fazer conexao com ulguma fonte de dados
    //e depois e retornando para a mainActivity para ser usada na view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Instanciando a variavel de acesso ao banco
        myBooksDB = Room.databaseBuilder(getContext(), MyBooksDataBase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();


        View v =  inflater.inflate(R.layout.fragment_one, container, false);
        lstViewLivros = v.findViewById(R.id.lstViewLivros);

        adapter = new LivroAdapter( getContext(),myBooksDB);
        //Agora quem cria a lista vai ser o adapter
        lstViewLivros.setAdapter(adapter);

        return v;
    }

    @Override
    public void onResume(){

        super.onResume();
        //Fazer o select no banco e jogar na variavel livro e mostrar na tela
        Livros = myBooksDB.daoLivro().selecionarTodos();
        //limpando a listView
        adapter.clear();
        //Adicionando os livros da lista
        adapter.addAll(Livros);

    }

}
