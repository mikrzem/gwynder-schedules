package pl.net.gwynder.schedules.events.services

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import pl.net.gwynder.schedules.common.database.BaseEntityRepository
import pl.net.gwynder.schedules.events.entities.ScheduledEvent
import java.time.LocalDateTime

interface ScheduledEventRepository : BaseEntityRepository<ScheduledEvent> {

    @Query("SELECT e FROM ScheduledEvent e WHERE e.owner = :owner AND e.startTime <= :toTime AND e.endTime >= :fromTime")
    fun findByDates(
            @Param("owner") owner: String,
            @Param("fromTime") fromTime: LocalDateTime,
            @Param("toTime") toTime: LocalDateTime
    ): List<ScheduledEvent>

}