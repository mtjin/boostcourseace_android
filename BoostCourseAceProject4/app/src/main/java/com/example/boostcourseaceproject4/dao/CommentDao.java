package com.example.boostcourseaceproject4.dao;

import androidx.room.Query;

public class CommentDao {
    @Query("SELECT * FROM person")
    void getAllPerson(): List<Person>

    @Query("DELETE FROM person")
    fun clearAll()

    //해당 데이터를 추가합니다.

    @Insert
    fun insert(vararg person: Person)

    //헤당 데이터를 업데이트 합니다.
    @Update
    fun update(vararg person: Person)

    //해당 데이터를 삭제합니다.
    @Delete
    fun delete(vararg person: Person)
}

