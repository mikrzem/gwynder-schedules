package pl.net.gwynder.schedules.events.entities

import pl.net.gwynder.schedules.common.database.BaseEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class ScheduledEvent(
        @Column(nullable = false)
        var title: String = "",
        @Column(nullable = false)
        var startTime: LocalDateTime = LocalDateTime.now(),
        @Column(nullable = false)
        var endTime: LocalDateTime = LocalDateTime.now(),
        owner: String = ""
) : BaseEntity(owner)