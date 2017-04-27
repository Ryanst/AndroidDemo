package com.ryanst.app.activity.aidl;

import com.ryanst.app.activity.aidl.Book;

interface IBookManager{
    List<Book> getBookList();
    void addBook(in Book book);
}