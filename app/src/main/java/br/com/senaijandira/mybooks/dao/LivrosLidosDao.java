package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.RawQuery;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.ParaLer;


@Dao
public interface LivrosLidosDao {

    @Insert
    void inserir(LivrosLidos lLidos);
    @Update
    void atualizar(LivrosLidos lLidos);
    @Delete
    void deletar(LivrosLidos lLidos);

    //select que recebe um id para verificar quantas linhas possui o mesmo id cadastrado
    @Query("select count(idLivros) from livrosLidos as ld inner join livro l on  ld.idLivros = l.id and ld.idLivros = :id")
    int selecionarId(int id);

    @Query("select ld.*, l.titulo, l.descricao,l.capa from livrosLidos as ld, livro as l WHERE ld.idLivros = l.id")
    LivrosLidos[] selecionarTodosLivros();

    @Query("SELECT idLivros FROM livrosLidos as ld, Livro as l WHERE l.id = ld.idLivros and ld.idLivros = :id")
    int verificarLivroExistente(int id);

    @Query("Delete from livrosLidos Where idLivros = :id")
    void deletarLidos(int id);



}
