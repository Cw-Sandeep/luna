package tv.cloudwalker.lunalauncher.com.utils

fun urlFormatter(url: String): String {
    return if (!url.contains("https"))
        url.replace("http", "https")
    else url
}
