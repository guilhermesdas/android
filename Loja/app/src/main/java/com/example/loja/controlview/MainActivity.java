package com.example.loja.controlview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.loja.model.Usuario;
import com.example.loja.model.UsuarioDAO;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;

import com.example.loja.R;

public class MainActivity extends AppCompatActivity {

    // Views
    MaterialButton btnTelaCadastro;
    MaterialButton btnLogin;
    TextInputEditText edtEmail;
    TextInputEditText edtSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Associar views a objetos
        btnTelaCadastro = findViewById(R.id.btnTelaCadastro);
        btnLogin = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.txtEmail);
        edtSenha = findViewById(R.id.txtSenha);

    }

    @Override
    protected void onResume() {
        super.onResume();

        edtEmail.getText().clear();
        edtSenha.getText().clear();
        edtEmail.requestFocus();

    }

    // Login
    public void login(View view){

        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();

        if ( email.isEmpty() || senha.isEmpty() ){
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioDAO userDAO = new UsuarioDAO(this);
        Usuario user = (Usuario) userDAO.get(email);
        
        // Verifica se há um usuário com esse email
        if ( user == null ){
            Toast.makeText(this, "Email não cadastrado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verifica se a senha está correta
        if ( user.getSenha().equals(senha) ){

            Toast.makeText(this, "Seja bem vindo, " + user.getNome(), Toast.LENGTH_SHORT).show();



            // Envia o objeto usuário para a próxima activity
            Intent intent = new Intent(MainActivity.this, ShopActivity.class);
            intent.putExtra("Usuario",user);
            startActivity(intent);

        } else {
            Toast.makeText(this, "Senha incorreta!", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    // Cadastro
    public void telaCadastro(View view){
        startActivity(new Intent(MainActivity.this, CadastroActivity.class));
    }


}