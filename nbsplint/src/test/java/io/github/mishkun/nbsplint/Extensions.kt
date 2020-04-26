package io.github.mishkun.nbsplint

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintResult
import com.android.tools.lint.checks.infrastructure.TestLintTask
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.android.tools.lint.detector.api.Issue

fun String.lintAsStringResourceWith(vararg issues: Issue): TestLintResult =
        lint().xmlFile("res/values/strings.xml", this)
                .allowDuplicates()
                .issues(*issues)
                .allowMissingSdk()
                .run()

fun TestLintTask.xmlFile(filename: String, content: String): TestLintTask = files(xml(filename, content))
