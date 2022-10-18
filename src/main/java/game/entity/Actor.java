package game.entity;

import game.enums.ActorType;

public class Actor {
    private int id;
    private ActorType type;


    public Actor(int id, ActorType type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public ActorType getType() {
        return type;
    }
}
