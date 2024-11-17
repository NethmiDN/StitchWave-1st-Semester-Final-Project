package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.FabricDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.SupplierOrderDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierOrderModel {

    public String getNextSupplierOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from supplier_order order by order_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last order ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("D%03d", newIdIndex); // Return the new order ID in format Dnnn
        }
        return "D001"; // Return the default order ID if no data is found
    }

    public boolean saveOrderWithFabrics(SupplierOrderDTO orderDTO, ArrayList<FabricDTO> fabrics) throws SQLException {
        boolean transactionSuccess = false;

        try {
            CrudUtil.setAutoCommit(false);

            boolean orderSaved = CrudUtil.execute(
                    "INSERT INTO supplier_order (order_id, qty_kg ,date, supplier_id) VALUES (?, ?, ?, ?)",
                    orderDTO.getOrder_id(),
                    orderDTO.getQty_kg(),
                    orderDTO.getDate(),
                    orderDTO.getSupplier_id()
            );

            boolean fabricSaved = true;
            for (FabricDTO fabric : fabrics) {
                fabricSaved = CrudUtil.execute(
                        "INSERT INTO fabric (fabric_id, color, weight_kg, width_inch) VALUES (?, ?, ?, ?)",
                        fabric.getFabric_id(),
                        fabric.getColor(),
                        fabric.getWeight_kg(),
                        fabric.getWidth_inch()
                );

                if (fabricSaved) {
                    fabricSaved = CrudUtil.execute(
                            "INSERT INTO fabric_order (fabric_id, order_id) VALUES (?, ?)",
                            fabric.getFabric_id(),
                            orderDTO.getOrder_id()
                    );
                }
                if (!fabricSaved) break;
            }

            if (orderSaved && fabricSaved) {
                CrudUtil.commit();
                transactionSuccess = true;
            } else {
                CrudUtil.rollback();
            }
        } catch (SQLException e) {
            CrudUtil.rollback();
            e.printStackTrace();
        } finally {
            CrudUtil.setAutoCommit(true);
        }

        return transactionSuccess;
    }

    public boolean saveSupplierOrder(SupplierOrderDTO supplierOrderDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into supplier_order values (?,?,?,?)",
                supplierOrderDTO.getOrder_id(),
                supplierOrderDTO.getQty_kg(),
                supplierOrderDTO.getDate(),
                supplierOrderDTO.getSupplier_id()
        );
    }

    public ArrayList<SupplierOrderDTO> getAllSupplierOrders() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier_order");

        ArrayList<SupplierOrderDTO> supplierOrderDTOS = new ArrayList<>();

        while (rst.next()) {
            SupplierOrderDTO supplierOrderDTO = new SupplierOrderDTO(
                    rst.getString(1),  // Supplier order Id
                    rst.getDouble(2),  // qty
                    rst.getDate(3).toLocalDate(),  // date
                    rst.getString(4) //supplier Id
            );
            supplierOrderDTOS.add(supplierOrderDTO);
        }
        return supplierOrderDTOS;
    }

    public boolean updateSupplierOrder(SupplierOrderDTO supplierOrderDTO) throws SQLException {
        return CrudUtil.execute(
                "update supplier_order set qty_kg=?, date=?, supplier_id=? where order_id=?",
                supplierOrderDTO.getQty_kg(),
                supplierOrderDTO.getDate(),
                supplierOrderDTO.getSupplier_id(),
                supplierOrderDTO.getOrder_id()
        );
    }

    public boolean deleteSupplierOrder(String order_id) throws SQLException {
        return CrudUtil.execute("delete from supplier_order where order_id=?", order_id);
    }

    public ArrayList<String> getAllSupplierOrderIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select order_id from supplier_order");

        ArrayList<String> order_ids = new ArrayList<>();

        while (rst.next()) {
            order_ids.add(rst.getString(1));
        }

        return order_ids;
    }

    public SupplierOrderDTO findById(String selectedSupOrderId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier_order where order_id=?", selectedSupOrderId);

        if (rst.next()) {
            return new SupplierOrderDTO(
                    rst.getString(1),  // Supplier order Id
                    rst.getDouble(2),  // Qty
                    rst.getDate(3).toLocalDate() , // Date
                    rst.getString(4) // Supplier Id
            );
        }
        return null;
    }
}
