package eu.wedgess.luas.domain.model

data class StopInformationEntity(
    val message: String,
    val stopName: String,
    val requestTime: String,
    val stopCode: String,
    val inboundTrams: List<TramEntity>,
    val outboundTrams: List<TramEntity>
)