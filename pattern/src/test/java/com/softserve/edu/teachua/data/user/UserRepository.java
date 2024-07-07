package com.softserve.edu.teachua.data.user;

import com.softserve.edu.teachua.data.comment.CommentContents;
import com.softserve.edu.teachua.tools.source.CsvResource;
import com.softserve.edu.teachua.tools.source.DbResource;
import com.softserve.edu.teachua.tools.source.ExcelResource;
import com.softserve.edu.teachua.tools.PropertiesUtils;
import com.softserve.edu.teachua.tools.source.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.softserve.edu.teachua.tools.source.ResourceLoader.loadUsers;

public final class UserRepository {
    private static final String TIME_TEMPLATE = "HH_mm_ss_S";
    private static volatile UserRepository instance = null;
    private List<IUser> users;

    private UserRepository() {
    }

    private void initSource() {
        if (users == null) {
            String propertySource = PropertiesUtils.get().readUserSource();
            if (propertySource.equals(PropertiesUtils.ERROR_READ_PROPERTY)) {
                users = setDefaultSource();
            } else {
                Resource resource = switch (propertySource) {
                    case "csv" -> Resource.CSV;
                    case "xls" -> Resource.EXCEL;
                    case "db" -> Resource.DB;
                    default -> Resource.DEFAULT;
                };
                users = resource.loadUsers();
                users = users.isEmpty() ? setDefaultSource() : users;
            }
        }
    }

    private List<IUser> setDefaultSource() {
        return List.of(admin(), defaultUser(), newUser());
    }

    public static UserRepository get() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                    instance.initSource();
                }
            }
        }
        return instance;
    }

    public List<IUser> getUsers() {
        return users;
    }


    public IUser defaultUser() {
        return customer();
    }

    public IUser admin() {
        return User.get()
                .setFirstName("firstName")
                .setLastName("lastName")
                .setPhoneNumber("PhoneNumber")
                .setEmail("emailr@gmail.com")
                .setPassword("password")
                .setComments(List.of(CommentContents.DEFAULT_COMMENT))
                .build();
    }

    public IUser customer() {
        return User.get()
                .setFirstName("Проба")
                .setLastName("Проба")
                .setPhoneNumber("0671234567")
                .setEmail("yagifij495@eqvox.com")
                .setPassword("Qwerty_1")
                .setComments(List.of(CommentContents.SECOND_COMMENT))
                .setVisitor(true)
                .build();
    }

    public IUser user() {
        return User.get()
                .setFirstName("user")
                .setLastName("userl")
                .setPhoneNumber("00000000")
                .setEmail("user@gmail.com")
                .setPassword("user")
                .setComments(List.of(CommentContents.DEFAULT_COMMENT))
                .setVisitor(true)
                .build();
    }

    public IUser newUser() {
        String currentTime = new SimpleDateFormat(TIME_TEMPLATE).format(new Date());
        return User.get()
                .setFirstName("user")
                .setLastName("userl")
                .setPhoneNumber("00000000")
                .setEmail("user" + currentTime + "@gmail.com")
                .setPassword(System.getenv().get("NEW_PASSWORD"))
                .setComments(List.of(CommentContents.DEFAULT_COMMENT))
                .setVisitor(true)
                .build();
    }

    public IUser invalidUser() {
        return User.get()
                .setFirstName("user1")
                .setLastName("user1")
                .setPhoneNumber("1111111111")
                .setEmail("hahaha@gmail.com")
                .setPassword("qwerty")
                .setComments(List.of(CommentContents.DEFAULT_COMMENT))
                .setVisitor(true)
                .build();
    }

    public List<IUser> fromCsv() {
        return loadUsers(new CsvResource(PropertiesUtils.get().readSourceCsv()));
    }

    public List<IUser> fromExcel() {
        return loadUsers(new ExcelResource(
                PropertiesUtils.get().readSourceExcel()));
    }

    public List<IUser> fromDB() {
        return loadUsers(new DbResource(
                PropertiesUtils.get().readDatasourceUrl(),
                PropertiesUtils.get().readDatasourceUsername(),
                PropertiesUtils.get().readDatasourcePassword(),
                "SELECT * FROM USERS"));
    }

    public static IUser createUserFromCsvRow(String[] rowData) {
        return User.get()
                .setFirstName(rowData[0])
                .setLastName(rowData[1])
                .setPhoneNumber(rowData[2])
                .setEmail(rowData[3])
                .setPassword(rowData[4])
                .setComments(List.of(CommentContents.DEFAULT_COMMENT))
                .setVisitor(true)
                .build();
    }

    public static IUser createUserFromExcelRow(String[] rowData) {
        return User.get()
                .setFirstName(rowData[0])
                .setLastName(rowData[1])
                .setPhoneNumber(rowData[2])
                .setEmail(rowData[3])
                .setPassword(rowData[4])
                .setComments(List.of(CommentContents.DEFAULT_COMMENT))
                .setVisitor(true)
                .build();
    }

    public static IUser createUserFromResultSet(ResultSet resultSet) throws SQLException {
        return User.get()
                .setFirstName(resultSet.getString("first_name"))
                .setLastName(resultSet.getString("last_name"))
                .setPhoneNumber(resultSet.getString("phone_number"))
                .setEmail(resultSet.getString("email"))
                .setPassword(resultSet.getString("password"))
                .setComments(List.of(CommentContents.DEFAULT_COMMENT))
                .setVisitor(true)
                .build();
    }
}
