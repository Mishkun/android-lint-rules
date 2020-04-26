package io.github.mishkun.nbsplint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.Issue

class NbspRegistry : IssueRegistry() {
    override val issues: List<Issue> = listOf(NbspIssue)
}
