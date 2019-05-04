package pl.net.gwynder.schedules.routes.services

import org.springframework.stereotype.Service
import pl.net.gwynder.schedules.common.BaseService
import pl.net.gwynder.schedules.common.DateParser
import pl.net.gwynder.schedules.events.services.ScheduledEventService
import pl.net.gwynder.schedules.routes.entities.ScheduledEventRoutePoint
import pl.net.gwynder.schedules.routes.entities.ScheduledEventRoutePointData
import pl.net.gwynder.schedules.routes.entities.ScheduledEventRouteSegment

@Service
class ScheduledEventRoutePointService(
        private val repository: ScheduledEventRoutePointRepository,
        private val eventService: ScheduledEventService,
        private val dateParser: DateParser
) : BaseService() {

    fun select(owner: String, eventId: Long): List<ScheduledEventRoutePoint> {
        return repository.findByEventOrderByPointTimeAsc(
                eventService.get(owner, eventId)
        )
    }

    fun append(owner: String, eventId: Long, segment: ScheduledEventRouteSegment) {
        val event = eventService.get(owner, eventId)
        for (dataPoint in segment.points) {
            val point = ScheduledEventRoutePoint(
                    event,
                    dateParser.toDateTime(dataPoint.time),
                    dataPoint.lat,
                    dataPoint.lng,
                    dataPoint.alt,
                    dataPoint.prc,
                    owner
            )
            repository.save(point)
        }
    }

    fun clear(owner: String, eventId: Long) {
        val event = eventService.get(owner, eventId)
        repository.deleteByEvent(event)
    }

    fun toData(point: ScheduledEventRoutePoint): ScheduledEventRoutePointData {
        return ScheduledEventRoutePointData(
                dateParser.toString(point.pointTime),
                point.latitude,
                point.longitude,
                point.altitude,
                point.precision
        )
    }
}