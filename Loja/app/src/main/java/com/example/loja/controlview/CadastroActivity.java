package com.example.loja.controlview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loja.R;
import com.example.loja.model.Usuario;
import com.example.loja.model.UsuarioDAO;
import com.google.android.material.textfield.TextInputEditText;

public class CadastroActivity extends AppCompatActivity {

    TextInputEditText edtNome;
    TextInputEditText edtEmail;
    TextInputEditText edtIdade;
    TextInputEditText edtSenha;
    Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.txtCadastrarNome);
        edtEmail = findViewById(R.id.txtCadastrarEmail);
        edtIdade = findViewById(R.id.txtCadastrarIdade);
        edtSenha = findViewById(R.id.txtCadastrarPassword);
        btnCadastrar = findViewById(R.id.btnCadastrar);

    }

    public void cadastrar(View view){

        String nome = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String idade = edtIdade.getText().toString();
        String senha = edtSenha.getText().toString();

        // Verifica se todos os campos estão preenchidos
        if ( nome.isEmpty() ||  email.isEmpty()
                || idade.isEmpty() || senha.isEmpty() ){
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cria um usuario a partir dos campos
        Usuario user = new Usuario(
            nome,senha,Integer.parseInt(idade),email
        );

        // Cria o objeto que irá salvar os dados do usuário no banco de dados
        UsuarioDAO userDAO = new UsuarioDAO(this);
        if ( !userDAO.create(user) ){
            Toast.makeText(this, "Email ja cadastrado!.", Toast.LENGTH_SHORT).show();
            edtEmail.requestFocus();
        } else {
            Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }

    }
}