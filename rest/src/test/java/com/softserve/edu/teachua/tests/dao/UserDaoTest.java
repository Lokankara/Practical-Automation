package com.softserve.edu.teachua.tests.dao;

import com.softserve.edu.teachua.dao.DaoUtil;
import com.softserve.edu.teachua.dao.user.UserJdbcDao;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.data.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.softserve.edu.teachua.tools.ResourceUtils.NEW_USER_JSON;

class UserDaoTest extends DaoTest {
    private final UserJdbcDao userDao = new UserJdbcDao();

    @Test
    @Order(0)
    @DisplayName("Test getColumnNames: Verify column names in 'users' table exist")
    void testGetColumnNames() throws SQLException {
        DatabaseMetaData metaData = DaoUtil.getConnection().getMetaData();
        ResultSet columns = metaData.getColumns(null, null, "users", null);

        Assertions.assertTrue(hasColumn(columns, "id"), "Column 'id' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "email"), "Column 'email' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "first_name"), "Column 'first_name' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "last_name"), "Column 'last_name' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "password"), "Column 'password' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "phone"), "Column 'phone' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "provider"), "Column 'provider' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "provider_id"), "Column 'provider_id' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "status"), "Column 'status' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "url_logo"), "Column 'url_logo' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "verification_code"), "Column 'verification_code' not found in feedbacks table");
        Assertions.assertTrue(hasColumn(columns, "role_id"), "Column 'role_id' not found in feedbacks table");
    }

    @Test
    @Order(1)
    @DisplayName("Create a new user and verify that user is successfully created")
    void testCreateUser() {
        LoggedUserDto newUser = LoggedUserDto.createUser();
        long actual = userDao.create(newUser);
        Assertions.assertTrue(actual > 0, "User should be created successfully");
    }

    @Test
    @Order(2)
    @DisplayName("Retrieve all users from the database and verify the list is not empty")
    void testGetAllUsers() {

        List<LoggedUserDto> users = userDao.getAll();

        Assertions.assertNotNull(users);
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("Retrieve user by email and verify existence")
    void testGetUserByEmail() {
        IUser user = UserRepository.get().newUserFromJson(NEW_USER_JSON);

        LoggedUserDto userByEmail = userDao.getUserByEmail(user.getEmail());

        Assertions.assertNotNull(userByEmail);
    }

    @Test
    @Order(4)
    @DisplayName("Update user status to true in the database and verify status change")
    void testUpdateStatusTrue() {
        IUser user = UserRepository.get().newUserFromJson(NEW_USER_JSON);

        boolean isUpdated = userDao.updateStatusUser(user.getId(), true);
        LoggedUserDto dto = userDao.getById(user.getId());

        Assertions.assertTrue(isUpdated);
        Assertions.assertTrue(Boolean.parseBoolean(dto.status()));
        Assertions.assertEquals(user.getId(), dto.id());
        Assertions.assertEquals(user.getEmail(), dto.email());
        Assertions.assertEquals(user.getFirstName(), dto.firstName());
    }

    @Test
    @Order(4)
    @DisplayName("Update user status to false in the database and verify status change")
    void testUpdateStatusFalse() {
        IUser user = UserRepository.get().newUserFromJson(NEW_USER_JSON);

        boolean isUpdated = userDao.updateStatusUser(user.getId(), false);
        LoggedUserDto dto = userDao.getById(user.getId());

        Assertions.assertTrue(isUpdated);
        Assertions.assertFalse(Boolean.parseBoolean(dto.status()));
        Assertions.assertEquals(user.getId(), dto.id());
        Assertions.assertEquals(user.getEmail(), dto.email());
        Assertions.assertEquals(user.getFirstName(), dto.firstName());
    }

    @Test
    @Order(8)
    @DisplayName("Delete user by ID from the database and verify deletion")
    void testDeleteUserById() {
        IUser user = UserRepository.get().newUserFromJson(NEW_USER_JSON);
        LoggedUserDto dto = userDao.getById(user.getId());

        boolean deleted = userDao.deleteById(user.getId());

        Assertions.assertNotNull(dto);
        Assertions.assertEquals(user.getId(), dto.id());
        Assertions.assertEquals(user.getEmail(), dto.email());
        Assertions.assertTrue(deleted);
    }
}
