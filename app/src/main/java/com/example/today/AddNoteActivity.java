package com.example.today;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class AddNoteActivity extends AppCompatActivity {


    DatabaseHelper myDb;
    EditText editTitle,editDate, editContent;
    Button btnSave;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);


        myDb = new DatabaseHelper(this);
        editTitle = findViewById(R.id.editTitle);
        editDate = findViewById(R.id.editDate);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSaveNote);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                // 返回主页或其他操作
                onBackPressed();
            }
        });

        Button btnCancel = findViewById(R.id.btnCancelNote);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 当点击“取消”按钮时，结束当前Activity并返回到前一个Activity
                finish();
            }
        });
    }

    private void saveNote() {
        EditText etNoteTitle = findViewById(R.id.editTitle);
        EditText etNoteContent = findViewById(R.id.editContent);

        String title = etNoteTitle.getText().toString();
        String content = etNoteContent.getText().toString();

        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);

        if (!title.isEmpty() && !content.isEmpty()) {
            // 调用DatabaseHelper的方法来保存笔记
            boolean isInserted = myDb.insertNote(title, content);

            if (isInserted) {
                Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show();
                editTitle.setText("");
                editContent.setText("");
                finish(); // 完成后关闭活动
            } else {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
        }
        } else {
            Toast.makeText(this, "Title and content cannot be empty", Toast.LENGTH_SHORT).show();
        }

        // 跳转到前一个界面
        onBackPressed();
    }

}
