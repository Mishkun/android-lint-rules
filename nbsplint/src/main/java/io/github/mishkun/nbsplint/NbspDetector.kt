package io.github.mishkun.nbsplint

import com.android.SdkConstants
import com.android.resources.ResourceFolderType
import com.android.tools.lint.detector.api.ResourceXmlDetector
import com.android.tools.lint.detector.api.XmlContext
import com.android.utils.text
import org.w3c.dom.Element

class NbspDetector : ResourceXmlDetector() {
    private val nbspRegexes: List<Regex> =
            (conjuntionsRu + prepositionsRu + particlesRu).map {
                "(^|\\s)$it\\s".toRegex(RegexOption.IGNORE_CASE)
            }.plus(digitsRegexes)

    override fun appliesTo(folderType: ResourceFolderType) = ResourceFolderType.VALUES == folderType
    override fun getApplicableElements(): Collection<String>? = listOf(SdkConstants.TAG_STRING)
    override fun visitElement(context: XmlContext, element: Element) {
        val childNode = element.childNodes.item(0)
        val nbspFindings = nbspRegexes.flatMap { wordRegex ->
            val matches = wordRegex.findAll(childNode.textContent)
            matches.map { childNode.textContent.substring(it.range).dropLast(1) to it.range }.asIterable()
        }

        if (nbspFindings.isNotEmpty()) {
            nbspFindings.forEach { (foundWithoutNbsp, range) ->

                val fix = fix()
                        .name("add nbsp")
                        .replace()
                        .text(" ")
                        .with("&#160;")
                        .autoFix()
                        .build()

                context.report(
                        issue = NbspIssue,
                        location = context.getLocation(childNode, range.first + 1, range.last + 1),
                        message = "Missing nbsp",
                        quickfixData = fix
                )
            }
        }
    }
}

private val digitsRegexes = listOf(
        """(^|\s)\d+\s""".toRegex(),
        """(^|\s)%d\s""".toRegex()
)

private val conjuntionsRu = listOf(
        "и",
        "но",
        "или",
        "да"
)

private val particlesRu = listOf("не")

private val prepositionsRu = listOf(
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
