package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note, int userId)
    {
        note.setUserid(userId);
        return noteMapper.addNote(note);
    }

    public void editNote(Note note, int userId)
    {
        note.setUserid(userId);
        noteMapper.editNote(note);
    }

    public Note getNote(int noteId)
    {
        return noteMapper.getNote(noteId);
    }

    public List<Note> getAllNotes(int userid)
    {
        return noteMapper.getAllNotes(userid);
    }

    public int deleteNote(int noteId)
    {
        return noteMapper.deleteNote(noteId);
    }
}
