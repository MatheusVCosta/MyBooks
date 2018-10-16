package br.com.senaijandira.mybooks.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;

import br.com.senaijandira.mybooks.model.Livro;

@Dao
public interface LivroDao {
    //Vai passar um objeto livro pela l para o insert e inerir o livro no banco
    //isso vale para update e delete
    @Insert
    void inserir(Livro l);
    @Update
    void atualizar(Livro l);
    @Delete
    void deletar(Livro l);

    @Query("SELECT * FROM livro")
    Livro[] selecionarTodos();

    @Query("Select * From livro as l Where l.id = :id")
    Cursor trazerLivro(int id);

}
