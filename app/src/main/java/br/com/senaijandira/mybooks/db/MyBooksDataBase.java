package br.com.senaijandira.mybooks.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import br.com.senaijandira.mybooks.dao.LivroDao;
import br.com.senaijandira.mybooks.dao.LivrosLidosDao;
import br.com.senaijandira.mybooks.dao.ParaLerDao;
import br.com.senaijandira.mybooks.model.Livro;
import br.com.senaijandira.mybooks.model.LivrosLidos;
import br.com.senaijandira.mybooks.model.ParaLer;

//
@Database(entities = {Livro.class, LivrosLidos.class, ParaLer.class}, version = 6)

public abstract class MyBooksDataBase extends RoomDatabase{

    public abstract LivroDao daoLivro();
    public abstract LivrosLidosDao daoLivroLidos();
    public abstract ParaLerDao daoParaLer();

}
