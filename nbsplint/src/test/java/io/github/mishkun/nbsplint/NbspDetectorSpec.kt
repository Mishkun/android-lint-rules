package io.github.mishkun.nbsplint

import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.android.tools.lint.detector.api.Severity
import io.kotest.core.spec.style.StringSpec
import org.intellij.lang.annotations.Language

class NbspDetectorSpec : StringSpec({
    "should catch some nbsp issues" {
        @Language("XML")
        val fileContent = """
        <resources>
            <string name="some_string">О нас</string>
        </resources>        
        """.trimIndent()
        lint().xmlFile("res/values/strings.xml", fileContent)
                .issues(NbspPrepositionIssue)
                .allowMissingSdk()
                .run()
                .expectCount(1, Severity.WARNING)
    }
})
