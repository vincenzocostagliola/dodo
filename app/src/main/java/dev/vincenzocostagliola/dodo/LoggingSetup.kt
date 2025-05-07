package dev.vincenzocostagliola.dodo

import timber.log.Timber
import java.util.Locale

object LoggingSetup {
    private const val TAG_LOGGING = "DODO"

    internal fun setupLogging() {
        val timberTag = TAG_LOGGING
        // TODO Add Remote logging like sentry
        Timber.plant(ExplicitDebugTree())
        Timber.tag(timberTag)
    }

    private open class ExplicitDebugTree : Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return String.format(
                Locale.US,
                "(%s:%s) [%s()]",
                element.fileName,
                element.lineNumber,
                element.methodName
            )
        }
    }
}