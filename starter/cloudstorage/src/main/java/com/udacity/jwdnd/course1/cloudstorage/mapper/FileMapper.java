package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("Select * FROM FILES WHERE filename = #{filename}")
    File getFile(String filename);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid}")
    File getFileById(int fileid);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> getAllFiles(int userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insert(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid} AND userid = #{userid}")
    int delete(File file);



}
