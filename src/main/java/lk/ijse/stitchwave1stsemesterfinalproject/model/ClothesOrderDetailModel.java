package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.ClothesOrderDetailDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClothesOrderDetailModel {

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

    public boolean saveOrderWithStockReduction(ClothesOrderDetailDTO clothesOrderDetailDTO, int orderQty) throws SQLException {
        boolean transactionSuccess = false;

        try {
            CrudUtil.setAutoCommit(false);

            ResultSet rst = CrudUtil.execute("SELECT qty FROM sewn_clothes_stock WHERE stock_id = ?", clothesOrderDetailDTO.getStock_Id());
            if (rst.next()) {
                int currentStockQty = rst.getInt("qty");

                if (currentStockQty >= orderQty) {
                    boolean orderSaved = CrudUtil.execute(
                            "INSERT INTO clothes_order (stock_id, order_id) VALUES (?, ?)",
                            clothesOrderDetailDTO.getStock_Id(),
                            clothesOrderDetailDTO.getOrder_Id()
                    );

                    boolean stockUpdated = CrudUtil.execute(
                            "UPDATE sewn_clothes_stock SET qty = qty - ? WHERE stock_id = ?",
                            orderQty,
                            clothesOrderDetailDTO.getStock_Id()
                    );

                    if (orderSaved && stockUpdated) {
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