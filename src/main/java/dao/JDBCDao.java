package dao;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by michal on 09.06.17.
 */
public interface JDBCDao {

	Connection getConnection();

	ResultSet executeSelectQuery(PreparedStatement query) throws SQLException;

	void executeDBModifyingQuery(PreparedStatement query) throws SQLException;

	void closeDatabase() throws SQLException;

	void resetDatabase() throws SQLException, FileNotFoundException;




}
