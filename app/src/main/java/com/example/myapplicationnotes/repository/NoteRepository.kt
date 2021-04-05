package com.example.myapplicationnotes.repository

import com.example.myapplicationnotes.data.NoteDatabase
import com.example.myapplicationnotes.models.Note

class NoteRepository(private val db: NoteDatabase) {

    suspend fun insertNote(note: Note) = db.getNoteDao().insertNote(note)
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)
    fun getAllNotes() = db.getNoteDao().getAllNotes()
    fun getNt() = db.getNoteDao().getNt()
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)

}