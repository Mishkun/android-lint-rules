package io.github.mishkun.nbsplint

import com.android.tools.lint.detector.api.Category.Companion.TYPOGRAPHY
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import com.android.tools.lint.detector.api.Severity

val NbspIssue = Issue.create(
        id = "MissingNbsp",
        briefDescription = "Use nbsp after some words",
        explanation = """
            |Slavonic languages require nbsp to be inserted after prepositions, 
            |short particles and conjuctions, between quantities and their units of measurement""".trimMargin(),
        category = TYPOGRAPHY,
        priority = 5,
        severity = Severity.WARNING,
        implementation = Implementation(NbspDetector::class.java, RESOURCE_FILE_SCOPE)
)
