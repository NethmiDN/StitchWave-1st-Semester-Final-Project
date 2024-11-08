package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.OrdersDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.SupplierOrderDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersModel {
    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from orders order by order_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last order ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("R%03d", newIdIndex); // Return the new order ID in format Rnnn
        }
        return "R001"; // Return the default order ID if no data is found
    }

    public boolean saveOrder(OrdersDTO ordersDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into orders values (?,?,?,?,?)",
                ordersDTO.getOrder_id(),
                ordersDTO.getDate(),
                ordersDTO.getQty(),
                ordersDTO.getCustomer_id(),
                ordersDTO.getPayment_id()
        );
    }

    public ArrayList<OrdersDTO> getAllOrders() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from orders");

        ArrayList<OrdersDTO> ordersDTOS = new ArrayList<>();

        while (rst.next()) {
            OrdersDTO ordersDTO = new OrdersDTO(
                    rst.getString(1),  // order ID
                    rst.getDate(2).toLocalDate(),  // date
                    rst.getInt(3),  // qty
                    rst.getString(4), //supplierid
                    rst.getString(5)
            );
            ordersDTOS.add(ordersDTO);
        }
        return ordersDTOS;
    }

    public boolean updateOrder(OrdersDTO ordersDTO) throws SQLException {
        return CrudUtil.execute(
                "update orders set date=?, qty=?, customer_id=?, payment_id=? where order_id=?",
                ordersDTO.getDate(),
                ordersDTO.getQty(),
                ordersDTO.getCustomer_id(),
                ordersDTO.getPayment_id(),
                ordersDTO.getOrder_id()
        );
    }

    public boolean deleteOrder(String order_id) throws SQLException {
        return CrudUtil.execute("delete from orders where order_id=?", order_id);
    }

    public ArrayList<String> getAllOrderIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from orders");

        ArrayList<String> order_ids = new ArrayList<>();

        while (rst.next()) {
            order_ids.add(rst.getString(1));
        }

        return order_ids;
    }

    public OrdersDTO findById(String selectedOrderId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from orders where order_id=?", selectedOrderId);

        if (rst.next()) {
            return new OrdersDTO(
                    rst.getString(1),  // Payment ID
                    rst.getDate(2).toLocalDate() , // Date
                    rst.getInt(3),  // Amount
                    rst.getString(4),
                    rst.getString(5)
            );
        }
        return null;
    }
}
