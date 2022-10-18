package game.entity;

import game.enums.ActorType;

import java.util.ArrayList;
import java.util.List;

public class Clan {
        private int id;
        private String name;
        private int balance;
        private Actor actor;
        private int expectBalance;


        public Clan(int id, String name, int balance, Actor actor) {
                this.id = id;
                this.name = name;
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

        public int getBalance() {
                return balance;
        }

        public int getExpectBalance() {
                return expectBalance;
        }

        public void setExpectBalance(int expectBalance) {
                this.expectBalance = expectBalance;
        }

        public Actor getActor() {
                return actor;
        }

        public void setBalance(int balance) {
                this.balance = balance;
        }
}
