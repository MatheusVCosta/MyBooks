package br.com.senaijandira.mybooks.adapter;

import android.content.Context;
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
import br.com.senaijandira.mybooks.model.livro;

public class LivroAdapter extends ArrayAdapter<livro>{
    //Criando um adapter do tipo livro
    //injeção de dependencia

    private MyBooksDataBase myBooksDB;

    public LivroAdapter(Context ctx, MyBooksDataBase myBooksDB){
        //super chama a mãe ou o pai da classe, nesse caso chama o arrayAdapter
        super(ctx, 0, new ArrayList<livro>());

        this.myBooksDB = myBooksDB;

    }

    private void deletarLivro(final livro Livro){

        //remover o livro da lista

        remove(Livro);

        //fazer um alert para ver se o usuario quer mesmo apagar
        myBooksDB.daoLivro().deletar(Livro);

        //lstViewLivros.removeView(v);


    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout,parent,false);
        }
        final livro Livro = getItem(position);


        //img lixeira
        ImageView imgDeleImageLivro = v.findViewById(R.id.imgDeleteLivro);

        //setando o click da lixeira
        final View finalV = v;
        imgDeleImageLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletarLivro(Livro);
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
}
