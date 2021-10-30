package gb.ru.note.ui;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.PopupMenu;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import gb.ru.note.listeners.OnItemPopUpMenuClickListener;
import gb.ru.note.R;
import gb.ru.note.domain.NoteEntity;


public class NoteVh extends RecyclerView.ViewHolder {
    private final TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    private final TextView detailTextView = itemView.findViewById(R.id.detail_text_view);
    private final Button menuItemNoteWidgetButton = itemView.findViewById(R.id.item_note_popupmenu_button);
    private OnItemPopUpMenuClickListener onItemPopUpMenuClickListener;

    private NoteEntity note;

    public NoteVh (@NonNull ViewGroup parent, NotesAdapter.OnItemClickListener clickListener){
    super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
        itemView.setOnClickListener(view -> clickListener.onItemClick(note));
    }

    public void bind(NoteEntity note) {
        this.note = note;
        titleTextView.setText(note.getTitle());
        detailTextView.setText(note.getDetail());

        menuItemNoteWidgetButton.setOnClickListener((View view) -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.note_popup_menu);
            popupMenu.setOnMenuItemClickListener((MenuItem menuItem) -> {
                onItemPopUpMenuClickListener.onClickItemPopUpMenu(menuItem, note);
                return true;
            });
            popupMenu.show();
        });
    }


}
