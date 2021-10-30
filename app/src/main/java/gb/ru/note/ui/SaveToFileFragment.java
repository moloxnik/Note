package gb.ru.note.ui;

import static gb.ru.note.ui.StartActivity.LOG_TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import gb.ru.note.R;

public class SaveToFileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG, "SaveToFileFragment   onCreateView.   savedInstanceState = " + savedInstanceState);
        return inflater.inflate(R.layout.fragment_save_file , container, false);
    }
}
