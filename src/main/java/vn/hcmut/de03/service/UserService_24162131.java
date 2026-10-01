package vn.hcmut.de03.service;

import vn.hcmut.de03.entity.User_24162131;

public interface UserService_24162131 {
    User_24162131 findByUsername(String username);

    /** @return user nếu đúng username + password (kể cả chưa kích hoạt), ngược lại null */
    User_24162131 authenticate(String username, String password);

    /** Lưu user mới ở trạng thái Active = false (chờ OTP). Ném IllegalStateException nếu username đã tồn tại và đã kích hoạt. */
    void registerPending(User_24162131 user);

    /** Kích hoạt tài khoản sau khi nhập đúng OTP. */
    void activate(String username);
}
