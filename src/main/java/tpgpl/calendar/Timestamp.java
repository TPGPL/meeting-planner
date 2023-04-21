package tpgpl.calendar;

public record Timestamp(int hour, int minute) implements Comparable<Timestamp> {
    public Timestamp {
        if (hour < 0 || hour > 23) {
            throw new IllegalArgumentException("The hour param must be in range 0-23");
        }

        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("The minute param must be in range 0-59");
        }
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d", hour, minute);
    }

    @Override
    public int compareTo(Timestamp o) {
        return hour != o.hour ? hour - o.hour : minute - o.minute;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Timestamp t) {
            return t.hour == hour && t.minute == minute;
        }

        return false;
    }
}
