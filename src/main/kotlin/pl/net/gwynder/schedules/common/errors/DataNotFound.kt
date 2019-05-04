package pl.net.gwynder.schedules.common.errors

class DataNotFound(
        type: String,
        value: Any
) : RuntimeException("Data of type $type not found for value $value")