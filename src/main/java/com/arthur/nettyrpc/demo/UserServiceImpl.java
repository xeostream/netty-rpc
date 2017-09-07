package com.arthur.nettyrpc.demo;

public class UserServiceImpl implements UserService {
    public String findUser(String userName) {
        return userName;
    }

    public String findUserList(int pageNum, int pageSize) {
        return pageNum + " " + pageSize;
    }
}
