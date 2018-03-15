package ma.najeh.ibnouzouhr.util;

import ma.najeh.ibnouzouhr.model.Hour;
import ma.najeh.ibnouzouhr.model.Planing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import static ma.najeh.ibnouzouhr.constant.Constant.Date.DATE_FORMAT;

/**
 * Created by youssef on 12/19/17.
 */
public class DateUtil {
    public static Date  toDate(String date){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat(DATE_FORMAT);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            return new Date();
        }
    }


    public static Date firstDay(Integer day, Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_YEAR,Math.abs(day-dayOfWeek));
        return calendar.getTime();
    }
    public static Date nextWeek(Date startDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR,7);
        return calendar.getTime();
    }

    public static Integer getYear(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static boolean periodsNotOverLapping(Hour startTime, Hour endTime, Planing planing) {
        LocalTime startA = LocalTime.of( startTime.getH() , startTime.getM() );
        LocalTime stopA = LocalTime.of( endTime.getH() , endTime.getM());

        LocalTime startB = LocalTime.of( planing.getStartHour().getH() , planing.getStartHour().getM() );
        LocalTime stopB = LocalTime.of( planing.getEndHour().getH() , planing.getEndHour().getM());

        return  !(( startA.isBefore( stopB ) )
                &&
                ( stopA.isAfter( startB ) )) ;
    }


}
