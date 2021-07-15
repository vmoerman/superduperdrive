package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid}")
    Note getNote(Integer noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{notetitle},#{notedescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int addNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteid}")
    int deleteNote(int noteid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Note> getAllNotes(int userid);

    @Update("UPDATE NOTES set notetitle= #{notetitle}, notedescription = #{notedescription}, userid = #{userid} WHERE noteid = #{noteid}")
    void editNote(Note note);
}
