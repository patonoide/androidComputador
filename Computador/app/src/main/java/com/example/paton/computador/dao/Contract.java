package com.example.paton.computador.dao;


import android.provider.BaseColumns;

public final class Contract {
    public static final String BD_NOME = "computador.db";
    public static final int BD_VERSAO = 4;


    private Contract() {
    }


    public static abstract class Computador implements BaseColumns {
        public static final String TABELA_NOME = "computador";
        public static final String COLUNA_ID = "_id";
        public static final String COLUNA_MARCA = "marca";
        public static final String COLUNA_CPU = "cpu";
        public static final String COLUNA_RAM = "RAM";
        public static final String COLUNA_HD = "Hd";

    }
}

