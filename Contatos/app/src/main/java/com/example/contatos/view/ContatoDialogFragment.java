package com.example.contatos.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.contatos.R;
import com.example.contatos.model.Contato;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

public class ContatoDialogFragment extends DialogFragment {

    private Contato contato = null;

    public ContatoDialogFragment(Contato contato) {

        this.contato  = contato;

    }

    public ContatoDialogFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // https://stackoverflow.com/questions/7622031/dialogfragment-and-back-button

        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
                // On backpress, do your stuff here.
                dialogDismiss();
            }
        };
    }

    public void dialogDismiss(){
        new MaterialAlertDialogBuilder(getContext())
                .setMessage("As alterações serão descartadas")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light);

    }

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contato_dialog, container, false);

        // Get views
        ImageButton back = view.findViewById(R.id.dialog_close);
        MaterialButton btnSalvar = view.findViewById(R.id.btnSalvar);
        final TextInputEditText nome = view.findViewById(R.id.edtNome);
        final TextInputEditText email = view.findViewById(R.id.edtEmail);
        final TextInputEditText telefone = view.findViewById(R.id.edtTelefone);
        final TextInputEditText empresa = view.findViewById(R.id.edtEmpresa);
        TextView pageTitle = view.findViewById(R.id.txtPageTitle);

        // Se está editando um contato, seta valores padrões para os campos
        if ( contato != null ){
            pageTitle.setText("Editar contato");
            nome.setText(contato.getNome());
            email.setText(contato.getEmail());
            empresa.setText(contato.getEmpresa());
            telefone.setText(contato.getTelefone());
        }

        // Salvar contato
        btnSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Verifica se o nome está nulo
                if ( nome.getText().toString().isEmpty() ){
                    Toast.makeText(getContext(), "Por favor, preencha um nome.", Toast.LENGTH_SHORT).show();
                    nome.requestFocus();
                    return;
                }

                // Atualizar novo contato
                if (contato != null ){
                    if ( onUpdateClick != null ){
                        contato.setEmail(email.getText().toString());
                        contato.setNome(nome.getText().toString());
                        contato.setEmpresa(empresa.getText().toString());
                        contato.setTelefone(telefone.getText().toString());
                        onUpdateClick.onUpdateClick(contato);
                    }
                }
                // Criar novo contato
                else {
                    if ( onAddClick != null ){
                        contato = new Contato(
                                nome.getText().toString(),
                                telefone.getText().toString(),
                                email.getText().toString(),
                                empresa.getText().toString()
                        );
                        onAddClick.onAddClick(contato);
                    }
                }

                // Sair
                dismiss();

            }
        });

        // Dismiss
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //new AlertDialog.Builder()

                /// Pedir confirmação
                dialogDismiss();
            }
        });

        return view;

    }

    private OnAddClick onAddClick = null;
    private OnUpdateClick onUpdateClick = null;

    public void setOnAddClick(OnAddClick OnAddClick) {
        this.onAddClick = OnAddClick;
    }

    public void setOnUpdateClick(OnUpdateClick onUpdateClick) {
        this.onUpdateClick = onUpdateClick;
    }

    public interface OnAddClick{
        void onAddClick(Contato contato);
    }

    public interface OnUpdateClick {
        void onUpdateClick(Contato contato);
    }
}
