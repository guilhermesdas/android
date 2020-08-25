package com.example.cadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Pessoas e animais
    ArrayList<Usuario> usuarios;
    ArrayList<Animal> animais;
    int iUsuarios = -1;
    int iAnimais = -1;
    /** TELA PRINCIPAL **/

    // Buttons
    MaterialButton btnTelaCadastro;
    MaterialButton btnListar;
    MaterialButton btnTelaCadastroAnimais;
    MaterialButton btnListarAnimais;

    /** TELA DE CADASTRO **/

    MaterialButton btnCancelar;
    MaterialButton btnSalvar;
    TextInputEditText edtNome;
    TextInputEditText edtProfissao;
    TextInputEditText edtIdade;

    /** TELA DE CADASTRO DE ANIMAIS **/

    MaterialButton btnCancelarAnimal;
    MaterialButton btnSalvarAnimal;
    TextInputEditText edtNomeAnimal;
    TextInputEditText edtEspecieAnimal;
    TextInputEditText edtIdadeAnimal;
    RadioGroup rgSexoAnimal;
    RadioButton rbMasculinoAnimal;
    RadioButton rbFemininoAnimal;

    /** TELA PARA LSITAR USUÁRIOS **/
    MaterialButton btnVoltar;
    MaterialButton btnAnterior;
    MaterialButton btnProximo;
    MaterialButton btnAlterar;
    TextView txtNome;
    TextView txtProfissao;
    TextView txtIdade;
    ImageView imgSexo;
    RadioGroup rgSexo;
    RadioButton rbMasculino;
    RadioButton rbFeminino;

    /** TELA PARA LSITAR USUÁRIOS **/
    MaterialButton btnVoltarAnimal;
    MaterialButton btnAnteriorAnimal;
    MaterialButton btnProximoAnimal;
    MaterialButton btnAlterarAnimal;
    TextView txtNomeAnimal;
    TextView txtEspecieAnimal;
    TextView txtIdadeAnimal;
    ImageView imgSexoAnimal;

    final String[] sexo = { "Masculino", "Feminino" };

    // New material dialog
    void newMaterialAlertDialogBuilder(String title, String message,
                                       DialogInterface.OnClickListener positiveClick ){


        new MaterialAlertDialogBuilder(MainActivity.this)
                // Add customization options here
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("SIM", positiveClick)
                .setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do nothing
                    }
                })
                .show();
    }

    // Inicializar campos do usuário no card
    void inicializarCardUsuario(){

        Usuario user = usuarios.get(iUsuarios);
        txtNome.setText(user.getNome());
        txtProfissao.setText(user.getProfissao());
        txtIdade.setText(String.format("%d anos", user.getIdade()));
        if ( user.getSexo() == Sexo.MASCULINO ){
            imgSexo.setImageResource(R.drawable.boy);
        } else {
            imgSexo.setImageResource(R.drawable.girl);
        }

    }

    // Inicializar campos do usuário no card
    void inicializarCardAnimal(){

        Animal animal = animais.get(iAnimais);
        txtNomeAnimal.setText(animal.getNome());
        txtEspecieAnimal.setText(animal.getEspecie());
        txtIdadeAnimal.setText(String.format("%d anos", animal.getIdade()));
        if ( animal.getSexo() == Sexo.MASCULINO ){
            imgSexoAnimal.setImageResource(R.drawable.mdog);
        } else {
            imgSexoAnimal.setImageResource(R.drawable.fdog);
        }

    }

    //
    public void onCreateTelaListarUsuarios(){
        setContentView(R.layout.listar_usuarios);
        this.setTitle(R.string.tela_lista);


        // Inicializar bjetos
        txtNome = findViewById(R.id.txtNome);
        txtProfissao = findViewById(R.id.txtProfissao);
        txtIdade = findViewById(R.id.txtIdade);
        imgSexo = findViewById(R.id.img);
        // Voltar para a tela principal
        btnVoltar = findViewById(R.id.btnVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTelaPrincipal();
            }
        });

        // Alterar, Proximo, Anterior
        btnAnterior = findViewById(R.id.btnAnterior);
        btnAnterior.setEnabled(false); // inicialmente desabilitado
        btnProximo = findViewById(R.id.btnProximo);
        btnAlterar = findViewById(R.id.btnAlterar);

        // Inicializar lista
        iUsuarios = -1;
        if ( usuarios.size() == 0 ){

            // Não há usuários
            txtNome.setText("");
            txtProfissao.setText("");
            txtIdade.setText("");
            imgSexo.setImageDrawable(null);
            btnAlterar.setEnabled(false);
            btnProximo.setEnabled(false);

        } else {

            // INicializar campos
            iUsuarios = 0;
            inicializarCardUsuario();

            // Se for igual a 1, não há próximo
            if (usuarios.size() == 1 )
                btnProximo.setEnabled(false);

        }

        // Próximo
        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iUsuarios++;
                // Verifica se está no final da lista
                if ( iUsuarios == usuarios.size()-1 ){
                    btnAnterior.setEnabled(true);
                    btnProximo.setEnabled(false);
                } else {
                    btnAnterior.setEnabled(true);
                    btnProximo.setEnabled(true);
                }
                inicializarCardUsuario();
            }
        });

        // Anterior
        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iUsuarios--;
                if ( iUsuarios <= 0 ){
                    btnAnterior.setEnabled(false);
                    btnProximo.setEnabled(true);
                } else {
                    btnAnterior.setEnabled(true);
                    btnProximo.setEnabled(true);
                }
                inicializarCardUsuario();
            }
        });

        // Alterar
        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTelaAlterarUsuario();
            }
        });

    }

    void onCreateTelaAlterarUsuario(){
        setContentView(R.layout.alterar_usuario);
        this.setTitle(R.string.tela_alterar);

        // Conect buttons and edits
        edtNome = findViewById(R.id.inputNameAlterar);
        edtIdade = findViewById(R.id.inputIdadeAlterar);
        edtProfissao = findViewById(R.id.inputProfissaoAlterar);

        // Get radio group
        rgSexo = findViewById(R.id.rgSexoAlterar);
        rbMasculino = findViewById(R.id.rbMasculinoAlterar);
        rbFeminino = findViewById(R.id.rbFemininoAlterar);

        // Set edits
        Usuario user = usuarios.get(iUsuarios);
        edtNome.setText(user.getNome());
        edtIdade.setText(Integer.toString(user.getIdade()));
        edtProfissao.setText(user.getProfissao());
        if ( user.getSexo() == Sexo.MASCULINO ){
            rbMasculino.setChecked(true);
        } else {
            rbFeminino.setChecked(true);
        }

        // Voltar para a tela principal
        btnCancelar = findViewById(R.id.btnCancelarAlterar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pedir confirmação
                newMaterialAlertDialogBuilder("Tem certeza que deseja sair?",
                        "",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Clean values and change activity
                                edtNome.getText().clear();
                                edtProfissao.getText().clear();
                                edtIdade.getText().clear();
                                rbMasculino.setChecked(false);
                                rbFeminino.setChecked(false);
                                // Return no main view
                                onCreateTelaListarUsuarios();
                            }
                        });

            }
        });

        // Salvar alterações e adicionar novo usuário
        btnSalvar = findViewById(R.id.btnAlterar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Verifica se há campos não preenchidos
                if ( edtNome.getText().toString().isEmpty() || edtProfissao.getText().toString().isEmpty()
                        || edtIdade.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get infos
                final String nome = edtNome.getText().toString();
                final String profissao = edtProfissao.getText().toString();
                final String sexo = (
                        rbMasculino.isChecked() ? rbMasculino.getText().toString() : rbFeminino.getText().toString() );
                final int idade = Integer.parseInt(edtIdade.getText().toString());

                // Mensagem
                final String message =  "Nome: " + nome + "\n" +
                        "Profissao: " + profissao + "\n" +
                        "Sexo: " + sexo + "\n" +
                        "Idade: " + idade;

                // Pedir confirmação
                newMaterialAlertDialogBuilder("Tem certeza que deseja salvar?",
                        message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // Altera os dados do usuário
                                usuarios.get(iUsuarios).setNome(nome);
                                usuarios.get(iUsuarios).setIdade(idade);
                                usuarios.get(iUsuarios).setProfissao(profissao);
                                usuarios.get(iUsuarios).setSexo(
                                        rbMasculino.isChecked() ? Sexo.MASCULINO : Sexo.FEMININO  );

                                // Limpar todos os campos
                                edtNome.getText().clear();
                                edtProfissao.getText().clear();
                                edtIdade.getText().clear();

                                // Volta para o view anterior
                                onCreateTelaListarUsuarios();

                            }
                        });
            }
        });

    }



    public void onCreateTelaCadastroAnimais(){
        setContentView(R.layout.tela_cadastro_animal);
        this.setTitle(R.string.tela_cadastro_animal);

        // Conect buttons and edits
        edtNomeAnimal = findViewById(R.id.inputNameAnimal);
        edtIdadeAnimal = findViewById(R.id.inputIdadeanimal);
        edtEspecieAnimal = findViewById(R.id.inputEspecieAnimal);
        rgSexoAnimal = findViewById(R.id.rgSexoAnimal);
        rbMasculinoAnimal = findViewById(R.id.rbMasculinoAnimal);
        rbFemininoAnimal = findViewById(R.id.rbFemininoAnimal);
        rbMasculinoAnimal.setChecked(true);

        // Voltar para a tela principal
        btnCancelarAnimal = findViewById(R.id.btnCancelarAnimal);
        btnCancelarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pedir confirmação
                newMaterialAlertDialogBuilder("Tem certeza que deseja sair?",
                        "",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Clean values and change activity
                                edtNomeAnimal.getText().clear();
                                edtEspecieAnimal.getText().clear();
                                edtIdadeAnimal.getText().clear();
                                rbMasculinoAnimal.setChecked(false);
                                rbFemininoAnimal.setChecked(false);
                                // Return no main view
                                onCreateTelaPrincipal();
                            }
                        });

            }
        });

        // Salvar alterações e adicionar novo usuário
        btnSalvarAnimal = findViewById(R.id.btnAdicionarAnimal);
        btnSalvarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Verifica se há campos não preenchidos
                if ( edtNomeAnimal.getText().toString().isEmpty() || edtEspecieAnimal.getText().toString().isEmpty()
                        || edtIdadeAnimal.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get infos
                final String nome = edtNomeAnimal.getText().toString();
                final String especie = edtEspecieAnimal.getText().toString();
                final String sexo = (
                        rbMasculinoAnimal.isChecked() ? rbMasculinoAnimal.getText().toString() : rbFemininoAnimal.getText().toString() );
                final int idade = Integer.parseInt(edtIdadeAnimal.getText().toString());

                // Mensagem
                final String message =  "Nome: " + nome + "\n" +
                        "especie: " + especie + "\n" +
                        "Sexo: " + sexo + "\n" +
                        "Idade: " + idade;

                // Pedir confirmação
                newMaterialAlertDialogBuilder("Tem certeza que deseja salvar?",
                        message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Cria um novo usuário
                                Animal animal = new Animal(nome, especie, idade,
                                        rbMasculinoAnimal.isChecked() ? Sexo.MASCULINO : Sexo.FEMININO);
                                // Adiciona usuário na lista
                                animais.add(animal);

                                // Limpar todos os campos
                                edtNomeAnimal.getText().clear();
                                edtEspecieAnimal.getText().clear();
                                edtIdadeAnimal.getText().clear();
                                rbMasculinoAnimal.setChecked(true);
                                rbFemininoAnimal.setChecked(false);
                            }
                        });
            }
        });
    }

    public void onCreateTelaListarAnimais(){
        setContentView(R.layout.listar_animais);
        this.setTitle(R.string.tela_lista_animais);


        // Inicializar bjetos
        txtNomeAnimal = findViewById(R.id.txtNomeAnimal);
        txtEspecieAnimal = findViewById(R.id.txtEspecieAnimal);
        txtIdadeAnimal = findViewById(R.id.txtIdadeAnimal);
        imgSexoAnimal = findViewById(R.id.imgAnimal);
        // Voltar para a tela principal
        btnVoltarAnimal = findViewById(R.id.btnVoltarAnimal);
        btnVoltarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTelaPrincipal();
            }
        });

        // Alterar, Proximo, Anterior
        btnAnteriorAnimal = findViewById(R.id.btnAnteriorAnimal);
        btnAnteriorAnimal.setEnabled(false); // inicialmente desabilitado
        btnProximoAnimal = findViewById(R.id.btnProximoAnimal);
        btnAlterarAnimal = findViewById(R.id.btnAlterarAnimal);

        // Inicializar lista
        iAnimais = -1;
        if ( animais.size() == 0 ){

            // Não há usuários
            txtNomeAnimal.setText("");
            txtEspecieAnimal.setText("");
            txtIdadeAnimal.setText("");
            imgSexoAnimal.setImageDrawable(null);
            btnAlterarAnimal.setEnabled(false);
            btnProximoAnimal.setEnabled(false);

        } else {

            // INicializar campos
            iAnimais = 0;
            inicializarCardAnimal();

            // Se for igual a 1, não há próximo
            if (animais.size() == 1 )
                btnProximoAnimal.setEnabled(false);

        }

        // Próximo
        btnProximoAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iAnimais++;
                // Verifica se está no final da lista
                if ( iAnimais == animais.size()-1 ){
                    btnAnteriorAnimal.setEnabled(true);
                    btnProximoAnimal.setEnabled(false);
                } else {
                    btnAnteriorAnimal.setEnabled(true);
                    btnProximoAnimal.setEnabled(true);
                }
                inicializarCardAnimal();
            }
        });

        // Anterior
        btnAnteriorAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iAnimais--;
                if ( iAnimais <= 0 ){
                    btnAnteriorAnimal.setEnabled(false);
                    btnProximoAnimal.setEnabled(true);
                } else {
                    btnAnteriorAnimal.setEnabled(true);
                    btnProximoAnimal.setEnabled(true);
                }
                inicializarCardAnimal();
            }
        });

        // Alterar
        btnAlterarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTelaAlterarAnimal();
            }
        });

    }

    public void onCreateTelaAlterarAnimal(){
        setContentView(R.layout.alterar_animal);
        this.setTitle(R.string.tela_alterar_animal);

        // Conect buttons and edits
        edtNomeAnimal = findViewById(R.id.inputNameAlterarAnimal);
        edtIdadeAnimal = findViewById(R.id.inputIdadeAlterarAnimal);
        edtEspecieAnimal = findViewById(R.id.inputEspecieAlterarAnimal);

        // Get radio group
        rgSexoAnimal = findViewById(R.id.rgSexoAlterarAnimal);
        rbMasculinoAnimal = findViewById(R.id.rbMasculinoAlterarAnimal);
        rbFemininoAnimal = findViewById(R.id.rbFemininoAlterarAnimal);

        // Set edits
        Animal animal = animais.get(iAnimais);
        edtNomeAnimal.setText(animal.getNome());
        edtIdadeAnimal.setText(Integer.toString(animal.getIdade()));
        edtEspecieAnimal.setText(animal.getEspecie());
        if ( animal.getSexo() == Sexo.MASCULINO ){
            rbMasculinoAnimal.setChecked(true);
        } else {
            rbFemininoAnimal.setChecked(true);
        }

        // Voltar para a tela principal
        btnCancelarAnimal = findViewById(R.id.btnCancelarAlterarAnimal);
        btnCancelarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pedir confirmação
                newMaterialAlertDialogBuilder("Tem certeza que deseja sair?",
                        "",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Clean values and change activity
                                edtNomeAnimal.getText().clear();
                                edtEspecieAnimal.getText().clear();
                                edtIdadeAnimal.getText().clear();
                                rbMasculinoAnimal.setChecked(false);
                                rbFemininoAnimal.setChecked(false);
                                // Return no main view
                                onCreateTelaListarAnimais();
                            }
                        });

            }
        });

        // Salvar alterações e adicionar novo usuário
        btnSalvarAnimal = findViewById(R.id.btnAlterarAnimal);
        btnSalvarAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Verifica se há campos não preenchidos
                if ( edtNomeAnimal.getText().toString().isEmpty() || edtEspecieAnimal.getText().toString().isEmpty()
                        || edtIdadeAnimal.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get infos
                final String nome = edtNomeAnimal.getText().toString();
                final String especie = edtEspecieAnimal.getText().toString();
                final String sexo = (
                        rbMasculinoAnimal.isChecked() ? rbMasculinoAnimal.getText().toString() : rbFemininoAnimal.getText().toString() );
                final int idade = Integer.parseInt(edtIdadeAnimal.getText().toString());

                // Mensagem
                final String message =  "Nome: " + nome + "\n" +
                        "Espécie: " + especie + "\n" +
                        "Sexo: " + sexo + "\n" +
                        "Idade: " + idade;

                // Pedir confirmação
                newMaterialAlertDialogBuilder("Tem certeza que deseja salvar?",
                        message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // Altera os dados do usuário
                                animais.get(iAnimais).setNome(nome);
                                animais.get(iAnimais).setIdade(idade);
                                animais.get(iAnimais).setEspecie(especie);
                                animais.get(iAnimais).setSexo(
                                        rbMasculinoAnimal.isChecked() ? Sexo.MASCULINO : Sexo.FEMININO  );

                                // Limpar todos os campos
                                edtNomeAnimal.getText().clear();
                                edtEspecieAnimal.getText().clear();
                                edtIdadeAnimal.getText().clear();

                                // Volta para o view anterior
                                onCreateTelaListarAnimais();

                            }
                        });
            }
        });

    }

    public void onCreateTelaCadastro(){
        setContentView(R.layout.cadastrar_usuario);
        this.setTitle(R.string.tela_cadastro);

        // Conect buttons and edits
        edtNome = findViewById(R.id.inputName);
        edtIdade = findViewById(R.id.inputIdade);
        edtProfissao = findViewById(R.id.inputProfissao);
        rgSexo = findViewById(R.id.rgSexo);
        rbMasculino = findViewById(R.id.rbMasculino);
        rbFeminino = findViewById(R.id.rbFeminino);
        rbMasculino.setChecked(true);

        // Voltar para a tela principal
        btnCancelar = findViewById(R.id.btnCancelarAnimal);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pedir confirmação
                newMaterialAlertDialogBuilder("Tem certeza que deseja sair?",
                        "",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Clean values and change activity
                                edtNome.getText().clear();
                                edtProfissao.getText().clear();
                                edtIdade.getText().clear();
                                rbMasculino.setChecked(false);
                                rbFeminino.setChecked(false);
                                // Return no main view
                                onCreateTelaPrincipal();
                            }
                        });

            }
        });

        // Salvar alterações e adicionar novo usuário
        btnSalvar = findViewById(R.id.btnAdicionar);
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Verifica se há campos não preenchidos
                if ( edtNome.getText().toString().isEmpty() || edtProfissao.getText().toString().isEmpty()
                        || edtIdade.getText().toString().isEmpty() ){
                    Toast.makeText(MainActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Get infos
                final String nome = edtNome.getText().toString();
                final String profissao = edtProfissao.getText().toString();
                final String sexo = (
                        rbMasculino.isChecked() ? rbMasculino.getText().toString() : rbFeminino.getText().toString() );
                final int idade = Integer.parseInt(edtIdade.getText().toString());

                // Mensagem
                final String message =  "Nome: " + nome + "\n" +
                        "Profissao: " + profissao + "\n" +
                        "Sexo: " + sexo + "\n" +
                        "Idade: " + idade;

                // Pedir confirmação
                newMaterialAlertDialogBuilder("Tem certeza que deseja salvar?",
                        message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Cria um novo usuário
                                Usuario user = new Usuario(nome, profissao, idade,
                                        rbMasculino.isChecked() ? Sexo.MASCULINO : Sexo.FEMININO);
                                // Adiciona usuário na lista
                                usuarios.add(user);

                                // Limpar todos os campos
                                edtNome.getText().clear();
                                edtProfissao.getText().clear();
                                edtIdade.getText().clear();
                                rbMasculino.setChecked(true);
                                rbFeminino.setChecked(false);
                            }
                        });
            }
        });

    }

    public void onCreateTelaPrincipal(){
        setContentView(R.layout.activity_main);
        this.setTitle(R.string.app_name);

        // Conect buttons
        btnTelaCadastro = findViewById(R.id.btnTelaCadastro);
        btnListar = findViewById(R.id.btnListar);

        // ir para tela de cadastro
        btnTelaCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTelaCadastro();
            }
        });

        // ir para tela para listar usuários
        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTelaListarUsuarios();
            }
        });

        // Conect buttons
        btnTelaCadastroAnimais = findViewById(R.id.btnTelaCadastroAnimal);
        btnListarAnimais = findViewById(R.id.btnListarAnimais);

        // ir para tela de cadastro
        btnTelaCadastroAnimais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTelaCadastroAnimais();
            }
        });

        // ir para tela para listar usuários
        btnListarAnimais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCreateTelaListarAnimais();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Array Lists
        usuarios = new ArrayList<Usuario>();
        animais = new ArrayList<Animal>();

        onCreateTelaPrincipal();

    }
}