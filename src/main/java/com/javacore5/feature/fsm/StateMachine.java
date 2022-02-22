package com.javacore5.feature.fsm;

import java.util.ArrayList;
import java.util.List;

public class StateMachine {
    private State state = State.idle;
    private List<StateMachineListener> listeners = new ArrayList<>();

    private String message;
    private int time;

    public void addListener(StateMachineListener listener) {
        listeners.add(listener);
    }

    public void handle(String text) {
        if (text.equals("Создать напоминание")) {
            onCreateNotificationPressed();
            return;
        }

        onTextReceived(text);

        try {
            int number = Integer.parseInt(text);
            onNumberReceived(number);
        } catch (Exception ex) {
        }
    }

    private void onCreateNotificationPressed() {
        if (state == State.idle) {
            switchState(State.waitForMessage);

            for (StateMachineListener listener : listeners) {
                listener.onSwitchedToWaitForMessage();
            }
        }
    }

    private void onTextReceived(String text) {
        if (state == State.waitForMessage) {
            message = text;
            switchState(State.waitForTime);

            for (StateMachineListener listener : listeners) {
                listener.onSwitchedToWaitForTime();
            }
        }
    }

    private void onNumberReceived(int number) {
        if (state == State.waitForTime) {
            this.time = number;
            switchState(State.idle);

            for(StateMachineListener listener: listeners) {
                listener.onMessageAndTimeReceived(message, time);
            }
        }
    }

    private void switchState(State newState) {
        System.out.println("Switch state " + state + " => " + newState);

        this.state = newState;
    }
}
