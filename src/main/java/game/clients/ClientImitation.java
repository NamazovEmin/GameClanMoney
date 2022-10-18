package game.clients;

import game.controller.UserController;
import game.entity.Actor;
import game.entity.User;
import game.repository.IUserRepository;
import game.repository.UserRepositoryImpl;

import javax.swing.plaf.TableHeaderUI;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ClientImitation {
    IUserRepository userRepository;
    Map<Integer, User> usersMap;
    ScheduledExecutorService scheduledExecutorService;
    static UserController userController = new UserController();
    public ClientImitation() {
        scheduledExecutorService = Executors.newScheduledThreadPool(5);
        this.userRepository = UserRepositoryImpl.getInstance();
        usersMap = new HashMap<>();
        start();
    }

    private void start() {
        try {
            usersMap = Map.copyOf(userRepository.getUserMap());
        } catch (SQLException e) {
            System.out.println("Не смог запустить клиентов");
            throw new RuntimeException(e);
        }
        for (int i= 1; i <= usersMap.size(); i ++){
          final User user =  usersMap.get(i);
            Runnable task1 = () -> {
            userController.toBankTransfer(user, 10);
        };
            scheduledExecutorService.scheduleAtFixedRate(task1, 1, 2, TimeUnit.SECONDS);
        }
        for (int i= 1; i <= usersMap.size(); i ++){
            final User user =  usersMap.get(i);
            Runnable task2 = () -> {
                userController.questComplited(user, 10);
            };
            scheduledExecutorService.scheduleAtFixedRate(task2, 1, 2, TimeUnit.SECONDS);
        }
    }
}
