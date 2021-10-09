package gb.ru.note.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import gb.ru.note.R;
import gb.ru.note.domain.NoteEntity;
import gb.ru.note.domain.NotesRepo;
import gb.ru.note.impl.NotesRepoImpl;

public class NotesListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private NotesRepo notesRepo = new NotesRepoImpl();
    private NotesAdapter adapter = new NotesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        fillRepoByTestValues();

        initRecycler();
        initToolbar();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes_list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.new_note_menu) {
            openNoteScreen(null);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openNoteScreen(@Nullable NoteEntity item) {
        Intent intent = new Intent(this, NoteEditActivity.class);

        startActivity(intent);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecycler(){
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this::onItemClick);

        adapter.setData(notesRepo.getNotes());
    }

    private void onItemClick(NoteEntity item){
        openNoteScreen(item);
    }


private void fillRepoByTestValues() {
    notesRepo.createNote(new NoteEntity("Note 1", "english, do you speak it?"));
    notesRepo.createNote(new NoteEntity("Note 2", "Заметка2"));
    notesRepo.createNote(new NoteEntity("Note 3", "новый год"));
    notesRepo.createNote(new NoteEntity("Note 4", "Расписание"));
    }

}