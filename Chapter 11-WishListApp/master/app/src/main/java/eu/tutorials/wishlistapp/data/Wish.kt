package eu.tutorials.wishlistapp.data

data class Wish(val id:Long = (0L..100L).shuffled().last(),
                val title:String="",
                val description:String="")
object DummyWish{
    val wishList = listOf(Wish(title = "Google Watch", description = "An android watch" +
            "made by Google for tracking health fitness"),
        Wish(title = "Oculus Quest 2", description = "A VR headset for " +
                "playing games"),
        Wish(title = "A Sci-fi, Book", description = "A science friction book" +
                "from any best seller"),
        Wish(title = "Bean bag", description = "A comfy bean bag to substitute" +
                "for a chair")
    )
}
