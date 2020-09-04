package com.example.contatos.view;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.OnDragInitiatedListener;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contatos.R;
import com.example.contatos.model.Contato;
import com.example.contatos.model.ContatoDAO;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    private RecyclerView listContatos;
    private ContatosAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ActionMode actionMode = null;
    private ActionMode.Callback actionModeController;
    private FloatingActionButton btnAdicionar;
    private ContatoDAO contatoDAO;
    private ArrayList<Contato> contatos;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get contato DAO
        contatoDAO = new ContatoDAO(this);

        // Get recycle view
        listContatos = findViewById(R.id.listContatos);

        // Floating button
        btnAdicionar = findViewById(R.id.btnAdd);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                ContatoDialogFragment dialog = new ContatoDialogFragment();
                dialog.show(getTransaction(), "Tag");
                dialog.setOnAddClick(new ContatoDialogFragment.OnAddClick() {
                    @Override
                    public void onAddClick(Contato contato) {
                        addContato(contato);
                    }
                });
            }
        });

        // To changes in content do not change the layout size
        listContatos.setHasFixedSize(true);

        // Linear layout manager
        listContatos.setLayoutManager(new LinearLayoutManager(this));

        // Obtém a lista de contatos
        contatos = (ArrayList<Contato>) contatoDAO.getAll();

        // set adapter
        adapter = new ContatosAdapter(this, contatos);
        listContatos.setAdapter(adapter);

        /////// https://www.thiengo.com.br/selectiontracker-para-selecao-de-itens-no-recyclerview-android
        // https://proandroiddev.com/a-guide-to-recyclerview-selection-3ed9f2381504
        // https://enoent.fr/posts/recyclerview-basics/
        // https://developer.android.com/guide/topics/ui/layout/recyclerview#select
        // https://heartbeat.fritz.ai/implementing-a-multi-select-recylerview-with-a-dynamic-actionbar-in-android-e36f16a47a1b
        // https://developer.android.com/reference/kotlin/androidx/recyclerview/selection/SelectionTracker.Builder

        // SelectionTracker provides support for managing a selection of items in a RecyclerView instance.
        final SelectionTracker tracker = new SelectionTracker.Builder<>(
                "my-selection",
                listContatos,
                // Provides selection library access to stable selection keys identifying items presented by a RecyclerView instance.
                new ContatoKeyProvider(ItemKeyProvider.SCOPE_MAPPED, adapter),
                // The Selection library calls getItemDetails(MotionEvent) when it needs access to
                // information about the area and/or ItemDetailsLookup.ItemDetails under a MotionEvent
                new ContatoLookup(listContatos),
                // Strategy for storing keys in saved state
                StorageStrategy.createLongStorage())
                // Register an OnDragInitiatedListener to be notified when user intent to perform
                // drag and drop operations on an item or items has been detected
                .withOnDragInitiatedListener(new OnDragInitiatedListener() {
                    @Override
                    public boolean onDragInitiated(@NonNull MotionEvent e) {
                        return true;
                    }
                })
                //  to control when items can be selected or unselected
                .withSelectionPredicate(new SelectionTracker.SelectionPredicate<Long>() {
                    @Override
                    public boolean canSetStateForKey(@NonNull Long key, boolean nextState) {
                        return true;
                    }

                    @Override
                    public boolean canSetStateAtPosition(int position, boolean nextState) {
                        return true;
                    }

                    @Override
                    public boolean canSelectMultiple() {
                        return true;
                    }
                })
                .build();
        adapter.setTracker(tracker);

        // Set click listener somente nenhum item estiver selecionado
        adapter.setItemClickListener(new ContatosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Contato contato) {

                if ( !tracker.hasSelection() ){

                    final ContatoViewDialogFragment dialog = ContatoViewDialogFragment.newInstance(contato);

                    dialog.setOnExcluirClick(new ContatoViewDialogFragment.OnExcluirClick() {
                        @Override
                        public void onExcluirClick(final Contato contato) {

                            new MaterialAlertDialogBuilder(MainActivity.this)
                                    .setMessage("Tem certeza que deseja excluir?")
                                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if ( excluirContato(contato.getId()) ){
                                                dialog.dismiss();
                                            }
                                        }
                                    })
                                    .setNegativeButton("Cancelar", null)
                                    .show();
                        }
                    });

                    // Abrir dialog quando apertar em editar contato
                    dialog.setOnEditClick(new ContatoViewDialogFragment.OnEditClick() {

                        public void onEditClick(Contato contato) {
                            ContatoDialogFragment contatoDialogFragment = new ContatoDialogFragment(contato);


                            contatoDialogFragment.setOnUpdateClick(new ContatoDialogFragment.OnUpdateClick() {
                                @Override
                                public void onUpdateClick(Contato contato) {
                                    if ( atualizaContato(contato) ){
                                        dialog.setContato(contato);
                                    }
                                }
                            });

                            contatoDialogFragment.show(getTransaction(), "Tag");



                        }
                    });

                    dialog.show(getTransaction(), "Tag");
                }
            }
        });

        // Represents a contextual mode of the user interface. Action modes can be used to provide
        // alternative interaction modes and replace parts of the normal UI until finished.
        // Examples of good action modes include text selection and contextual actions.
        // In this case is a contextual action
        // https://androidkt.com/recyclerview-selection-28-0-0/
        actionModeController = new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                // inflate action bar menu
                getMenuInflater().inflate(R.menu.contextual_action_bar, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(final ActionMode actionMode, MenuItem menuItem) {

                switch (menuItem.getItemId()){
                    case R.id.delete:
                        if ( tracker.hasSelection() ){
                            // Pergunta se deseja excluir
                            new MaterialAlertDialogBuilder(MainActivity.this)
                                    .setMessage("Tem certeza que deseja excluir?")
                                    // Caso positivo, exclui todos os itens selecionados
                                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Iterator it = tracker.getSelection().iterator();
                                            while ( it.hasNext() ){
                                                excluirContato((Long)it.next());
                                            }
                                            actionMode.finish();
                                        }
                                    })
                                    .setNegativeButton("Cancelar", null)
                                    .show();
                        }
                        break;
                    case R.id.select_all:
                           tracker.setItemsSelected(adapter.getIds(), true);
                        break;

                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                tracker.clearSelection();

            }
        };

        // add observer
        tracker.addObserver(new SelectionTracker.SelectionObserver() {

            @Override
            public void onSelectionChanged() {
                super.onSelectionChanged();

                // https://androidkt.com/recyclerview-selection-28-0-0/
                // Cria action mode se houver algum item selecionado
                // Finaliza caso contrário
                if (tracker.hasSelection() && actionMode == null) {
                    actionMode = MainActivity.this.startSupportActionMode(actionModeController);
                } else if (!tracker.hasSelection() && actionMode != null) {
                    actionMode.finish();
                    actionMode = null;
                }

                // Se houver alguma seleção, alteara titulo do actionbar com o numero de selecionados
                if ( tracker.hasSelection() ){
                    if ( tracker.getSelection().size() == 1 ){
                        actionMode.setTitle( tracker.getSelection().size() + " selecionado");
                    } else {
                        actionMode.setTitle( tracker.getSelection().size() + " selecionados");
                    }
                }
            }

        });

        // Top bar menu
        // https://developer.android.com/training/appbar
        topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if ( menuItem.getItemId() == R.id.select_all ){
                    tracker.setItemsSelected(adapter.getIds(), true);
                }
                return true;
            }
        });

    }

    /////////////////// DAO Acess

    public void atualizaLista(){
        contatos = (ArrayList<Contato>) contatoDAO.getAll();
        adapter.setContatos(contatos);
        //listContatos.setAdapter(adapter);
    }

    public boolean addContato(Contato contato){
        if ( contatoDAO.add(contato) ){
            atualizaLista();
            return true;
        } else {
            Toast.makeText(this, "Não foi possível criar contato.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean atualizaContato(Contato contato){
        if ( contatoDAO.update(contato) ){
            atualizaLista();
            return true;
        } else {
            Toast.makeText(this, "Não foi possível atualizar contato.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public FragmentTransaction getTransaction(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        return transaction;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean excluirContato(Long id){

        if ( contatoDAO.delete(id) ){
            atualizaLista();
            return true;
        } else {
            Toast.makeText(this, "Não foi possível remover contato.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}