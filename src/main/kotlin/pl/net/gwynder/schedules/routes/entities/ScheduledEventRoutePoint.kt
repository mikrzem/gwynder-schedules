package pl.net.gwynder.schedules.routes.entities

import pl.net.gwynder.schedules.common.database.BaseEntity
import pl.net.gwynder.schedules.events.entities.ScheduledEvent
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class ScheduledEventRoutePoint(
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "eventId", nullable = false)
        var event: ScheduledEvent = ScheduledEvent(),
        @Column(nullable = false)
        var pointTime: LocalDateTime = LocalDateTime.now(),
        @Column(nullable = false)
        var latitude: Double = 0.0,
        @Column(nullable = false)
        var longitude: Double = 0.0,
        @Column(nullable = false)
        var altitude: Double = 0.0,
        @Column(nullable = false)
        var precision: Double = 0.0,
        owner: String
) : BaseEntity(owner)