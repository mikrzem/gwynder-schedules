package pl.net.gwynder.schedules.common

import org.springframework.stereotype.Service
import pl.net.gwynder.schedules.common.errors.InvalidFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class DateParser : BaseService() {

    private val format = DateTimeFormatter.ISO_LOCAL_DATE

    fun toDate(text: String): LocalDate {
        try {
            return LocalDate.parse(text, format)
        } catch (ex: Exception) {
            logger.error("Error reading date", ex)
            throw InvalidFormat("date")
        }
    }

    fun toString(date: LocalDate): String {
        return format.format(date)
    }

}