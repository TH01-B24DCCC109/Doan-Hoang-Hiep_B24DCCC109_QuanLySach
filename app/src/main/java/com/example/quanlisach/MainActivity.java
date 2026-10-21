package com.example.quanlisach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlisach.adapters.BookAdapter;
import com.example.quanlisach.models.Book;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD = 100;
    public static final int REQUEST_CODE_DETAIL = 101;

    private TextView tvTotalBooks, tvReadBooks, tvUnreadBooks;
    private ListView lvBooks;
    private Button btnAddBook;

    private ArrayList<Book> bookList;
    private BookAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalBooks = findViewById(R.id.tvTotalBooks);
        tvReadBooks = findViewById(R.id.tvReadBooks);
        tvUnreadBooks = findViewById(R.id.tvUnreadBooks);
        lvBooks = findViewById(R.id.lvBooks);
        btnAddBook = findViewById(R.id.btnAddBook);

        initData();

        adapter = new BookAdapter(this, bookList);
        lvBooks.setAdapter(adapter);

        updateStatistics();

        btnAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        });

        lvBooks.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, BookDetailActivity.class);
            intent.putExtra("selected_book", bookList.get(position));
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_CODE_DETAIL);
        });
    }

    private void initData() {
        bookList = new ArrayList<>();
        bookList.add(new Book("Lập trình Android cơ bản", "Nguyễn Văn A", "Công nghệ", "Đang đọc", 4.0f, "Sách dành cho người mới bắt đầu"));
        bookList.add(new Book("Đắc nhân tâm", "Dale Carnegie", "Kỹ năng sống", "Đã đọc", 5.0f, "Sách rất hay nên đọc"));
        bookList.add(new Book("Dế Mèn phiêu lưu ký", "Tô Hoài", "Văn học", "Chưa đọc", 0.0f, "Truyện thiếu nhi"));
    }

    private void updateStatistics() {
        int total = bookList.size();
        int read = 0;
        int unread = 0;

        for (Book b : bookList) {
            if ("Đã đọc".equalsIgnoreCase(b.getStatus())) {
                read++;
            } else {
                unread++;
            }
        }

        tvTotalBooks.setText(String.valueOf(total));
        tvReadBooks.setText(String.valueOf(read));
        tvUnreadBooks.setText(String.valueOf(unread));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == REQUEST_CODE_ADD) {
                Book newBook = (Book) data.getSerializableExtra("new_book");
                if (newBook != null) {
                    bookList.add(newBook);
                }
            } else if (requestCode == REQUEST_CODE_DETAIL) {
                boolean isDeleted = data.getBooleanExtra("is_deleted", false);
                int position = data.getIntExtra("position", -1);

                if (position != -1) {
                    if (isDeleted) {
                        bookList.remove(position);
                    } else {
                        Book updatedBook = (Book) data.getSerializableExtra("updated_book");
                        if (updatedBook != null) {
                            bookList.set(position, updatedBook);
                        }
                    }
                }
            }
            adapter.notifyDataSetChanged();
            updateStatistics();
        }
    }
}