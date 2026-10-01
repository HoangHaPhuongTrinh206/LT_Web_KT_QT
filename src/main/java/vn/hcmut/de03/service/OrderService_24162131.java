package vn.hcmut.de03.service;

import java.util.List;
import vn.hcmut.de03.entity.Order_24162131;
import vn.hcmut.de03.entity.User_24162131;
import vn.hcmut.de03.model.CartItem_24162131;

public interface OrderService_24162131 {
    boolean checkout(User_24162131 user, List<CartItem_24162131> items, String paymentMethod);
    List<Order_24162131> findByUser(String username);
    List<Order_24162131> findByUserAndStatus(String username, Integer status);
    void updateStatus(Integer orderId, Integer status);
    Order_24162131 findById(Integer orderId);
}
