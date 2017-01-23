package model.policy;

import model.data.Level;

public interface Policy {
    boolean isFinished();
    boolean moveMade();
    void Move(String move);
    Level getLvl();
}
