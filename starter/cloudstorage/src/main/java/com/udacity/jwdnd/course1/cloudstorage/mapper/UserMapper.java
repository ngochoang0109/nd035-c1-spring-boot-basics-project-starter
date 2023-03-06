package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.controller.request.Authenticated;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT userid, username, password, salt, firstname, lastname " +
            "FROM USERS " +
            "WHERE username = #{username}")
    public Users findUserByUsername(String username);

    @Insert({"INSERT INTO USERS (username, password, salt, firstname, lastname) " +
            "values (#{users.username}, #{users.password}, #{users.salt}, #{users.firstName}, #{users.lastName})"})
    @Options(useGeneratedKeys = true, keyColumn = "userid")
    public Integer insertUserDataset(@Param("users") Authenticated users);
}
