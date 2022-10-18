package game.service;

import game.entity.User;
import game.enums.Reason;

public interface IUserService {

    void putGoldToClanBank(User user, int gold, Reason reason);
}
