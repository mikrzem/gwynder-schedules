package pl.net.gwynder.schedules.common.errors

class InvalidFormat(type: String) : RuntimeException("Invalid $type format")