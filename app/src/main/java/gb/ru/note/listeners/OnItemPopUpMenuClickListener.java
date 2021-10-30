package gb.ru.note.listeners;

import android.view.MenuItem;

import gb.ru.note.domain.NoteEntity;

public interface OnItemPopUpMenuClickListener {

    void onClickItemPopUpMenu(MenuItem menuItem, NoteEntity noteEntity);

}
