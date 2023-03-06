package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.controller.response.FileResponse;
import com.udacity.jwdnd.course1.cloudstorage.controller.response.NoteResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert({"INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) " +
            "values (#{filename}, #{contentType}, #{size}, #{data}, #{userid})"})
    @Options(useGeneratedKeys = true, keyColumn = "fileid")
    public void insertFileUpload(String filename, String contentType, double size, byte[] data, int userid);

    @Select("SELECT fileid, filename " +
            "FROM FILES " +
            "WHERE userid = #{userid}")
    public List<FileResponse> getAllFile(@Param("userid") int userid);

    @Delete({"DELETE " +
            "FROM FILES " +
            "WHERE fileid = #{fileid} AND userid = #{userid}"})
    public int deleteFile(@Param("fileid") int fileId, @Param("userid") int userid) throws DataAccessException;

    @Select("SELECT fileid, filename, contenttype, userid, filesize, filedata " +
            "FROM FILES " +
            "WHERE userid = #{userid} " +
            "AND fileid = #{fileid}")
    public FileResponse getFile(@Param("fileid") int fileid, @Param("userid") int userid);
}
