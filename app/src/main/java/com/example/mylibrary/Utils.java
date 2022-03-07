package com.example.mylibrary;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private SharedPreferences sharedPreferences;
    private static final String ALL_BOOKS_KEY = "all_books", ALREADY_BOOKS_KEY = "already_books",
                                CURRENTLY_BOOKS_KEY = "currently_books", WANT_BOOKS_KEY = "want_books",
                                FAVORITE_BOOKS = "favorite_books";

    private static Utils instance;

    private static ArrayList<Book> allBook;
    private static ArrayList<Book> currentlyBook;
    private static ArrayList<Book> alreadyBook;
    private static ArrayList<Book> wantBook;
    private static ArrayList<Book> favoriteBook;

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("alternative_db",Context.MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if(null == getAllBook()){
            initData();
        }

        if(null == getCurrentlyBook()) {
            editor.putString(CURRENTLY_BOOKS_KEY,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if(null == getAlreadyBook()) {
            editor.putString(ALREADY_BOOKS_KEY,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if(null == getWantBook()) {
            editor.putString(WANT_BOOKS_KEY,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if(null == getFavoriteBook()) {
            editor.putString(FAVORITE_BOOKS,gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    private void initData() {
        ArrayList<Book> books = new ArrayList<>();

        books.add(new Book(1,"Vài giây nữa thôi","Reddy, FreakD","https://i1.sndcdn.com/artworks-iw5qWosMzzvw2sIe-Saz8Mw-t500x500.jpg",
                "long Desc","short Desc",1035));
        books.add(new Book(2,"The Heart Wants What It Wants","Selena Gomez","https://i1.sndcdn.com/artworks-000097111411-fuuns9-t500x500.jpg",
                "It wants what it wants","Baby",410));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit();
    }

    public static Utils getInstance(Context context) {
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<Book> getAllBook() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY,null), type);
        return books;
    }

    public ArrayList<Book> getCurrentlyBook() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getAlreadyBook() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_BOOKS_KEY,null),type);
        return books;
    }

    public ArrayList<Book> getWantBook() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getFavoriteBook() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS,null),type);
        return books;
    }

    public  Book getBookById (int id){
        if(getAllBook()!= null) {
            for (Book b: getAllBook()) {
                if(b.getId() == id){
                    return b;
                }
            }
        }

        return null;
    }

    public boolean addToAlready (Book book) {
        ArrayList<Book> books = getAlreadyBook();

        if(books != null) {
            if(books.add(book)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                editor.remove(ALREADY_BOOKS_KEY);
                editor.putString(ALREADY_BOOKS_KEY,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToWant (Book book) {
        ArrayList<Book> books = getWantBook();
        if(books != null) {
            if(books.add(book)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                editor.remove(WANT_BOOKS_KEY);
                editor.putString(WANT_BOOKS_KEY,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrently(Book book) {
        ArrayList<Book> books = getCurrentlyBook();
        if(books != null){
            if(books.add(book)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                editor.remove(CURRENTLY_BOOKS_KEY);
                editor.putString(CURRENTLY_BOOKS_KEY,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavorite(Book book) {
        ArrayList<Book> books = getFavoriteBook();
        if(books != null) {
            if(books.add(book)){
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                editor.remove(FAVORITE_BOOKS);
                editor.putString(FAVORITE_BOOKS,gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeAlready (Book book) {
        ArrayList<Book> books = getAlreadyBook();
        if(books != null){
            for (Book b : books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        editor.remove(ALREADY_BOOKS_KEY);
                        editor.putString(ALREADY_BOOKS_KEY,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeCurrently (Book book) {
        ArrayList<Book> books = getCurrentlyBook();
        if(books != null){
            for (Book b : books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        editor.remove(CURRENTLY_BOOKS_KEY);
                        editor.putString(CURRENTLY_BOOKS_KEY,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeWant (Book book) {
        ArrayList<Book> books = getWantBook();
        if(books != null){
            for (Book b : books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        editor.remove(WANT_BOOKS_KEY);
                        editor.putString(WANT_BOOKS_KEY,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean removeFavorite (Book book) {
        ArrayList<Book> books = getFavoriteBook();
        if(books != null){
            for (Book b : books){
                if(b.getId() == book.getId()){
                    if(books.remove(b)){
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        editor.remove(FAVORITE_BOOKS);
                        editor.putString(FAVORITE_BOOKS,gson.toJson(books));
                        editor.commit();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
