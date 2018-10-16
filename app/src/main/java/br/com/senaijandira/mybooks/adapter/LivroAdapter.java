package br.com.senaijandira.mybooks.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import br.com.senaijandira.mybooks.EditarActivity;
import br.com.senaijandira.mybooks.R;
import br.com.senaijandira.mybooks.Utils;
import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.ParaLer;


public class LivroAdapter extends ArrayAdapter<Livro>{
    //Criando um adapter do tipo livro
    //injeção de dependencia
    private MyBooksDataBase myBooksDB;
    //Criando uma nova instacia de LivrosLidos
    LivrosLidos livrosLidos = new LivrosLidos();
    ParaLer paraLer = new ParaLer();
    private final Context context;
    private int idEdit;



    public LivroAdapter(Context ctx, MyBooksDataBase myBooksDB){
        //super chama a mãe ou o pai da classe, nesse caso chama o arrayAdapter
        super(ctx, 0, new ArrayList<Livro>());

        this.myBooksDB = myBooksDB;
        this.context = getContext();

    }

    public void deletarLivro(final Livro Livro){
        //remover o livro da lista
        remove(Livro);
        myBooksDB.daoLivro().deletar(Livro);

    }

    private void enviarParaLidos(final Livro livro){
        //a viriavel do livros lidos vai receber o id que esta na tabela de livro
        livrosLidos.setIdLivros(livro.getId());
        //disparando uma ação para o banco de dados que ira inserir o id na tabela
        myBooksDB.daoLivroLidos().inserir(livrosLidos);

    }
    private void enviarParaLer(final Livro livro){
        //a viriavel do livros lidos vai receber o id que esta na tabela de livro
        paraLer.setIdLivros(livro.getId());
        //disparando uma ação para o banco de dados que ira inserir o id na tabela
        myBooksDB.daoParaLer().inserir(paraLer);
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.livro_layout,parent,false);
        }
        final Livro livro = getItem(position);
        //img lixeira
        ImageView imgDeleImageLivro = v.findViewById(R.id.imgDeleteLivro);
        Button btnLer = v.findViewById(R.id.livrosLidos);
        Button btnParaLer = v.findViewById(R.id.ParaLer);
        Button btnEditar = v.findViewById(R.id.Editar);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EditarActivity.class);
               // Intent.makeRestartActivityTask(getContext(),EditarActivity.class);
                idEdit = livro.getId();
                intent.putExtra("id", idEdit);
                context.startActivity(intent);

            }
        });
        imgDeleImageLivro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext()) ;
                int id = livro.getId();
                int idLivrosLidos = myBooksDB.daoLivroLidos().verificarLivroExistente(id);
                int idLivrosParaLer  = myBooksDB.daoParaLer().verificarLivroExistente(id);

                if(idLivrosLidos > 0){
                    alert.setTitle("Aviso!");
                    alert.setMessage("Esse Livro está na biblioteca de livros Lidos, deseja apaga-lo?");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert("Livro apagado", "O livro foi apagado e removido da biblioteca de Livros Lidos");
                            deletarLivro(livro);
                        }
                    });
                    alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert("Livro não apagado", "O livro não foi apagado");
                        }
                    });
                }
                else if(idLivrosParaLer > 0){
                    alert.setTitle("Aviso!");
                    alert.setMessage("Esse Livro está na biblioteca de Para Ler, deseja apaga-lo?");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert("Livro apagado", "O livro foi apagado e removido da biblioteca de Para Ler");
                            deletarLivro(livro);
                        }
                    });
                    alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert("Livro não apagado", "O livro não foi apagado");
                        }
                    });
                }
                else{
                    alert.setTitle("Aviso!");
                    alert.setMessage("Deseja apagar mesmo esse livro? \n Ele será removido da sua lista");

                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert("Livro apagado", "O livro foi apagado");
                            deletarLivro(livro);
                        }
                    });
                    alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert("Livro não apagado", "O livro não foi apagado");
                        }
                    });
                }
                alert.create().show();
            }
        });
        btnLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setCancelable(false);

                //Qunado clicado no botão de add em livrosLidos é verificado se o livro ja existe
                //pegando a variavel do livro clicado na tela de livros cadastrado
               final int id = livro.getId();

                //numLinhas vai pegar receber um select que ta passando o id do livro clicado
                //esse select vai trazer o numero de linhas que possui o mesmo id
                int numLinhas = myBooksDB.daoLivroLidos().selecionarId(id);
                int idParaLer = myBooksDB.daoParaLer().verificarLivroExistente(id);

                final String titulo = livro.getTitulo();
                //se o numLinhas for maior que 0 significa que o livro ja existe se não o livro é adicionado
                if (idParaLer > 0) {

                    alert.setTitle("Livro encontrado em outra biblioteca");
                    alert.setMessage("Esse livro já está na biblioteca de Livros Para Ler \nDeseja remove-lo?");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            enviarParaLidos(livro);
                            myBooksDB.daoParaLer().deletarParaLer(id);
                            alert("Livro removido", "O livro "+ titulo +" foi removido dos livros Para Ler");
                        }
                    });
                    alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert("Livro não enviado", "O livro "+ titulo +" não foi adicionado na biblioteca Livros Lidos");
                        }
                    });


                }
                else{
                    if(numLinhas > 0){
                        alert.setTitle("Aviso");
                        alert.setMessage("O livro "+titulo+" já está na biblioteca de livros lidos");
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        });
                    } else {
                        alert.setTitle("Aviso");
                        alert.setMessage("Você tem certeza que deseja adicionar "+ titulo +" em livros Lidos");

                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alert("Livro adicionado", titulo + " adicionado em Livros Lidos");
                                //passando o livro que será adicionado no fragment de livrosLidos
                                enviarParaLidos(livro);
                            }
                        });
                        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alert("Livro não adicionado", "Livro não adicionado em Livros Lidos");
                            }
                        });
                    }

                }
                alert.create().show();
            }
        });
        btnParaLer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setCancelable(false);
                final String titulo = livro.getTitulo();

                //Qunado clicado no botão de add em livrosLidos é verificado se o livro ja existe
                //pegando a variavel do livro clicado na tela de livros cadastrado
                final int id = livro.getId();
                //numLinhas vai pegar receber um select que ta passando o id do livro clicado
                //esse select vai trazer o numero de linhas que possui o mesmo id
                int numLinhas = myBooksDB.daoParaLer().selecionarId(id);
                int idLivrosLidos = myBooksDB.daoLivroLidos().verificarLivroExistente(id);
                //se o numLinhas for maior que 0 significa que o livro ja existe se não o livro é adicionado
                if(idLivrosLidos > 0){
                    alert.setTitle("Livro encontrado em outra biblioteca");
                    alert.setMessage("Esse livro já está na biblioteca de Livros Lidos\nDeseja remove-lo?");
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            enviarParaLer(livro);
                            myBooksDB.daoLivroLidos().deletarLidos(id);
                            alert("Livro removido", "O livro "+ titulo +" foi removido dos livros Lidos");
                        }
                    });
                    alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alert("Livro não enviado", "O livro "+ titulo +" não foi adicionado na biblioteca Livros Para Ler");
                        }
                    });
                }
                else {
                    if (numLinhas > 0) {

                        alert.setMessage("O livro " + titulo + " já foi adicionado na biblioteca de Para ler");
                        alert.setTitle("Aviso");

                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                    } else {
                        alert.setTitle("Aviso");
                        alert.setMessage("Você tem certeza que deseja adicionar " + titulo + " em livros Para ler");

                        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alert("Livro adicionado", titulo + " adicionado em Para Ler");
                                //passando o livro que será adicionado no fragment de livrosLidos
                                enviarParaLer(livro);
                            }

                        });
                        alert.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alert("Livro não adicionado", "Livro não adicionado em Para Ler");
                            }
                        });

                    }
                }

                alert.create().show();
            }
        });

        ImageView imgLivroCapa = v.findViewById(R.id.imgLivroCapa);
        TextView txtLivroTitulo = v.findViewById(R.id.txtTituloLivro);//só vai procurar um elemento dentro da variavel v
        TextView txtLivroDescriacao = v.findViewById(R.id.txtLivroDescricao);

        //Setando a imagem
        imgLivroCapa.setImageBitmap(Utils.toBitmap(livro.getCapa()));
        //setando o titulo
        txtLivroTitulo.setText(livro.getTitulo());
        //setando a descricao
        txtLivroDescriacao.setText(livro.getDescricao());



        return v;
    }

    public void alert(String titulo, String mensagem){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.create().show();
    }


}
