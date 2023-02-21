package eu.tutorials.tour.data
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Tour(
    var placeName: String = "",
    var description: String = "",
    var userId: String = "",
    var placeImage: String = "",
    var authorName:String = "",
    var tourId:String = UUID.randomUUID().toString()
):Parcelable  {

    fun toMap() =
        mapOf(
            "tourId" to tourId,
            "placeName" to placeName,
            "description" to description,
            "userId" to userId,
            "authorName" to authorName,
            "placeImage" to placeImage
        )
}
