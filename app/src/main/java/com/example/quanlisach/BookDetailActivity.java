package com.example.quanlisach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlisach.models.Book;

public class BookDetailActivity extends AppCompatActivity {
    private TextView tvDetailTitle, tvDetailAuthorCategory, tvDetailStatus, tvDetailNote;
    private RatingBar detailRatingBar;
    private Button btnMarkAsRead, btnBack, btnDelete;

    private Book currentBook;
    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        tvDetailTitle = findViewById(R.id.tvDetailTitle);
        tvDetailAuthorCategory = findViewById(R.id.tvDetailAuthorCategory);
        tvDetailStatus = findViewById(R.id.tvDetailStatus);
        tvDetailNote = findViewById(R.id.tvDetailNote);
        detailRatingBar = findViewById(R.id.detailRatingBar);
        btnMarkAsRead = findViewById(R.id.btnMarkAsRead);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        if (intent != null) {
            currentBook = (Book) intent.getSerializableExtra("selected_book");
            position = intent.getIntExtra("position", -1);
        }

        if (currentBook != null) {
            displayBookDetails();
        }

        detailRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser && currentBook != null) {
                currentBook.setRating(rating);
            }
        });

        btnMarkAsRead.setOnClickListener(v -> {
            if (currentBook != null) {
                currentBook.setStatus("Đã đọc");
                displayBookDetails();
                Toast.makeText(this, "Đã đổi trạng thái thành 'Đã đọc'", Toast.LENGTH_SHORT).show();
            }
        });

        btnBack.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("updated_book", currentBook);
            resultIntent.putExtra("position", position);
            resultIntent.putExtra("is_deleted", false);
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        btnDelete.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("position", position);
            resultIntent.putExtra("is_deleted", true);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private void displayBookDetails() {
        tvDetailTitle.setText(currentBook.getTitle());
        tvDetailAuthorCategory.setText(currentBook.getAuthor() + "  •  " + currentBook.getCategory());
        tvDetailStatus.setText(currentBook.getStatus());
        tvDetailNote.setText(currentBook.getNote().isEmpty() ? "(Không có ghi chú)" : currentBook.getNote());
        detailRatingBar.setRating(currentBook.getRating());

        if ("Đã đọc".equalsIgnoreCase(currentBook.getStatus())) {
            btnMarkAsRead.setEnabled(false);
            btnMarkAsRead.setAlpha(0.5f);
        } else {
            btnMarkAsRead.setEnabled(true);
            btnMarkAsRead.setAlpha(1.0f);
        }
    }
}