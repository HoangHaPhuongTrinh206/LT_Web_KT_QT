package vn.hcmut.de03.service.impl;

import vn.hcmut.de03.dao.UserDao_24162131;
import vn.hcmut.de03.dao.impl.UserDaoImpl_24162131;
import vn.hcmut.de03.entity.User_24162131;
import vn.hcmut.de03.service.UserService_24162131;

public class UserServiceImpl_24162131 implements UserService_24162131 {
    private final UserDao_24162131 userDao = new UserDaoImpl_24162131();

    @Override
    public User_24162131 findByUsername(String username) {
        return userDao.findById(username);
    }

    @Override
    public User_24162131 authenticate(String username, String password) {
        User_24162131 u = userDao.findById(username);
        if (u != null && u.getPassword() != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    @Override
    public void registerPending(User_24162131 user) {
        User_24162131 old = userDao.findById(user.getUsername());
        if (old != null && Boolean.TRUE.equals(old.getActive())) {
            throw new IllegalStateException("Tên đăng nhập đã tồn tại");
        }
        user.setActive(false);
        user.setAdmin(false);
        if (old == null) {
            userDao.insert(user);
        } else {
            userDao.update(user);    // tài khoản cũ chưa kích hoạt -> ghi đè thông tin mới, gửi lại OTP
        }
    }

    @Override
    public void activate(String username) {
        User_24162131 u = userDao.findById(username);
        if (u != null) {
            u.setActive(true);
            userDao.update(u);
        }
    }
}
