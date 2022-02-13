package book_2;

import book_2.question.Question;

public class Answer {
    private int index;
    private Question question;

    public Answer(final Question question, final int index) {
        this.index = index;
        this.question = question;
    }

    public Answer(Question characteristic, String matchingValue) {
        this.question = characteristic;
        this.index = characteristic.indexOf(matchingValue);
    }

    public String getQuestionText() {
        return question.getText();
    }

    public boolean match(int expected) {
        return question.match(expected, index);
    }

    public boolean match(Answer otherAnswer) {
        return question.match(index, otherAnswer.index);
    }

    public Question getCharacteristic() {
        return question;
    }
}
