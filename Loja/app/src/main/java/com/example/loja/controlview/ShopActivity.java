package com.example.loja.controlview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loja.R;
import com.example.loja.model.Produto;
import com.example.loja.model.ProdutoDAO;
import com.example.loja.model.Shop;
import com.example.loja.model.ShopDAO;
import com.example.loja.model.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public class ShopActivity extends AppCompatActivity {

    MaterialButton btnComprar;
    ListView listShop;
    TextView txtValorTotal;
    HashMap<Integer,Integer> itensComprados;
    Usuario usuario;
    ArrayList<Object> produtos;
    ProdutosAdapter produtosAdapter;
    ItensCompradosAdapter itensAdapter;
    BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        // Recupera usuario
        usuario = (Usuario) getIntent().getSerializableExtra("Usuario");
        this.setTitle("Seja bem vindo, " + usuario.getNome() + "!");

        // Associar views com objetos
        btnComprar = findViewById(R.id.btnComprar);
        listShop = findViewById(R.id.listShop);
        txtValorTotal = findViewById(R.id.txtValorTotal);

        /////////////////////////////////////////////////////// Setar lista de produtos
        produtos = new ProdutoDAO(ShopActivity.this).getList();
        itensComprados = new HashMap<>();
        produtosAdapter = new ProdutosAdapter(ShopActivity.this, R.layout.itens_celula);
        produtosAdapter.addAll(produtos);

        // Definir um callback para quando o usuário selecionar o item para compra
        produtosAdapter.setOnCheckBoxClickListener(new ProdutosAdapter.OnCheckBoxClickListener() {
            @Override
            public void onCheckBoxClickListener(boolean isChecked, int position, int qnt) {
                Produto p = (Produto) produtosAdapter.getItem(position);
                if ( isChecked ){
                    itensComprados.put(p.getId(), qnt);
                } else {
                    itensComprados.remove(p.getId());
                }
                txtValorTotal.setText(String.format("R$ %.2f", somaPrecos()));
            }
        });

        // Definir um callback para quando o usuário alterar a quantidade de itens
        produtosAdapter.setOnSpinnerItemSelectedListener(new ProdutosAdapter.OnSpinnerItemSelectedListener() {
            @Override
            public void onSpinnerItemSelectedListener(boolean isChecked, int position, int qnt) {
                Produto p = (Produto) produtosAdapter.getItem(position);
                if ( isChecked ){
                    itensComprados.put(p.getId(),qnt);
                    txtValorTotal.setText(String.format("R$ %.2f", somaPrecos()));
                }
            }
        });

        listShop.setAdapter(produtosAdapter);

        /////////////////////////////////////////////////////// Setar lista de itens comprados pelo usuario


        // Verifica qual a página
        menu = findViewById(R.id.bottom_navigation);
        menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.shopping:
                        btnComprar.setVisibility(View.VISIBLE);
                        txtValorTotal.setVisibility(View.VISIBLE);
                        listShop.setAdapter(produtosAdapter);
                        break;
                    case R.id.pedidos:
                        atualizarPedidos();
                        btnComprar.setVisibility(View.INVISIBLE);
                        txtValorTotal.setVisibility(View.INVISIBLE);
                        listShop.setAdapter(itensAdapter);
                        break;
                }
                return true;
            }
        });
    }

    public void atualizarPedidos(){
        ArrayList<Pair<Produto,Integer>> pedidos = new ShopDAO(this).getProdutos(usuario.getId());
        itensAdapter = new ItensCompradosAdapter(this, R.layout.itens_comprados_celula);
        itensAdapter.addAll(pedidos);
    }

    // Soma o preço total da compra
    private double somaPrecos(){
        double res = 0;

        for ( Object obj : produtos ){
            Produto p = (Produto) obj;
            if ( itensComprados.containsKey(p.getId()) ){
                res += itensComprados.get(p.getId()) * p.getPreco();
            }
        }
        return res;
    }

    // Efetuar compra
    public void efetuarCompra(View view){

        // Verifica se nenhum item foi selecionado
        if ( itensComprados.isEmpty() ){
            Toast.makeText(this, "Nenhum item foi selecionado!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pedir confirmação
        new MaterialAlertDialogBuilder(this)
                .setTitle("Deseja efetuar a compra?")
                .setMessage(String.format("Total a pagar: R$ %.2f", somaPrecos()))
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Efetucar compra
                        ShopDAO shopDAO = new ShopDAO(ShopActivity.this);
                        for ( Integer idProduto : itensComprados.keySet() ){
                            // Cria um novo objeto Shop para inserir no banco de dados
                            shopDAO.create(
                                    new Shop(
                                    usuario.getId(),
                                    idProduto,
                                    itensComprados.get(idProduto)
                            ));
                        }
                        // Exibe mensage de conclusão
                        Toast.makeText(ShopActivity.this, "Compra efetuada com sucesso!", Toast.LENGTH_SHORT).show();
                        // Limpa todos os dados
                        //produtosAdapter.clear();
                        //produtosAdapter.addAll(produtos);
                        listShop.setAdapter(produtosAdapter);
                        itensComprados.clear();
                        txtValorTotal.setText(String.format("R$ %.2f", somaPrecos()));

                    }
                })
                .show();

    }


}