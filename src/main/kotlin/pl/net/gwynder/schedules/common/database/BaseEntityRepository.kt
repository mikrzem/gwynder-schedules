package pl.net.gwynder.schedules.common.database

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface BaseEntityRepository<Entity : BaseEntity> : JpaRepository<Entity, Long>, JpaSpecificationExecutor<Entity> {

    fun findByOwner(owner: String): List<Entity>

    fun findByOwner(owner: String, pageable: Pageable): Page<Entity>

    fun findFirstByOwnerAndId(owner: String, id: Long): Optional<Entity>

}