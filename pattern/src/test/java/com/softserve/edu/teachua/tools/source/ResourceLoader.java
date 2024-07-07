package com.softserve.edu.teachua.tools.source;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.softserve.edu.teachua.data.user.IUser;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static com.softserve.edu.teachua.data.user.UserRepository.createUserFromCsvRow;
import static com.softserve.edu.teachua.data.user.UserRepository.createUserFromExcelRow;
import static com.softserve.edu.teachua.data.user.UserRepository.createUserFromResultSet;

public class ResourceLoader {

    private static final Logger logger = Logger.getLogger(ResourceLoader.class.getName());

    public static List<IUser> loadUsers(CsvResource csvResource) {
        return fromCsv(csvResource);
    }

    public static List<IUser> loadUsers(ExcelResource excelResource) {
        return fromExcel(excelResource);
    }

    public static List<IUser> loadUsers(DbResource dbResource) {
        return fromDB(dbResource);
    }

    private static List<IUser> fromCsv(CsvResource csvResource) {
        List<IUser> users = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(csvResource.path()))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                IUser user = createUserFromCsvRow(nextLine);
                users.add(user);
            }
        } catch (IOException | CsvValidationException e) {
            logger.error("Error reading CSV file");
        }
        return users;
    }

    private static List<IUser> fromExcel(ExcelResource excelResource) {
        List<IUser> users = new ArrayList<>();
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(excelResource.path()))) {
            for (Row row : workbook.getSheetAt(0)) {
                String[] rowData = new String[row.getLastCellNum()];
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                    rowData[i] = cell == null ? "" : cell.toString();
                }
                IUser user = createUserFromExcelRow(rowData);
                users.add(user);
            }
        } catch (IOException e) {
            logger.error("Error reading Excel file");
        }
        return users;
    }

    private static List<IUser> fromDB(DbResource dbResource) {
        List<IUser> users = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(dbResource.url(), dbResource.username(), dbResource.password());
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(dbResource.query())) {
            while (resultSet.next()) {
                IUser user = createUserFromResultSet(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            logger.error("Error executing DB query");
        }
        return users;
    }
}
