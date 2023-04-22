package tpgpl.calendar;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeRangeTest {
    @Test(expected = IllegalArgumentException.class)
    public void startIsNullTest() {
        // given
        Timestamp end = new Timestamp(1, 2);

        // when
        TimeRange r = new TimeRange(null, end);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void endIsNullTest() {
        // given
        Timestamp st = new Timestamp(1, 2);

        // when
        TimeRange r = new TimeRange(st, null);

        // then
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void endBeforeStartTest() {
        // given
        Timestamp st = new Timestamp(12, 14);
        Timestamp end = new Timestamp(11, 47);

        // when
        TimeRange r = new TimeRange(st, end);

        // then
        assert false;
    }

    @Test
    public void durationInMinutesTest() {
        // given
        Timestamp st = new Timestamp(11, 14);
        Timestamp end = new Timestamp(12, 47);
        int expected = 93;

        // when
        TimeRange r = new TimeRange(st, end);
        int actual = r.getDurationInMinutes();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void subHourDurationInMinutesTest() {
        // given
        Timestamp st = new Timestamp(11, 53);
        Timestamp end = new Timestamp(12, 47);
        int expected = 54;

        // when
        TimeRange r = new TimeRange(st, end);
        int actual = r.getDurationInMinutes();

        // then
        assertEquals(expected, actual);
    }

    @Test
    public void rangeIsContainedTest() {
        // given
        TimeRange t1 = new TimeRange(new Timestamp(10, 14), new Timestamp(11, 46));
        TimeRange t2 = new TimeRange(new Timestamp(11, 34), new Timestamp(11, 43));

        // when
        boolean isContained = t1.containsRange(t2);

        // then
        assertTrue(isContained);
    }

    @Test
    public void rangeCollidesAndIsNotContainedTest() {
        // given
        TimeRange t1 = new TimeRange(new Timestamp(10, 14), new Timestamp(11, 46));
        TimeRange t2 = new TimeRange(new Timestamp(10, 13), new Timestamp(11, 43));

        // when
        boolean isContained = t1.containsRange(t2);

        // then
        assertFalse(isContained);
    }

    @Test
    public void rangeCollidesTest() {
        // given
        TimeRange t1 = new TimeRange(new Timestamp(10, 14), new Timestamp(11, 46));
        TimeRange t2 = new TimeRange(new Timestamp(8, 20), new Timestamp(10, 15));

        // when
        boolean isColliding = t1.collidesWith(t2);
        boolean isCollidingReverse = t2.collidesWith(t1);

        // then
        assertTrue(isColliding);
        assertTrue(isCollidingReverse);
    }

    @Test
    public void rangeNotCollidingTest() {
        // given
        TimeRange t1 = new TimeRange(new Timestamp(10, 14), new Timestamp(11, 46));
        TimeRange t2 = new TimeRange(new Timestamp(8, 20), new Timestamp(10, 9));

        // when
        boolean isColliding = t1.collidesWith(t2);
        boolean isCollidingReverse = t2.collidesWith(t1);

        // then
        assertFalse(isColliding);
        assertFalse(isCollidingReverse);
    }
}