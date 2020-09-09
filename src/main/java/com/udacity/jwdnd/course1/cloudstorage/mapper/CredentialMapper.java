package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;

@Mapper
public interface CredentialMapper {

	@Select("SELECT * FROM CREDENTIALS WHERE url = #{url}")
	Credentials getCredential(String url);

	@Select("SELECT * FROM CREDENTIALS")
	List<Credentials> getAllCredentials();

	@Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) "
			+ "VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialiId")
	int addCredential(Credentials credential);
}
