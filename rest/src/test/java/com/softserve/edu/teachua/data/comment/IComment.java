package com.softserve.edu.teachua.data.comment;

import com.softserve.edu.teachua.data.club.IClub;

public interface IComment {
    String getAuthor();

    String getDatetime();

    String getText();

    IClub getClub();
}
