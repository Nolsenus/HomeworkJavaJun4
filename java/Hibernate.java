import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Hibernate {

    public static void showcase() {
        final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        System.out.println("Exercise 2:");
        fillDB(sessionFactory);
        readDB(sessionFactory);
        System.out.println("\n\n");
        System.out.println("Exercise 3:");
        fillDBWithAuthors(sessionFactory);
        readDBWithAuthors(sessionFactory);
        readAuthorsWithBooks(sessionFactory);
    }

    private static void fillDB(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(new Book("Book #1", "Author #1"));
            session.persist(new Book("Book #2", "Author #2"));
            session.persist(new Book("Book #3", "Author #2"));
            session.persist(new Book("Book #4", "Author #2"));
            session.persist(new Book("Book #5", "Author #1"));
            session.persist(new Book("Book #6", "Author #3"));
            session.persist(new Book("Book #7", "Author #3"));
            session.persist(new Book("Book #8", "Author #2"));
            session.persist(new Book("Book #9", "Author #4"));
            session.persist(new Book("Book #10", "Author #5"));
            session.getTransaction().commit();
        }
    }

    private static void readDB(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            List<Book> books = session.createQuery("select b from Book b where author='Author #1'", Book.class)
                    .getResultList();
            books.forEach(System.out::println);
        }
    }

    private static void fillDBWithAuthors(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Author author1 = new Author("Author #1+");
            Author author2 = new Author("Author #2+");
            Author author3 = new Author("Author #3+");
            Author author4 = new Author("Author #4+");
            Author author5 = new Author("Author #5+");
            session.persist(author1);
            session.persist(author2);
            session.persist(author3);
            session.persist(author4);
            session.persist(author5);
            session.getTransaction().commit();
            session.beginTransaction();
            session.persist(new BookWithAuthor("Book #1+", author1));
            session.persist(new BookWithAuthor("Book #2+", author2));
            session.persist(new BookWithAuthor("Book #3+", author2));
            session.persist(new BookWithAuthor("Book #4+", author2));
            session.persist(new BookWithAuthor("Book #5+", author1));
            session.persist(new BookWithAuthor("Book #6+", author3));
            session.persist(new BookWithAuthor("Book #7+", author3));
            session.persist(new BookWithAuthor("Book #8+", author2));
            session.persist(new BookWithAuthor("Book #9+", author4));
            session.persist(new BookWithAuthor("Book #10+", author5));
            session.getTransaction().commit();
        }
    }

    private static void readDBWithAuthors(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            List<BookWithAuthor> books = session
                    .createQuery("select b from BookWithAuthor b", BookWithAuthor.class).getResultList();
            books.forEach(System.out::println);
        }
    }

    private static void readAuthorsWithBooks(SessionFactory sessionFactory) {
        try(Session session = sessionFactory.openSession()) {
            List<Author> authors = session.createQuery("select a from Author a", Author.class).getResultList();
            authors.forEach(it -> {
                System.out.print(it + " - ");
                System.out.println(it.getBooks());
            });
        }
    }

}
