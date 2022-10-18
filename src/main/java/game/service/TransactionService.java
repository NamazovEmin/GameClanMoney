package game.service;

import game.entity.Actor;
import game.entity.Clan;
import game.enums.ActorType;
import game.enums.Reason;
import game.repository.ClanRepositoryImpl;
import game.repository.IClanRepository;
import game.entity.Transaction;
import game.repository.ITransactionRepository;
import game.repository.TransactionRepositoryImpl;
import game.repository.IUserRepository;
import game.entity.User;
import game.repository.UserRepositoryImpl;

import java.sql.SQLException;
import java.util.*;

public class TransactionService {

    private final Queue<Transaction> transactionQueue;
    private final Map<Integer,Clan> clanMap;
    private final Map<Integer,User> userMap;
    static TransactionService transactionService ;
    ITransactionRepository transactionRepository;
    IClanRepository clanRepository;
    IUserRepository userRepository = UserRepositoryImpl.getInstance();
    private User userRollBack;
    private Clan clanRollBack;
    Transaction transaction;



    public TransactionService() {
        this.clanRepository = new ClanRepositoryImpl();
        this.transactionRepository = new TransactionRepositoryImpl();
        this.transactionQueue = new LinkedList<>();
        try {
            this.clanMap = Map.copyOf(clanRepository.getClanMap());
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        try {
            this.userMap = Map.copyOf(userRepository.getUserMap());
        } catch (SQLException e) {
            throw new RuntimeException();
        }

        new Thread(() -> {
            while (true) {
                execute();
            }
        }).start();
    }

    public static synchronized TransactionService getInstance(){
        if (transactionService == null) {
            transactionService = new TransactionService();
        }
        return transactionService;
    }

    private void execute() {
        System.out.println();
       transaction = transactionQueue.peek();
        if (transaction != null) {
            switch (transaction.getSender().getType()) {
                case User -> userSender();
                case GlobalBank -> {
                }
                case Clan -> {
                }
            }
            switch (transaction.getReceiver().getType()) {
                case Clan -> {
                    if (transaction.getSender().getType() == ActorType.GlobalBank) {
                        clanReceiverFromGlobalBank();
                    } else {
                        clanReceiver();
                    }

                }
                case User -> {
                }
                case GlobalBank -> {
                }
            }
                transactionQueue.poll();
                System.out.println("Transaction processed");
        }
    }



    public synchronized void  addToTransactionQueue(User user, int gold, Reason reason) {

        switch (reason) {
            case FROM_USER_TO_CLAN_TRANSFER -> {
                System.out.println(user.getExpectBalance());
                if (user.getExpectBalance() >= gold) {
                    user.setExpectBalance(user.getExpectBalance() - gold);
                    clanMap.get(user.getClanID()).setExpectBalance(clanMap.get(user.getClanID()).getExpectBalance() + gold);
                    transactionQueue.add(new Transaction(user.getActor(),clanMap.get(user.getClanID()).getActor(),gold,reason));
                    System.out.println("Transaction add to Queue");
                } else {
                    System.out.println(user.getName() + " does not have enough money to transfer");
                }
            }

            case QUEST_COMPLETED -> {
                    clanMap.get(user.getClanID()).setExpectBalance(clanMap.get(user.getClanID()).getExpectBalance() + gold);
                    transactionQueue.add(new Transaction(new Actor(3, ActorType.GlobalBank),clanMap.get(user.getClanID()).getActor(),gold,reason));
                    System.out.println("Transaction add to Queue");
            }
        }
    }

    private void userSender(){
        try {
            userRollBack = userRepository.findUserByActor(transaction.getSender());
        } catch (SQLException e) {
            System.out.println("Can't save user to roll back");
        }
        try {
            userRepository.decreaseBalanceById(userRollBack.getId(),transaction.getGold());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void clanReceiver(){
        try {
            clanRollBack = clanRepository.findClanActorId(transaction.getReceiver().getId());
        } catch (SQLException e) {
            System.out.println("Can't save clan to roll back");
            userRepository.rollBackUser(userRollBack);
        }
        try {
            clanRepository.IncreaseBalanceById(clanRollBack.getId(),transaction.getGold());
        } catch (SQLException e) {
            userRepository.rollBackUser(userRollBack);
            System.out.println("I start user roll back");
            throw new RuntimeException(e);
        }
        try {
            transaction.setSenderBalanceBefore(userRollBack.getBalance());
            transaction.setSenderBalanceAfter(userRollBack.getBalance() - transaction.getGold());
            transaction.setReceiverBalanceBefore(clanRollBack.getBalance());
            transaction.setReceiverBalanceAfter(clanRollBack.getBalance() + transaction.getGold());
            transactionRepository.add(transaction);
            Clan clan = clanMap.get(clanRollBack.getId());
            clan.setBalance(clan.getBalance() + transaction.getGold());
            User user = userMap.get(userRollBack.getId());
            user.setBalance(user.getBalance() - transaction.getGold());

        } catch (SQLException e) {
            clanRepository.rollBackClan(clanRollBack);
            userRepository.rollBackUser(userRollBack);
            System.out.println("I start user  and clan roll back");
            throw new RuntimeException(e);
        }
    }

    private void clanReceiverFromGlobalBank() {
        try {
            clanRollBack = clanRepository.findClanActorId(transaction.getReceiver().getId());
        } catch (SQLException e) {
            System.out.println("Can't save clan to roll back");
        }
        try {
            clanRepository.IncreaseBalanceById(clanRollBack.getId(),transaction.getGold());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            transaction.setSenderBalanceBefore(0);
            transaction.setSenderBalanceAfter(0);
            transaction.setReceiverBalanceBefore(clanRollBack.getBalance());
            transaction.setReceiverBalanceAfter(clanRollBack.getBalance() + transaction.getGold());
            transactionRepository.add(transaction);
            Clan clan = clanMap.get(clanRollBack.getId());
            clan.setBalance(clan.getBalance() + transaction.getGold());

        } catch (SQLException e) {
            clanRepository.rollBackClan(clanRollBack);
            System.out.println("I star clan roll back");
            throw new RuntimeException(e);
        }
    }
}


