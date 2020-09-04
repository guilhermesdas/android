package com.example.contatos.view;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contatos.R;
import com.example.contatos.model.Contato;

import java.util.ArrayList;

//
public class ContatosAdapter extends RecyclerView.Adapter<ContatosAdapter.MyViewHolder>  {

    private Context context;
    private ArrayList<Contato> contatos;
    private SelectionTracker tracker;
    private OnItemClickListener itemClickListener = null;

    public ArrayList<Contato> getContatos() {
        return contatos;
    }

    public ArrayList<Long> getIds() {
        ArrayList<Long> ids = new ArrayList<>();
        for ( Contato c : contatos ){
            ids.add(c.getId());
        }
        return ids;
    }

    public Contato getItem(int position){
        return contatos.get(position);
    }

    public int getPosition(Long id){
        for ( int i = 0; i < contatos.size(); i++ ){
            if ( contatos.get(i).getId() == id ){
                return i;
            }
        }
        return RecyclerView.NO_POSITION;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface  OnItemClickListener{
        public void onItemClick(Contato contato);
    }

    public void setTracker(SelectionTracker tracker) {
        this.tracker = tracker;
    }

    // Constructor
    public ContatosAdapter(Context context, ArrayList<Contato> contatos) {
        super();
        this.context = context;
        this.contatos = contatos;
    }

    public void setContatos(ArrayList<Contato> contatos) {
        this.contatos = new ArrayList<>();
        this.contatos = contatos;
        notifyDataSetChanged();
    }

    // Create new views
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contatos_celula, parent, false);

        ContatosView contatosView = new ContatosView();
        contatosView.txtContato = v.findViewById(R.id.txtContato);
        contatosView.layout = v.findViewById(R.id.celulaLayout);
        v.setTag(contatosView);

        MyViewHolder vh = new MyViewHolder(v);


        return vh;
    }

    // Replace the contentx of a view
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Contato contato = contatos.get(position);
        holder.bind(contato, position, itemClickListener);
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public TextView txtContatos;
        public ConstraintLayout layout;
        private ContatosDetail contatosDetail = null;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContatos = itemView.findViewById(R.id.txtContato);
            layout = itemView.findViewById(R.id.celulaLayout);
        }


        public void bind(final Contato contato, int pos, final OnItemClickListener itemClickListener) {

            // https://stackoverflow.com/questions/10171009/android-how-to-propagate-click-event-to-linearlayout-childs-and-change-their-dr
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if ( itemClickListener != null ){
                        itemClickListener.onItemClick(contato);
                    }
                }
            });
            txtContatos.setText(contato.getNome());
            contatosDetail = new ContatosDetail(pos, contato);
            if ( tracker.isSelected(contatosDetail.getSelectionKey()) ){
                layout.setBackgroundColor(context.getResources().getColor(R.color.colorItemSelected, context.getTheme()));
            } else {
                // https://stackoverflow.com/questions/26963365/how-to-create-a-click-animation-for-items-in-a-recycleview
                // https://stackoverflow.com/questions/37987732/programmatically-set-selectableitembackground-on-android-view
                TypedValue outValue = new TypedValue();
                context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
                layout.setBackgroundResource(outValue.resourceId);
            }
        }

        public ItemDetailsLookup.ItemDetails getItemDetails() {
            return contatosDetail;
        }
    }

}

// https://stackoverflow.com/questions/56726970/how-to-use-recyclerview-selection-by-setting-the-key-as-a-string
class ContatoKeyProvider extends ItemKeyProvider<Long>{

    ContatosAdapter adapter;

    /**
     * Creates a new provider with the given scope.
     *
     * @param scope Scope can't be changed at runtime.
     */
    protected ContatoKeyProvider(int scope, ContatosAdapter adapter) {
        super(scope);
        this.adapter = adapter;
    }

    @Nullable
    @Override
    public Long getKey(int position) {
        return adapter.getItem(position).getId();
    }

    @Override
    public int getPosition(@NonNull Long key) {
        return adapter.getPosition(key);
    }
}

class ContatoLookup extends ItemDetailsLookup {

    private final RecyclerView recyclerView;

    public ContatoLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetails getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof ContatosAdapter.MyViewHolder) {
                return ((ContatosAdapter.MyViewHolder) viewHolder).getItemDetails();
            }
        }

        return null;
    }
}

class ContatosDetail extends ItemDetailsLookup.ItemDetails<Long> {

    private final int adapterPosition;
    private final Contato selectionKey;

    public ContatosDetail(int adapterPosition, Contato selectionKey) {
        this.adapterPosition = adapterPosition;
        this.selectionKey = selectionKey;
    }

    @Override
    public int getPosition() {
        return adapterPosition;
    }

    @Nullable
    @Override
    public Long getSelectionKey() {
        if ( selectionKey != null ){
            return selectionKey.getId();
        }
        return new Long(0);
    }
}