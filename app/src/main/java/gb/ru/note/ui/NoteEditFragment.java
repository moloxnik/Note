package gb.ru.note.ui;

import static gb.ru.note.ui.StartActivity.LOG_TAG;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import gb.ru.note.R;
import gb.ru.note.domain.NoteEntity;


public class NoteEditFragment extends Fragment {

    public static final String IN_DATA_KEY = "IN_DATA_KEY";
    static final String BACK_DATA_KEY = "BACK_DATA_KEY";
    static final String IN_NOTE_ENTITY_KEY = "IN_NOTE_ENTITY_KEY";
    static final String NOTE_ENTITY_KEY = "NOTE_ENTITY_KEY";
    static final String TYPE_OPERATION_KEY = "TYPE_OPERATION_KEY";
    static final int CREATE_NOTE = 1;
    static final int DELETE_NOTE = 2;
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button saveButton;
    private Button delButton;
    private int id;
    private NoteEntity noteEntityTemp;
    private FragmentManager fragmentManager;
    private static boolean firstStart = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "NoteEditFragment  onCreateView.   inflater = " + inflater + "   savedInstanceState = " + savedInstanceState);
        return inflater.inflate(R.layout.fragment_note_edit, container, false);
    }

    private boolean checkOrientation() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "NoteEditFragment  onViewCreated.   notesRepo = ");
        fragmentManager = requireActivity().getSupportFragmentManager();
        initNoteEdit();
        fillNoteData();
        super.onViewCreated(view, savedInstanceState);
    }

    private void fillNoteData() {
        Log.d(LOG_TAG, "NoteEditFragment  fillNoteData");
        fragmentManager.setFragmentResultListener(IN_DATA_KEY, this, (requestKey, result) -> {
            if (result.getParcelable(IN_NOTE_ENTITY_KEY) != null) {
                noteEntityTemp = result.getParcelable(IN_NOTE_ENTITY_KEY);
                id = noteEntityTemp.getId();
                titleEditText.setText(noteEntityTemp.getTitle());
                descriptionEditText.setText(noteEntityTemp.getDetail());
            }
        });
    }


    private void onClickSaveButton(View view) {
        Log.d(LOG_TAG, "NoteEditFragment  onClickSaveButton");
        if (!titleEditText.getText().toString().equals("")) {
            NoteEntity noteEntity = new NoteEntity(id,
                    titleEditText.getText().toString(),
                    descriptionEditText.getText().toString()
            );

            Bundle result = new Bundle();
            result.putParcelable(NOTE_ENTITY_KEY, noteEntity);
            result.putInt(TYPE_OPERATION_KEY, CREATE_NOTE);
            fragmentManager.setFragmentResult(BACK_DATA_KEY, result);
        }
        closeNoteEditFragment();

    }

    private void onClickDeleteButton(View view) {
        Log.d(LOG_TAG, "NoteEditFragment  onClickDeleteButton");
        Bundle result = new Bundle();
        if (noteEntityTemp != null) {
            NoteEntity noteEntity = new NoteEntity(id,
                    titleEditText.getText().toString(),
                    descriptionEditText.getText().toString()
            );
            result.putParcelable(NOTE_ENTITY_KEY, noteEntity);
        } else {
            result.putParcelable(NOTE_ENTITY_KEY, null);
        }

        result.putInt(TYPE_OPERATION_KEY, DELETE_NOTE);
        fragmentManager.setFragmentResult(BACK_DATA_KEY, result);
        closeNoteEditFragment();
    }

    private void closeNoteEditFragment() {
        fragmentManager.popBackStack();
    }

    private void initNoteEdit() {
        Log.d(LOG_TAG, "NoteEditFragment  initNoteEdit");
        titleEditText = getView().findViewById(R.id.title_edit_text);
        descriptionEditText = getView().findViewById(R.id.detail_edit_text);
        saveButton = getView().findViewById(R.id.save_button);
        delButton = getView().findViewById(R.id.del_button);
        saveButton.setOnClickListener(this::onClickSaveButton);
        delButton.setOnClickListener(this::onClickDeleteButton);
    }
}