package pl.net.gwynder.schedules.common.catalogs

import pl.net.gwynder.schedules.common.database.BaseEntity
import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseCatalogEntity(
        @Column(nullable = false)
        var name: String = "",
        owner: String = ""
) : BaseEntity(owner)