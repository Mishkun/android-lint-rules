package io.github.mishkun.nbsplint

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask

fun TestLintTask.xmlFile(filename: String, content: String): TestLintTask = files(xml(filename, content))
