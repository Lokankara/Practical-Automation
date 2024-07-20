package com.softserve.edu.teachua.dao.user;

import com.softserve.edu.teachua.dao.DaoUtil;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import com.softserve.edu.teachua.exception.DaoSQLException;
import com.softserve.edu.teachua.tools.ResourceUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.softserve.edu.teachua.tools.ResourceUtils.NEW_USER_JSON;

public class UserJdbcDao implements UserDao {

    private static final String GET_ALL = "SELECT * FROM users";
    private static final String GET_BY_ID = "SELECT * FROM users WHERE id = ?";
    private static final String GET_ROLE = "SELECT id FROM roles WHERE name = ?";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id = ?";
    private static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    private static final String UPDATE_USER_STATUS = "UPDATE users SET status = ? WHERE id = ?";
    private static final String DELETE_REFRESH_TOKENS = "DELETE FROM refresh_tokens WHERE user_id = ?";
    private static final String INSERT_USER = "INSERT INTO users (first_name, last_name, phone, email, password, role_id, url_logo, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, phone = ?, email = ?, password = ?, role_id = ?, url_logo = ?, status = ? WHERE id = ?";

    private final Connection conn;

    public UserJdbcDao() {
        this.conn = DaoUtil.getConnection();
    }

    public UserJdbcDao(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean updateStatusUser(int id, boolean status) {
        try (PreparedStatement prepared = conn.prepareStatement(UPDATE_USER_STATUS)) {
            prepared.setBoolean(1, status);
            prepared.setInt(2, id);
            return prepared.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoSQLException("Error updating user status", e);
        }
    }

    @Override
    public long create(LoggedUserDto user) {
        try (PreparedStatement prepared = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            setStatement(user, prepared);
            if (prepared.executeUpdate() > 0) {
                try (ResultSet generatedKeys = prepared.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        saveToFile(user);
                        return generatedKeys.getLong(1);
                    } else {
                        throw new DaoSQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
            throw new DaoSQLException("Creating user failed, no rows affected.");
        } catch (SQLException e) {
            throw new DaoSQLException("Error creating user", e);
        }
    }

    @Override
    public List<LoggedUserDto> getAll() {
        List<LoggedUserDto> users = new ArrayList<>();
        try (PreparedStatement prepared = conn.prepareStatement(GET_ALL)) {
            ResultSet resultSet = prepared.executeQuery();
            while (resultSet.next()) {
                users.add(getUserFrom(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoSQLException("Error getting all users", e);
        }
        return users;
    }

    @Override
    public LoggedUserDto getById(int id) {
        try (PreparedStatement statement = conn.prepareStatement(GET_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return getUserFrom(resultSet);
            } else {
                throw new DaoSQLException("User not found with id " + id);
            }
        } catch (SQLException e) {
            throw new DaoSQLException("Error getting user by id", e);
        }
    }

    @Override
    public LoggedUserDto getUserByEmail(String email) {
        try (PreparedStatement prepared = conn.prepareStatement(GET_USER_BY_EMAIL)) {
            prepared.setString(1, email);
            try (ResultSet resultSet = prepared.executeQuery()) {
                if (resultSet.next()) {
                    LoggedUserDto userFrom = getUserFrom(resultSet);
                    saveToFile(userFrom);
                    return userFrom;
                }
            }
        } catch (SQLException e) {
            throw new DaoSQLException("Error fetching user by email", e);
        }
        throw new DaoSQLException("User not found by email " + email);
    }

    @Override
    public void update(LoggedUserDto user) {
        try (PreparedStatement prepared = conn.prepareStatement(UPDATE_USER)) {
            setStatement(user, prepared);
            prepared.setInt(9, user.id());
            prepared.executeUpdate();
        } catch (SQLException e) {
            throw new DaoSQLException("Error updating user", e);
        }
    }

    @Override
    public boolean deleteById(int id) {

        try (PreparedStatement prepared = conn.prepareStatement(DELETE_REFRESH_TOKENS);
             PreparedStatement stmt2 = conn.prepareStatement(DELETE_USER_BY_ID)) {

            conn.setAutoCommit(false);
            prepared.setInt(1, id);
            prepared.executeUpdate();
            stmt2.setInt(1, id);
            boolean deleted = stmt2.executeUpdate() == 1;
            conn.commit();
            saveToFile(LoggedUserDto.empty());
            return deleted;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                System.err.println("Error during rollback: " + rollbackEx.getMessage());
            }
            throw new DaoSQLException("Error deleting user", e);
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException autoCommitEx) {
                System.err.println("Error restoring auto-commit mode: " + autoCommitEx.getMessage());
            }
        }
    }

    private void setStatement(LoggedUserDto user, PreparedStatement prepared) throws SQLException {
        prepared.setString(1, user.firstName());
        prepared.setString(2, user.lastName());
        prepared.setString(3, user.phone());
        prepared.setString(4, user.email());
        prepared.setString(5, user.password());
        prepared.setInt(6, getRoleIdByName(user.roleName()));
        prepared.setString(7, user.urlLogo());
        prepared.setString(8, user.status());
    }

    private LoggedUserDto getUserFrom(ResultSet resultSet) throws SQLException {
        return new LoggedUserDto(
                resultSet.getInt("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("phone"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                resultSet.getString("role_id"),
                resultSet.getString("url_logo"),
                String.valueOf(resultSet.getBoolean("status"))
        );
    }

    private int getRoleIdByName(String roleName) throws SQLException {
        try (PreparedStatement prepared = conn.prepareStatement(GET_ROLE)) {
            prepared.setString(1, roleName);
            try (ResultSet resultSet = prepared.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    throw new DaoSQLException("Role name not found: " + roleName);
                }
            }
        }
    }

    @Override
    public void saveToFile(LoggedUserDto user) {
        ResourceUtils.get().saveAsJson(NEW_USER_JSON, user);
    }
}
