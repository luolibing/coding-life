package cn.tim.i18.time;

import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by LuoLiBing on 16/5/27.
 */
public class TimeZoneTest {

    @Test
    public void test1() {
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = calendar.getTimeZone();
        System.out.println(timeZone.getDisplayName());
        System.out.println(timeZone.getID());

        String[] availableIDs = TimeZone.getAvailableIDs();
        for(String id: availableIDs) {
            System.out.println(id);
        }
        TimeZone america1 = TimeZone.getTimeZone("America/Anchorage");
        int offsetInMillis = america1.getOffset(calendar.getTimeInMillis());

        String offset = String.format("%02d:%02d", Math.abs(offsetInMillis / 3600000), Math.abs((offsetInMillis / 60000) % 60));
        offset = (offsetInMillis >= 0 ? "+" : "-") + offset;
        System.out.println("offset:" + offset);
    }
}
