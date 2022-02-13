package book_2.question;

import book_2.Answer;

public abstract class Question {
    private String text;
    private String[] answerChoices;
    private int id;

    public Question(final int id, final String text, final String[] answerChoices) {
        this.text = text;
        this.answerChoices = answerChoices;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public String getAnswerChoices(int i) {
        return answerChoices[i];
    }

    public boolean match(Answer answer) {
        return false;
    }

    abstract public boolean match(int expected, int actual);

    public int indexOf(String matchingAnswerCode) {
        for (int i = 0; i < answerChoices.length; i++) {
            if (answerChoices[i].equals(matchingAnswerCode)) {
                return i;
            }
        }
        return -1;
    }
}
