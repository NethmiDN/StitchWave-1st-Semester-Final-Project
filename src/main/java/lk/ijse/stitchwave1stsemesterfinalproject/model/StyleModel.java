package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.StyleDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StyleModel {
    public String getNextStyleId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select style_id from style order by style_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last Style ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("L%03d", newIdIndex); // Return the new Style ID in format Lnnn
        }
        return "L001"; // Return the default Style ID if no data is found
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
                    rst.getString(1),  // Style ID
                    rst.getString(2), //size
                    rst.getInt(3),  // qty
                    rst.getString(4),  // employee ID
                    rst.getString(5) //stock ID
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

    public StyleDTO findById(String selectedStyleId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from style where style_id=?", selectedStyleId);

        if (rst.next()) {
            return new StyleDTO(
                    rst.getString(1),  // Style ID
                    rst.getString(2), // size
                    rst.getInt(3),  // Qty
                    rst.getString(4), // employee id
                    rst.getString(5) // stock id
            );
        }
        return null;
    }
}
