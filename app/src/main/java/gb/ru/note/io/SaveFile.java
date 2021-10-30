package gb.ru.note.io;

import static gb.ru.note.ui.NotesListFragment.notesRepo;

import android.content.Context;
import android.util.Log;

import gb.ru.note.domain.NoteEntity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SaveFile {

    static final String LOG_TAG = "@@@";
    static final String FILENAME = "file.txt";


    public static boolean writeToFile(String data, Context ctx, Boolean add) {
        FileOutputStream fou;
        try {
            if (add == true) {
                fou = ctx.openFileOutput(FILENAME, Context.MODE_APPEND);
            } else {
                fou = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            }
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fou, StandardCharsets.UTF_8);
//            Log.d(LOG_TAG, "writeToFile " + FILENAME + "  " + data);
            outputStreamWriter.write(data);
            outputStreamWriter.close();
            return true;
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
            return false;
        }
    }

    public static String updateFile() {
        String temp = "";
        List<NoteEntity> data = notesRepo.getNotes();
        for (NoteEntity noteEntity : data) {
            temp += new IoAdapter().saveToFile(noteEntity.getId(), noteEntity.getTitle(), noteEntity.getDetail());
        }
        Log.d(LOG_TAG, temp);
        return temp;
    }


    public static String readFromFile(Context ctx) {

        String ret = "";
        try {
            InputStream inputStream;
            inputStream = ctx.openFileInput(FILENAME);
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString + "\r\n");
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }

        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
}