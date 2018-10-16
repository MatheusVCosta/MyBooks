package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import br.com.senaijandira.mybooks.db.MyBooksDataBase;
import br.com.senaijandira.mybooks.model.Livro;

public class CadastroActivity extends AppCompatActivity {
    //ORM - sistema de craição de banco de dados através de uma class
    //Serve para colocar no banco
    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;
    byte[] capa;

    //variavel final é um variavel que não muda o valor
    private final int COD_REQ_GALERIA = 101;

    private MyBooksDataBase myBooksBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instavia da variavel
        myBooksBD = Room.databaseBuilder(getApplicationContext(), MyBooksDataBase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        setContentView(R.layout.activity_cadastro);
        imgLivroCapa = findViewById(R.id.imgLivroCapa);

        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);
    }

    public void abrirGaleria(View view) {
        //intent no modo inplicito, basicamente o sistema operacional que decide o que abrir
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //selecionar pelo o nome imagem
        intent.setType("image/*");
        //é usado esse start para pegar o que vai voltar, vai me da uma resultado
        startActivityForResult(Intent.createChooser(intent,"Selecione uma imagem"), COD_REQ_GALERIA);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Verificar o que vai vim do forResult e verificar o que o usuario fez
        if(requestCode == COD_REQ_GALERIA && resultCode == Activity.RESULT_OK){

            try{
                InputStream input = getContentResolver().openInputStream(data.getData());
                //Converteu para bitmap
                livroCapa = BitmapFactory.decodeStream(input);
                //Exibindo na tela a imagem depois de convertida
                imgLivroCapa.setImageBitmap(livroCapa);
            }catch (Exception e){
                e.printStackTrace();

            }
        }


    }



    public void salvaLivro(View view) {
        if(txtDescricao.getText().toString() == "" || txtTitulo.getText().toString() == "" || livroCapa == null){
            alert("Erro", "Preencha todos os campos e adicione a imagem");
        }
        else{
            alert("Salva", "Salvo com sucesso");

            byte[] capa = Utils.toByteArray(livroCapa);
            String titulo = txtTitulo.getText().toString();
            String descricao = txtDescricao.getText().toString();


            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            livroCapa.compress(Bitmap.CompressFormat.JPEG,50,stream);
            capa = stream.toByteArray();

            Livro livro = new Livro(capa, titulo, descricao);

            //inserir no banco de dados
            myBooksBD.daoLivro().inserir(livro);

        }

    }
    public void alert(String titulo, String mensagem){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.create().show();

    }
}
