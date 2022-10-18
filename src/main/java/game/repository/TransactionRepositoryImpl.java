package game.repository;

import game.dbPostgres.DataBaseConnection;
import game.entity.Transaction;

import java.sql.SQLException;

public class TransactionRepositoryImpl implements ITransactionRepository{
    DataBaseConnection dataBaseConnection;

    public TransactionRepositoryImpl() {
        this.dataBaseConnection = DataBaseConnection.getInstance();
    }

    @Override
    public void add(Transaction transaction) throws SQLException {

            dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement
                    ("INSERT INTO transactions (reason,sender, receiver, gold_transaction,sender_balance_before,sender_balance_after,receiver_balance_before,receiver_balance_after)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            System.out.println(transaction.getReason().toString());
        System.out.println(transaction.getSender().getId());
        System.out.println(transaction.getReceiver().getId());
        System.out.println(transaction.getGold());
            dataBaseConnection.pstmt.setString(1, transaction.getReason().toString());
            dataBaseConnection.pstmt.setInt(2, transaction.getSender().getId());
            dataBaseConnection.pstmt.setInt(3, transaction.getReceiver().getId());
            dataBaseConnection.pstmt.setInt(4, transaction.getGold());
            dataBaseConnection.pstmt.setInt(5, transaction.getSenderBalanceBefore());
            dataBaseConnection.pstmt.setInt(6, transaction.getSenderBalanceAfter());
            dataBaseConnection.pstmt.setInt(7, transaction.getReceiverBalanceBefore());
            dataBaseConnection.pstmt.setInt(8, transaction.getReceiverBalanceAfter());
            dataBaseConnection.pstmt.executeUpdate();
            System.out.println("Transaction added");
        }

    @Override
    public void rollBackTransaction() {
//        try {
//            dataBaseConnection.pstmt = dataBaseConnection.conn.prepareStatement
//                    ("DELETE FROM transaction_from_user_to_clan " +
//                            "WHERE id in (SELECT id from transaction_from_user_to_clan ORDER BY id DESC LIMIT 1)");
//            dataBaseConnection.pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Can't rollBack Transaction");
//        }
    }
}
