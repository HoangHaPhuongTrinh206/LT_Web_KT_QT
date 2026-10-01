package vn.hcmut.de03.dao;

import java.util.List;
import vn.hcmut.de03.entity.Order_24162131;

public interface OrderDao_24162131 {
    void insert(Order_24162131 order);
    List<Order_24162131> findByUser(String username);
    List<Order_24162131> findByUserAndStatus(String username, Integer status);
    void updateStatus(Integer orderId, Integer status);
    Order_24162131 findById(Integer orderId);
}
