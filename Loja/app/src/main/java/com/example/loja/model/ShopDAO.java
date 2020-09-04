package com.example.loja.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;

public class ShopDAO implements DAO {

    private SQLiteDatabase db;

    public ShopDAO(Context context){
        this.db = (new Database(context)).getWritableDatabase();
    }

    @Override
    public boolean create(Object obj) {

        if ( obj instanceof Shop ){

            ContentValues values = createContentValues(obj);
            if ( db.insert("Shop", null, values) != -1 ){
                //db.close();
                return true;
            } else {
                Log.e("LojaModel","Error ao criar shop (ShopDAO)");
                //db.close();
                return false;
            }
        }

        return false;
    }

    @Override
    public boolean update(int id, Object obj) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public Object get(int id) {

        return false;

    }

    @Override
    public ArrayList<Object> getList() {
        return null;
    }

    public ArrayList<Pair<Produto,Integer>> getProdutos(int idUsuario){
        String clauses[] = {Integer.toString(idUsuario)};
        String sql = Database.SQL_JOIN_SHOP_PRODUTO;
        Cursor c =  this.db.rawQuery(sql,clauses);

        ArrayList<Pair<Produto,Integer>> produtos = new ArrayList<>();

        // Obtém os produtos e as quantidades
        if ( c.moveToNext() ){
            do {
                produtos.add(new Pair<>(
                        new Produto(
                                c.getString(0),
                                "",
                                c.getDouble(1),
                                c.getBlob(2)),
                        c.getInt(3)
                ));
            } while( c.moveToNext() );
        }

        // Retorna os pares <produto,qnt>
        return produtos;

    }

    private ContentValues createContentValues(Object obj){

        Shop s = (Shop) obj;
        ContentValues values = new ContentValues();
        values.put("idUsuario", s.getId_usuario());
        values.put("idProduto",s.getId_produto());
        values.put("qnt", s.getQnt());
        return values;

    }

}
