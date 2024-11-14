package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.OrdersDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public OrdersDTO findById(String selectedOrderId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from orders where order_id=?", selectedOrderId);

        if (rst.next()) {
            return new OrdersDTO(
                    rst.getString(1),  // Order ID
                    rst.getDate(2).toLocalDate() , // Date
                    rst.getInt(3),  // qty
                    rst.getString(4), // customer id
                    rst.getString(5) // payment id
            );
        }
        return null;
    }
}
