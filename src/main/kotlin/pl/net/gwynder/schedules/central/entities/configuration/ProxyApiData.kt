package pl.net.gwynder.schedules.central.entities.configuration

import com.fasterxml.jackson.annotation.JsonCreator

data class ProxyApiData @JsonCreator constructor(
        val name: String,
        val path: String,
        val application: ProxyApplicationData?,
        val dashboard: ProxyDashboardData?,
        val health: ProxyHealthData?
)