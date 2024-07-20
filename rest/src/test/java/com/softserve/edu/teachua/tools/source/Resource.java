package com.softserve.edu.teachua.tools.source;

import com.softserve.edu.teachua.data.user.IUser;
import com.softserve.edu.teachua.data.user.UserRepository;

import java.util.List;

public enum Resource {

    DEFAULT {
        @Override
        public List<? extends IUser> loadUsers() {
            return List.of(UserRepository.get().admin(), UserRepository.get().defaultUser(), UserRepository.get().user());
        }
    },
    CSV {
        @Override
        public List<? extends IUser> loadUsers() {
            return UserRepository.get().fromCsv();
        }
    },
    JSON {
        @Override
        public List<? extends IUser> loadUsers() {
            return UserRepository.get().fromJson();
        }
    },
    DB {
        @Override
        public List<? extends IUser> loadUsers() {
            return UserRepository.get().fromDB();
        }
    };

    public abstract List<? extends IUser> loadUsers();
}
