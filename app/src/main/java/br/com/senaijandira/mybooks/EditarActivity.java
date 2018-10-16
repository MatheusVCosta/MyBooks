package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.database.Cursor;
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

public class EditarActivity extends AppCompatActivity {

    Bitmap capa;
    ImageView imgLivroCapaEdit;
    EditText txtTitulo,txtDescricao;

    byte[] livroCapa;
    int id;

    MyBooksDataBase myBooksDB;
    private Livro livro;
    private final int COD_REQ_GALERIA = 101;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        myBooksDB = Room.databaseBuilder(getApplicationContext(), MyBooksDataBase.class, Utils.DATABASE_NAME).fallbackToDestructiveMigration().allowMainThreadQueries().build();

        imgLivroCapaEdit = findViewById(R.id.imgLivroCapa);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtDescricao = findViewById(R.id.txtDescricao);

        id = getIntent().getExtras().getInt("id");
        Cursor li = myBooksDB.daoLivro().trazerLivro(id);
        li.moveToFirst();

        String titulo = li.getString(li.getColumnIndex("titulo"));
        String descricao = li.getString(li.getColumnIndex("descricao"));
        livroCapa = li.getBlob(li.getColumnIndex("capa"));
        capa = Utils.toBitmap(livroCapa);

        imgLivroCapaEdit.setImageBitmap(capa);
        txtTitulo.setText(titulo);
        txtDescricao.setText(descricao);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Verificar o que vai vim do forResult e verificar o que o usuario fez
        if(requestCode == COD_REQ_GALERIA && resultCode == Activity.RESULT_OK){

            try{
                InputStream input = getContentResolver().openInputStream(data.getData());
                //Converteu para bitmap
                capa = BitmapFactory.decodeStream(input);
                //Exibindo na tela a imagem depois de convertida
                imgLivroCapaEdit.setImageBitmap(capa);
            }catch (Exception e){
                e.printStackTrace();

            }
        }


    }
    public void abrirGaleria(View view) {
        //intent no modo inplicito, basicamente o sistema operacional que decide o que abrir
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //selecionar pelo o nome imagem
        intent.setType("image/*");
        //é usado esse start para pegar o que vai voltar, vai me da uma resultado
        startActivityForResult(Intent.createChooser(intent,"Selecione uma imagem"), COD_REQ_GALERIA);


    }
    public void editarLivro(View v) {

        byte[] livroCapa = Utils.toByteArray(capa);
        String titulo = txtTitulo.getText().toString();
        String descricao = txtDescricao.getText().toString();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        capa.compress(Bitmap.CompressFormat.JPEG,50,stream);
        livroCapa = stream.toByteArray();





        if (livroCapa == null) {
            alert("Aviso", "Insira uma imagem");
        }
        if (txtTitulo.getText().toString().equals("") || txtDescricao.getText().toString().equals("")) {
            alert("Aviso", "Preencha todos os campos");

        } else {
            Livro livro = new Livro(livroCapa, titulo, descricao);
            livro.setId(id);
            myBooksDB.daoLivro().atualizar(livro);

            alert("Editado", "O livro foi editado com sucesso");

        }
    }
    public void alert(String titulo, String mensagem){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.create().show();
    }


}
