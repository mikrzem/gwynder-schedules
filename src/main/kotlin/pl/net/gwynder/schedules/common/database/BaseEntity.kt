package pl.net.gwynder.schedules.common.database

import java.io.Serializable
import javax.persistence.*

@MappedSuperclass
abstract class BaseEntity(
        @Column(nullable = false)
        var owner: String = ""
) : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}