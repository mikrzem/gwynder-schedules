package pl.net.gwynder.schedules.common.catalogs

import pl.net.gwynder.schedules.common.BaseData

abstract class BaseCatalogData(
        id: Long?,
        val name: String
) : BaseData(id)