package pl.net.gwynder.schedules.central.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.net.gwynder.schedules.central.entities.responses.HealthInfo
import pl.net.gwynder.schedules.common.BaseController
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/api/schedules/central/health")
class HealthController : BaseController() {

    private val startupTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)

    @GetMapping("/")
    fun checkHealth(): HealthInfo {
        return HealthInfo(
                true,
                startupTime
        )
    }

}