package pl.net.gwynder.schedules.hosting

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.util.AntPathMatcher
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import pl.net.gwynder.schedules.common.BaseController
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/application/schedules")
class HostingController(
        @Value("\${schedules_application_directory}")
        private val applicationDirectory: String
) : BaseController() {

    private fun pagePath(): Path {
        return Paths.get(applicationDirectory, "index.html")
    }

    @GetMapping("/page/**", produces = [MediaType.TEXT_HTML_VALUE])
    @ResponseBody
    fun getPage(): ByteArray {
        return Files.readAllBytes(
                pagePath()
        )
    }

    private fun resourcePath(file: String): Path {
        return Paths.get(applicationDirectory, file)
    }

    @GetMapping("/resources/**")
    fun getResource(
            request: HttpServletRequest
    ): ResponseEntity<ByteArray> {
        val file = AntPathMatcher().extractPathWithinPattern("/application/schedules/resources/**", request.requestURI)
        val content = Files.readAllBytes(
                resourcePath(file)
        )
        val headers = HttpHeaders.writableHttpHeaders(HttpHeaders())
        fixHeaders(headers, file)
        return ResponseEntity(content, headers, HttpStatus.OK)
    }

    private fun fixHeaders(headers: HttpHeaders, file: String) {
        if (file.endsWith(".js")) {
            headers.set(HttpHeaders.CONTENT_TYPE, "application/javascript")
        } else if (file.endsWith(".css")) {
            headers.set(HttpHeaders.CONTENT_TYPE, "text/css")
        }
    }
}