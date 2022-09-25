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
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + ID_FIELD + " INT PRIMARY KEY AUTO_INCREMENT, " + NAME_FIELD + " VARCHAR(255) NOT NULL, " + LASTNAME_FIELD + " VARCHAR(255) NOT NULL, " + AGE_FIELD + " INT NOT NULL" + ")";
        try (Connection conn = Util.getDbConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement prSt = conn.prepareStatement(query)) {
                prSt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        try (Connection conn = Util.getDbConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement prSt = conn.prepareStatement(query)) {
                prSt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO " + TABLE_NAME + "(" + NAME_FIELD + "," + LASTNAME_FIELD + "," + AGE_FIELD + ")" + "VALUES(?,?,?)";
        try (Connection conn = Util.getDbConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement prSt = conn.prepareStatement(query)) {
                prSt.setString(1, name);
                prSt.setString(2, lastName);
                prSt.setString(3, String.valueOf(age));
                prSt.executeUpdate();
                conn.commit();
                System.out.println("User with name - " + name + " is added to database");
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=" + id;
        try (Connection conn = Util.getDbConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement prSt = conn.prepareStatement(query)) {
                prSt.executeUpdate();
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.rollback();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        try (Connection conn = Util.getDbConnection()) {
            PreparedStatement prSt = conn.prepareStatement(query);
            ResultSet resSet = prSt.executeQuery();
            while (resSet.next()) {
                usersList.add(new User(resSet.getInt(ID_FIELD), resSet.getString(NAME_FIELD), resSet.getString(LASTNAME_FIELD), resSet.getByte(AGE_FIELD)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        dropUsersTable();
        createUsersTable();
    }
}
