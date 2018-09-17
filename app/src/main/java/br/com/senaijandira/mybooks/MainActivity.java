package br.com.senaijandira.mybooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.senaijandira.mybooks.model.livro;

public class MainActivity extends AppCompatActivity {

    LinearLayout listaDeLivros;

    public static livro[] Livros;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaDeLivros = findViewById(R.id.ListaLivros);

        //Criado cadastros fake
        Livros = new livro[]{
//                new livro(1,Utils.toByteArray(getResources(),R.drawable.pequeno_principe),"O pequeno principe", getString(R.string.pequeno_principe)),
//                new livro(2,Utils.toByteArray(getResources(),R.drawable.cinquenta_tons_cinza),"Cinquenta tons de cinza", getString(R.string.cinquenta_tons)),
//                new livro(3,Utils.toByteArray(getResources(),R.drawable.kotlin_android),"O pequeno principe", getString(R.string.kotlin))

        };

    }

    @Override
    protected void onResume() {
        super.onResume();
        listaDeLivros.removeAllViews();
        for(livro l: Livros){
            criarLivro(l, listaDeLivros);

        }

    }

    public void criarLivro(livro Livro, ViewGroup root){
        //v ta recebendo um linearLayout que ta carregando as imagens e textViews
        //esta carregando o linearlayout do livro_layout
        View v = LayoutInflater.from(this).inflate(R.layout.livro_layout,root,false);//layoutInfalte carrega um xml dentro do código

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtTituloLivro);//só vai procurar um elemento dentro da variavel v
        TextView txtLivroDescriacao = v.findViewById(R.id.txtLivroDescricao);

        //Setando a imagem
        imgLivroCapa.setImageBitmap(Utils.toBitmap(Livro.getCapa()));
        //setando o titulo
        txtLivroTitulo.setText(Livro.getTitulo());
        //setando a descricao
        txtLivroDescriacao.setText(Livro.getDescricao());

        //mostrar na tela
        root.addView(v);
    }
    public void abrirCadastro(View v){//quando for abrir pelo xml usar o View v
        startActivity(new Intent(this, CadastroActivity.class));
    }


}
