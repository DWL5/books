package book_2;

import book_2.question.Bool;
import book_2.question.BooleanQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileTest {
    private Profile profile;
    private BooleanQuestion question;
    private Criteria criteria;

    @BeforeEach
    public void setUp() {
        profile = new Profile("Bull Hockey Inc");
        question = new BooleanQuestion(1, "Got bonuses?");
        criteria = new Criteria();
    }

    @DisplayName("MustMatch기준의 질문에 맞지 않을 경우 False를 리턴한다.")
    @Test
    public void matchAnswersFalseWhenMustMatchCriteriaNotMet() throws Exception {
        //given
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(Weight.MustMatch,  new Answer(question, Bool.TRUE)));

        //when
        boolean matches = profile.matches(criteria);

        //then
        assertFalse(matches);
    }

    @DisplayName("DonCare기준의 질문은 true를 리턴한다.")
    @Test
    public void matchAnswersTrueForAnyDonCare() {
        //given
        profile.add(new Answer(question, Bool.FALSE));
        criteria.add(new Criterion(Weight.DontCare, new Answer(question, Bool.TRUE)));

        //when
        boolean matches = profile.matches(criteria);

        //then
        assertTrue(matches);

    }
}
