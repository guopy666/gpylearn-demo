package com.gpy.designpatterndemo.designpatterns.statemachine;

/**
 * @ClassName CapeMario
 * @Description
 * @Author guopy
 * @Date 2021/7/28 14:13
 */
public class CapeMario implements IMario{

    private MarioStateMachine marioStateMachine;

    public CapeMario(MarioStateMachine marioStateMachine){
        this.marioStateMachine = marioStateMachine;
    }

    @Override
    public State getName() {
        return State.CAPE;
    }

    @Override
    public void obtainMushRoom() {
        // do something...
        marioStateMachine.setScore(marioStateMachine.getScore() + 100);
        System.out.println("Mario meet mushRoom, score + 100");
    }

    @Override
    public void obtainCape() {
        // 马里奥形态不变
        marioStateMachine.setScore(marioStateMachine.getScore() + 200);
    }

    @Override
    public void obtainFireFlower() {
        marioStateMachine.setCurrentState(new FireFlowerMario(marioStateMachine));
        marioStateMachine.setScore(marioStateMachine.getScore() + 300);
    }

    @Override
    public void meetMonster() {
        marioStateMachine.setCurrentState(new SmallMario(marioStateMachine));
        marioStateMachine.setScore(marioStateMachine.getScore() - 100);
    }
}
