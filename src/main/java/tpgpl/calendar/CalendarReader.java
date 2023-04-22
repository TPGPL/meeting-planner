package tpgpl.calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.PatternSyntaxException;

public class CalendarReader {
    public static Calendar readCalendarFromJSON(String calendarJSON) {
        try {
            JSONObject calendarData = new JSONObject(calendarJSON);
            JSONObject data = calendarData.getJSONObject("working_hours");
            Calendar cal = new Calendar(new TimeRange(parseStringToTimestamp(data.getString("start")), parseStringToTimestamp(data.getString("end"))));

            JSONArray meetings = calendarData.getJSONArray("planned_meeting");

            for (Object meeting : meetings) {
                JSONObject m = (JSONObject) meeting;

                cal.addPlannedMeeting(new TimeRange(parseStringToTimestamp(m.getString("start")), parseStringToTimestamp(m.getString("end"))));
            }

            return cal;
        } catch (JSONException e) {
            throw new IllegalArgumentException("Invalid JSON format.");
        }
    }

    private static Timestamp parseStringToTimestamp(String time) {
        try {
            String[] split = time.split(":");

            return new Timestamp(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        } catch (PatternSyntaxException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Invalid JSON format.");
        }
    }
}
