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

public class ClanRepositoryImpl implements IClanRepository{

    DataBaseConnection dataBaseConnection;

    public ClanRepositoryImpl() {
        this.dataBaseConnection = DataBaseConnection.getInstance();
    }


    @Override
    public void IncreaseBalanceById(int id, int gold) throws SQLException {
        dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement("update clans " +
                "set balance = clans.balance +  ? " +
                "where clans.id =  ?;");
        dataBaseConnection.pstmt.setInt(1, gold);
        dataBaseConnection.pstmt.setInt(2, id);
        dataBaseConnection.pstmt.executeUpdate();
        System.out.println("Clans with ID = " + id  + "updated");
    }

    @Override
    public void rollBackClan(Clan clan) {
        try {
            dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement("UPDATE clans set balance = ? where id = ?;");
            dataBaseConnection.pstmt.setInt(1, clan.getBalance());
            dataBaseConnection.pstmt.setInt(2, clan.getId());
            dataBaseConnection.pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Can't roll back Clans");
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, Clan> getClanMap() throws SQLException {
        Map<Integer, Clan> clanMap = new HashMap<>();
        dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement("SELECT (clans.id, clans.name, clans.balance, clans.actor_id, a.actor_type)" +
                " FROM clans\n" +
                "left join actor a on clans.actor_id = a.id");
                        ResultSet rs = dataBaseConnection.pstmt.executeQuery();
            while (rs.next()) {
                String[] row = rs.getString("row").substring(1, rs.getString("row").length()-1).split(",");
                clanMap.put((Integer.valueOf(row[0])),
                      new Clan(Integer.parseInt(row[0]),row[1],
                     Integer.parseInt(row[2]),
                              new Actor(Integer.parseInt(row[3]), ActorType.valueOf(row[4]))));
            }
        System.out.println("Clan List Made");
        return clanMap;
    }

    @Override
    public Clan findClanActorId(int id) throws SQLException {
        System.out.println(id);
        dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement("SELECT (clans.id, clans.name, clans.balance, clans.actor_id, a.actor_type) FROM clans\n" +
                "left join actor a on clans.actor_id = a.id WHERE a.id = ?");
        dataBaseConnection.pstmt.setInt(1, id);
        ResultSet rs = dataBaseConnection.pstmt.executeQuery();
        rs.next();
        System.out.println(rs.getString("row"));
        String[] row = rs.getString("row").substring(1, rs.getString("row").length()-1).split(",");
        System.out.println("Данные Clan c ID = " + id + " saved");
        return new Clan(Integer.parseInt(row[0]),row[1],
                Integer.parseInt(row[2]),new Actor(Integer.parseInt(row[3]), ActorType.valueOf(row[4])));
    }
}
