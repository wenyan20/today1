package com.example.today;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.today.Note;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {
    private Context context;
    private int layoutResourceId;
    private List<Note> notesList;

    public NoteAdapter(Context context, int layoutResourceId, List<Note> notesList) {
        super(context, layoutResourceId, notesList);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.notesList = notesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) row.findViewById(R.id.noteTitle);
            holder.contentTextView = (TextView) row.findViewById(R.id.noteContent);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Note note = notesList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getContent());

        return row;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView contentTextView;
    }
}
