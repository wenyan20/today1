package com.example.today;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Adapter;
import android.widget.ArrayAdapter;
import androidx.core.graphics.Insets;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import java.util.List;
import java.util.ArrayList;
import android.widget.ListView;
import android.database.Cursor;
import com.example.today.Note;
import com.example.today.DatabaseHelper;
import androidx.appcompat.widget.SearchView;
import java.text.SimpleDateFormat;
import java.util.Date;




public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private ListView listViewNotes;
    private DatabaseHelper myDb;

    private ListView notesListView;
    private NoteAdapter noteAdapter;
    private List<com.example.today.Note> notesList;

    public class Note {
        private int id;
        private String title;
        private String content;

        public Note(int id, String title, String content) {
            this.id = id;
            this.title = title;
            this.content = content;
        }

        // 省略 getter 和 setter 方法
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        listViewNotes = findViewById(R.id.ListView);

        // 查询所有笔记的标题
        List<String> noteTitles = myDb.getAllNoteTitles();

        // 创建适配器，将笔记标题绑定到ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, noteTitles);
        listViewNotes.setAdapter(adapter);

        Button btnAddNote = findViewById(R.id.btnAddNote);
        btnAddNote.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                // 当点击“添加”按钮时，启动添加笔记页面
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 当活动恢复时刷新列表
        updateListView();
    }
    private void updateListView() {
        // 查询所有笔记的标题
        List<String> editTitle = myDb.getAllNoteTitles();

        // 更新适配器数据
        adapter.clear();
        adapter.addAll(editTitle);
        adapter.notifyDataSetChanged();
    }

}



