package org.aksw.simba.topicmodeling.automaton;

public interface AutomatonCallback {

    public void foundPattern(int patternId, int startPos, int length);
}
