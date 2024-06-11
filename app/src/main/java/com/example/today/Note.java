package com.example.today;

public class Note {
    private int id;
    private String title;
    private String content;
    private String date;

    public Note(int id,String date ,String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getDate() {
        return date;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public Note() {
        // 可以在这里初始化默认值
    }



    // 省略 getter 和 setter 方法
}
