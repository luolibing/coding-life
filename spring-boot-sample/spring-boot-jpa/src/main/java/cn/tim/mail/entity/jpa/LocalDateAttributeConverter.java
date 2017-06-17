package cn.tim.mail.entity.jpa;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by LuoLiBing on 17/1/9.
 */
@Converter
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return (localDate == null ? null : convertToDate(localDate));
    }

    @Override
    public LocalDate convertToEntityAttribute(Date dbData) {
        return (dbData == null ? null : convertLocalDate(dbData));
    }

    public static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate convertLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}

