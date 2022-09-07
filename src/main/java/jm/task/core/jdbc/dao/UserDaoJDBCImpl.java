package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String TABLE_NAME = "user";
    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String LASTNAME_FIELD = "lastname";
    private static final String AGE_FIELD = "age";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID_FIELD + " INT PRIMARY KEY AUTO_INCREMENT, "
                + NAME_FIELD + " VARCHAR(255) NOT NULL, "
                + LASTNAME_FIELD + " VARCHAR(255) NOT NULL, "
                + AGE_FIELD + " INT NOT NULL"
                + ")";
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            prSt.executeUpdate();
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            prSt.executeUpdate();
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO " + TABLE_NAME + "(" + NAME_FIELD + "," + LASTNAME_FIELD + "," + AGE_FIELD + ")"
                + "VALUES(?,?,?)";
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            prSt.setString(1, name);
            prSt.setString(2, lastName);
            prSt.setString(3, String.valueOf(age));
            prSt.executeUpdate();
            conn.commit();
            System.out.println("User with name - " + name + " is added to database");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=" + id;
        Connection conn = null;
        try {
            conn = Util.getDbConnection();
            conn.setAutoCommit(false);
            PreparedStatement prSt = conn.prepareStatement(query);
            prSt.executeUpdate();
            conn.commit();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public List<User> getAllUsers() {
        ResultSet resSet;
        List<User> usersList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection conn = Util.getDbConnection()) {
            PreparedStatement prSt = conn.prepareStatement(query);
            resSet = prSt.executeQuery();
            while (resSet.next()) {
                usersList.add(new User(resSet.getInt(ID_FIELD),
                        resSet.getString(NAME_FIELD),
                        resSet.getString(LASTNAME_FIELD),
                        resSet.getByte(AGE_FIELD)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return usersList;
    }

    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
