package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.controller.request.NoteRequest;
import com.udacity.jwdnd.course1.cloudstorage.controller.response.NoteResponse;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert({"INSERT INTO NOTES (notetitle, notedescription, userid) " +
            "values (#{note.title}, #{note.description}, #{userid})"})
    @Options(useGeneratedKeys = true, keyColumn = "noteid")
    public int addNewNote(@Param("note") NoteRequest noteRequest, @Param("userid") int userid);

    @Select("SELECT notetitle, notedescription, userid, noteid " +
            "FROM NOTES " +
            "WHERE userid = #{userid}")
    public List<NoteResponse> findNoteByUserid(@Param("userid") int userid);

    @Select("SELECT notetitle, notedescription, userid, noteid " +
            "FROM NOTES " +
            "WHERE userid = #{userid} " +
            "AND noteid = #{noteid}")
    public NoteResponse getNote(@Param("noteid") int noteId, @Param("userid") int userid);

    @Update({"UPDATE NOTES SET notetitle = #{note.title}, " +
            "notedescription = #{note.description} " +
            "WHERE noteid = #{note.noteid} AND userid = #{userid}"})
    public int editNote(@Param("note") NoteRequest noteRequest, @Param("userid") int userid) throws DataAccessException;

    @Delete({"DELETE " +
            "FROM NOTES " +
            "WHERE noteid = #{noteid} AND userid = #{userid}"})
    public int deleteNote(@Param("noteid") int noteid, @Param("userid") int userid) throws DataAccessException;
}
