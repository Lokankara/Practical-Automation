package service;

import dao.ChildDB;
import exception.ValidationException;
import model.Child;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChildServiceTest {

    @Mock
    private ChildDB dao;
    @InjectMocks
    private ChildService childService;
    private List<Child> expectedChildren;
    private final long id = 1L;
    private Child tony;
    private Child john;
    private final String errorMessage = "Some problem with database or sql query";

    @BeforeEach
    public void setup() {
        john = new Child(id, "John", "Snow", LocalDate.now().minusYears(1), List.of());
        tony = new Child("Tony", "Stark", LocalDate.now().minusYears(2));
        expectedChildren = List.of(john, tony);
//        childService = new ChildService(dao);
    }

    private static Stream<Arguments> provideNamesForAddOrUpdate() {
        return Stream.of(
                Arguments.of( false),
                Arguments.of( true)
        );
    }

    @Test
    @DisplayName("Given a valid child, when add is called, then the child is added and returned")
    void testAddChild() throws SQLException {
        when(dao.add(tony)).thenReturn(tony);

        Child result = childService.add(tony);

        Assertions.assertThat(result).isEqualTo(tony);
        verify(dao, times(1)).add(tony);
    }

    @Test
    @DisplayName("Given a child with a future birthdate, when add is called, then a ValidationException is thrown")
    void testAddChildWithFutureBirthdate() {
        LocalDate birthDate = LocalDate.now().plusDays(1);
        Child child = new Child("Arya", "Stark", birthDate);
        String errorMessage = String.format("Birthdate %s can't be in future", birthDate);

        Throwable thrown = Assertions.catchThrowable(() -> childService.add(child));
        Assertions.assertThat(thrown)
                .isInstanceOf(ValidationException.class)
                .hasMessageContaining(errorMessage);
    }

    @Test
    @DisplayName("Given a new child without an id, when add is called, then a child with an id is returned")
    void testAddNewChildAndCheckId() throws SQLException {
        Child snow = new Child(null, "John", "Snow", LocalDate.now().minusYears(1), List.of());
        when(dao.add(snow)).thenReturn(john);

        var actual = childService.add(snow);

        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(john);
        verify(dao, times(1)).add(snow);
    }

    @Test
    @DisplayName("Given a child with a null birthdate, when add is called, then a ValidationException is thrown")
    void testAddChildWithNullBirthDate() {
        String errorMessage = "Birthdate can't be null";
        Child child = new Child(null, "John", "Doe", null);

        Throwable thrown = Assertions.catchThrowable(() -> childService.add(child));

        Assertions.assertThat(thrown).isInstanceOf(ValidationException.class)
                .hasMessageContaining(errorMessage);
    }

    @Test
    @DisplayName("When adding a child with a birthdate in the future, then a ValidationException is thrown")
    void testNegativeAddChildErrorMessageInTheFuture() {
        Child unborn = new Child(null, "John", "Snow", LocalDate.now().plusDays(1));
        String errorMessage = String.format("Birthdate %s can't be in future", unborn.birthDate());

        Throwable exception = assertThrows(ValidationException.class, () -> childService.add(unborn));

        Assertions.assertThat(exception.getMessage()).contains(errorMessage);
    }

    @ParameterizedTest()
    @MethodSource("provideNamesForAddOrUpdate")
    @DisplayName("When updating a child, then the updated child is returned")
    void testUpdateChild(boolean updateResult) throws SQLException {
        when(dao.update(john)).thenReturn(updateResult);

        Child result = childService.add(john);

        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(john);
        verify(dao, times(1)).update(john);
    }

    @Test
    @DisplayName("When updating a child, then the updated child is returned and matches the original child")
    void testUpdateChildAndCheckRecursiveComparison() throws SQLException {
        when(dao.update(john)).thenReturn(true);

        var actual = childService.add(john);

        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(john);
        verify(dao, times(1)).update(john);
    }

    @Test
    @DisplayName("When a SQLException occurs during update, then a RuntimeException is thrown")
    void testNegativeUpdateChildSQLException() throws SQLException {
        when(dao.update(john)).thenThrow(new SQLException(errorMessage));

        Throwable thrown = Assertions.catchThrowable(() -> childService.add(john));

        Assertions.assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(SQLException.class)
                .hasMessageContaining(errorMessage);
        verify(dao, times(1)).update(john);
    }

    @Test
    @DisplayName("Given a SQLException when getAll is called, then a RuntimeException is thrown")
    void testGetAllChildrenSQLException() throws SQLException {
        when(dao.getAll()).thenThrow(SQLException.class);

        Throwable thrown = Assertions.catchThrowable(() -> childService.getAll());
        Assertions.assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(SQLException.class);
        verify(dao, times(1)).getAll();
    }

    @Test
    @DisplayName("Given a SQLException when getByName is called, then a RuntimeException is thrown")
    void testGetChildrenByNameSQLException() throws SQLException {
        final String name = "John";

        when(dao.getByName(name)).thenThrow(new SQLException(errorMessage));

        Throwable thrown = Assertions.catchThrowable(() -> childService.byName(name));
        Assertions.assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(SQLException.class)
                .hasMessageContaining(errorMessage);
        verify(dao, times(1)).getByName(name);
    }

    @ParameterizedTest
    @ValueSource(strings = {"John", "Tony"})
    @DisplayName("Given a name, when getByName is called, then children with that name are returned")
    void testGetChildrenByName(String name) throws SQLException {
        when(dao.getByName(name)).thenReturn(expectedChildren);
        List<Child> actualChildren = childService.byName(name);

        Assertions.assertThat(actualChildren)
                .hasSameElementsAs(List.of(john, tony))
                .isEqualTo(expectedChildren);
        verify(dao, times(1)).getByName(name);
    }

    @Test
    @DisplayName("When getAll is invoked, it should return a list of all children in the database")
    void testGetAllChildren() throws SQLException {
        when(dao.getAll()).thenReturn(expectedChildren);

        List<Child> actualChildren = childService.getAll();

        Assertions.assertThat(actualChildren)
                .hasSameElementsAs(List.of(john, tony))
                .isEqualTo(expectedChildren);
        verify(dao, times(1)).getAll();
    }

    @Test
    @DisplayName("When getAll is invoked and no children exist, it should return an empty list")
    void testGetAllChildrenEmptyResult() throws SQLException {
        when(dao.getAll()).thenReturn(List.of());

        var actual = childService.getAll();

        verify(dao, times(1)).getAll();
        Assertions.assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("When getAll is invoked and there's a SQLException, it should throw a RuntimeException")
    void negativeGetAllChildren() throws SQLException {
        when(dao.getAll()).thenThrow(new SQLException(errorMessage));

        Throwable exception = assertThrows(RuntimeException.class, () -> childService.getAll());

        Assertions.assertThat(exception)
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(SQLException.class)
                .hasMessageContaining(errorMessage);;
    }

    @ParameterizedTest
    @ValueSource(strings = {"John", "Tony"})
    @DisplayName("When getByName is invoked with a name, it should return a list of children with that name")
    void testGetChildrenByNameParams(String name) throws SQLException {
        when(dao.getByName(name)).thenReturn(expectedChildren);

        List<Child> actualChildren = childService.byName(name);

        Assertions.assertThat(actualChildren)
                .isEqualTo(expectedChildren)
                .hasSameElementsAs(List.of(john, tony));

        verify(dao, times(1)).getByName(name);
    }


    @Test
    @DisplayName("When delete is invoked and there's a SQLException, it should throw a RuntimeException")
    void testDeleteChildSQLException() throws SQLException {
        when(dao.get(id)).thenThrow(new SQLException(errorMessage));

        Throwable thrown = Assertions.catchThrowable(() -> childService.delete(id));
        Assertions.assertThat(thrown)
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(SQLException.class)
                .hasMessageContaining(errorMessage);
        verify(dao, times(1)).get(id);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("When delete is invoked with an id, it should return the child object that was deleted")
    void testDeleteChildRecursiveComparison(boolean deletedResult) throws SQLException {
        when(dao.get(id)).thenReturn(john);
        when(dao.delete(id)).thenReturn(deletedResult);

        Child actual = childService.delete(id);

        Assertions.assertThat(actual).isEqualTo(john).usingRecursiveComparison().isEqualTo(john);
        verify(dao, times(1)).get(id);
        verify(dao, times(1)).delete(id);
    }
}
