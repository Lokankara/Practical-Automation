package com.softserve.edu.teachua.tests.dao;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import java.sql.ResultSet;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class DaoTest {
    protected boolean hasColumn(ResultSet columns, String columnName) throws SQLException {
        while (columns.next()) {
            if (columnName.equals(columns.getString("COLUMN_NAME"))) {
                return true;
            }
        }
        return false;
    }
}
