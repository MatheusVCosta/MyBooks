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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.LivrosLidos;


public class LivroLidosAdapter extends ArrayAdapter<LivrosLidos>{

    private MyBooksDataBase myBooksBD;
    ImageView imgDeleImageLivro;
    public LivroLidosAdapter(Context ctx, MyBooksDataBase myBooksBD){
        super(ctx, 0, new ArrayList<LivrosLidos>());

        this.myBooksBD = myBooksBD;
    }
    public void deletarLivro(final LivrosLidos Livro){
        //remover o livro da lista
        remove(Livro);
        //fazer um alert para ver se o usuario quer mesmo apagar
        myBooksBD.daoLivroLidos().deletar(Livro);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.lidos_layout,parent,false);
        }

        final LivrosLidos Livro = getItem(position);
        imgDeleImageLivro = v.findViewById(R.id.imgDeleteLivro);

        imgDeleImageLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext()) ;
                alert.setTitle("Aviso!");
                alert.setMessage("Deseja remover esse livro dos livros lidos? \nEle será removido da sua lista");

                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert("Livro removido", "O livro foi removido");
                        deletarLivro(Livro);
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
        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtTituloLivro);//só vai procurar um elemento dentro da variavel v
        TextView txtLivroDescriacao = v.findViewById(R.id.txtLivroDescricao);

        //Setando a imagem
        imgLivroCapa.setImageBitmap(Utils.toBitmap(Livro.getCapa()));
        //setando o titulo
        txtLivroTitulo.setText(Livro.getTitulo());
        //setando a descricao
        txtLivroDescriacao.setText(Livro.getDescricao());
        return v;
    }
    public void alert(String titulo, String mensagem){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.create().show();
    }

}
