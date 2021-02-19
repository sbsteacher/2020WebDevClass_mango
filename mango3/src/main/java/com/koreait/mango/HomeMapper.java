package com.koreait.mango;

import org.apache.ibatis.annotations.Mapper;

import com.koreait.mango.model.UserEntity;

@Mapper
public interface HomeMapper {
	int insUser(UserEntity p);
	UserEntity selUser(UserEntity p);
}
