package pl.net.gwynder.schedules.common

import org.springframework.stereotype.Service
import pl.net.gwynder.schedules.common.errors.InvalidFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Service
class DateParser : BaseService() {

    private val dateFormat = DateTimeFormatter.ISO_LOCAL_DATE
    private val dateTimeFormat = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    fun toDate(text: String): LocalDate {
        try {
            return LocalDate.parse(text, dateFormat)
        } catch (ex: Exception) {
            logger.error("Error reading date", ex)
            throw InvalidFormat("date")
        }
    }

    fun toString(date: LocalDate): String {
        return dateFormat.format(date)
    }


    fun toDateTime(text: String): LocalDateTime {
        try {
            return OffsetDateTime.parse(text, dateTimeFormat).atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
        } catch (ex: Exception) {
            logger.error("Error reading datetime", ex)
            throw InvalidFormat("datetime")
        }
    }

    fun toString(datetime: LocalDateTime): String {
        return dateTimeFormat.format(datetime)
    }

}