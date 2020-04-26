package io.github.mishkun.nbsplint

import com.android.tools.lint.detector.api.Category.Companion.TYPOGRAPHY
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Scope.Companion.RESOURCE_FILE_SCOPE
import com.android.tools.lint.detector.api.Severity

val NbspPrepositionIssue = Issue.create(
        id = "MissingPrepositionNbsp",
        briefDescription = "Use nbsp after preposition",
        explanation = "Slavonic languages require nbsp to be inserted after prepositions to avoid hanging prepositions",
        category = TYPOGRAPHY,
        priority = 5,
        severity = Severity.WARNING,
        implementation = Implementation(NbspDetector::class.java, RESOURCE_FILE_SCOPE)
)

val NbspDashIssue = Issue.create(
        id = "MissingDashNbsp",
        briefDescription = "Use nbsp before dash",
        explanation = "Slavonic languages require nbsp to be inserted before dashes",
        category = TYPOGRAPHY,
        priority = 5,
        severity = Severity.WARNING,
        implementation = Implementation(NbspDetector::class.java, RESOURCE_FILE_SCOPE)
)
