package com.example.today;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import android.text.TextUtils;
import com.example.today.DatabaseHelper;
import com.example.today.Note;

import kotlin.Suppress;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NOTES = "notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_DATE = "date";
    private static final String COL_2 = "TITLE";
    private static final String COL_3 = "CONTENT";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES +
                "(" +
                COLUMN_DATE + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_CONTENT + " TEXT" +
                ")";
        db.execSQL(CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES );
        onCreate(db);
    }

    public void addNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, note.getTitle());
        values.put(COLUMN_DATE, note.getDate());
        values.put(COLUMN_CONTENT, note.getContent());

        db.insert(TABLE_NOTES, null, values);
        db.close();
    }
    public boolean insertNote(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);

        contentValues.put("content", content);
        long result = db.insert(TABLE_NOTES, null, contentValues);
        return result != -1;
    }
    public boolean deleteNote(int title) {
        SQLiteDatabase db = this.getWritableDatabase();
        boolean result = db.delete(TABLE_NOTES, "TITLE = ?", new String[]{String.valueOf(title)}) > 0;
        db.close();
        return result;
    }

    public int updateData(Note note) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title", note.getTitle());
        values.put("content", note.getContent());
        values.put("date", note.getDate());


        return db.update(TABLE_NOTES, values, "id like ?", new String[]{note.getContent()});
    }



    public List<Note> getAllNotes() {
        List<Note> notesList = new ArrayList<>();

        String SELECT_ALL_NOTES = "SELECT * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(SELECT_ALL_NOTES, null);


        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(0, "Date", "Title", "Content");
                note.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                note.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(COLUMN_CONTENT)));


                notesList.add(note);


            }
                while (cursor.moveToNext()) ;
            }

        return notesList;
    }

    public List<Note> getAllNotesByTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return getAllNotes();
        }

        SQLiteDatabase db = getWritableDatabase();
        List<Note> noteList = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NOTES, null, "title like ?", new String[]{"%"+title+"%"}, null, null, null);

        if (cursor != null) {

            while (cursor.moveToNext()) {
                String title2 = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String date = cursor.getString(cursor.getColumnIndex("date"));

                Note note = new Note();
                note.setDate(date);
                note.setTitle(title);
                note.setContent(content);


                noteList.add(note);
            }
            cursor.close();
        }
        return noteList;
    }

    public List<String> getAllNoteTitles() {
        List<String> titles = new ArrayList<>();
        String selectQuery = "SELECT TITLE FROM " + TABLE_NOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                titles.add(cursor.getString(0)); // 获取标题列的值
            } while (cursor.moveToNext());
        }

        cursor.close();
        return titles;
    }


    public Cursor searchData(String keyword) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NOTES + " WHERE " +
                COL_2 + " LIKE '%" + keyword + "%' OR " +
                COL_3 + " LIKE '%" + keyword + "%'";
        return db.rawQuery(query, null);
    }

}