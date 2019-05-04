package pl.net.gwynder.schedules.common.catalogs

import org.springframework.data.repository.NoRepositoryBean
import pl.net.gwynder.schedules.common.database.BaseEntityRepository

@NoRepositoryBean
interface BaseCatalogRepository<Catalog : BaseCatalogEntity> : BaseEntityRepository<Catalog>