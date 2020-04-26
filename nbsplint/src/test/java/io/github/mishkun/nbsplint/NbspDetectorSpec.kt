package io.github.mishkun.nbsplint

import io.kotest.core.spec.style.StringSpec
import org.intellij.lang.annotations.Language

class NbspDetectorSpec : StringSpec({
    "should be clean" {
        @Language("XML")
        val fileContent = """
        <resources>
            <string name="about">О&#160;нас</string>
            <string name="for_us">Для&#160;нас</string>
        </resources>        
        """.trimIndent()

        fileContent.lintAsStringResourceWith(NbspIssue)
                .expectClean()
    }
    "should catch nbsp issue" {
        @Language("XML")
        val fileContent = """
        <resources>
            <string name="about">о нас</string>
        </resources>        
        """.trimIndent()

        fileContent.lintAsStringResourceWith(NbspIssue)
                .expect("""
                    res/values/strings.xml:2: Warning: Missing nbsp [MissingNbsp]
                        <string name="about">о нас</string>
                                              ^
                    0 errors, 1 warnings
                """.trimIndent())
                .expectFixDiffs("""
                    Fix for res/values/strings.xml line 2: add nbsp:
                    @@ -2 +2
                    -     <string name="about">о нас</string>
                    +     <string name="about">о&#160;нас</string>
                """.trimIndent())
    }
    "should catch cased issue" {
        @Language("XML")
        val fileContent = """
        <resources>
            <string name="jaggered_case">ЧеРеЗ нас</string>
        </resources>        
        """.trimIndent()


        fileContent.lintAsStringResourceWith(NbspIssue)
                .expect("""
                    res/values/strings.xml:2: Warning: Missing nbsp [MissingNbsp]
                        <string name="jaggered_case">ЧеРеЗ нас</string>
                                                      ^
                    0 errors, 1 warnings
                """.trimIndent())
                .expectFixDiffs("""
                    Fix for res/values/strings.xml line 2: add nbsp:
                    @@ -2 +2
                    -     <string name="jaggered_case">ЧеРеЗ нас</string>
                    +     <string name="jaggered_case">ЧеРеЗ&#160;нас</string>
                """.trimIndent())
    }
    "should catch multiple issues in one string" {
        @Language("XML")
        val fileContent = """
        <resources>
            <string name="about">о нас и за нас</string>
        </resources>        
        """.trimIndent()

        fileContent.lintAsStringResourceWith(NbspIssue)
                .expect("""
                    res/values/strings.xml:2: Warning: Missing nbsp [MissingNbsp]
                        <string name="about">о нас и за нас</string>
                                              ^
                    res/values/strings.xml:2: Warning: Missing nbsp [MissingNbsp]
                        <string name="about">о нас и за нас</string>
                                                   ^
                    res/values/strings.xml:2: Warning: Missing nbsp [MissingNbsp]
                        <string name="about">о нас и за нас</string>
                                                     ^
                    0 errors, 3 warnings
                """.trimIndent())
                .expectFixDiffs("""
                    Fix for res/values/strings.xml line 2: add nbsp:
                    @@ -2 +2
                    -     <string name="about">о нас и за нас</string>
                    +     <string name="about">о&#160;нас и за нас</string>
                    Fix for res/values/strings.xml line 2: add nbsp:
                    @@ -2 +2
                    -     <string name="about">о нас и за нас</string>
                    +     <string name="about">о нас и&#160;за нас</string>
                    Fix for res/values/strings.xml line 2: add nbsp:
                    @@ -2 +2
                    -     <string name="about">о нас и за нас</string>
                    +     <string name="about">о нас и за&#160;нас</string>
                """.trimIndent())
    }
    "should catch missing nbsp after digit" {
        @Language("XML")
        val fileContent = """
        <resources>
            <string name="many_hours">3500 часов</string>
            <string name="some_meters">%d метров</string>
        </resources>        
        """.trimIndent()

        fileContent.lintAsStringResourceWith(NbspIssue)
                .expect("""
                    res/values/strings.xml:2: Warning: Missing nbsp [MissingNbsp]
                        <string name="many_hours">3500 часов</string>
                                                   ^
                    res/values/strings.xml:3: Warning: Missing nbsp [MissingNbsp]
                        <string name="some_meters">%d метров</string>
                                                    ^
                    0 errors, 2 warnings
                """.trimIndent())
                .expectFixDiffs("""
                    Fix for res/values/strings.xml line 2: add nbsp:
                    @@ -2 +2
                    -     <string name="many_hours">3500 часов</string>
                    +     <string name="many_hours">3500&#160;часов</string>
                    Fix for res/values/strings.xml line 3: add nbsp:
                    @@ -3 +3
                    -     <string name="some_meters">%d метров</string>
                    +     <string name="some_meters">%d&#160;метров</string>
                """.trimIndent())
    }
})
