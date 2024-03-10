package com.bobooi.mall.common.utils.misc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;

/**
 * @author bobo
 * @date 2021/3/31
 */

public class DateUtils {
    public static final ZoneOffset DEFAULT_ZONE = ZoneOffset.of("+8");

    public static long toMillisecond(LocalDateTime time) {
        return time.toEpochSecond(DEFAULT_ZONE) * 1000L;
    }

    public static LocalDateTime fromSecond(long time) {
        return LocalDateTime.ofEpochSecond(time, 0, DEFAULT_ZONE);
    }

    public static LocalDateTime fromMillisecond(long time) {
        return LocalDateTime.ofEpochSecond(time / 1000, (int) (time % 1000), DEFAULT_ZONE);
    }

    public static int getDaysByYearMonth(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        return a.get(Calendar.DATE);
    }
}