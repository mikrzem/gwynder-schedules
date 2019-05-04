package pl.net.gwynder.schedules.events.controllers

import org.springframework.web.bind.annotation.*
import pl.net.gwynder.schedules.common.BaseController
import pl.net.gwynder.schedules.events.entities.ScheduledEventHeader
import pl.net.gwynder.schedules.events.services.ScheduledEventService

@RestController
@RequestMapping("/api/schedules/events")
class ScheduledEventController(
        private val service: ScheduledEventService
) : BaseController() {

    @GetMapping
    fun select(
            @RequestParam("from") from: String,
            @RequestParam("to") to: String
    ): List<ScheduledEventHeader> {
        return service.select(
                ownerProvider.owner(),
                from,
                to
        ).map(service::toHeader)
    }

    @GetMapping("/{id}")
    fun get(
            @PathVariable("id") id: Long
    ): ScheduledEventHeader {
        return service.toHeader(
                service.get(ownerProvider.owner(), id)
        )
    }

    @PostMapping
    fun create(
            @RequestBody data: ScheduledEventHeader
    ): ScheduledEventHeader {
        return service.toHeader(
                service.create(
                        ownerProvider.owner(),
                        data
                )
        )
    }

    @PutMapping("/{id}")
    fun update(
            @PathVariable("id") id: Long,
            @RequestBody data: ScheduledEventHeader
    ): ScheduledEventHeader {
        return service.toHeader(
                service.update(ownerProvider.owner(), id, data)
        )
    }

    @DeleteMapping("/{id}")
    fun delete(
            @PathVariable("id") id: Long
    ) {
        service.delete(ownerProvider.owner(), id)
    }

}