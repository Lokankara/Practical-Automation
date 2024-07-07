package com.softserve.edu.teachua.tools.source;

import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.data.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

public enum Resource {

    DEFAULT {
        @Override
        public List<IUser> loadUsers() {
            return List.of(UserRepository.get().admin(), UserRepository.get().defaultUser(), UserRepository.get().newUser());
        }
    },
    CSV {
        @Override
        public List<IUser> loadUsers() {
            return UserRepository.get().fromCsv();
        }
    },
    EXCEL {
        @Override
        public List<IUser> loadUsers() {
            return UserRepository.get().fromExcel();
        }
    },
    DB {
        @Override
        public List<IUser> loadUsers() {
            return UserRepository.get().fromDB();
        }
    };

    public abstract List<IUser> loadUsers();
}
