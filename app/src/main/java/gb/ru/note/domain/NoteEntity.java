package gb.ru.note.domain;

import androidx.annotation.Nullable;

public class NoteEntity {

    @Nullable
    private  Integer id;
    private String title;
    private String detail;

    public NoteEntity(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }


    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }
}
