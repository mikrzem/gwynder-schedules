package pl.net.gwynder.schedules.common

import org.slf4j.Logger
import org.slf4j.LoggerFactory

abstract class BaseService {

    protected val logger: Logger = LoggerFactory.getLogger(javaClass)

}