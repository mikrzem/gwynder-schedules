package pl.net.gwynder.schedules.central.entities.responses

import com.fasterxml.jackson.annotation.JsonCreator

class HealthInfo @JsonCreator constructor(
        val healthy: Boolean,
        val startupTime: String
)

class HealthResponse @JsonCreator constructor(
        val code: Int,
        val info: HealthInfo?
)