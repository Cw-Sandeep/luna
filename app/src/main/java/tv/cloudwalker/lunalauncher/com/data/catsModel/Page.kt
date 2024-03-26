package tv.cloudwalker.lunalauncher.com.data.catsModel

data class Page(
    val carouselEndpoint: String,
    val pageLogo: String,
    val pageName: String,
    val rowContentEndpoint: List<String>
) {
    operator fun invoke(): String {
        val argList = StringBuilder()
        pageName.let { nnArgs ->
            nnArgs.forEach { arg -> argList.append("/{$arg}") }
        }
        return pageName + argList
    }

}