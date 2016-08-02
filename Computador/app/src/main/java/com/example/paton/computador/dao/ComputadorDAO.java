package com.example.paton.computador.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.paton.computador.model.Computador;



public class ComputadorDAO {

    private static ComputadorDAO ctoDAO;
    private final Context context;
    private DBHelper myHelper;
    private SQLiteDatabase database;



    private ComputadorDAO(Context context) {
        this.context = context;
    }


    public static ComputadorDAO getInstance(Context context) {
        if (ctoDAO == null) {
            ctoDAO = new ComputadorDAO(context);
            return ctoDAO;
        }

        return ctoDAO;
    }


    public void open() {
        myHelper = DBHelper.getInstance(context);
        database = myHelper.getWritableDatabase();
    }


    public void close() {
        database.close();
    }


    public Boolean salvar(Computador cto) {
        if (cto.getId_computador() == null) {
            return inserir(cto);
        } else {
            return alterar(cto);
        }
    }


    public Boolean inserir(Computador cto) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Computador.COLUNA_MARCA, cto.getMarca());
        valores.put(Contract.Computador.COLUNA_CPU, cto.getCpu());
        valores.put(Contract.Computador.COLUNA_RAM, cto.getRam());
        valores.put(Contract.Computador.COLUNA_HD, cto.getHd());

        database.insert(Contract.Computador.TABELA_NOME, null, valores);

        return true;
    }


    public Boolean alterar(Computador cto) {
        ContentValues valores = new ContentValues();

        valores.put(Contract.Computador.COLUNA_MARCA, cto.getMarca());
        valores.put(Contract.Computador.COLUNA_RAM, cto.getRam());
        valores.put(Contract.Computador.COLUNA_CPU, cto.getCpu());
        valores.put(Contract.Computador.COLUNA_HD, cto.getHd());

        database.update(Contract.Computador.TABELA_NOME, valores, Contract.Computador.COLUNA_ID + " = " + cto.getId_computador(), null);

        return true;
    }


    public Integer excluir(Computador cto) {

        return database.delete(Contract.Computador.TABELA_NOME, Contract.Computador.COLUNA_ID + " = " + cto.getId_computador(), null);
    }


    public Cursor getLista() {

        return database.rawQuery("SELECT  * FROM " + Contract.Computador.TABELA_NOME, null);
    }


    public Cursor getListaByMarca(String marca) {

        return database.rawQuery("SELECT  * FROM " + Contract.Computador.TABELA_NOME + " WHERE marca LIKE '" + marca + "%'", null);
    }
}
