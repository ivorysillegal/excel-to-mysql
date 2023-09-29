import pojo.News;
import util.NewsUtil;

import java.sql.*;
import java.util.List;

public class ObjectToDatabase {

    private Connection connection;

    public ObjectToDatabase() {
        // 在构造函数中初始化数据库连接
        try {
            String url = "jdbc:mysql://localhost:3306/news";
            String username = "1";
            String password = "1";
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<News> news = NewsUtil.getNews("anti.xlsx");
        WriteToDataBase(news);
    }

    public void insertObjectIntoDatabase(News news) {
        String insertQuery = "INSERT INTO news.news (pic_url, title, main,upload_time) VALUES (?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // 设置SQL语句中的参数
            preparedStatement.setString(1, news.getPic_url());
            preparedStatement.setString(2, news.getTitle());
            preparedStatement.setString(3, news.getMain());
            preparedStatement.setDate(4, Date.valueOf(news.getUpload_time()));
            // 设置更多参数...

            // 执行插入操作
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }



    private static void WriteToDataBase(List<News> news) {
        ObjectToDatabase objectToDatabase = new ObjectToDatabase();
        int i = 0;
        for (News myObject : news) {
            // 插入对象到数据库
            objectToDatabase.insertObjectIntoDatabase(myObject);
            System.out.println(i++);
            if (i == 60){
                break;
            }
        }
        // 关闭数据库连接
        objectToDatabase.closeConnection();
    }
}
