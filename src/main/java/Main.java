import java.sql.*;
import java.util.Scanner;

public class Main {
    //параметри для підключення до БД
    static final String USER = "root";
    static final String PASSWORD = "rootroot";
    static final String URL = "jdbc:mysql://localhost:3306/jdbcLesson?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";

    //запити до нашої БД
    //Константа для CREATE_USER_TABLE створення таблички user і вказуємо які колонки вона буде мати
    static final String CREATE_USER_TABLE = "create table user(" +
            "id bigint primary key auto_increment, " +
            "name varchar(255) not null, " +
            "password varchar(255) not null)";
    //Константа INSERT_USER для наповнення таблички
    static final String INSERT_USER = "insert into user(name, password) values " +
            "('%s', '%s')";

    public static void main(String[] args) throws SQLException {
        //конекшин до БД
        Connection c = DriverManager.getConnection(URL, USER, PASSWORD);

        //помічники для запитів SQL
//        Statement st = c.createStatement();
//        (запускаємо перший раз, потім коментуємо, так як у нас таблиця створюється тільки одного разу)
//        st.execute(CREATE_USER_TABLE);

        //цикл для заповлення таблички юзерами
//        Scanner sc = new Scanner(System.in);
//        for (int i = 0; i < 3; i++) {
//            System.out.println("Input name and password");
//            st.execute(String.format(INSERT_USER, sc.next(), sc.next()));
//        }

        //створення запиту (а саме витягаємо всі данні з таблиці user
//        ResultSet rs = st.executeQuery("select * from user");
//        while (rs.next()) {
//            long id = rs.getLong("id");
//            String name = rs.getString("name");
//            String password = rs.getString("password");
//            System.out.println(id + ". " + name + " " + password);
//        }

        //помічники для запитів SQL котрий може приймати параметри котрі у нас йдуть після where
        PreparedStatement preparedStatement = c.prepareStatement("select * from user where name Like ? and password = ?");

        Scanner sc = new Scanner(System.in);
        System.out.println("Input name for search: ");
        String partOfName = sc.next();

        System.out.println("Input password for search: ");
        String inputPassword = sc.next();

        //скрипт котрий шукає всю інформацію по юзеру, коли ми не знаємо повністю ім'я, а тільки його частину і знаємо пароль
        //1 це вказівник на перший знак питання, 2 - других, замість знаків питання підставляємо данні
        preparedStatement.setString(1, "%" + partOfName + "%");
        preparedStatement.setString(2, inputPassword);
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String password = rs.getString("password");
            System.out.println(id + ". " + name + " " + password);
        }
        preparedStatement.close();
        c.close();
    }
}
