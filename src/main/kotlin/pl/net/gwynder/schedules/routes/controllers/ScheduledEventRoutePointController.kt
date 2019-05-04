package pl.net.gwynder.schedules.routes.controllers

import org.springframework.web.bind.annotation.*
import pl.net.gwynder.schedules.common.BaseController
import pl.net.gwynder.schedules.routes.entities.ScheduledEventRoutePointData
import pl.net.gwynder.schedules.routes.entities.ScheduledEventRouteSegment
import pl.net.gwynder.schedules.routes.services.ScheduledEventRoutePointService

@RestController
@RequestMapping("/api/schedules/events/{eventId}/route")
class ScheduledEventRoutePointController(
        private val service: ScheduledEventRoutePointService
) : BaseController() {

    @GetMapping
    fun select(
            @PathVariable("eventId") eventId: Long
    ): List<ScheduledEventRoutePointData> {
        return service.select(
                ownerProvider.owner(),
                eventId
        ).map(service::toData)
    }

    @PostMapping
    fun append(
            @PathVariable("eventId") eventId: Long,
            @RequestBody segment: ScheduledEventRouteSegment
    ) {
        service.append(
                ownerProvider.owner(),
                eventId,
                segment
        )
    }

    @DeleteMapping
    fun clear(
            @PathVariable("eventId") eventId: Long
    ) {
        service.clear(
                ownerProvider.owner(),
                eventId
        )
    }

}