package game.service;

import game.entity.User;
import game.enums.Reason;
import game.repository.IUserRepository;
import game.repository.UserRepositoryImpl;

public class UserServesImpl implements IUserService {

    TransactionService transactionService = TransactionService.getInstance();
    static UserServesImpl userServes;

    public static synchronized UserServesImpl getInstance(){
        if (userServes == null) {
            userServes = new UserServesImpl();
        }
        return userServes;
    }


    @Override
    public void putGoldToClanBank(User user, int gold, Reason reason) {
    transactionService.addToTransactionQueue(user,gold, reason);
    }



}
