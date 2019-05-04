package pl.net.gwynder.schedules.events.entities

import pl.net.gwynder.schedules.common.BaseData

class ScheduledEventHeader(
        id: Long?,
        val title: String,
        val start: String,
        val end: String
) : BaseData(id)