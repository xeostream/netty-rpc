package com.arthur.nettyrpc.demo;

public interface UserService {

    String findUser(String userName);

    String findUserList(int pageNum, int pageSize);
}
