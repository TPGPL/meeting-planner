package tpgpl.calendar;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimestampTest {
    @Test(expected = IllegalArgumentException.class)
    public void invalidHourTest() {
        // given
        int hour = 25;
        int minute = 38;

        // when
        Timestamp st = new Timestamp(hour, minute);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidMinuteTest() {
        // given
        int hour = 14;
        int minute = 72;

        // when
        Timestamp st = new Timestamp(hour, minute);

        // then
        assert false;
    }

    @Test
    public void singleDigitHourStringTest() {
        // given
        int hour = 6, minute = 14;
        String expected = "06:14";

        // when
        Timestamp st = new Timestamp(hour, minute);
        String actual = st.toString();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void singleDigitMinuteStringTest() {
        // given
        int hour = 12, minute = 3;
        String expected = "12:03";

        // when
        Timestamp st = new Timestamp(hour, minute);
        String actual = st.toString();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void singleDigitParamsStringTest() {
        // given
        int hour = 4, minute = 1;
        String expected = "04:01";

        // when
        Timestamp st = new Timestamp(hour, minute);
        String actual = st.toString();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void timeStringTest() {
        // given
        int hour = 23, minute = 54;
        String expected = "23:54";

        // when
        Timestamp st = new Timestamp(hour, minute);
        String actual = st.toString();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void equalTimestampsTest() {
        // given
        int hour = 12, minute = 43;
        Timestamp st1 = new Timestamp(hour, minute);
        Timestamp st2 = new Timestamp(hour, minute);
        int expected = 0;

        // when
        int actual = st1.compareTo(st2);

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void higherTimestampComparisonTest() {
        // given
        Timestamp st1 = new Timestamp(8, 14);
        Timestamp st2 = new Timestamp(5, 48);

        // when
        int result = st1.compareTo(st2);

        // then
        assert result > 0;
    }

    @Test
    public void lowerTimestampComparisonTest() {
        // given
        Timestamp st1 = new Timestamp(8, 14);
        Timestamp st2 = new Timestamp(12, 13);

        // when
        int result = st1.compareTo(st2);

        // then
        assert result < 0;
    }

    @Test
    public void minuteDifferenceComparisonTest() {
        // given
        int hour = 12;
        Timestamp st1 = new Timestamp(hour, 14);
        Timestamp st2 = new Timestamp(hour, 48);

        // when
        int result = st1.compareTo(st2);

        // then
        assert result < 0;
    }
}