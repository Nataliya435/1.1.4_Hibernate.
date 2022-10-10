package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import static jm.task.core.jdbc.service.util.Util.getConnection;


public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = getConnection();
    private PreparedStatement preparedStatement;
    private String query;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        query = "CREATE TABLE IF NOT EXISTS users (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NULL,\n" +
                "  `lastName` VARCHAR(45) NULL,\n" +
                "  `age` INT NULL,\n" +
                "  PRIMARY KEY (`id`))";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Не удалось создать таблицу");
        }
    }

    public void dropUsersTable() {
        query = "DROP TABLE IF EXISTS users";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        query = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Не удалось сохранить юзера");
        }
    }

    public void removeUserById(long id) {
        query = "DELETE FROM users WHERE id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Не удалось удалить юзера");
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        query = "SELECT * FROM users";
        try {
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Не вывести список юзеров");
        }
        return userList;
    }

    public void cleanUsersTable() {
        query = "DELETE FROM users";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Не удалось удалить юзеров");
        }
    }
}
// здесь расписываем основной функционал (CRUD методы взаимодействия с SQL)