# excel-to-mysql
使用JDBC+poi库实现读取excel信息导入mysql当中

程序针对的excel格式全部写死 可在工具类 `NewsUtil` 中修改 

输入数据库账户名密码 excel路径即可运行

````
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
````java
上方修改数据库信息
````
    public static void main(String[] args) {
        List<News> news = NewsUtil.getNews("anti.xlsx");
        WriteToDataBase(news);
    }
````java
上方修改文件路径

使用的excel格式 在`NewsUtil`类中的内层循环中可以修改
