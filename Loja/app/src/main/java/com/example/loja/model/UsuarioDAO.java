package com.example.loja.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class UsuarioDAO implements DAO {

    private SQLiteDatabase db;	

    // Construtor inicializa o objeto SQLiteDatabase
    public UsuarioDAO(Context context){
        this.db = (new Database(context)).getWritableDatabase();
    }

    // Cria um novo usuério
    @Override
    public boolean create(Object obj) {

        if ( obj instanceof Usuario ){

            ContentValues values = createContentValues(obj);
            if ( db.insert("Usuario", null, values) != -1 ){
                //db.close();
                return true;
            } else {
                Log.e("LojaModel","Error ao criar usuário (UsuarioDAO)");
                //db.close();
                return false;
            }
        }

        return false;

    }

    @Override
    public boolean update(int id, Object obj) {

        if ( obj instanceof Usuario ){

            Usuario user = (Usuario) obj;

            ContentValues values = createContentValues(obj);
            String[] whereArgs = {String.valueOf(user.getId())};

            if ( db.update("Usuario", values, "id=?", whereArgs) != 0 ){
                //db.close();
                return true;
            } else {
                Log.e("LojaModel","Error ao atualizar usuário (UsuarioDAO)");
                //db.close();
                return false;
            }

        }

        return false;

    }

    @Override
    public boolean delete(int id) {

        String[] where = {String.valueOf(id)};

        if ( db.delete("Usuario","id=?",where) != 0 ){
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Object get(int id) {

        Usuario user = null;
        String sql = String.format(Database.SQL_SELECT_USER_BY_ID);
        String clauses[] = {Integer.toString(id)};

        Cursor cursor = this.db.rawQuery(sql, clauses);

        if ( cursor.moveToNext() ){
            user = new Usuario(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4)
            );
        }

        cursor.close();
        return user;

    }

    public Object get(String email) {

        Usuario user = null;
        String sql = String.format(Database.SQL_SELECT_USER_BY_EMAIL);
        String clauses[] = {email};

        Cursor cursor = this.db.rawQuery(sql, clauses);

        if ( cursor.moveToNext() ){
            user = new Usuario(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4)
            );
        }

        cursor.close();
        return user;

    }

    @Override
    public ArrayList<Object> getList() {

        Cursor cursor = this.db.rawQuery(Database.SQL_SELECT_ALL_USER_BY_ID,null);
        ArrayList<Object> usuarios = new ArrayList<>();
        if ( cursor.moveToNext() ){
            do {
                // Adiciona o produto atual na lista
                usuarios.add( new Usuario(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getString(4) )
                );
            } while(cursor.moveToNext());
        }

        return usuarios;
    }

    private ContentValues createContentValues(Object obj){
        Usuario user = (Usuario) obj;
        ContentValues values = new ContentValues();
        values.put("nome",user.getNome());
        values.put("senha", user.getSenha());
        values.put("idade", user.getIdade());
        values.put("email",user.getEmail());
        return values;
    }

}
