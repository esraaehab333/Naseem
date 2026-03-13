import com.google.gson.annotations.SerializedName

data class MainForecast(
    @SerializedName("temp")
    val temp: Double,

    @SerializedName("feels_like")
    val feelsLike: Double,

    @SerializedName("temp_min")
    val tempMin: Double,

    @SerializedName("temp_max")
    val tempMax: Double,

    @SerializedName("pressure")
    val pressure: Long,

    @SerializedName("sea_level")
    val seaLevel: Long,

    @SerializedName("grnd_level")
    val grndLevel: Long,

    @SerializedName("humidity")
    val humidity: Long,

    @SerializedName("temp_kf")
    val tempKf: Double
)