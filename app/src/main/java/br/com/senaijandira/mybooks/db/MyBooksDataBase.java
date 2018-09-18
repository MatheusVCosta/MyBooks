package br.com.senaijandira.mybooks.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.senaijandira.mybooks.dao.LivroDao;
import br.com.senaijandira.mybooks.model.livro;

//
@Database(entities = {livro.class}, version = 2)

public abstract class MyBooksDataBase extends RoomDatabase{

    public abstract LivroDao daoLivro();
}
