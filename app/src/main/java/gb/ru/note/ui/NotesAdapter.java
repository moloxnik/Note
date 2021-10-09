package gb.ru.note.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import gb.ru.note.R;
import gb.ru.note.domain.NoteEntity;

public class NotesAdapter extends RecyclerView.Adapter<NoteVh> {
    private List<NoteEntity> data = new ArrayList<>();
    private OnItemClickListener clickListener= null;

    public void setData(List<NoteEntity> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NoteVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteVh(parent, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVh holder, int position) {
        NoteEntity note = getItem(position);
        holder.bind(note);

    }

    private NoteEntity getItem(int position){
        return data.get(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        clickListener = listener;
    }
    static interface OnItemClickListener{
        void onItemClick(NoteEntity item);
    }
}
