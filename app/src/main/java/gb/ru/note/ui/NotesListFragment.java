package gb.ru.note.ui;

import static gb.ru.note.ui.StartActivity.LOG_TAG;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import gb.ru.note.R;
import gb.ru.note.domain.NoteEntity;
import gb.ru.note.domain.NotesRepo;
import gb.ru.note.impl.NotesRepoImpl;
import gb.ru.note.io.IoAdapter;
import gb.ru.note.io.SaveFile;

public class NotesListFragment extends Fragment{

    private FragmentManager fragmentManager;

    public static final NotesRepo notesRepo = new NotesRepoImpl();
    private final NotesAdapter adapter = new NotesAdapter();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        if (notesRepo.getNotes().isEmpty()) {
            new IoAdapter().readFromFile(SaveFile.readFromFile(requireActivity().getApplicationContext()));
        }
        Log.d(LOG_TAG, "onCreate.   savedInstanceState = " + savedInstanceState
                + "      notesRepo.getNotes().isEmpty() = " + notesRepo.getNotes().isEmpty());
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreateView.   savedInstanceState = " + savedInstanceState);
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume.");
        fragmentManager = requireActivity().getSupportFragmentManager();
        initToolbar();
        initRecycler();

        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onViewCreated.   savedInstanceState = " + savedInstanceState);
        fragmentManager = requireActivity().getSupportFragmentManager();
        initToolbar();
        initRecycler();
        dataFromNoteEditFragment();
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notes_list_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
        int container;
        if (!checkOrientation()) {
            container = R.id.fragment_container_main;
        } else {
            container = R.id.fragment_container_note_edit;
        }
        Log.d(LOG_TAG, "NotesListFragment  openNoteScreen." + container);
        Bundle result = new Bundle();
        result.putParcelable(NoteEditFragment.IN_NOTE_ENTITY_KEY, item);
        fragmentManager.setFragmentResult(NoteEditFragment.IN_DATA_KEY, result);
        fragmentManager
                .beginTransaction()
                .replace(container, new NoteEditFragment())
                .addToBackStack(null)
                .commit();
    }

    private boolean checkOrientation() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private void dataFromNoteEditFragment() {
        Log.d(LOG_TAG, "NotesListFragment  dataFromNoteEditFragment.");
        fragmentManager.setFragmentResultListener(NoteEditFragment.BACK_DATA_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                NoteEntity noteEntity = result.getParcelable(NoteEditFragment.NOTE_ENTITY_KEY);
                int operationType = result.getInt(NoteEditFragment.TYPE_OPERATION_KEY);
                if (operationType == 1)
                    saveNoteEntity(noteEntity);
                else if (operationType == 2)
                    deleteNoteEntity(noteEntity);
            }
        });
    }


    private void saveNoteEntity(NoteEntity noteEntity) {
        if (noteEntity.getId() == 0) {
            notesRepo.createNote(noteEntity);
            SaveFile.writeToFile(new IoAdapter().saveToFile(noteEntity.getId(), noteEntity.getTitle(), noteEntity.getDetail()), getActivity().getApplicationContext(), true);
        } else {
            notesRepo.updateNote(noteEntity.getId(), noteEntity);
            SaveFile.writeToFile(SaveFile.updateFile(), requireActivity().getApplicationContext(), false);
        }
    }

    private void deleteNoteEntity(NoteEntity noteEntity) {
        notesRepo.deleteNote(noteEntity.getId());
        SaveFile.writeToFile(SaveFile.updateFile(), requireActivity().getApplicationContext(), false);
    }



    private void initRecycler() {
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(NotesListFragment.this::onItemClick);
        adapter.setOnItemClickListenerPopUpMenu(this::onPopupButtonClick);
        adapter.setData(notesRepo.getNotes());
        Log.d(LOG_TAG, "initRecycler.   notesRepo = " + notesRepo);
    }


    public void onPopupButtonClick(MenuItem menuItem, NoteEntity noteEntity) {
        switch (menuItem.getItemId()) {
            case R.id.popup_menu_item_delete:
                deleteNoteEntity(noteEntity);
                initRecycler();
                break;
            case R.id.popup_menu_item_duplicate:
                Toast.makeText(getContext(), "Дублирование заметки", Toast.LENGTH_SHORT).show();
                break;
            case R.id.popup_menu_item_fil_repo:
                fillRepoByTestValues();
                Toast.makeText(getContext(), "Данные записаны в файл. Перезапустите програму", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void initToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

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
