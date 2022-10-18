package game.enums;

public enum Reason {
    FROM_USER_TO_CLAN_TRANSFER(1),
    FROM_BANK_TO_USER_TRANSFER(2),
    KILL_MONSTER(3),
    Kill_BOSS(4),
    PASSED_THE_DUNGEON(5),
    QUEST_COMPLETED(6);

    Reason(int i) {
    }
}
