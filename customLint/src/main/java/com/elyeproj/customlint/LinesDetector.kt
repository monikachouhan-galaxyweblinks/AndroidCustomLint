package com.elyeproj.customlint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.*
import org.jetbrains.uast.UClass
import sun.rmi.log.ReliableLog
import java.util.*

const val MAX_LINE_IN_CLASS = 10

val ISSUE_LINE_EXCEEDED = Issue.create("LinesExceeded",
        "Only $MAX_LINE_IN_CLASS allow in class.",
        "Class should not use more than $MAX_LINE_IN_CLASS lines.",
        Category.CORRECTNESS, 5, Severity.WARNING,
        Implementation(LineDetector::class.java, EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES)))

class LineDetector : Detector(), Detector.UastScanner {

    override fun getApplicableUastTypes() = listOf(UClass::class.java)
    override fun createUastHandler(context: JavaContext) = LineDetectionHandler(context)

    class LineDetectionHandler(private val context: JavaContext) :
            UElementHandler() {

        override fun visitClass(node: UClass) {
/*
            if (node.name?.contains("Activity") == true) {
*/            // if (node.psi.methods)
                context.report(ISSUE_LINE_EXCEEDED, node,
                        context.getNameLocation(node),
                        "Class ${node.name} ${node.textLength} should contain only $MAX_LINE_IN_CLASS line.")
           // }

        }

    }
}
