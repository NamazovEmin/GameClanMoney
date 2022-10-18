package game.repository;

import game.dbPostgres.DataBaseConnection;
import game.entity.Actor;
import game.entity.Clan;
import game.entity.User;
import game.enums.ActorType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements IUserRepository {

   static UserRepositoryImpl userRepository;
   DataBaseConnection dataBaseConnection = DataBaseConnection.getInstance();

   public static synchronized UserRepositoryImpl getInstance() {
      if (userRepository == null) {
         userRepository = new UserRepositoryImpl();
      }
      return userRepository;
   }

   @Override
   public void increaseBalanceById(int id, int gold) throws SQLException {
      System.out.println("Start to update DB");
      gold = gold - 2 * gold;
      dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement("update users set balance = users.balance +  ? " +
              "where users.id =  ?;");
      dataBaseConnection.pstmt.setInt(1, gold);
      dataBaseConnection.pstmt.setInt(2, id);
      dataBaseConnection.pstmt.executeUpdate();
      System.out.println("Данные Пользователя c ID = " + id + "обновлены");

   }

   @Override
   public void decreaseBalanceById(int id, int gold) throws SQLException {
      System.out.println("Start to update DB");
      dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement("update users set balance = balance -  ? " +
              "where users.id =  ?;");
      dataBaseConnection.pstmt.setInt(1, gold);
      dataBaseConnection.pstmt.setInt(2, id);
      dataBaseConnection.pstmt.executeUpdate();
      System.out.println("Данные Пользователя c ID = " + id + "обновлены");
   }

   @Override
   public Map<Integer, User> getUserMap() throws SQLException {
      Map<Integer, User> userMap = new HashMap<>();
      dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement("SELECT (users.id, users.name,users.clan_id, users.balance, users.actor_id, a.actor_type)" +
              " FROM users\n" +
              "left join actor a on users.actor_id = a.id");
      ResultSet rs = dataBaseConnection.pstmt.executeQuery();
      while (rs.next()) {
         String[] row = rs.getString("row").substring(1, rs.getString("row").length()-1).split(",");
         userMap.put(Integer.valueOf(row[0]),
                 new User(Integer.parseInt(row[0]),row[1],Integer.parseInt(row[3]),
                         Integer.parseInt(row[2]), new Actor(Integer.parseInt(row[4]), ActorType.valueOf(row[5]))));
      }
      System.out.println("User Map Made");
      return userMap;
   }

   @Override
   public User findUserByActor(Actor actor) throws SQLException {
      dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement("SELECT (users.id, users.name,users.balance,users.clan_id,users.actor_id,a.actor_type) " +
              "from users  " +
              "left join actor a on users.actor_id = a.id  where actor_id = ?;");
      dataBaseConnection.pstmt.setInt(1, actor.getId());
      ResultSet rs = dataBaseConnection.pstmt.executeQuery();
      rs.next();
      System.out.println(rs.getString("row"));
      String[] row = rs.getString("row").substring(1, rs.getString("row").length()-1).split(",");
      System.out.println("Данные Пользователя " + row[1] + "saved for rollBack");
      return new User(Integer.parseInt(row[0]), row[1], Integer.parseInt(row[2]),Integer.parseInt(row[3]),actor);
   }

   @Override
   public void rollBackUser(User user) {
      try {
         dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement("UPDATE users set balance = ? where id = ?;");
         dataBaseConnection.pstmt.setInt(1, user.getBalance());
         dataBaseConnection.pstmt.setInt(2, user.getId());
         dataBaseConnection.pstmt.executeUpdate();
      } catch (SQLException e) {
         System.out.println("Can't roll back User");
         throw new RuntimeException(e);
      }
   }
}
