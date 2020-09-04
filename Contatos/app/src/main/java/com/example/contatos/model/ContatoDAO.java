package com.example.contatos.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.contatos.db.TaskContract;
import com.example.contatos.db.TaskDBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ContatoDAO implements  DAO<Contato> {

    private TaskDBHelper db;

    public ContatoDAO(Context context) {
        this.db = TaskDBHelper.getInstance(context);
    }

    // Optional: https://www.baeldung.com/java-optional
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Optional<Contato> get(long id) {

        // Obtém um banco de dados em modo de leitura
        SQLiteDatabase dbr = db.getReadableDatabase();
        Optional<Contato> opcontato = Optional.empty();

        // Executar busca pelo id
        String sql = String.format(TaskContract.Queries.SELECT_BY_ID);
        String clauses[] = {"" + id};
        Cursor cursor = dbr.rawQuery(sql, clauses);

        // Recupera contato que foi encontrado
        if ( cursor.moveToNext() ){
            opcontato.of(new Contato(
                    cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            ));
        }

        // Fecha banco de dados
        dbr.close();

        // se nenhum contato for adicionado, opcantato está vazio
        return opcontato;

    }

    @Override
    public List<Contato> getAll() {

        // Pegar banco de dados em modo de leitura
        SQLiteDatabase dbr = db.getReadableDatabase();


        // Executar SQL para exibir todos
        Cursor cursor = dbr.rawQuery(TaskContract.Queries.SELECT_ALL,null);
        List<Contato> contatos = new ArrayList<>();
        if ( cursor.moveToNext() ){
            do {
                contatos.add(
                        new Contato(
                                cursor.getLong(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4)
                ));
            } while( cursor.moveToNext() );
        }

        dbr.close();

        return contatos;

    }

    @Override
    public boolean add(Contato contato) {
        SQLiteDatabase dbw = db.getWritableDatabase();

        // Create content values with contato
        ContentValues values = new ContentValues();
        values.put(TaskContract.Columns.NOME, contato.getNome());
        values.put(TaskContract.Columns.TELEFONE, contato.getTelefone());
        values.put(TaskContract.Columns.EMAIL, contato.getEmail());
        values.put(TaskContract.Columns.EMPRESA, contato.getEmpresa());

        try {
            dbw.insertOrThrow(TaskContract.TABLE, null, values);
        } catch (SQLException e) {
            Log.e("[ContatoDAO]", e.getMessage());
            dbw.close();
            return false;
        }

        dbw.close();
        return true;

    }

    @Override
    public boolean update(Contato contato) {
        SQLiteDatabase dbw = db.getWritableDatabase();

        // Create content values with contato
        ContentValues values = new ContentValues();
        values.put(TaskContract.Columns.NOME, contato.getNome());
        values.put(TaskContract.Columns.TELEFONE, contato.getTelefone());
        values.put(TaskContract.Columns.EMAIL, contato.getEmail());
        values.put(TaskContract.Columns.EMPRESA, contato.getEmpresa());
        String[] whereArgs = {String.valueOf(contato.getId())};

        // Atualiza os dados do contato
        int result = dbw.update(TaskContract.TABLE, values, TaskContract.Columns._ID + "=?", whereArgs);
        dbw.close();

        if ( result != 0 ){
            return true;
        } else {
            Log.e("[ContatoDAO]", "Não foi possível atualizar o contato");
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        SQLiteDatabase dbw = db.getWritableDatabase();

        String[] where = {String.valueOf(id)};

        // Atualiza os dados do contato
        int result = dbw.delete(TaskContract.TABLE,  TaskContract.Columns._ID + "=?", where);
        dbw.close();

        if ( result != 0 ){
            return true;
        } else {
            Log.e("[ContatoDAO]", "Não foi possível deletar o contato");
            return false;
        }

    }
}
