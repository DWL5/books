package book_2.question;

public class BooleanQuestion extends Question {
    public BooleanQuestion(int id, String text) {
        super(id, text, new String[] {"No", "Yes"});
    }

    @Override
    public boolean match(final int expected, final int actual) {
        return expected == actual;
    }
}
