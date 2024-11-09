package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.dto.PaymentDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select payment_id from payment order by payment_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1); // Last payment ID
            String substring = lastId.substring(1); // Extract the numeric part
            int i = Integer.parseInt(substring); // Convert the numeric part to integer
            int newIdIndex = i + 1; // Increment the number by 1
            return String.format("P%03d", newIdIndex); // Return the new payment ID in format Pnnn
        }
        return "P001"; // Return the default payment ID if no data is found
    }

    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into payment values (?,?,?)",
                paymentDTO.getPayment_id(),
                paymentDTO.getAmount(),
                paymentDTO.getDate()
        );
    }

    public ArrayList<PaymentDTO> getAllPayments() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from payment");

        ArrayList<PaymentDTO> paymentDTOS = new ArrayList<>();

        while (rst.next()) {
            PaymentDTO paymentDTO = new PaymentDTO(
                    rst.getString(1),  // Payment ID
                    rst.getDouble(2),  // Amount
                    rst.getDate(3).toLocalDate()  // Date
            );
            paymentDTOS.add(paymentDTO);
        }
        return paymentDTOS;
    }

    public boolean updatePayment(PaymentDTO paymentDTO) throws SQLException {
        return CrudUtil.execute(
                "update payment set amount=?, date=? where payment_id=?",
                paymentDTO.getAmount(),
                paymentDTO.getDate(),
                paymentDTO.getPayment_id()
        );
    }

    public boolean deletePayment(String payment_id) throws SQLException {
        return CrudUtil.execute("delete from payment where payment_id=?", payment_id);
    }

    public ArrayList<String> getAllPaymentIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select payment_id from payment");

        ArrayList<String> payment_ids = new ArrayList<>();

        while (rst.next()) {
            payment_ids.add(rst.getString(1));
        }

        return payment_ids;
    }

    public PaymentDTO findById(String selectedPayId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from payment where payment_id=?", selectedPayId);

        if (rst.next()) {
            return new PaymentDTO(
                    rst.getString(1),  // Payment ID
                    rst.getDouble(2),  // Amount
                    rst.getDate(3).toLocalDate()  // Date
            );
        }
        return null;
    }
}
