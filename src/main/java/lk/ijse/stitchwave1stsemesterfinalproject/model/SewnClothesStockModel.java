package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.SewnClothesStockDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SewnClothesStockModel {

    public String getNextStockId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select stock_id from sewn_clothes_stock order by stock_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last stock ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("W%03d", newIdIndex); // Return the new stock ID in format Wnnn
        }
        return "W001"; // Return the default stock ID if no data is found
    }

    public boolean saveStock(SewnClothesStockDTO sewnClothesStockDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into sewn_clothes_stock values (?,?,?)",
                sewnClothesStockDTO.getStock_id(),
                sewnClothesStockDTO.getQty(),
                sewnClothesStockDTO.getFabric_id()
        );
    }

    public ArrayList<SewnClothesStockDTO> getAllStocks() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sewn_clothes_stock");

        ArrayList<SewnClothesStockDTO> sewnClothesStockDTOS = new ArrayList<>();

        while (rst.next()) {
            SewnClothesStockDTO sewnClothesStockDTO = new SewnClothesStockDTO(
                    rst.getString(1),  // stock ID
                    rst.getInt(2),  // qty
                    rst.getString(3) //fabric id
            );
            sewnClothesStockDTOS.add(sewnClothesStockDTO);
        }
        return sewnClothesStockDTOS;
    }

    public boolean updateStock(SewnClothesStockDTO sewnClothesStockDTO) throws SQLException {
        return CrudUtil.execute(
                "update sewn_clothes_stock set qty=?, fabric_id=? where stock_id=?",
                sewnClothesStockDTO.getQty(),
                sewnClothesStockDTO.getFabric_id(),
                sewnClothesStockDTO.getStock_id()
        );
    }

    public boolean deleteStock(String stock_id) throws SQLException {
        return CrudUtil.execute("delete from sewn_clothes_stock where stock_id=?", stock_id);
    }

    public Map<String, Integer> getLowStockItems(int threshold) throws SQLException {
        Map<String, Integer> lowStockItems = new HashMap<>();

        ResultSet rst = CrudUtil.execute(
                "SELECT stock_id, qty FROM sewn_clothes_stock WHERE qty < ?",
                threshold
        );

        while (rst.next()) {
            String stockId = rst.getString("stock_id");
            int quantity = rst.getInt("qty");
            lowStockItems.put(stockId, quantity);
        }

        return lowStockItems;
    }

    public ArrayList<String> getAllStockIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select stock_id from sewn_clothes_stock");

        ArrayList<String> stock_ids = new ArrayList<>();

        while (rst.next()) {
            stock_ids.add(rst.getString(1));
        }

        return stock_ids;
    }

    public SewnClothesStockDTO findById(String selectedStockId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from sewn_clothes_stock where stock_id=?", selectedStockId);

        if (rst.next()) {
            return new SewnClothesStockDTO(
                    rst.getString(1),  // stock ID
                    rst.getInt(2),  // qty
                    rst.getString(3) // fabric id
            );
        }
        return null;
    }
}
