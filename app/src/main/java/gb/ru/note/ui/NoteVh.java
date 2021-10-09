package gb.ru.note.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gb.ru.note.R;
import gb.ru.note.domain.NoteEntity;


public class NoteVh extends RecyclerView.ViewHolder {
    private final TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    private final TextView detailTextView = itemView.findViewById(R.id.detail_text_view);

    private NoteEntity note;

    public NoteVh (@NonNull ViewGroup parent, NotesAdapter.OnItemClickListener clickListener){
    super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        itemView.setOnClickListener(view -> clickListener.onItemClick(note));
    }

public void bind (NoteEntity note){
        this.note = note;
    titleTextView.setText(note.getTitle());
    detailTextView.setText(note.getDetail());
}


}
