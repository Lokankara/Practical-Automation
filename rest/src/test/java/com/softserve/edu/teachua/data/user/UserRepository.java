package com.softserve.edu.teachua.data.user;

import com.softserve.edu.teachua.dao.user.UserJdbcDao;
import com.softserve.edu.teachua.dao.user.model.LoggedUserDto;
import com.softserve.edu.teachua.tools.PropertiesUtils;
import com.softserve.edu.teachua.tools.ResourceUtils;
import com.softserve.edu.teachua.tools.source.CsvResource;
import com.softserve.edu.teachua.tools.source.DbResource;
import com.softserve.edu.teachua.tools.source.JsonResource;

import java.util.List;

import static com.softserve.edu.teachua.tools.source.ResourceLoader.loadUsers;

public final class UserRepository {
    private static volatile UserRepository instance = null;
    private final UserJdbcDao userJdbcDao = new UserJdbcDao();

    private UserRepository() {
    }

    public static UserRepository get() {
        if (instance == null) {
            synchronized (UserRepository.class) {
                if (instance == null) {
                    instance = new UserRepository();
                }
            }
        }
        return instance;
    }

    public IUser defaultUser() {
        return customer();
    }

    public IUser admin() {
        return User.get()
                .setId(1)
                .setFirstName("firstName")
                .setLastName("lastName")
                .setPhoneNumber("PhoneNumber")
                .setEmail("email")
                .setPassword("password")
                .build();
    }

    public IUser customer() {
        return User.get()
                .setId(0)
                .setFirstName("Проба")
                .setLastName("Проба")
                .setPhoneNumber("0671234567")
                .setEmail("yagifij495@eqvox.com")
                .setPassword("Qwerty_1")
                .setVisitor(true)
                .build();
    }

    public IUser user() {
        return User.get()
                .setId(0)
                .setFirstName("user")
                .setLastName("userl")
                .setPhoneNumber("00000000")
                .setEmail("user@gmail.com")
                .setPassword("user")
                .setVisitor(true)
                .build();
    }

    public IUser invalidUser() {
        return User.get()
                .setId(0)
                .setFirstName("user1")
                .setLastName("user1")
                .setPhoneNumber("1111111111")
                .setEmail("hahaha@gmail.com")
                .setPassword("qwerty")
                .setVisitor(true)
                .build();
    }

    public IUser newUserFromJson(String jsonFilename) {
        LoggedUserDto dto = ResourceUtils.get().convertJsonToEntity(jsonFilename, LoggedUserDto.class);
        return User.get()
                .setId(dto.id())
                .setFirstName(dto.firstName())
                .setLastName(dto.lastName())
                .setPhoneNumber(dto.phone())
                .setEmail(dto.email())
                .setPassword(dto.password())
                .setVisitor(Boolean.parseBoolean(dto.status()))
                .build();
    }

    public IUser userFromDBById(Integer userId) {
        LoggedUserDto user = userJdbcDao.getById(userId);
        return User.get()
                .setId(userId)
                .setFirstName(user.firstName())
                .setLastName(user.lastName())
                .setPhoneNumber(user.phone())
                .setEmail(user.email())
                .setPassword(user.password())
                .setVisitor(Boolean.parseBoolean(user.status()))
                .build();
    }

    public List<? extends IUser> fromCsv() {
        return loadUsers(new CsvResource(PropertiesUtils.get().readSourceCsv()));
    }

    public List<? extends IUser> fromJson() {
        return loadUsers(new JsonResource(
                PropertiesUtils.get().readSourceJson()));
    }

    public List<? extends IUser> fromDB() {
        return loadUsers(new DbResource(
                PropertiesUtils.get().readDatasourceUrl(),
                PropertiesUtils.get().readDatasourceUsername(),
                PropertiesUtils.get().readDatasourcePassword()));
    }
}
