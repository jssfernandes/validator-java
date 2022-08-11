package br.com.jssfernandes.validator.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;
import static java.time.LocalDateTime.now;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;


public class DateUtil {

    private DateUtil() {
    }

    private static final String FORMAT_DATE = "yyyyMMddHHmmss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDD000000 = "YYYYMMdd000000";
    public static final String YYYYMMDD000000000 = "YYYYMMdd000000000";
    public static final String NNNNMMDD000000 = "9999MMdd000000";

    /**
     * Formatação "2020-07-02T21:06:26.779+0000"
     */
    private static DateTimeFormatter fmtIso = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .optionalStart()
            .appendLiteral('T')
            .append(ISO_LOCAL_TIME)
            .optionalStart()
            .appendOffset("+HHMM", "GMT")
            .toFormatter(Locale.getDefault());

    public static LocalDateTime convertISODateToLocalDateTime(String isoDate) {
        try {

            if (isoDate != null) {
                return LocalDateTime.parse(isoDate, fmtIso);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String nowFormatedWithMilliseconds() {
        try {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        } catch (Exception e) {
            return null;
        }
    }

    public static String nowTrucatedWithLeftZeros() {
        try {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            return StringUtils.rightPad(date, 17, "0");
        } catch (Exception e) {
            return null;
        }

    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception e) {
            return null;
        }

    }

    public static LocalDateTime convertCalendarToLocalDateTime(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        try {
            TimeZone tz = calendar.getTimeZone();
            ZoneId zid = tz == null ? ZoneId.systemDefault() : tz.toZoneId();
            return LocalDateTime.ofInstant(calendar.toInstant(), zid);
        } catch (Exception e) {
            return null;
        }
    }

    public static Long getPeriodInMonths(String stringDate) {

        if (StringUtils.isEmpty(stringDate)) {
            return null;
        }
        try {
            LocalDateTime datetime = DateUtil.convertStringToLocalDateTime(stringDate);
            if (datetime != null) {
                LocalDate date = LocalDate.of(datetime.getYear(), datetime.getMonth(), datetime.getDayOfMonth());
                Period period = Period.between(date, LocalDate.now());
                return period.toTotalMonths();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static Optional<LocalDateTime> convertStringToLocalDateTime(Optional<String> date) {
        try {
            return date.map(x -> x.substring(0, 14))
                    .map(x -> LocalDateTime.parse(x, DateTimeFormatter.ofPattern(FORMAT_DATE)));
        } catch (Exception e) {
            return null;
        }

    }

    public static LocalDateTime convertStringToLocalDateTime(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        try {
            return convertStringToLocalDateTime(date.substring(0, 14), FORMAT_DATE);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDateTime convertStringToLocalDateTime(String date, String formatDate) {
        if (StringUtils.isEmpty(date) || StringUtils.isEmpty(formatDate)) {
            return null;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDate);
            return LocalDateTime.parse(date, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate convertStringToLocalDate(String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }

        try {
            return convertStringToLocalDate(date.substring(0, 14), FORMAT_DATE);
        } catch (Exception e) {
            return null;
        }
    }

    public static LocalDate convertStringToLocalDate(String time, String formatTime) {
        if (StringUtils.isEmpty(time) || StringUtils.isEmpty(formatTime)) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatTime);
            return LocalDate.parse(time, formatter);
        } catch (Exception e) {
            return null;
        }

    }

    public static LocalTime convertStringToLocalTime(String time, String formatTime) {
        if (StringUtils.isEmpty(time) || StringUtils.isEmpty(formatTime)) {
            return null;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatTime);
            return LocalTime.parse(time, formatter);
        } catch (Exception e) {
            return null;
        }

    }

    public static Date convertLocalDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        try {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            return null;
        }

    }

    public static Date convertCalendarToDate(Calendar calendar) {

        if (calendar == null) {
            return null;
        }

        try {
            Date date = null;
            date = calendar.getTime();
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertDateToString(Date date, String format) {

        if (date == null || StringUtils.isEmpty(format)) {
            return null;
        }

        try {
            String result = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            result = simpleDateFormat.format(date);
            return result;

        } catch (Exception e) {
            return null;
        }

    }

    public static String convertCalendarToString(Calendar date, String format) {

        if (date == null || StringUtils.isEmpty(format)) {
            return null;
        }

        try {
            String result = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            result = simpleDateFormat.format(date.getTime());

            return result;

        } catch (Exception e) {
            return null;
        }

    }

    public static String convertLocalDateToString(LocalDate localDate, String format) {

        if (localDate == null || StringUtils.isEmpty(format)) {
            return null;
        }
        try {
            String result = "";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

            result = localDate.format(formatter);

            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime, String format) {

        if (localDateTime == null || StringUtils.isEmpty(format)) {
            return null;
        }

        try {
            String result = "";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

            result = localDateTime.format(formatter);

            return result;

        } catch (Exception e) {
            return null;
        }
    }

    public static Date retornaMenorData(List<Date> listaDatas) {
        if (CollectionUtils.isEmpty(listaDatas)) {
            return null;
        }

        try {
            return Collections.min(listaDatas);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date retornaMaiorData(List<Date> listaDatas) {
        if (CollectionUtils.isEmpty(listaDatas)) {
            return null;
        }
        try {
            return Collections.max(listaDatas);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer calculateAge(LocalDateTime dataNascimento) {
        if (dataNascimento == null) {
            return null;
        }

        try {
            return Long.valueOf(ChronoUnit.YEARS.between(dataNascimento, now())).intValue();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param hourMinute Esperado HH:MM
     */
    public static LocalDateTime hhmm(String hourMinute) {
        if (StringUtils.isEmpty(hourMinute)) {
            return null;
        }

        try {

            String[] hhmm = hourMinute.split(":");
            return LocalDateTime.of(1, 1, 1, Integer.parseInt(hhmm[0]), Integer.parseInt(hhmm[1]));

        } catch (Exception e) {
            return null;
        }
    }

    public static boolean isBetweenLocalTime(LocalTime start, LocalTime end) {

        if (start == null || end == null) {
            return false;
        }

        try {
            LocalTime agora = LocalTime.now();
            // agora é antes do fim e depois do inicio
            return (agora.isBefore(end) && agora.isAfter(start));
        } catch (Exception e) {
            return false;
        }
    }

    public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) {

        if (date == null) {
            return null;
        }
        try {
            XMLGregorianCalendar xmlDate = null;
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(date);
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
            return xmlDate;

        } catch (Exception e) {
            return null;
        }

    }

    public static Date convertStringTimeZoneToDate(String stringDate, String format) {

        if (StringUtils.isEmpty(stringDate) || StringUtils.isEmpty(format)) {
            return null;
        }
        try {
            DateTimeFormatter zonedFormatter = DateTimeFormatter.ofPattern(format);
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(stringDate, zonedFormatter);
            return Date.from(zonedDateTime.toInstant());
        } catch (Exception e) {
            return null;
        }
    }

}
