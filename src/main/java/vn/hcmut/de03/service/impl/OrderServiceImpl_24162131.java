package vn.hcmut.de03.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.hcmut.de03.dao.OrderDao_24162131;
import vn.hcmut.de03.dao.VideoDao_24162131;
import vn.hcmut.de03.dao.impl.OrderDaoImpl_24162131;
import vn.hcmut.de03.dao.impl.VideoDaoImpl_24162131;
import vn.hcmut.de03.entity.OrderDetail_24162131;
import vn.hcmut.de03.entity.Order_24162131;
import vn.hcmut.de03.entity.User_24162131;
import vn.hcmut.de03.entity.Video_24162131;
import vn.hcmut.de03.model.CartItem_24162131;
import vn.hcmut.de03.service.OrderService_24162131;

public class OrderServiceImpl_24162131 implements OrderService_24162131 {
    private final OrderDao_24162131 orderDao = new OrderDaoImpl_24162131();
    private final VideoDao_24162131 videoDao = new VideoDaoImpl_24162131();

    @Override
    public boolean checkout(User_24162131 user, List<CartItem_24162131> items, String paymentMethod) {
        if (items == null || items.isEmpty()) return false;

        Order_24162131 order = new Order_24162131();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(0); // 0: mới
        order.setPaymentMethod(paymentMethod);
        
        double totalAmount = 0;
        List<OrderDetail_24162131> orderDetails = new ArrayList<>();
        
        for (CartItem_24162131 item : items) {
            // Kiểm tra stock và decrease quantity
            Video_24162131 v = videoDao.findById(item.getVideoId());
            if (v != null && v.getQuantity() != null && v.getQuantity() >= item.getQuantity()) {
                videoDao.decreaseQuantity(item.getVideoId(), item.getQuantity());
                
                OrderDetail_24162131 detail = new OrderDetail_24162131();
                detail.setOrder(order);
                detail.setVideo(v);
                detail.setPrice(item.getPrice());
                detail.setQuantity(item.getQuantity());
                
                orderDetails.add(detail);
                totalAmount += item.getTotalPrice();
            } else {
                return false; // Không đủ hàng
            }
        }
        
        order.setTotalAmount(totalAmount);
        order.setOrderDetails(orderDetails);
        orderDao.insert(order);
        return true;
    }

    @Override
    public List<Order_24162131> findByUser(String username) {
        return orderDao.findByUser(username);
    }

    @Override
    public List<Order_24162131> findByUserAndStatus(String username, Integer status) {
        return orderDao.findByUserAndStatus(username, status);
    }

    @Override
    public void updateStatus(Integer orderId, Integer status) {
        orderDao.updateStatus(orderId, status);
    }

    @Override
    public Order_24162131 findById(Integer orderId) {
        return orderDao.findById(orderId);
    }
}
