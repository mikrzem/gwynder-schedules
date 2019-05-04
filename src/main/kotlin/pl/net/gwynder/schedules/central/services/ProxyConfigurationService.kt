package pl.net.gwynder.schedules.central.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import pl.net.gwynder.schedules.central.entities.configuration.ProxyApiData
import pl.net.gwynder.schedules.central.entities.configuration.ProxyApplicationData
import pl.net.gwynder.schedules.central.entities.configuration.ProxyDashboardData
import pl.net.gwynder.schedules.central.entities.configuration.ProxyHealthData
import pl.net.gwynder.schedules.common.BaseService
import java.nio.file.Files
import java.nio.file.Path

@Service
class ProxyConfigurationService(
        private val restTemplate: RestTemplate,
        @Value("\${schedules_register:false}")
        private val shouldRegister: Boolean,
        @Value("\${schedules_register_url:invalid}")
        private val registerUrl: String,
        @Value("\${schedules_self_url:localhost}")
        private val selfUrl: String,
        @Value("\${schedules_token_file:none}")
        private val tokenFile: String,
        private val applicationContext: ApplicationContext
) : BaseService() {

    private var registered = false

    private var registrationAttemptsLeft = 10

    private val configuration = ProxyApiData(
            "schedules",
            "$selfUrl/api/schedules",
            ProxyApplicationData(
                    true,
                    "$selfUrl/application/schedules",
                    "Schedules",
                    "page/"
            ),
            ProxyDashboardData(
                    true,
                    null
            ),
            ProxyHealthData(
                    true,
                    null
            )
    )

    @Scheduled(initialDelay = 100, fixedDelay = 60 * 1000)
    fun registerWithCentral() {
        if (!registered && shouldRegister) {
            if (registrationAttemptsLeft > 0) {
                try {
                    sendRegistration()
                    registered = true
                } catch (ex: Exception) {
                    logger.error("Failed to register with central", ex)
                    registrationAttemptsLeft--
                    logger.warn("Attempts left: $registrationAttemptsLeft")
                }
                if (registrationAttemptsLeft == 0) {
                    logger.warn("Failed to register with central! Shutting down!")
                    SpringApplication.exit(applicationContext)
                }
            }
        }
    }

    private fun token(): String {
        return Files.readString(Path.of(tokenFile))
    }

    private fun sendRegistration() {
        val url = "$registerUrl/proxy/api/notes"
        val headers = HttpHeaders.writableHttpHeaders(HttpHeaders())
        headers.set("InternalToken", token())
        val entity = HttpEntity(
                configuration,
                headers
        )
        val response = restTemplate.exchange(url, HttpMethod.PUT, entity, ProxyApiData::class.java)
        if (response.statusCode != HttpStatus.OK) {
            throw RuntimeException("Failed to register with code: ${response.statusCodeValue}")
        }
    }

}