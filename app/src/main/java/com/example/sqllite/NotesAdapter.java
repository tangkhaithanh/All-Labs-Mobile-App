package com.example.sqllite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.List;

public class NotesAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<NotesModel> noteList;

    public NotesAdapter(Context context, List<NotesModel> noteList, int layout) {
        this.context = context;
        this.noteList = noteList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder
    {
        TextView textViewNote;
        ImageView imageViewEdit;
        ImageView imageViewDelete;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // gọi viewHolder
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder.textViewNote = (TextView) convertView.findViewById(R.id.textViewNameNote);
            viewHolder.imageViewDelete = (ImageView) convertView.findViewById(R.id.imageViewDelete);
            viewHolder.imageViewEdit = (ImageView) convertView.findViewById(R.id.imageViewEdit);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // lấy giá trị
        NotesModel notes = noteList.get(position);
        viewHolder.textViewNote.setText(notes.getNameNote());

        // Xử lý khi nhấn vào nút Chỉnh sửa (Edit)
        viewHolder.imageViewEdit.setOnClickListener(v -> {
            showEditNoteDialog(notes);
        });

        viewHolder.imageViewDelete.setOnClickListener(v -> {
            showDeleteConfirmationDialog(notes);
        });


        return convertView;
    }

    private void showEditNoteDialog(NotesModel note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Chỉnh Sửa Ghi Chú");

        // Tạo EditText và điền nội dung cũ vào
        final EditText input = new EditText(context);
        input.setText(note.getNameNote());
        builder.setView(input);

        // Nút "Lưu"
        builder.setPositiveButton("Lưu", (dialog, which) -> {
            String newText = input.getText().toString().trim();
            if (!newText.isEmpty()) {
                // Cập nhật dữ liệu trong SQLite
                DatabaseHandler databaseHandler = new DatabaseHandler(context, "notes.sqlite", null, 1);
                databaseHandler.QueryData("UPDATE Notes SET NameNotes = '" + newText + "' WHERE Id = " + note.getIdNote());

                // Cập nhật dữ liệu trong danh sách
                note.setNameNote(newText);
                notifyDataSetChanged();
                Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Nội dung không được để trống!", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút "Hủy"
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void showDeleteConfirmationDialog(NotesModel note) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Xác Nhận Xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa ghi chú này?");

        // Nút "Xóa"
        builder.setPositiveButton("Xóa", (dialog, which) -> {
            // Xóa dữ liệu trong SQLite
            DatabaseHandler databaseHandler = new DatabaseHandler(context, "notes.sqlite", null, 1);
            databaseHandler.QueryData("DELETE FROM Notes WHERE Id = " + note.getIdNote());

            // Xóa khỏi danh sách và cập nhật ListView
            noteList.remove(note);
            notifyDataSetChanged();
            Toast.makeText(context, "Đã xóa ghi chú!", Toast.LENGTH_SHORT).show();
        });

        // Nút "Hủy"
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
