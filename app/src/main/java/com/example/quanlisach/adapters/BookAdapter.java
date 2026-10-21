package com.example.quanlisach.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.quanlisach.R;
import com.example.quanlisach.models.Book;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;

    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        }

        Book book = getItem(position);

        TextView tvTitle = convertView.findViewById(R.id.tvBookTitle);
        TextView tvAuthor = convertView.findViewById(R.id.tvBookAuthor);
        TextView tvCategory = convertView.findViewById(R.id.tvCategory);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);
        RatingBar ratingBar = convertView.findViewById(R.id.itemRatingBar);

        if (book != null) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText("Tác giả: " + book.getAuthor());
            tvCategory.setText(book.getCategory());
            tvStatus.setText(book.getStatus());
            ratingBar.setRating(book.getRating());
        }

        return convertView;
    }
}