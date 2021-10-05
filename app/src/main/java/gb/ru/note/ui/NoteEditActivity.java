package gb.ru.note.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import gb.ru.note.R;
import gb.ru.note.domain.NoteEntity;

public class NoteEditActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText detailEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        titleEditText = findViewById(R.id.title_edit_text);
        detailEditText = findViewById(R.id.detail_edit_text);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(v -> {
            NoteEntity noteEntity = new NoteEntity(
                    titleEditText.getText().toString(),
                    titleEditText.getText().toString()
            );
            //todo
        });

    }
}
