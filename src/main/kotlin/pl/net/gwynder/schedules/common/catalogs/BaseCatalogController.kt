package pl.net.gwynder.schedules.common.catalogs

import org.springframework.web.bind.annotation.*
import pl.net.gwynder.schedules.common.BaseController

abstract class BaseCatalogController<Entity : BaseCatalogEntity, Data : BaseCatalogData>(
        protected val service: BaseCatalogService<Entity, Data>
) : BaseController() {

    @GetMapping("/{id}")
    fun get(@PathVariable("id") id: Long): Data {
        return service.toData(
                service.get(ownerProvider.owner(), id)
        )
    }

    @PostMapping
    fun create(@RequestBody data: Data): Data {
        return service.toData(
                service.create(ownerProvider.owner(), data)
        )
    }

    @PutMapping("/{id}")
    fun update(
            @PathVariable("id") id: Long,
            @RequestBody data: Data
    ): Data {
        return service.toData(
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