package model.policy;

import model.data.Level;

public interface Policy {
    boolean isFinished();
    void Move(String move);
    Level getLvl();
}
