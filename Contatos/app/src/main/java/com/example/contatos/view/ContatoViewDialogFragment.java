package com.example.contatos.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.contatos.R;
import com.example.contatos.model.Contato;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

// https://developer.android.com/reference/androidx/fragment/app/DialogFragment
// https://developer.android.com/guide/components/fragments
// https://github.com/adrianseraspi12/Android-Tutorials/tree/master/Fullscreen%20Dialog
// A Fragment represents a behavior or a portion of user interface in a FragmentActivity
//  You can think of a fragment as a modular section of an activity,
//  which has its own lifecycle, receives its own input events, and which you can add or
//  remove while the activity is running
public class ContatoViewDialogFragment extends DialogFragment {

    ImageButton close;
    TextView nome ;
    TextView email;
    TextView telefone;
    TextView empresa;
    ExtendedFloatingActionButton btnEditarContato;
    MaterialButton btnExcluir;

    private Contato contato;

    public void setContato(Contato contato) {
        this.contato = contato;
        updateUI();
    }

    static ContatoViewDialogFragment newInstance(Contato contato) {
        return new ContatoViewDialogFragment(contato);
    }

    private ContatoViewDialogFragment(Contato contato) {
        this.contato = contato;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // https://stackoverflow.com/a/10869308
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_Material_Light);

    }

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contato_view_dialog, container, false);

        // Get views
        close = view.findViewById(R.id.dialog_back);
        nome = view.findViewById(R.id.txtNome);
        email = view.findViewById(R.id.txtEmail);
        telefone = view.findViewById(R.id.txtTelefone);
        empresa = view.findViewById(R.id.txtEmpresa);
        btnEditarContato = view.findViewById(R.id.btnEditarContato);
        btnExcluir = view.findViewById(R.id.btnExcluir);

        // Excluir contato
        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( onExcluirClick != null ){
                    onExcluirClick.onExcluirClick(contato);
                }
            }
        });

        btnEditarContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( onEditClick != null ){
                    onEditClick.onEditClick(contato);
                }
            }
        });

        // Seta os valores
        updateUI();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });

        return view;

    }

    private void updateUI(){
        nome.setText(contato.getNome());
        telefone.setText(contato.getTelefone());
        email.setText(contato.getEmail());
        empresa.setText(contato.getEmpresa());
    }

    private OnEditClick onEditClick = null;
    private  OnExcluirClick onExcluirClick = null;

    public void setOnEditClick(OnEditClick onEditClick) {
        this.onEditClick = onEditClick;
    }

    public void setOnExcluirClick(OnExcluirClick onExcluirClick) {
        this.onExcluirClick = onExcluirClick;
    }

    public interface OnEditClick {
        void onEditClick(Contato contato);
    }

    public interface OnExcluirClick {
        void onExcluirClick(Contato contato);
    }

}
