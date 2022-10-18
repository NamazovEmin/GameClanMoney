package game.repository;

import game.entity.Actor;
import game.entity.User;

import java.sql.SQLException;
import java.util.Map;

public interface IUserRepository {

    void increaseBalanceById(int id, int gold) throws SQLException;
    void decreaseBalanceById(int id, int gold) throws SQLException;
    Map<Integer,User> getUserMap() throws SQLException;
    User findUserByActor(Actor actor) throws SQLException;
    void rollBackUser(User user);
}
