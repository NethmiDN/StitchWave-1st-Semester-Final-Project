package lk.ijse.stitchwave1stsemesterfinalproject.model;

import lk.ijse.stitchwave1stsemesterfinalproject.db.DBConnection;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.SupplierDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.UserDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserModel {
    public boolean saveUser(UserDTO userDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "insert into user values (?,?,?,?,?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, userDTO.getUserId());
        pst.setString(2, userDTO.getFirstName());
        pst.setString(3, userDTO.getLastName());
        pst.setString(4, userDTO.getUsername());
        pst.setString(5, userDTO.getEmail());
        pst.setString(6, userDTO.getPassword());

        return pst.executeUpdate() > 0;
    }

    public boolean updateUser(UserDTO userDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "update user set firstName = ?, lastName = ?, username = ?, email = ?, password = ? where userId = ?";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, userDTO.getFirstName());
        pst.setString(2, userDTO.getLastName());
        pst.setString(3, userDTO.getUsername());
        pst.setString(4, userDTO.getEmail());
        pst.setString(5, userDTO.getPassword());
        pst.setString(6, userDTO.getUserId());

        return pst.executeUpdate() > 0;
    }

    public boolean isEmailExists(String email) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select email from user where email = ?";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();

        return rs.next();
    }

    public String getNextUserId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select userId from user order by userId desc limit 1";

        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            String string = rs.getString(1);
            String subString = string.substring(1);
            int lastIdIndex = Integer.parseInt(subString);
            int nextIdIndex = lastIdIndex + 1;

            String nextId = String.format("U%03d", nextIdIndex);
            return nextId;
        }
        return "U001";
    }

    public List<UserDTO> getAllUsers() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "select * from user";


        PreparedStatement pst = connection.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
//
        List<UserDTO> userList = new ArrayList<>();

        while (rs.next()) {
            UserDTO user = new UserDTO(
                    rs.getString("userId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password")
            );
            userList.add(user);
        }

        return userList;
    }

    public ArrayList<String> getAllUserIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select userId from user");

        ArrayList<String> user_ids = new ArrayList<>();

        while (rst.next()) {
            user_ids.add(rst.getString(1));
        }

        return user_ids;
    }

    public UserDTO findById(String selectedUserId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from user where userId=?", selectedUserId);

        if (rst.next()) {
            return new UserDTO(
                    rst.getString(1),  // user ID
                    rst.getString(2),  // fName
                    rst.getString(3),  // lname
                    rst.getString(4), //uname
                    rst.getString(5), //email
                    rst.getString(6) //password
            );
        }
        return null;
    }
}