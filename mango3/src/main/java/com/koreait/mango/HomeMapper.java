package com.koreait.mango;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.mango.model.UserEntity;
import com.koreait.mango.model.security.UserPrincipal;

@Mapper
public interface HomeMapper {
	int insUser(UserEntity p);
	UserPrincipal login(UserEntity p);
}
