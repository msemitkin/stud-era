package ua.knu.csc.studera.repository.util

import org.springframework.stereotype.Component

@Component
open class QueryLoader {

    private val sqlBasePath = "/sql/"

    fun getQueryAsText(fileName: String): String {
        return object {}.javaClass.getResource(sqlBasePath.plus(fileName)).readText()
    }

}