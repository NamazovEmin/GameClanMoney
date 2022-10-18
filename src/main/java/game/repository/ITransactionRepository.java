package game.repository;

import game.entity.Transaction;
import game.entity.User;
import game.enums.Reason;

import java.sql.SQLException;

public interface ITransactionRepository {
    void add(Transaction transaction) throws SQLException;
    void rollBackTransaction();
}
