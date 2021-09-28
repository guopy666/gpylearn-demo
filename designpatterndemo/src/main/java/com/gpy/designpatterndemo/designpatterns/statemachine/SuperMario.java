package com.gpy.designpatterndemo.designpatterns.statemachine;

/**
 * @ClassName SuperMario
 * @Description
 * @Author guopy
 * @Date 2021/7/28 14:09
 */
public class SuperMario implements IMario{

    private MarioStateMachine marioStateMachine;

    public SuperMario(MarioStateMachine marioStateMachine){
        this.marioStateMachine = marioStateMachine;
    }

    @Override
    public State getName() {
        return State.SUPER;
    }

    @Override
    public void obtainMushRoom() {
        //do something...
        marioStateMachine.setScore(marioStateMachine.getScore() + 100);
        System.out.println("Mario meet mushRoom, score + 100");
    }

    @Override
    public void obtainCape() {
        marioStateMachine.setCurrentState(new CapeMario(marioStateMachine));
        marioStateMachine.setScore(marioStateMachine.getScore() + 200);
    }

    @Override
    public void obtainFireFlower() {
        marioStateMachine.setScore(marioStateMachine.getScore() + 300);
        marioStateMachine.setCurrentState(new FireFlowerMario(marioStateMachine));
    }

    @Override
    public void meetMonster() {
        marioStateMachine.setScore(marioStateMachine.getScore() - 100);
        marioStateMachine.setCurrentState(new SmallMario(marioStateMachine));
    }
}
