package book_1;

import book_1.ScoreCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCollectionTest {

    @DisplayName("평균값을 반환한다.")
    @Test
    public void answersArithmeticMeanOfTwoNumbers ()  {
        //given
        ScoreCollection collection = new ScoreCollection();
        collection.add(() -> 5);
        collection.add(() -> 7);

        //when
        int actual = collection.arithmeticMean();

        //then
        assertEquals(6, actual);
    }

}