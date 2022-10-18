package game.repository;

import game.entity.Clan;

import java.sql.SQLException;
import java.util.Map;

public interface IClanRepository {
    void IncreaseBalanceById(int id, int gold) throws SQLException;
    void rollBackClan(Clan clan);
    Map<Integer, Clan> getClanMap() throws SQLException;
    Clan findClanActorId(int id) throws SQLException;
}
