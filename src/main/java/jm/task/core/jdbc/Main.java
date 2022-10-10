package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        User Alex = new User("Alex", "Alexeev", (byte) 6);
        User Ivan = new User("Ivan", "Ivanich", (byte) 10);
        User Karl = new User("Karl", "Alexeev", (byte) 50);
        User Fedy = new User("Fedy", "Fadeev", (byte) -2);

        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser(Alex.getName(), Alex.getLastName(), Alex.getAge());
        userService.saveUser(Ivan.getName(), Ivan.getLastName(), Ivan.getAge());
        userService.saveUser(Karl.getName(), Karl.getLastName(), Karl.getAge());
        userService.saveUser(Fedy.getName(), Fedy.getLastName(), Fedy.getAge());

        List<User> userList = userService.getAllUsers();
        for (User us : userList) {
            System.out.println(us.toString());
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}