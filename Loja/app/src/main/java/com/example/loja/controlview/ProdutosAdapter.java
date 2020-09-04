package com.example.loja.controlview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loja.R;
import com.example.loja.model.DbBitmapUtility;
import com.example.loja.model.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutosAdapter extends ArrayAdapter {

    private OnCheckBoxClickListener onCheckBoxClickListener;
    private OnSpinnerItemSelectedListener onSpinnerItemSelectedListener;

    public void setOnCheckBoxClickListener(OnCheckBoxClickListener onCheckBoxClickListener){
        this.onCheckBoxClickListener = onCheckBoxClickListener;
    }

    public void setOnSpinnerItemSelectedListener(OnSpinnerItemSelectedListener onSpinnerItemSelectedListener){
        this.onSpinnerItemSelectedListener = onSpinnerItemSelectedListener;
    }

    public ProdutosAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {

        View minhaView = convertView;
        final ProdutoView produtoView;

        if ( convertView == null ){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            minhaView = inflater.inflate(R.layout.itens_celula,
                    parent,
                    false);

            // Cria um Produto View

            produtoView = new ProdutoView();
            produtoView.custo = minhaView.findViewById(R.id.txtCusto);
            produtoView.img = minhaView.findViewById(R.id.imgProduto);
            produtoView.nome = minhaView.findViewById(R.id.txtItemNome);
            produtoView.spnQnt = minhaView.findViewById(R.id.spnItemQnt);
            produtoView.card = minhaView.findViewById(R.id.cardItem);
            produtoView.detalhes = minhaView.findViewById(R.id.btnDetalhes);

            // Seta ele como item para minhaView
            minhaView.setTag(produtoView);

        } else {
            produtoView = (ProdutoView) minhaView.getTag();
        }

        // String de 1 a 100
        List<Integer> list = new ArrayList<>();
        for ( int i = 1; i < 101; i++ ){
            list.add(i);
        }

        // Recupera o item na posiçãp correspondente
        final Produto p = (Produto) this.getItem(position);
        produtoView.custo.setText( String.format("R$ %.2f", p.getPreco()) );
        produtoView.img.setImageBitmap(DbBitmapUtility.getImage(p.getImg()));
        produtoView.nome.setText(p.getNome());
        produtoView.spnQnt.setAdapter(new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_dropdown_item_1line, list));
        // Invoca onSpinnerItemSelectedListener
        produtoView.spnQnt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                onSpinnerItemSelectedListener.onSpinnerItemSelectedListener(produtoView.card.isChecked(), position, (int) produtoView.spnQnt.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        // Invoka onCheckBoxClickListener
        produtoView.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produtoView.card.toggle();
                onCheckBoxClickListener.onCheckBoxClickListener(produtoView.card.isChecked(), position, (int) produtoView.spnQnt.getSelectedItem());
            }
        });
        produtoView.detalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog builder = new Dialog(getContext());
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(Color.TRANSPARENT));
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //nothing;
                    }
                });

                View detalhesView;
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                detalhesView = inflater.inflate(R.layout.item_detalhes,
                        parent,
                        false);
                ProdutoDetalhesView produtoDetalhesV = new ProdutoDetalhesView();
                produtoDetalhesV.card = detalhesView.findViewById(R.id.cardDetalhes);
                produtoDetalhesV.descricao = detalhesView.findViewById(R.id.txtDetalhes);
                produtoDetalhesV.nome = detalhesView.findViewById(R.id.txtDetalhesNome);
                produtoDetalhesV.img = detalhesView.findViewById(R.id.imgDetalhes);
                detalhesView.setTag(produtoDetalhesV);
                produtoDetalhesV.descricao.setText(p.getDescricao());
                produtoDetalhesV.nome.setText(p.getNome());
                produtoDetalhesV.img.setImageBitmap(DbBitmapUtility.getImage(p.getImg()));

                // Cria Layout
                // Adiciona no card

                // Adiciona card View ao dialog
                builder.addContentView(detalhesView, new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
                builder.show();
            }
        });

        return minhaView;

    }

    // Interfaces de callback para onClick do checkbox e ItemSelecteedChange do Spinner
    // O método será implementado na activity

    public interface OnCheckBoxClickListener{
        void onCheckBoxClickListener(boolean isChecked, int position, int qnt);
    }

    public interface OnSpinnerItemSelectedListener{
        void onSpinnerItemSelectedListener(boolean isChecked, int position, int qnt);
    }

}
