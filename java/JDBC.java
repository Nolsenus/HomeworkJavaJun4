import java.sql.*;

public class JDBC {

    public static void showcase() {;
        try(Connection connection = DriverManager.getConnection("jdbc:h2:mem:database.db")) {
            System.out.println("Exercise 1:");
            createDB(connection);
            fillDB(connection);
            readDB(connection);
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database.");
            e.printStackTrace();
        }
    }

    private static void createDB(Connection connection) {
        try(Statement statement = connection.createStatement()) {
            statement.execute("""
                create table if not exists book (
                id bigint not null auto_increment primary key,
                name varchar(255),
                author varchar(255))
                """);
        } catch (SQLException e) {
            System.out.println("Couldn't create the database.");
            e.printStackTrace();
        }
    }

    private static void fillDB(Connection connection) {
        try(Statement statement = connection.createStatement()) {
            statement.execute("""
                insert into book(name, author) values
                ('Book #1','Author #1'),
                ('Book #2','Author #2'),
                ('Book #3','Author #2'),
                ('Book #4','Author #2'),
                ('Book #5','Author #1'),
                ('Book #6','Author #3'),
                ('Book #7','Author #3'),
                ('Book #8','Author #2'),
                ('Book #9','Author #4'),
                ('Book #10','Author #5')
                """);
        } catch (SQLException e) {
            System.out.println("Couldn't fill the database.");
            e.printStackTrace();
        }
    }

    private static void readDB(Connection connection) {
        try(Statement statement = connection.createStatement()) {
            ResultSet books = statement.executeQuery("select name, author from book where author='Author #2'");
            int count = 0;
            while (books.next()) {
                System.out.printf("%d - %s by %s%n", ++count,
                        books.getString("name"), books.getString("author"));
            }
        } catch (SQLException e) {
            System.out.println("Couldn't read the database.");
            e.printStackTrace();
        }
    }

}
