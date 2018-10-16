package br.com.senaijandira.mybooks.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.ParaLer;

public class ParaLerAdapter extends ArrayAdapter<ParaLer> {

    private MyBooksDataBase myBooksDB;

    LivrosLidos livrosLidos = new LivrosLidos();

    public ParaLerAdapter(Context ctx, MyBooksDataBase myBookDB){
        super(ctx, 0, new ArrayList<ParaLer>());
        this.myBooksDB = myBookDB;
    }
    public void deletarLivro(final ParaLer paraLer){
        remove(paraLer);
        myBooksDB.daoParaLer().deletar(paraLer);
    }

    private void enviarParaLidos(final ParaLer paraLer){
        //a viriavel do livros lidos vai receber o id que esta na tabela de livro
        livrosLidos.setIdLivros(paraLer.getIdLivros());
        //disparando uma ação para o banco de dados que ira inserir o id na tabela
        myBooksDB.daoLivroLidos().inserir(livrosLidos);
       //deletarLivro(livroLido);

    }    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.paraler_layout, parent, false);
        }
        final ParaLer paraLer = getItem(position);

        ImageView imgDeleImageLivro = v.findViewById(R.id.imgDeleteLivro);
        Button btnLer = v.findViewById(R.id.livrosLidos);

        imgDeleImageLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext()) ;
                alert.setTitle("Aviso!");
                alert.setMessage("Deseja remover esse livro dos livros Para ler? \nEle será removido da sua lista");

                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert("Livro removido", "O livro foi removido");
                        deletarLivro(paraLer);
                    }
                });
                alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert("Livro não removido", "O livro não foi removido");
                    }
                });
                alert.create().show();
            }
        });

        btnLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setCancelable(false);
                final String titulo = paraLer.getTitulo();
                final int id = paraLer.getId();

                alert.setTitle("Aviso");
                alert.setMessage("Você tem certeza que deseja adicionar " + titulo + " em livros Lidos?");

                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        enviarParaLidos(paraLer);
                        alert("Livro adicionado", titulo + " adicionado em Livros Lidos");
                        //passando o livro que será adicionado no fragment de livrosLidos
                        //myBooksDB.daoParaLer().deletarParaLer(id);
                        deletarLivro(paraLer);
                    }

                });
                alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert("Livro não adicionado", "Livro não adicionado em Livros Lidos");
                    }
                });
                alert.create().show();
            }
        });

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtTituloLivro);//só vai procurar um elemento dentro da variavel v
        TextView txtLivroDescriacao = v.findViewById(R.id.txtLivroDescricao);

        //Setando a imagem
        imgLivroCapa.setImageBitmap(Utils.toBitmap(paraLer.getCapa()));
        //setando o titulo
        txtLivroTitulo.setText(paraLer.getTitulo());
        //setando a descricao
        txtLivroDescriacao.setText(paraLer.getDescricao());

        return v;
    }

    public void alert(String titulo, String mensagem){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(titulo);
        alert.setMessage(mensagem);

        alert.create().show();

    }
}
