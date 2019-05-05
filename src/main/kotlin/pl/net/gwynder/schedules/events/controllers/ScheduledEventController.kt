package pl.net.gwynder.schedules.events.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.web.bind.annotation.*
import pl.net.gwynder.schedules.common.BaseController
import pl.net.gwynder.schedules.events.entities.ScheduledEventFilter
import pl.net.gwynder.schedules.events.entities.ScheduledEventHeader
import pl.net.gwynder.schedules.events.services.ScheduledEventService
import java.util.*

@RestController
@RequestMapping("/api/schedules/events")
class ScheduledEventController(
        private val service: ScheduledEventService,
        private val json: ObjectMapper
) : BaseController() {

    @GetMapping
    fun select(
            @RequestParam("filter") filter: String
    ): List<ScheduledEventHeader> {
        return service.select(
                ownerProvider.owner(),
                json.readValue(
                        Base64.getDecoder().decode(filter),
                        ScheduledEventFilter::class.java
                )
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