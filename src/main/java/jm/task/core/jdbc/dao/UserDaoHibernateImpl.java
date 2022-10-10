package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

import static jm.task.core.jdbc.service.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private Transaction transaction;

    // public UserDaoHibernateImpl() {
    //}

    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (" +
                        "id INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
                        "name VARCHAR(45) NULL," +
                        "lastName VARCHAR(45) NULL," +
                        "age TINYINT(20) NULL)")
                .addEntity(User.class)
                .executeUpdate();
        transaction.commit();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users")
                .addEntity(User.class)
                .executeUpdate();
        transaction.commit();

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
        session.save(new User(name, lastName, age));
        transaction.commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        User user = (User) session.get(User.class, id);
        if (user != null) {
            session.delete(user);
        }
        transaction.commit();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = getSessionFactory().openSession();
        List<User> userList = session.createSQLQuery("SELECT * FROM users").addEntity(User.class).list();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().getCurrentSession();
        transaction = session.beginTransaction();
        session.createSQLQuery("DELETE FROM users")
                .executeUpdate();
        transaction.commit();
    }
}