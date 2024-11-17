package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.CustomerDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {
    public String getNextCustomerId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer order by customer_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last customer ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("C%03d", newIdIndex); // Return the new customer ID in format Cnnn
        }
        return "C001"; // Return the default customer ID if no data is found
    }

    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into customer values (?,?,?)",
                customerDTO.getCustomer_id(),
                customerDTO.getName(),
                customerDTO.getContact()
        );
    }

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer");

        ArrayList<CustomerDTO> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            CustomerDTO customerDTO = new CustomerDTO(
                    rst.getString(1),  // customer ID
                    rst.getString(2),  // Name
                    rst.getString(3)  // Contact
            );
            customerDTOS.add(customerDTO);
        }
        return customerDTOS;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException {
        return CrudUtil.execute("update customer set name=?, contact=? where customer_id=?",
                customerDTO.getName(),
                customerDTO.getContact(),
                customerDTO.getCustomer_id()
        );
    }

    public boolean deleteCustomer(String customer_id) throws SQLException {
        return CrudUtil.execute("delete from customer where customer_id=?", customer_id);
    }

    public ArrayList<String> getAllCustomerIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select customer_id from customer");

        ArrayList<String> customer_ids = new ArrayList<>();

        while (rst.next()) {
            customer_ids.add(rst.getString(1));
        }

        return customer_ids;
    }

    public CustomerDTO findById(String selectedCusId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from customer where customer_id=?", selectedCusId);

        if (rst.next()) {
            return new CustomerDTO(
                    rst.getString(1),  // customer ID
                    rst.getString(2),  // Name
                    rst.getString(3)  // Contact
            );
        }
        return null;
    }

}
