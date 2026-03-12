package com.mehdi.petreminder.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.*;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@DisplayName("SqliteRepository Exception Testleri")
class SqliteRepositoryTest {

    @Mock private Connection connection;
    @Mock private PreparedStatement ps;
    @Mock private Statement stmt;
    @Mock private ResultSet rs;

    private SqliteRepository<String> repo;

    @BeforeEach
    void setUp() throws SQLException {
        // Constructor icindeki createTableIfNotExists cagirini mocklayalim
        when(connection.createStatement()).thenReturn(stmt);
        DatabaseMetaData meta = mock(DatabaseMetaData.class);
        when(connection.getMetaData()).thenReturn(meta);
        when(meta.getDatabaseProductName()).thenReturn("SQLite");
        
        repo = new SqliteRepository<String>(connection) {
            @Override protected String getTableName() { return "test_table"; }
            @Override protected String getCreateTableSql() { return "CREATE"; }
            @Override protected String getInsertSql() { return "INSERT"; }
            @Override protected String getUpdateSql() { return "UPDATE"; }
            @Override protected void setInsertParams(PreparedStatement ps, String entity) {}
            @Override protected void setUpdateParams(PreparedStatement ps, String entity) {}
            @Override protected String mapRow(ResultSet rs) { return "mapped"; }
        };
    }

    @Test
    void testSaveThrowsRepositoryException() throws SQLException {
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);
        when(ps.executeUpdate()).thenThrow(new SQLException("Mock DB Error"));
        assertThrows(RepositoryException.class, () -> repo.save("test"));
    }

    @Test
    void testSaveNoGeneratedKeysThrowsRepositoryException() throws SQLException {
        when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);
        when(ps.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(false); // ID donmuyor
        assertThrows(RepositoryException.class, () -> repo.save("test"));
    }

    @Test
    void testFindByIdThrowsRepositoryException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenThrow(new SQLException("Mock DB Error"));
        assertThrows(RepositoryException.class, () -> repo.findById(1));
    }

    @Test
    void testFindAllThrowsRepositoryException() throws SQLException {
        when(connection.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenThrow(new SQLException("Mock DB Error"));
        assertThrows(RepositoryException.class, () -> repo.findAll());
    }

    @Test
    void testUpdateThrowsRepositoryException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenThrow(new SQLException("Mock DB Error"));
        assertThrows(RepositoryException.class, () -> repo.update("test"));
    }

    @Test
    void testDeleteThrowsRepositoryException() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenThrow(new SQLException("Mock DB Error"));
        assertThrows(RepositoryException.class, () -> repo.delete(1));
    }

    @Test
    void testDeleteAllThrowsRepositoryException() throws SQLException {
        when(connection.createStatement()).thenReturn(stmt);
        when(stmt.execute(anyString())).thenThrow(new SQLException("Mock DB Error"));
        assertThrows(RepositoryException.class, () -> repo.deleteAll());
    }

    @Test
    void testCountThrowsRepositoryException() throws SQLException {
        when(connection.createStatement()).thenReturn(stmt);
        when(stmt.executeQuery(anyString())).thenThrow(new SQLException("Mock DB Error"));
        assertThrows(RepositoryException.class, () -> repo.count());
    }

    @Test
    void testCloseCatchesSQLException() throws SQLException {
        when(connection.isClosed()).thenReturn(false);
        doThrow(new SQLException("Close Error")).when(connection).close();
        assertDoesNotThrow(() -> repo.close());
    }

    @Test
    void testCreateTableThrowsRepositoryException() throws SQLException {
        Connection badConnection = mock(Connection.class);
        when(badConnection.createStatement()).thenThrow(new SQLException("Mock DB Error"));
        assertThrows(RepositoryException.class, () -> {
            new SqliteRepository<String>(badConnection) {
                @Override protected String getTableName() { return "test"; }
                @Override protected String getCreateTableSql() { return "CREATE"; }
                @Override protected String getInsertSql() { return "INSERT"; }
                @Override protected String getUpdateSql() { return "UPDATE"; }
                @Override protected void setInsertParams(PreparedStatement p, String e) {}
                @Override protected void setUpdateParams(PreparedStatement p, String e) {}
                @Override protected String mapRow(ResultSet r) { return "mapped"; }
            };
        });
    }
}
