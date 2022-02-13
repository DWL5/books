package book_3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

public class SetTest {
    private Set<Integer> numbers;

    @BeforeEach
    void setUp() {
        numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }


    @DisplayName("set의 크기를 구한다.")
    @Test
    public void getSetSize() {
        Assertions.assertThat(numbers).hasSize(3);
    }

    @DisplayName("")
    @Test
    public void contatins() {
        Assertions.assertThat(numbers).contains(1,2,3);
    }

    @DisplayName("")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4})
    public void containsParameterizedTest(int number) {
        Assertions.assertThat(numbers).contains(number);
    }

    @DisplayName("")
    @ParameterizedTest
    @CsvSource(value = {"1:true", "2:true", "3:true", "4:false", "5:false"}, delimiter = ':')
    public void containsCsvSource(int input, boolean expected) {
        Assertions.assertThat(numbers.contains(input)).isEqualTo(expected);
    }
}
