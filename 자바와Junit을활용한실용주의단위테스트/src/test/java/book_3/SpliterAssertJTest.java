package book_3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SpliterAssertJTest {

    @DisplayName("주어진 구분자로 잘 분리되는지 확인한다.")
    @Test
    public void split() {
        //given
        Spliter spliter = new Spliter();

        //when
        String[] actual = spliter.split("1,2", ",");

        //then
        Assertions.assertThat(actual).contains("1", "2");
    }

    @DisplayName("주어진 문자를 정제한다.")
    @Test
    public void refine() {
        //given
        Spliter spliter = new Spliter();

        //when
        String actual = spliter.refine("(1,2)");

        //then
        Assertions.assertThat(actual).contains("1,2");
    }

    @DisplayName("특정 문자의 위치를 가져오지 못하면 예외가 발생한다.")
    @Test
    public void testThrowExceptionWhenCanNotFound() {
        //given
        Spliter spliter = new Spliter();

        //when
        //then
        Assertions.assertThatThrownBy(() -> {
            spliter.findCharacterFromIndex("abc", 4);
        }).isInstanceOf(IndexOutOfBoundsException.class);

        Assertions.assertThatExceptionOfType(IndexOutOfBoundsException.class)
                .isThrownBy(() -> {
                    spliter.findCharacterFromIndex("abc", 4);
                });

        org.junit.jupiter.api.Assertions.assertThrows(IndexOutOfBoundsException.class, () -> {
            spliter.findCharacterFromIndex("abc", 4);
        });
    }
}