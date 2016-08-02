package com.example.paton.computador.dao;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {


    private static DBHelper myHelper;


    private DBHelper(Context context) {
        super(context, Contract.BD_NOME, null, Contract.BD_VERSAO);
    }

    public static DBHelper getInstance(Context context) {
        if (myHelper == null) {
            myHelper = new DBHelper(context);
            return myHelper;
        }

        return myHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + Contract.Computador.TABELA_NOME + " ("
                + Contract.Computador.COLUNA_ID + " integer primary key autoincrement,"
                + Contract.Computador.COLUNA_MARCA + " text,"
                + Contract.Computador.COLUNA_CPU + " text,"
                + Contract.Computador.COLUNA_RAM + " text,"
                + Contract.Computador.COLUNA_HD + " text);";

        db.execSQL(sql);
        Log.i("paton", "Executou o script de criação da tabela "
                + Contract.Computador.TABELA_NOME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("paton", "Upgrade da versão " + oldVersion + " para "
                + newVersion + ", destruindo tudo.");
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Computador.TABELA_NOME);
        onCreate(db);
        Log.i("paton", "Executou o script de upgrade da tabela " + Contract.Computador.TABELA_NOME);
    }

}
