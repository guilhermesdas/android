package com.example.contatos.db;

import android.provider.BaseColumns;

public class TaskContract {

    public static final String DB_NAME = "Contatos";
    public static final int DB_VERSION = 1;
    public static final String TABLE = "Contato";

    public static class Columns{
        public static final String _ID = BaseColumns._ID;
        public static final String NOME = "nome";
        public static final String EMPRESA = "empresa";
        public static final String TELEFONE = "telefone";
        public static final String EMAIL = "email";
    }
    public static class Queries{
        public static final String SELECT_BY_ID = String.format(
                "SELECT * FROM %s WHERE %s=?",TABLE, Columns._ID); ;
        public static final String SELECT_ALL = String.format(
                "SELECT * FROM %s ORDER BY %s",TABLE, Columns.NOME); ;
    }

}
