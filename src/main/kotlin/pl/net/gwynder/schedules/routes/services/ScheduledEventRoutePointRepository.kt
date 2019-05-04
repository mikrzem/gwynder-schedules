package pl.net.gwynder.schedules.routes.services

import org.springframework.data.jpa.repository.Modifying
import pl.net.gwynder.schedules.common.database.BaseEntityRepository
import pl.net.gwynder.schedules.events.entities.ScheduledEvent
import pl.net.gwynder.schedules.routes.entities.ScheduledEventRoutePoint
import javax.transaction.Transactional

interface ScheduledEventRoutePointRepository : BaseEntityRepository<ScheduledEventRoutePoint> {

    fun findByEventOrderByPointTimeAsc(event: ScheduledEvent): List<ScheduledEventRoutePoint>

    @Modifying
    @Transactional
    fun deleteByEvent(event: ScheduledEvent)

}