package br.com.senaijandira.mybooks.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity (tableName = "livrosLidos")
public class LivrosLidos{

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ForeignKey(entity = Livro.class,
            childColumns = "idLivros",
            parentColumns = "id")
    private int idLivros;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte [] capa;
    private String titulo;
    private String descricao;

    @Ignore
    public LivrosLidos(){}

    public LivrosLidos(int idLivros){
        this.setIdLivros(idLivros);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdLivros() {
        return idLivros;
    }

    public void setIdLivros(int idLivros) {
        this.idLivros = idLivros;
    }

    public byte[] getCapa() {
        return capa;
    }

    public void setCapa(byte[] capa) {
        this.capa = capa;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
