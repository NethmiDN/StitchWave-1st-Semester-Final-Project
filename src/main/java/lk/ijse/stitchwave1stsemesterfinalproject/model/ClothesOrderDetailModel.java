package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.ClothesOrderDetailDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.ClothesOrderDetailTM;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClothesOrderDetailModel {

    public String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from clothes_order order by order_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last Style ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("R%03d", newIdIndex); // Return the new Style ID in format Lnnn
        }
        return "R001"; // Return the default Style ID if no data is found
    }

    public ArrayList<ClothesOrderDetailDTO> getAllOrders() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM clothes_order");

        ArrayList<ClothesOrderDetailDTO> clothesOrderDetailDTOS = new ArrayList<>();

        while (rst.next()) {
            ClothesOrderDetailDTO clothesOrderDetailDTO = new ClothesOrderDetailDTO(
                    rst.getString(1),
                    rst.getString(2)
            );
            clothesOrderDetailDTOS.add(clothesOrderDetailDTO);
        }
        return clothesOrderDetailDTOS;
    }

    public ArrayList<ClothesOrderDetailTM> getAllDetails() throws SQLException {
        // SQL query to join tables for complete order details
        String query = """
                SELECT o.order_id, o.date, co.stock_id, o.qty,
                       c.customer_id, c.name AS customer_name,
                       p.payment_id, p.amount AS payment_amount
                FROM orders o
                JOIN clothes_order co ON o.order_id = co.order_id
                JOIN sewn_clothes_stock sc ON co.stock_id = sc.stock_id
                JOIN customer c ON o.customer_id = c.customer_id
                JOIN payment p ON o.payment_id = p.payment_id;
                """;

        ResultSet rst = CrudUtil.execute(query);
        ArrayList<ClothesOrderDetailTM> clothesOrderDetailDTOS = new ArrayList<>();

        while (rst.next()) {
            ClothesOrderDetailTM ClothesOrderDetailTM = new ClothesOrderDetailTM(
                    rst.getString("order_id"),
                    rst.getString("stock_id"),
                    rst.getDate("date").toLocalDate(),
                    rst.getInt("qty"),
                    rst.getString("customer_id"),
                    rst.getString("payment_id")
            );
            clothesOrderDetailDTOS.add(ClothesOrderDetailTM);
        }
        return clothesOrderDetailDTOS;
    }

    public boolean saveOrderWithStockReduction(ClothesOrderDetailTM clothesOrderDetailTM, int orderQty) throws SQLException {
        boolean transactionSuccess = false;

        try {
            CrudUtil.setAutoCommit(false);

            ResultSet rst = CrudUtil.execute("SELECT qty FROM sewn_clothes_stock WHERE stock_id = ?", clothesOrderDetailTM.getStock_id());
            if (rst.next()) {
                int currentStockQty = rst.getInt("qty");

                if (currentStockQty >= orderQty) {
                    boolean ordersetted = CrudUtil.execute(
                            "INSERT INTO orders (order_id, date, qty, customer_id, payment_id) VALUES (?, ?, ?, ?, ?)",
                            clothesOrderDetailTM.getOrder_id(),
                            clothesOrderDetailTM.getDate(),
                            clothesOrderDetailTM.getQty(),
                            clothesOrderDetailTM.getCustomer_id(),
                            clothesOrderDetailTM.getPayment_id()
                    );

                    boolean orderSaved = CrudUtil.execute(
                            "INSERT INTO clothes_order (stock_id, order_id) VALUES (?, ?)",
                            clothesOrderDetailTM.getStock_id(),
                            clothesOrderDetailTM.getOrder_id()
                    );

                    boolean stockUpdated = CrudUtil.execute(
                            "UPDATE sewn_clothes_stock SET qty = qty - ? WHERE stock_id = ?",
                            orderQty,
                            clothesOrderDetailTM.getStock_id()
                    );

                    if (orderSaved && stockUpdated && ordersetted) {
                        CrudUtil.commit();
                        transactionSuccess = true;
                    } else {
                        CrudUtil.rollback();
                    }
                } else {
                    CrudUtil.rollback();
                }
            }
        } catch (SQLException e) {
            CrudUtil.rollback();
            e.printStackTrace();
        } finally {
            CrudUtil.setAutoCommit(true);
        }
        return transactionSuccess;
    }

    public boolean deleteOrder(String order_id) throws SQLException {
        return CrudUtil.execute("delete from clothes_order where order_id=?", order_id);
    }

    public boolean updateOrderWithStockReduction(ClothesOrderDetailTM clothesOrderDetailTM, int orderQty) throws SQLException {
        boolean transactionSuccess = false;

        try {
            CrudUtil.setAutoCommit(false);

            ResultSet rst = CrudUtil.execute("SELECT qty FROM sewn_clothes_stock WHERE stock_id = ?", clothesOrderDetailTM.getStock_id());
            if (rst.next()) {
                int currentStockQty = rst.getInt("qty");

                if (currentStockQty >= orderQty) {
                    boolean ordersetted = CrudUtil.execute(
                            "update orders (order_id, date, qty, customer_id, payment_id) VALUES (?, ?, ?, ?, ?)",
                            clothesOrderDetailTM.getOrder_id(),
                            clothesOrderDetailTM.getDate(),
                            clothesOrderDetailTM.getQty(),
                            clothesOrderDetailTM.getCustomer_id(),
                            clothesOrderDetailTM.getPayment_id()
                    );

                    boolean orderSaved = CrudUtil.execute(
                            "update clothes_order (order_id, stock_id, date, qty, customer_id, payment_id) VALUES (?,?,?,?,?,?)",
                            clothesOrderDetailTM.getStock_id(),
                            clothesOrderDetailTM.getOrder_id()
                    );

                    boolean stockUpdated = CrudUtil.execute(
                            "UPDATE sewn_clothes_stock SET qty = qty - ? WHERE stock_id = ?",
                            orderQty,
                            clothesOrderDetailTM.getStock_id()
                    );

                    if (orderSaved && stockUpdated && ordersetted) {
                        CrudUtil.commit();
                        transactionSuccess = true;
                    } else {
                        CrudUtil.rollback();
                    }
                } else {
                    CrudUtil.rollback();
                }
            }
        } catch (SQLException e) {
            CrudUtil.rollback();
            e.printStackTrace();
        } finally {
            CrudUtil.setAutoCommit(true);
        }
        return transactionSuccess;
    }
}