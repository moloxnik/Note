package gb.ru.note.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import gb.ru.note.R;


public class NoteVh extends RecyclerView.ViewHolder {
    public NoteVh(@NonNull View itemView) {
        super(itemView);
    }

    public TextView titleTextView = itemView.findViewById(R.id.title_text_view);
    public TextView detailTextView = itemView.findViewById(R.id.detail_text_view);
}
