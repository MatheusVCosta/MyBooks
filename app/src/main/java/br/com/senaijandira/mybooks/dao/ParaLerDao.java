package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.ParaLer;
@Dao
public interface ParaLerDao {
    @Insert
    void inserir(ParaLer pLer);
    @Update
    void atualizar(ParaLer pLer);
    @Delete
    void deletar(ParaLer pLer);

    //void deletarLivro(int id);

    //select que recebe um id para verificar quantas linhas possui o mesmo id cadastrado
    @Query("select count(idLivros) from ParaLer as ld inner join livro l on  ld.idLivros = l.id and ld.idLivros = :id")
    int selecionarId(int id);

    @Query("select ld.*, l.titulo, l.descricao,l.capa from ParaLer as ld, livro as l WHERE ld.idLivros = l.id")
    ParaLer[] selecionarTodosLivros();

    @Query("SELECT idLivros FROM ParaLer as pl, Livro as l WHERE l.id = pl.idLivros and pl.idLivros = :id")
    int verificarLivroExistente(int id);

    @Query("Delete from ParaLer Where idLivros = :id")
    void deletarParaLer(int id);
}
