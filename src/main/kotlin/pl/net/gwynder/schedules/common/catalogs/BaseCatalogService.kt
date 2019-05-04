package pl.net.gwynder.schedules.common.catalogs

import pl.net.gwynder.schedules.common.BaseService
import pl.net.gwynder.schedules.common.errors.DataNotFound

abstract class BaseCatalogService<Entity : BaseCatalogEntity, Data : BaseCatalogData>(
        protected val repository: BaseCatalogRepository<Entity>
) : BaseService() {

    fun select(owner: String): List<Entity> {
        return repository.findByOwner(owner)
    }

    fun create(owner: String, data: Data): Entity {
        return repository.save(
                newEntity(owner, data)
        )
    }

    protected abstract fun newEntity(owner: String, data: Data): Entity

    fun get(owner: String, id: Long): Entity {
        return repository.findFirstByOwnerAndId(owner, id)
                .orElseThrow { DataNotFound(name, id) }
    }

    fun update(owner: String, id: Long, data: Data): Entity {
        val current = get(owner, id)
        current.name = data.name
        fillEntity(current, data)
        return repository.save(current)
    }

    protected abstract fun fillEntity(entity: Entity, data: Data)

    fun delete(owner: String, id: Long) {
        val current = get(owner, id)
        repository.delete(current)
    }

    abstract fun toData(entity: Entity): Data

    protected abstract val name: String

    fun fromData(owner: String, data: Data): Entity {
        if (data.id == null) {
            throw DataNotFound(name, "<null>")
        }
        return get(owner, data.id)
    }

}