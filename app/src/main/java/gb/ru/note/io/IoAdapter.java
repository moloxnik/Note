package gb.ru.note.io;

import static gb.ru.note.ui.NotesListFragment.notesRepo;

import gb.ru.note.domain.NoteEntity;


public class IoAdapter {


    public String saveToFile(int id, String title, String description) {
        return id + "\t|\t" + title + "\t|\t" + description + "\t|\r\n";
    }

    public static void readFromFile(String res) {
        int firstPosition = 0;
        int id;
        String title;
        String detail;
        while (firstPosition < res.length()) {
            id = Integer.valueOf(res.substring(firstPosition, res.indexOf("\t|\t", firstPosition)));
            firstPosition = res.indexOf("\t|\t", firstPosition) + 3;
            title = res.substring(firstPosition, res.indexOf("\t|\t", firstPosition));
            firstPosition = res.indexOf("\t|\t", firstPosition) + 3;
            detail = res.substring(firstPosition, res.indexOf("\t|\r\n", firstPosition));
            firstPosition = res.indexOf("\t|\r\n", firstPosition) + 4;
            notesRepo.createNote(new NoteEntity(id, title, detail));
        }
    }
}