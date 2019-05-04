package pl.net.gwynder.schedules.central.entities.configuration

import com.fasterxml.jackson.annotation.JsonCreator

data class ProxyDashboardData @JsonCreator constructor(
        val active: Boolean,
        val path: String?
)