package book_2;

import book_1.Scoreable;

public class Criterion implements Scoreable {
    private Weight weight;
    private Answer answer;
    private int score;

    public Criterion(final Weight weight, final Answer answer) {
        this.weight = weight;
        this.answer = answer;
    }

    public Answer getAnswer() {
        return answer;
    }

    public Weight getWeight() {
        return weight;
    }

    public void setScore(final int score) {
        this.score = score;
    }

    @Override
    public int getScore() {
        return 0;
    }
}
