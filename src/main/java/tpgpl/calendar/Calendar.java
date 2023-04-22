package tpgpl.calendar;

import java.util.ArrayList;
import java.util.List;

public class Calendar {
    private final TimeRange workingHours;
    private final List<TimeRange> plannedMeetings;

    public Calendar(TimeRange workingHours) {
        if (workingHours == null) {
            throw new IllegalArgumentException("The workingHours param must not be null.");
        }

        plannedMeetings = new ArrayList<>();
        this.workingHours = workingHours;
    }

    public void addPlannedMeeting(TimeRange newMeeting) {
        if (newMeeting == null) {
            throw new IllegalArgumentException("The newMeeting param must not be null");
        }

        if (!workingHours.containsRange(newMeeting)) {
            throw new IllegalArgumentException("The newMeeting cannot be held within the workingHours.");
        }

        for (TimeRange m : plannedMeetings) {
            if (m.collidesWith(newMeeting)) {
                throw new IllegalArgumentException("The newMeeting collides with one of the existing meetings.");
            }
        }

        plannedMeetings.add(newMeeting);
    }

    public List<TimeRange> getBreakTimes() {
        List<TimeRange> breakTimes = new ArrayList<>();

        Timestamp start = workingHours.start(), end;
        TimeRange breakTime;

        plannedMeetings.sort(TimeRange::compareTo);

        for (TimeRange m : plannedMeetings) {
            end = m.start();

            breakTime = new TimeRange(start, end);

            if (breakTime.getDurationInMinutes() > 0) {
                breakTimes.add(breakTime);
            }

            start = m.end();
        }

        end = workingHours.end();

        breakTime = new TimeRange(start, end);

        if (breakTime.getDurationInMinutes() > 0) {
            breakTimes.add(breakTime);
        }

        return breakTimes;
    }
}
