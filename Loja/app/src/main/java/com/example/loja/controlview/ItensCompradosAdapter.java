package com.example.loja.controlview;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loja.R;
import com.example.loja.model.DbBitmapUtility;
import com.example.loja.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ItensCompradosAdapter extends ArrayAdapter {
    public ItensCompradosAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View minhaView = convertView;
        final ItensCompradosView itensView;

        if ( convertView == null ){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            minhaView = inflater.inflate(R.layout.itens_comprados_celula,
                    parent,
                    false);

            // Cria um Itens View

            itensView = new ItensCompradosView();
            itensView.custoTotal = minhaView.findViewById(R.id.txtCustoTotal);
            itensView.preco = minhaView.findViewById(R.id.txtCusto);
            itensView.img = minhaView.findViewById(R.id.imgProduto);
            itensView.nome = minhaView.findViewById(R.id.txtItemNome);
            itensView.card = minhaView.findViewById(R.id.cardItem);
            itensView.qnt = minhaView.findViewById(R.id.txtQnt);

            // Seta ele como item para minhaView
            minhaView.setTag(itensView);

        } else {
            itensView = (ItensCompradosView) minhaView.getTag();
        }

        // String de 1 a 100
        List<Integer> list = new ArrayList<>();
        for ( int i = 1; i < 101; i++ ){
            list.add(i);
        }

        // Recupera o item na posiçãp correspondente
        final Pair<Produto,Integer> p = (Pair<Produto,Integer>) this.getItem(position);
        itensView.custoTotal.setText(String.format("R$ %.2f",p.first.getPreco()*p.second));
        itensView.preco.setText(String.format("R$ %.2f",p.first.getPreco()));
        itensView.img.setImageBitmap(DbBitmapUtility.getImage(p.first.getImg()));
        itensView.nome.setText(p.first.getNome());
        itensView.qnt.setText("" + p.second + " uni");

        return minhaView;
    }
}
