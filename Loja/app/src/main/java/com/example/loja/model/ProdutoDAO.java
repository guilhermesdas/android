package com.example.loja.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class ProdutoDAO implements DAO {

    private SQLiteDatabase db;

    public ProdutoDAO(Context context){
            this.db = (new Database(context)).getWritableDatabase();
    }

    @Override
    public boolean create(Object obj) {

        if ( obj instanceof Produto ){

            ContentValues values = createContentValues(obj);
            if ( db.insert("Produto", null, values) != -1 ){
                db.close();
                return true;
            } else {
                Log.e("LojaModel","Error ao criar produto (ProdutoDAO)");
                db.close();
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean update(int id, Object obj) {

        if ( obj instanceof Produto ){

            Produto p = (Produto) obj;

            ContentValues values = createContentValues(obj);
            String[] whereArgs = {String.valueOf(p.getId())};

            if ( db.update("Produto", values, "id=?", whereArgs) != 0 ){
                db.close();
                return true;
            } else {
                Log.e("LojaModel","Error ao atualizar produto (ProdutoDAO)");
                db.close();
                return false;
            }

        }

        return false;

    }

    @Override
    public boolean delete(int id) {

        String[] where = {String.valueOf(id)};

        if ( db.delete("Produto","id=?",where) != 0 ){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object get(int id) {
        Produto p = null;
        String sql = String.format(Database.SQL_SELECT_PRODUTO_BY_ID);
        String clauses[] = {Integer.toString(id)};

        Cursor cursor = this.db.rawQuery(sql, clauses);

        if ( cursor.moveToNext() ){
            p = new Produto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getDouble(3),
                    cursor.getBlob(4)
            );
        }

        cursor.close();
        return p;
    }

    @Override
    public ArrayList<Object> getList() {
        Cursor cursor =  this.db.rawQuery(Database.SQL_SELECT_ALL_PRODUTO_BY_ID,null);
        ArrayList<Object> produtos = new ArrayList<>();
        if ( cursor.moveToNext() ){
            do {
                // Adiciona o produto atual na lista
                produtos.add( new Produto(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getBlob(4) )
                );
            } while(cursor.moveToNext());
        }

        return produtos;

    }

    protected static ContentValues createContentValues(Object obj){

        Produto p = (Produto) obj;
        ContentValues values = new ContentValues();
        values.put("nome",p.getNome());
        values.put("descricao", p.getDescricao());
        values.put("valor", p.getPreco());
        values.put("img", p.getImg());
        return values;

    }

}
