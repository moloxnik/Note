package gb.ru.note.impl;
//имплиметация
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import gb.ru.note.domain.NoteEntity;
import gb.ru.note.domain.NotesRepo;

public class NotesRepoImpl implements NotesRepo {
    private final ArrayList<NoteEntity> cache = new ArrayList<>();
    private int counter = 0;

    @Override
    public List<NoteEntity> getNotes() {
        return new ArrayList<>(cache);
    }

    @Nullable
    @Override
    public Integer createNote(NoteEntity note) {
        int newId = ++counter;
        note.setId(newId);
        cache.add(note);
        return newId;
    }

    @Override
    public boolean deleteNote(int id) {
        for (int i = 0; i < cache.size(); i++) {
            if (cache.get(i).getId() == id){
                cache.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateNote(int id, NoteEntity note) {
        cache.set(id-1, note);
        return true;
    }
}
