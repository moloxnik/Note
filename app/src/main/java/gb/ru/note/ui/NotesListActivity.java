package gb.ru.note.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import gb.ru.note.R;
import gb.ru.note.domain.NoteEntity;
import gb.ru.note.domain.NotesRepo;
import gb.ru.note.impl.NotesRepoImpl;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
//import androidx.navigation.ui.AppBarConfiguration;

import gb.ru.note.databinding.ActivityMainBinding;

public class NotesListActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;

    private NotesRepo notesRepo = new NotesRepoImpl();
    private NotesAdapter adapter = new NotesAdapter();

    private ActivityMainBinding binding;

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
        getMenuInflater().inflate(R.menu.notes_list_menu, menu);
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
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String name = data.getStringExtra("plot");
        //todo что то что поставит текст
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this::onItemClick);

        adapter.setData(notesRepo.getNotes());
    }

    private void onItemClick(NoteEntity item) {
        openNoteScreen(item);
    }


   // @Override
   // protected void onCreate(Bundle savedInstanceState) {
     //   super.onCreate(savedInstanceState);

       // binding = ActivityMainBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        //setSupportActionBar(binding.appBarMain.toolbar);
        //DrawerLayout drawer = binding.drawerLayout;
        //NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
     //   mAppBarConfiguration = new AppBarConfiguration.Builder(
       //         R.id.nav_home, R.id.nav_about_the_app, R.id.nav_settings)
      //          .setOpenableLayout(drawer)
         //       .build();
       // NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
       // NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);
    //}


    private void fillRepoByTestValues() {
        notesRepo.createNote(new NoteEntity("Note 1", "english, do you speak it?"));
        notesRepo.createNote(new NoteEntity("Note 2", "Заметка2"));
        notesRepo.createNote(new NoteEntity("Note 3", "новый год"));
        notesRepo.createNote(new NoteEntity("Note 4", "Расписание"));
    }

}