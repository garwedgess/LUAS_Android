package eu.wedgess.luas.domain.model

data class StopInformationEntity(
    val message: String,
    val name: String,
    val time: String,
    val code: String,
    val inboundTrams: List<TramEntity>,
    val outboundTrams: List<TramEntity>
)