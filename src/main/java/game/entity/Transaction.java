package game.entity;

import game.enums.Reason;

public class  Transaction {

    private final Actor sender;
    private final Actor receiver;
    private final int gold;
    private final Reason reason;
    private  int senderBalanceBefore;
    private  int senderBalanceAfter;
    private  int receiverBalanceBefore;
    private  int receiverBalanceAfter;
    private int clanID;

    public Transaction(Actor sender, Actor receiver, int gold, Reason reason) {
        this.sender = sender;
        this.receiver = receiver;
        this.gold = gold;

        this.reason = reason;
    }

    public Actor getSender() {
        return sender;
    }

    public Actor getReceiver() {
        return receiver;
    }

    public int getGold() {
        return gold;
    }

    public Reason getReason() {
        return reason;
    }

    public int getSenderBalanceBefore() {
        return senderBalanceBefore;
    }

    public int getSenderBalanceAfter() {
        return senderBalanceAfter;
    }

    public int getReceiverBalanceBefore() {
        return receiverBalanceBefore;
    }

    public int getReceiverBalanceAfter() {
        return receiverBalanceAfter;
    }

    public void setSenderBalanceBefore(int senderBalanceBefore) {
        this.senderBalanceBefore = senderBalanceBefore;
    }

    public void setSenderBalanceAfter(int senderBalanceAfter) {
        this.senderBalanceAfter = senderBalanceAfter;
    }

    public void setReceiverBalanceBefore(int receiverBalanceBefore) {
        this.receiverBalanceBefore = receiverBalanceBefore;
    }

    public void setReceiverBalanceAfter(int receiverBalanceAfter) {
        this.receiverBalanceAfter = receiverBalanceAfter;
    }
}
