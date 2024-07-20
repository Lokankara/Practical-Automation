package com.softserve.edu.teachua.tests.dao;

import com.softserve.edu.teachua.dao.DaoUtil;
import com.softserve.edu.teachua.dao.club.ClubDao;
import com.softserve.edu.teachua.dao.club.ClubJdbcDao;
import com.softserve.edu.teachua.dao.club.model.ClubDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

class ClubJdbcDaoTest extends DaoTest {

    private static final ClubDao clubDao = new ClubJdbcDao(DaoUtil.getConnection());

    @Test
    void testFieldsClub() throws SQLException {
        String[] fieldNames = {
                "id",
                "age_from",
                "age_to",
                "center_external_id",
                "club_external_id",
                "contacts",
                "description",
                "feedback_count",
                "is_approved",
                "is_online",
                "name",
                "rating",
                "url_background",
                "url_logo",
                "url_web",
                "center_id",
                "user_id"
        };

        DatabaseMetaData metaData = DaoUtil.getConnection().getMetaData();
        ResultSet columns = metaData.getColumns(null, null, "clubs", null);

        for (String fieldName : fieldNames) Assertions.assertTrue(hasColumn(columns, fieldName), fieldName);
    }

    @Test
    void testGetClubById() {
        ClubDto club = clubDao.getClubById(27);

        Assertions.assertNotNull(club);
        Assertions.assertNotNull(club.name());
        Assertions.assertNotNull(club.description());
        Assertions.assertNotNull(club.categories());
        Assertions.assertTrue(club.ageFrom() > 0);
        Assertions.assertTrue(club.ageTo() > club.ageFrom());
        Assertions.assertNotNull(club.urlLogo());
        Assertions.assertNotNull(club.urlBackground());
        Assertions.assertTrue(club.feedbackCount() >= 0);
        Assertions.assertNotNull(club.rating());
        Assertions.assertEquals(27, club.id());
        Assertions.assertEquals("IT освіта: курси \"ГРАНД\"", club.name());
    }
}
