package io.github.mishkun.nbsplint

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.XmlContext
import com.android.utils.text
import org.w3c.dom.Element
import java.util.*

class NbspDetector : ResourceXmlDetector() {
    override fun appliesTo(folderType: ResourceFolderType) = ResourceFolderType.VALUES == folderType
    override fun getApplicableElements(): Collection<String>? = listOf(SdkConstants.TAG_STRING)
    override fun visitElement(context: XmlContext, element: Element) {
        val prepositionsWithSpaces = prepositionsRu.filter { prep -> "$prep " in element.text().toLowerCase(Locale("ru")) }
        if (prepositionsWithSpaces.isNotEmpty()) {
            prepositionsWithSpaces.forEach { prep ->
                val fix = fix()
                        .name("add nbsp")
                        .replace()
                        .text("$prep ")
                        .with("$prep&#160;")
                        .independent(true)
                        .autoFix()
                        .build()

                context.report(NbspPrepositionIssue, context.getLocation(element), "Missing nbsp", fix)
            }
        }
    }
}

private val prepositionsRu = setOf(
        "без",
        "в",
        "для",
        "за",
        "из",
        "к",
        "на",
        "над",
        "о",
        "об",
        "от",
        "по",
        "под",
        "пред",
        "при",
        "про",
        "с",
        "у",
        "через"
)
