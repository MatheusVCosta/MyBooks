package br.com.senaijandira.mybooks;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.InputStream;
import java.util.Arrays;

import br.com.senaijandira.mybooks.model.livro;

public class CadastroActivity extends AppCompatActivity {

    //Serve para colocar no banco
    Bitmap livroCapa;
    ImageView imgLivroCapa;
    EditText txtTitulo, txtDescricao;

    //variavel final é um variavel que não muda o valor
    private final int COD_REQ_GALERIA = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            alert("Erro", "Preencha todos os campos");
        }else{
            alert("Salva", "Salvo com sucesso");
            byte[] capa = Utils.toByteArray(livroCapa);

            String titulo = txtTitulo.getText().toString();

            String descricao = txtDescricao.getText().toString();

            livro Livro = new livro(0, capa, titulo, descricao);

            //Inserir a variavel estática da MainActivity
            int tamanhoArray = MainActivity.Livros.length;
            MainActivity.Livros = Arrays.copyOf(MainActivity.Livros, tamanhoArray + 1);
            MainActivity.Livros[tamanhoArray] = Livro;


        }
     

    }
    public void alert(String titulo, String mensagem){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(titulo);
        alert.setMessage(mensagem);
        alert.create().show();

    }
}
