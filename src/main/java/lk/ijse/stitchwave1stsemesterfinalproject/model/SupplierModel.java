package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.SupplierDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierModel {
    public String getNextSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last supplier ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("S%03d", newIdIndex); // Return the new supplier ID in format Snnn
        }
        return "S001"; // Return the default supplier ID if no data is found
    }

    public boolean saveSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into supplier values (?,?,?)",
                supplierDTO.getSupplier_id(),
                supplierDTO.getName(),
                supplierDTO.getContact()
        );
    }

    public ArrayList<SupplierDTO> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier");

        ArrayList<SupplierDTO> supplierDTOS = new ArrayList<>();

        while (rst.next()) {
            SupplierDTO supplierDTO = new SupplierDTO(
                    rst.getString(1),  // Supplier ID
                    rst.getString(2),  // Name
                    rst.getString(3)  // Contact
            );
            supplierDTOS.add(supplierDTO);
        }
        return supplierDTOS;
    }

    public boolean updateSupplier(SupplierDTO supplierDTO) throws SQLException {
        return CrudUtil.execute(
                "update supplier set name=?, contact=? where supplier_id=?",
                supplierDTO.getName(),
                supplierDTO.getContact(),
                supplierDTO.getSupplier_id()
        );
    }

    public boolean deleteSupplier(String supplier_id) throws SQLException {
        return CrudUtil.execute("delete from supplier where supplier_id=?", supplier_id);
    }

    public ArrayList<String> getAllSupplierIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier");

        ArrayList<String> supplier_ids = new ArrayList<>();

        while (rst.next()) {
            supplier_ids.add(rst.getString(1));
        }

        return supplier_ids;
    }

    public SupplierDTO findById(String selectedSupId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier where supplier_id=?", selectedSupId);

        if (rst.next()) {
            return new SupplierDTO(
                    rst.getString(1),  // Supplier ID
                    rst.getString(2),  // Name
                    rst.getString(3)  // Contact
            );
        }
        return null;
    }
}
