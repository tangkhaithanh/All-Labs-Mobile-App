package com.example.sqllite;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler databaseHandler;
    ListView listView;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView =(ListView) findViewById(R.id.listView1);
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, arrayList, R.layout.item_note); // Khởi tạo adapter
        listView.setAdapter(adapter);

        // gọi hàm databaseSQLite:
        InitDatabaseSQLite();
        createDatabaseSQLite();
        // createDatabaseSQLite();
        databaseSQLite();
    }
    private void createDatabaseSQLite() {
        // thêm dữ liệu vào bảng
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 3')" );
        databaseHandler.QueryData("INSERT INTO Notes VALUES(null, 'Ví dụ SQLite 4')" );
    }
    private void InitDatabaseSQLite() {
        // khởi tạo database
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);

        // tạo bảng Notes
        databaseHandler.QueryData("CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))");
    }
    private void databaseSQLite(){
        Cursor cursor =databaseHandler.GetData("SELECT * FROM Notes");
        while(cursor.moveToNext())
        {
            String name=cursor.getString(1);
            int id=cursor.getInt(0);
            arrayList.add(new NotesModel(id,name));
            //Toast.makeText(this,name,Toast.LENGTH_SHORT).show();
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAddNotes) {
            // Gọi hộp thoại thêm ghi chú
            showAddNoteDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Hiển thị hộp thoại để người dùng thêm notes:
    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm Ghi Chú");

        // Tạo EditText để nhập ghi chú
        final EditText input = new EditText(this);
        input.setHint("Nhập nội dung ghi chú...");
        builder.setView(input);

        // Nút "Thêm"
        builder.setPositiveButton("Thêm", (dialog, which) -> {
            String noteText = input.getText().toString().trim();
            if (!noteText.isEmpty()) {
                databaseHandler.QueryData("INSERT INTO Notes VALUES(null, '" + noteText + "')");
                databaseSQLite(); // Cập nhật danh sách
                Toast.makeText(this, "Đã thêm ghi chú!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ghi chú không được để trống!", Toast.LENGTH_SHORT).show();
            }
        });

        // Nút "Hủy"
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}