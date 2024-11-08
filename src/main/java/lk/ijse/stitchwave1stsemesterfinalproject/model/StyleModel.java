package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.StyleDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.SupplierOrderDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StyleModel {
    public String getNextStyleId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select style_id from style order by style_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last order ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("L%03d", newIdIndex); // Return the new order ID in format Lnnn
        }
        return "L001"; // Return the default order ID if no data is found
    }

    public boolean saveStyle(StyleDTO styleDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into style values (?,?,?,?,?)",
                styleDTO.getStyle_id(),
                styleDTO.getSize(),
                styleDTO.getQty(),
                styleDTO.getEmployee_id(),
                styleDTO.getStock_id()
        );
    }

    public ArrayList<StyleDTO> getAllStyles() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from style");

        ArrayList<StyleDTO> styleDTOS = new ArrayList<>();

        while (rst.next()) {
            StyleDTO styleDTO = new StyleDTO(
                    rst.getString(1),  // order ID
                    rst.getString(2),
                    rst.getInt(3),  // qty
                    rst.getString(4),  // date
                    rst.getString(5) //supplierid
            );
            styleDTOS.add(styleDTO);
        }
        return styleDTOS;
    }

    public boolean updateStyle(StyleDTO styleDTO) throws SQLException {
        return CrudUtil.execute(
                "update style set size=?, qty=?, employee_id=?, stock_id=? where style_id=?",
                styleDTO.getSize(),
                styleDTO.getQty(),
                styleDTO.getEmployee_id(),
                styleDTO.getStock_id(),
                styleDTO.getStyle_id()
        );
    }

    public boolean deleteStyle(String style_id) throws SQLException {
        return CrudUtil.execute("delete from style where style_id=?", style_id);
    }

    public ArrayList<String> getAllStyleIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select style_id from style");

        ArrayList<String> style_ids = new ArrayList<>();

        while (rst.next()) {
            style_ids.add(rst.getString(1));
        }

        return style_ids;
    }

    public StyleDTO findByEmpId(String selectedEmpId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from style where style_id=?", selectedEmpId);

        if (rst.next()) {
            return new StyleDTO(
                    rst.getString(1),  // Payment ID
                    rst.getString(2),
                    rst.getInt(3),  // Amount
                    rst.getString(4), // Date
                    rst.getString(5)
            );
        }
        return null;
    }

    public StyleDTO findByStockId(String selectedStockId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from style where style_id=?", selectedStockId);

        if (rst.next()) {
            return new StyleDTO(
                    rst.getString(1),  // Payment ID
                    rst.getString(2),
                    rst.getInt(3),  // Amount
                    rst.getString(4), // Date
                    rst.getString(5)
            );
        }
        return null;
    }
}
