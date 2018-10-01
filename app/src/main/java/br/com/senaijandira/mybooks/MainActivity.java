package br.com.senaijandira.mybooks;

import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import br.com.senaijandira.mybooks.adapter.LivroAdapter;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.livro;

public class MainActivity extends AppCompatActivity {

    //ListView que carregará os livros
    ListView lstViewLivros;

    public static livro[] Livros;

    //variavel de acesso ao banco
    private MyBooksDataBase myBooksDB;
    //Adapter para criar a lista de livros
    LivroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Instanciando a variavel de acesso ao banco
        myBooksDB = Room.databaseBuilder(getApplicationContext(), MyBooksDataBase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        lstViewLivros = findViewById(R.id.lstViewLivros);

        adapter = new LivroAdapter(this, myBooksDB);
        //Agora quem cria a lista vai ser o adapter
        lstViewLivros.setAdapter(adapter);
    }

    @Override
    protected void onResume() {

        super.onResume();

        //Fazer o select no banco e jogar na variavel livro e mostrar na tela
        Livros = myBooksDB.daoLivro().selecionarTodos();

        //limpando a listView
        adapter.clear();
        //Adicionando os livros da lista
        adapter.addAll(Livros);

    }
    public void deletarLivro(final livro livro, final View v) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Deletar");
        alert.setMessage("Tem certeza que deseja deletar");
        alert.setNegativeButton("não", null);
        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                myBooksDB.daoLivro().deletar(livro);

                //lstViewLivros.removeView(v);
            }
        });
        alert.show();
    }
//    public void criarLivro(final livro Livro, ViewGroup root){
//
//        //v ta recebendo um linearLayout que ta carregando as imagens e textViews
//        //esta carregando o linearlayout do livro_layout
//        final View v = LayoutInflater.from(this).inflate(R.layout.livro_layout,root,false);//layoutInfalte carrega um xml dentro do código
//
//        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
//        TextView txtLivroTitulo = v.findViewById(R.id.txtTituloLivro);//só vai procurar um elemento dentro da variavel v
//        TextView txtLivroDescriacao = v.findViewById(R.id.txtLivroDescricao);
//
//
//        //img lixeira
//        ImageView imgDeleImageLivro = v.findViewById(R.id.imgDeleteLivro);
//
//        //setando o click da lixeira
//        imgDeleImageLivro.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                deletarLivro(Livro, v);
//            }
//        });
//
//
//        //Setando a imagem
//        imgLivroCapa.setImageBitmap(Utils.toBitmap(Livro.getCapa()));
//        //setando o titulo
//        txtLivroTitulo.setText(Livro.getTitulo());
//        //setando a descricao
//        txtLivroDescriacao.setText(Livro.getDescricao());
//
//        //mostrar na tela
//        root.addView(v);
//
//    }
    public void abrirCadastro(View v){//quando for abrir pelo xml usar o View v
        startActivity(new Intent(this, CadastroActivity.class));
    }



}
