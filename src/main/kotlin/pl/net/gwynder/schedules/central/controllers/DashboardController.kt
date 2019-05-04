package pl.net.gwynder.schedules.central.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.net.gwynder.schedules.central.entities.responses.DashboardInfo
import pl.net.gwynder.schedules.common.BaseController

@RestController
@RequestMapping("/api/schedules/central/dashboard")
class DashboardController : BaseController() {

    @GetMapping("/")
    fun readDashboard(): DashboardInfo {
        return DashboardInfo(
                "Schedules",
                ArrayList()
        )
    }

}