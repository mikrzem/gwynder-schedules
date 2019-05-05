package pl.net.gwynder.schedules.events.services

import org.springframework.stereotype.Service
import pl.net.gwynder.schedules.common.BaseService
import pl.net.gwynder.schedules.common.DateParser
import pl.net.gwynder.schedules.common.errors.DataNotFound
import pl.net.gwynder.schedules.events.entities.ScheduledEvent
import pl.net.gwynder.schedules.events.entities.ScheduledEventFilter
import pl.net.gwynder.schedules.events.entities.ScheduledEventHeader
import java.time.LocalDateTime

@Service
class ScheduledEventService(
        private val repository: ScheduledEventRepository,
        private val dateParser: DateParser
) : BaseService() {

    fun select(owner: String, filter: ScheduledEventFilter): List<ScheduledEvent> {
        return repository.findByDates(
                owner,
                dateParser.toDateTime(filter.from),
                dateParser.toDateTime(filter.to)
        )
    }

    fun get(owner: String, id: Long): ScheduledEvent {
        return repository.findFirstByOwnerAndId(owner, id).orElseThrow { DataNotFound("event", id) }
    }

    fun create(owner: String, data: ScheduledEventHeader): ScheduledEvent {
        val event = ScheduledEvent(
                data.title,
                dateParser.toDateTime(data.start),
                dateParser.toDateTime(data.end),
                owner
        )
        return repository.save(event)
    }

    fun update(owner: String, id: Long, data: ScheduledEventHeader): ScheduledEvent {
        val current = get(owner, id)
        current.title = data.title
        current.startTime = dateParser.toDateTime(data.start)
        current.endTime = dateParser.toDateTime(data.end)
        return repository.save(current)
    }

    fun updateDate(event: ScheduledEvent, start: LocalDateTime, end: LocalDateTime): ScheduledEvent {
        event.startTime = start
        event.endTime = end
        return repository.save(event)
    }

    fun delete(owner: String, id: Long) {
        val event = get(owner, id)
        repository.delete(event)
    }

    fun toHeader(event: ScheduledEvent): ScheduledEventHeader {
        return ScheduledEventHeader(
                event.id,
                event.title,
                dateParser.toString(event.startTime),
                dateParser.toString(event.endTime)
        )
    }

}