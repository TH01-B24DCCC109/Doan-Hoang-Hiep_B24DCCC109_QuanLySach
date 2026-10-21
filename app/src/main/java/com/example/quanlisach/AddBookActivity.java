package com.example.quanlisach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlisach.models.Book;

public class AddBookActivity extends AppCompatActivity {
    private EditText etTitle, etAuthor, etNote;
    private Spinner spCategory;
    private RadioGroup rgStatus;
    private RatingBar ratingBar;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        etTitle = findViewById(R.id.etTitle);
        etAuthor = findViewById(R.id.etAuthor);
        etNote = findViewById(R.id.etNote);
        spCategory = findViewById(R.id.spCategory);
        rgStatus = findViewById(R.id.rgStatus);
        ratingBar = findViewById(R.id.ratingBar);
        btnSave = findViewById(R.id.btnSave);

        String[] categories = {"Văn học", "Khoa học", "Công nghệ", "Kỹ năng sống", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spCategory.setAdapter(adapter);

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String author = etAuthor.getText().toString().trim();

            if (title.isEmpty()) {
                etTitle.setError("Tên sách không được để trống");
                return;
            }
            if (author.isEmpty()) {
                etAuthor.setError("Tác giả không được để trống");
                return;
            }

            String category = spCategory.getSelectedItem().toString();

            int selectedRadioId = rgStatus.getCheckedRadioButtonId();
            RadioButton selectedRadio = findViewById(selectedRadioId);
            String status = selectedRadio != null ? selectedRadio.getText().toString() : "Chưa đọc";

            float rating = ratingBar.getRating();
            String note = etNote.getText().toString().trim();

            Book newBook = new Book(title, author, category, status, rating, note);

            Intent resultIntent = new Intent();
            resultIntent.putExtra("new_book", newBook);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}