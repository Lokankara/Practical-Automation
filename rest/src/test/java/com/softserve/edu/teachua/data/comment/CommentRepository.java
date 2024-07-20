package com.softserve.edu.teachua.data.comment;

import com.softserve.edu.teachua.data.club.ClubRepository;

public final class CommentRepository {

    private static volatile CommentRepository instance = null;

    private CommentRepository() {
    }

    public static CommentRepository get() {
        if (instance == null) {
            synchronized (CommentRepository.class) {
                if (instance == null) {
                    instance = new CommentRepository();
                }
            }
        }
        return instance;
    }

    public IComment defaultComment() {
        return Comment.get()
                .setAuthor("Проба Проба")
                .setDatetime("2024-06-22 10:59:59")
                .setText("Проба Коментар")
                .setClub(ClubRepository.get().defaultClub())
                .build();
    }

    public IComment firstComment() {
        return Comment.get()
                .setAuthor("Проба Проба")
                .setDatetime("2024-06-22 10:59:59")
                .setText("Проба Коментар")
                .setClub(ClubRepository.get().americanGymnastics())
                .build();
    }

    public IComment secondComment() {
        return Comment.get()
                .setAuthor("Проба Проба")
                .setDatetime("2024-06-22 10:59:59")
                .setText("Проба Коментар44")
                .setClub(ClubRepository.get().vector())
                .build();
    }
}
