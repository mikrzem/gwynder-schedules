package pl.net.gwynder.schedules.central.entities.responses

import com.fasterxml.jackson.annotation.JsonCreator

class DashboardInfo @JsonCreator constructor(
        val title: String,
        val rows: List<DashboardInfoRow>
)

class DashboardInfoRow @JsonCreator constructor(
        val description: String,
        val content: String
)