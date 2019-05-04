package pl.net.gwynder.schedules.common

import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

const val USER_HEADER = "CentralUserDisplayName"
const val DEFAULT_USER = "default_user"

@Service
class OwnerProvider(
        private val request: HttpServletRequest
) {

    fun owner(): String {
        val headerContent = request.getHeader(USER_HEADER)
        return headerContent ?: DEFAULT_USER
    }

}