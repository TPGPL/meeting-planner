package tpgpl.calendar;

import java.util.ArrayList;
import java.util.List;

public class MeetingPlanner {

    public static List<TimeRange> calculatePossibleMeetings(Calendar firstCalendar, Calendar secondCalendar, int meetingDuration) {
        if (firstCalendar == null || secondCalendar == null) {
            throw new IllegalArgumentException("The calendar params must not be null.");
        }

        if (meetingDuration <= 0) {
            throw new IllegalArgumentException("The meeting duration must be higher than 0.");
        }

        List<TimeRange> possibleMeetings = new ArrayList<>();

        // 1. Calculate break times for both calendars;

        List<TimeRange> firstBreakTimes = firstCalendar.getBreakTimes();
        List<TimeRange> secondBreakTimes = secondCalendar.getBreakTimes();

        // 2. Remove breaks shorter than meeting duration and not within possible working hours

        Timestamp higherStart = firstCalendar.getWorkingHours().start().compareTo(secondCalendar.getWorkingHours().start()) >= 0
                ? firstCalendar.getWorkingHours().start() : secondCalendar.getWorkingHours().start();

        Timestamp lowerEnd = firstCalendar.getWorkingHours().end().compareTo(secondCalendar.getWorkingHours().end()) <= 0
                ? firstCalendar.getWorkingHours().end() : secondCalendar.getWorkingHours().end();

        TimeRange validMeetingRange = new TimeRange(higherStart, lowerEnd);

        firstBreakTimes.removeIf(m -> m.getDurationInMinutes() < meetingDuration || !validMeetingRange.containsRange(m));
        secondBreakTimes.removeIf(m -> m.getDurationInMinutes() < meetingDuration || !validMeetingRange.containsRange(m));

        // 3. Calculate possible meeting times for colliding breaks

        for (TimeRange m : firstBreakTimes) {
            for (TimeRange n : secondBreakTimes) {
                if (m.collidesWith(n)) {
                    Timestamp start = m.start().compareTo(n.start()) >= 0 ? m.start() : n.start();
                    Timestamp end = m.end().compareTo(n.end()) <= 0 ? m.end() : n.end();
                    TimeRange possibleMeeting = new TimeRange(start, end);

                    if (possibleMeeting.getDurationInMinutes() >= meetingDuration && !possibleMeetings.contains(possibleMeeting)) {
                        possibleMeetings.add(possibleMeeting);
                    }
                }
            }
        }

        return possibleMeetings;
    }
}
