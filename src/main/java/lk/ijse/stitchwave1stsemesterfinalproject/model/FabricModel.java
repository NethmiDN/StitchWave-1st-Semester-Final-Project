package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.FabricDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FabricModel {
    public String getNextFabricId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select fabric_id from fabric order by fabric_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last fabric ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("F%03d", newIdIndex); // Return the new fabric ID in format Fnnn
        }
        return "F001"; // Return the default fabric ID if no data is found
    }

    public boolean saveFabric(FabricDTO fabricDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into fabric values (?,?,?,?)",
                fabricDTO.getFabric_id(),
                fabricDTO.getColor(),
                fabricDTO.getWeight_kg(),
                fabricDTO.getWidth_inch()
        );
    }

    public ArrayList<FabricDTO> getAllFabrics() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from fabric");

        ArrayList<FabricDTO> fabricDTOS = new ArrayList<>();

        while (rst.next()) {
            FabricDTO fabricDTO = new FabricDTO(
                    rst.getString(1),  // fabric ID
                    rst.getString(2),  // color
                    rst.getDouble(3),  // weight
                    rst.getDouble(4)   //width
            );
            fabricDTOS.add(fabricDTO);
        }
        return fabricDTOS;
    }

    public boolean updateFabric(FabricDTO fabricDTO) throws SQLException {
        return CrudUtil.execute(
                "update fabric set color=?, weight_kg=?, width_inch=? where fabric_id=?",
                fabricDTO.getColor(),
                fabricDTO.getWeight_kg(),
                fabricDTO.getWidth_inch(),
                fabricDTO.getFabric_id()
        );
    }

    public boolean deleteFabric(String fabric_id) throws SQLException {
        return CrudUtil.execute("delete from fabric where fabric_id=?", fabric_id);
    }

    public ArrayList<String> getAllFabricIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select fabric_id from fabric");

        ArrayList<String> fabric_ids = new ArrayList<>();

        while (rst.next()) {
            fabric_ids.add(rst.getString(1));
        }

        return fabric_ids;
    }

    public FabricDTO findById(String selectedFabId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from fabric where fabric_id=?", selectedFabId);

        if (rst.next()) {
            return new FabricDTO(
                    rst.getString(1),  // fabric ID
                    rst.getString(2),  // color
                    rst.getDouble(3),  // weight
                    rst.getDouble(4) //width
            );
        }
        return null;
    }
}
