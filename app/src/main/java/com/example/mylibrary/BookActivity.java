package com.example.mylibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {
    public static final String BOOK_ID_KEY = "bookID";
    private ImageView imgBook;
    private TextView txtName, txtAuthor, txtPage, txtDesc;
    private Button btnCurrently, btnAlready, btnWant, btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        initView();

        //TODO: Get data from recycle view in here
//        Book book = new Book(1,"Vài giây nữa thôi","Reddy, FreakD","https://i1.sndcdn.com/artworks-iw5qWosMzzvw2sIe-Saz8Mw-t500x500.jpg",
//                "long Desc","short Desc",1035);

        Intent intent = getIntent();
        if(null != intent){
            int bookID = intent.getIntExtra(BOOK_ID_KEY,-1);
            if(bookID != -1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookID);
                if(incomingBook != null){
                    setData(incomingBook);

                    handleAlready(incomingBook);
                    handleWant(incomingBook);
                    handleCurrently(incomingBook);
                    handleFavorite(incomingBook);
                }
            }
        }


    }

    private void handleFavorite(Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBook();
        boolean isExist = false;
        for (Book b : favoriteBooks) {
            if(b.getId() == book.getId()){
                isExist = true;
            }
        }
        if(isExist) {
            btnFavorite.setEnabled(false);
        } else {
            btnFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToFavorite(book)){
                        Toast.makeText(BookActivity.this, "book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,FavoriteBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurrently(final Book book) {
        ArrayList<Book> currentlyBooks = Utils.getInstance(this).getCurrentlyBook();
        boolean isExist = false;
        for (Book b : currentlyBooks) {
            if(b.getId() == book.getId()){
                isExist = true;
            }
        }
        if(isExist) {
            btnCurrently.setEnabled(false);
        } else {
            btnCurrently.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToCurrently(book)){
                        Toast.makeText(BookActivity.this, "book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,CurrentlyBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleWant(final Book book) {
        ArrayList<Book> wantBooks = Utils.getInstance(this).getWantBook();
        boolean isExist = false;
        for (Book b : wantBooks) {
            if(b.getId() == book.getId()){
                isExist = true;
            }
        }
        if(isExist) {
            btnWant.setEnabled(false);
        } else {
            btnWant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToWant(book)){
                        Toast.makeText(BookActivity.this, "book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,WantToReadBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Enable and disable already button
     * add book to already list
     * @param book
     */
    private void handleAlready(Book book) {
        ArrayList<Book> alreadyBooks = Utils.getInstance(this).getAlreadyBook();
        boolean isExist = false;
        for (Book b : alreadyBooks) {
            if(b.getId() == book.getId()){
                isExist = true;
            }
        }
        if(isExist) {
            btnAlready.setEnabled(false);
        } else {
            btnAlready.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(BookActivity.this).addToAlready(book)){
                        Toast.makeText(BookActivity.this, "book added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BookActivity.this,AlreadyReadBookActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(Book book) {
        txtName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPage.setText(String.valueOf(book.getPage()));

        Glide.with(this).asBitmap().load(book.getImgUrl()).into(imgBook);
    }

    private void initView() {
        imgBook = findViewById(R.id.bookImg);

        txtName = findViewById(R.id.nameTxt);
        txtAuthor = findViewById(R.id.authorTxt);
        txtPage = findViewById(R.id.pageTxt);
        txtDesc = findViewById(R.id.descTxt);

        btnCurrently = findViewById(R.id.btnCurrentRead);
        btnAlready = findViewById(R.id.btnAlreadyRead);
        btnWant = findViewById(R.id.btnWant);
        btnFavorite = findViewById(R.id.btnAddFavor);
    }
}