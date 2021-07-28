package com.gpy.designpatterns.statemachine;

/**
 * @ClassName FireFlowerMario
 * @Description
 * @Author guopy
 * @Date 2021/7/28 14:15
 */
public class FireFlowerMario implements IMario {
    private MarioStateMachine marioStateMachine;

    public FireFlowerMario(MarioStateMachine marioStateMachine) {
        this.marioStateMachine = marioStateMachine;
    }

    @Override
    public State getName() {
        return State.FIRE;
    }

    @Override
    public void obtainMushRoom() {
        //do something
        marioStateMachine.setScore(marioStateMachine.getScore() + 100);
        System.out.println("Mario meet mushRoom, score + 100");
    }

    @Override
    public void obtainCape() {
        //形态不变
        marioStateMachine.setScore(marioStateMachine.getScore() + 200);
    }

    @Override
    public void obtainFireFlower() {
        // 形态不变
        marioStateMachine.setScore(marioStateMachine.getScore() + 300);
    }

    @Override
    public void meetMonster() {
        marioStateMachine.setCurrentState(new SmallMario(marioStateMachine));
        marioStateMachine.setScore(marioStateMachine.getScore() - 300);
    }
}
