package com.softserve.edu.teachua.tools.source;

import com.softserve.edu.teachua.dao.user.UserJdbcDao;
import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.data.user.User;
import com.softserve.edu.teachua.exception.DaoSQLException;
import com.softserve.edu.teachua.tools.ResourceUtils;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static com.softserve.edu.teachua.tools.ResourceUtils.LIST_USERS;

public class ResourceLoader {

    public static List<IUser> loadUsers(CsvResource csvResource) {
        return fromCsv(csvResource);
    }

    public static List<IUser> loadUsers(JsonResource jsonResource) {
        return fromJson(jsonResource);
    }

    public static List<? extends IUser> loadUsers(DbResource dbResource) {
        return fromDB(dbResource);
    }

    private static List<IUser> fromCsv(CsvResource csvResource) {
        return ResourceUtils.get().convertCsvToEntity(csvResource.path(), IUser.class);
    }

    private static List<IUser> fromJson(JsonResource jsonResource) {
        return Collections.singletonList(
                ResourceUtils.get().convertJsonToEntity(jsonResource.path(), IUser.class));
    }

    private static List<? extends IUser> fromDB(DbResource dbResource) {
        try {
            UserJdbcDao dao = new UserJdbcDao(DriverManager.getConnection(dbResource.url(), dbResource.username(), dbResource.password()));
            ResourceUtils.get().saveAsJson(LIST_USERS, dao.getAll());
            return ResourceUtils.get().convertJsonToList(LIST_USERS, User.class);

        } catch (SQLException e) {
            throw new DaoSQLException(e.getMessage());
        }
    }
}
