import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

class DB{
	static Connection conn = null;
	static Statement stmt = null;
//	static String url = "jdbc:mysql://10.96.121.63:3306/mrn?serverTimezone=UTC";
	String url = "jdbc:mysql://127.0.0.1:3306/mrn?serverTimezone=UTC";
	static String id = "root";
	static String password = "2323";
	void connect(){
		try {
			conn = DriverManager.getConnection(url,id, password);
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

	public static String getId() {
		return id;
	}
	public static void setId(String id) {
		DB.id = id;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		DB.password = password;
	}
	
}