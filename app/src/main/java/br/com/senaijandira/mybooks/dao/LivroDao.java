package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import br.com.senaijandira.mybooks.model.livro;

@Dao
public interface LivroDao {
    //Vai passar um objeto livro pela l para o insert e inerir o livro no banco
    //isso vale para update e delete
    @Insert
    void inserir(livro l);
    @Update
    void atualizar(livro l);
    @Delete
    void deletar(livro l);

    @Query("SELECT * FROM livro")
    livro[] selecionarTodos();
}
