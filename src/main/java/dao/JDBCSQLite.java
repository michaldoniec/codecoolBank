package dao;

import utils.FileReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;



public class JDBCSQLite implements JDBCDao {
	private Connection connection;
	private ResultSet resultSet;
	private String dbPath;
	private static JDBCSQLite database;

	private JDBCSQLite(String dbPath) throws SQLException, ClassNotFoundException {
		this.dbPath = dbPath;
		Class.forName("org.sqlite.JDBC");
		this.connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		this.connection.setAutoCommit(false);
	}

	public Connection getConnection() {
		return connection;
	}

	public static void createDatabase(String dbPath) throws SQLException, ClassNotFoundException {
		database = new JDBCSQLite(dbPath);
	}

	public static JDBCSQLite getDatabase(){
		return database;
	}

	public ResultSet executeSelectQuery(PreparedStatement query) throws SQLException {
		resultSet = query.executeQuery();
		return  resultSet;
	}

	public void executeDBModifyingQuery(PreparedStatement query) throws SQLException {
		query.executeUpdate();
		this.connection.commit();
		query.close();
	}

	public void closeDatabase() throws SQLException {
		clearResultSet();
		this.connection.close();
	}

	public void resetDatabase() throws SQLException, FileNotFoundException {
		String workingDirectory = System.getProperty("user.dir");
		String fileNameCreateTables = "src/test/resources/sql/createTables.sql";
		String fileNameInsertData = "src/test/resources/sql/insertData.sql";
		String fileNameDropTables = "src/test/resources/sql/dropTables.sql";
		File fileCreateTables = new File(workingDirectory, fileNameCreateTables);
		File fileInsertData = new File(workingDirectory, fileNameInsertData);
		File fileDropTables = new File(workingDirectory, fileNameDropTables);
		FileReader fileReaderCreateTables = new FileReader(fileCreateTables);
		FileReader fileReaderInsertData = new FileReader(fileInsertData);
		FileReader fileReaderDropTables = new FileReader(fileDropTables);
		String[] createTables= fileReaderCreateTables.retrieveStringFromFile().split(";");
		String[] insertData = fileReaderInsertData.retrieveStringFromFile().split(";");
		String[] dropTables = fileReaderDropTables.retrieveStringFromFile().split(";");

		for(int index = 0; index < dropTables.length; index++){
			PreparedStatement dropTableQuery = this.connection.prepareStatement(dropTables[index]);
			dropTableQuery.executeUpdate();
			this.connection.commit();
		}

		System.out.println("Tables dropped");

		for(int index = 0; index < createTables.length; index++){
			PreparedStatement createTableQuery = this.connection.prepareStatement(createTables[index]);
			createTableQuery.executeUpdate();
			this.connection.commit();
		}

		System.out.println("Tables created");

		for(int index = 0; index < insertData.length; index++){
			PreparedStatement insertDataQuery = this.connection.prepareStatement(insertData[index]);
			insertDataQuery.executeUpdate();
			this.connection.commit();
		}

		System.out.println("Tables filled with mock data");
	}

	private void clearResultSet() throws  SQLException {
		if(this.resultSet != null){
			this.resultSet.close();
		}
	}
}
