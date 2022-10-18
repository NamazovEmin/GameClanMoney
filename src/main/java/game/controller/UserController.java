package game.controller;

import game.entity.User;
import game.service.IUserService;
import game.service.UserServesImpl;
import game.enums.Reason;

public class UserController {
    private final IUserService userService = new UserServesImpl();
    Reason reasonToBankTransfer = Reason.FROM_USER_TO_CLAN_TRANSFER;
    Reason reasonQuestCompleted = Reason.QUEST_COMPLETED;



    public void toBankTransfer(User user, int putGold) {
        userService.putGoldToClanBank(user,putGold, reasonToBankTransfer);
    }

    public void questComplited(User user, int putGold) {
        userService.putGoldToClanBank(user,putGold, reasonQuestCompleted);
    }

}
