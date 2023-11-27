package crm_project_02.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlConfig {


	public static Connection getConnection() {
		try {
			//Khai báo Driver sử dụng cho JDBC tương ứng với CSDL cần kết nối
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Khai báo Driver sẽ mở kết nối tói CSDL nào
            return DriverManager.getConnection("jdbc:mysql://localhost:3307/crm","root", "phong2002");
        } catch (Exception ex) {
            //Lỗi xảy ra sẽ chạy vào đây
        	System.out.println("Lỗi kết nối database " + ex.getLocalizedMessage());
        	return null;
        }
	}
}
