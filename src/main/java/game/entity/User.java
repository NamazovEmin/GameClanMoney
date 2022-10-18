package game.entity;

public class User {
    private final int id;
    private final String name;
    private int balance;
    private final int clanID;
    private final Actor actor;
    private long createdAt;
    private long updatedAt;
    private int expectBalance;

    public User(int id, String name, int balance, int clanID, Actor actor) {
        this.id = id;
        this.name = name;
        this.clanID = clanID;
        this.balance = balance;
        this.expectBalance = balance;
        this.actor = actor;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getClanID() {
        return clanID;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int goldChange) {
    }

    public int getExpectBalance() {
        return expectBalance;
    }

    public void setExpectBalance(int expectBalance) {
        this.expectBalance = expectBalance;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Actor getActor() {
        return actor;
    }

}
