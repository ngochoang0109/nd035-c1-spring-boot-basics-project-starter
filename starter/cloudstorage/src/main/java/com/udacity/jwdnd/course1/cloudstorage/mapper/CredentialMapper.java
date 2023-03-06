package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.controller.request.CredentialRequest;
import com.udacity.jwdnd.course1.cloudstorage.controller.response.CredentialResponse;
import org.apache.ibatis.annotations.*;
import org.springframework.dao.DataAccessException;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT credentialid, url, username, password " +
            "FROM CREDENTIALS " +
            "WHERE userid = #{userid}")
    public List<CredentialResponse> findCredentialByUserid(@Param("userid") int userid);

    @Insert({"INSERT INTO CREDENTIALS (url, username, password, key, userid) " +
            "values (#{credential.url}, #{credential.username}, #{credential.password}, #{credential.key}, #{userid})"})
    @Options(useGeneratedKeys = true, keyColumn = "credentialid")
    public int addNewCredential(@Param("credential") CredentialRequest credentialRequest, @Param("userid") int userid);

    @Select("SELECT credentialid, url, username, password, key " +
            "FROM CREDENTIALS " +
            "WHERE userid = #{userid} " +
            "AND credentialid = #{credentialid}")
    public CredentialResponse getCredential(@Param("credentialid") int credentialId, @Param("userid") int userid);

    @Update({"UPDATE CREDENTIALS SET url = #{credential.url}, " +
            "username = #{credential.username}, " +
            "password = #{credential.password}, " +
            "key = #{credential.key} " +
            "WHERE credentialid = #{credential.credentialid} AND userid = #{userid}"})
    public int editCredential(@Param("credential") CredentialRequest credentialRequest, @Param("userid") int userid) throws DataAccessException;

    @Delete({"DELETE " +
            "FROM CREDENTIALS " +
            "WHERE userid = #{userid} " +
            "AND credentialid = #{credentialid}"})
    public int deleteCredential(@Param("credentialid") int credentialId, @Param("userid") int userid) throws DataAccessException;
}
