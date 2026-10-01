package vn.hcmut.de03.dao;

import vn.hcmut.de03.entity.User_24162131;

public interface UserDao_24162131 {
    User_24162131 findById(String username);

    void insert(User_24162131 user);

    void update(User_24162131 user);
}
