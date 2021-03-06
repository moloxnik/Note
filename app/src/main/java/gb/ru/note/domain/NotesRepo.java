package gb.ru.note.domain;
//хранилище заметок
import androidx.annotation.Nullable;

import java.util.List;

public interface NotesRepo {
    List<NoteEntity> getNotes();//получить список заметок
    @Nullable
    Integer createNote(NoteEntity note);
    boolean deleteNote(int id);
    boolean updateNote(int id, NoteEntity note);
}
