package com.example.today;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import com.example.today.Note;
import com.example.today.DatabaseHelper;
import androidx.appcompat.widget.SearchView;
import java.text.SimpleDateFormat;
import java.util.Date;


public class NoteDetailActivity extends AppCompatActivity{
    private DatabaseHelper myDb;
    private TextView textViewNoteTitle;
    private TextView textViewNoteContent;
    EditText editTitle,editDate, editContent;
    Button btnSave;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        myDb = new DatabaseHelper(this);
        editTitle = findViewById(R.id.editTitle);
        editDate = findViewById(R.id.editDate);
        editContent = findViewById(R.id.editContent);
        btnSave = findViewById(R.id.btnSaveNote);


        // 从Intent中获取传递的笔记标题
        int noteTitle = getIntent().getIntExtra("noteTitle", -1);
        String titleText = editTitle.getText().toString();
        Note note = myDb.getAllNotesByTitle(titleText);

        // 显示笔记的详细信息
        if (note != null) {
            textViewNoteTitle.setText(note.getTitle());
            textViewNoteContent.setText(note.getContent());
        } else {
            Toast.makeText(this, "没有找到相关笔记", Toast.LENGTH_SHORT).show();
            // 处理笔记不存在的情况
        };

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

        Button btnDelete = findViewById(R.id.btnCancelNote);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
    private void onDeleteClick() {

        int editTitle = getIntent().getIntExtra("note_title", -1);

        // 调用数据库帮助类的删除方法
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        boolean isDeleted = dbHelper.deleteNote(editTitle);
        if (editTitle != -1) {
            dbHelper.deleteNote(editTitle);
            // 删除后可能需要更新UI或返回到前一个界面
            finish();
        }
    }

    private void saveNote() {
        EditText etNoteTitle = findViewById(R.id.editTitle);
        EditText etNoteContent = findViewById(R.id.editContent);

        String title = etNoteTitle.getText().toString();
        String content = etNoteContent.getText().toString();


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
