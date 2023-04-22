package tpgpl.calendar;

public record TimeRange(Timestamp start, Timestamp end) implements Comparable<TimeRange> {
    public TimeRange {
        if (start == null || end == null) {
            throw new IllegalArgumentException("The params (start, end) cannot be null.");
        }

        if (start.compareTo(end) > 0) {
            throw new IllegalArgumentException("The start timestamp must occur before the end timestamp.");
        }
    }

    public int getDurationInMinutes() {
        return (end.hour() - start.hour()) * 60 + (end.minute() - start.minute());
    }

    public boolean containsRange(TimeRange range) {
        if (range == null) {
            throw new IllegalArgumentException("The range param must not be null.");
        }

        return start.compareTo(range.start) <= 0 && end.compareTo(range.end) >= 0;
    }

    public boolean collidesWith(TimeRange range) {
        if (range == null) {
            throw new IllegalArgumentException("The range param must not be null.");
        }

        return !(start.compareTo(range.end) >= 0 || end.compareTo(range.start) <= 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TimeRange t) {
            return start.equals(t.start) && end.equals(t.end);
        }

        return false;
    }

    @Override
    public int compareTo(TimeRange o) {
        return start.compareTo(o.start) != 0 ? start.compareTo(o.start) : end.compareTo(o.end);
    }

    @Override
    public String toString() {
        return String.format("%s-%s", start, end);
    }
}
