package li.songe.gkd.accessibility

import android.content.Context
import android.content.Intent
import kotlinx.coroutines.delay
import li.songe.gkd.App
import li.songe.gkd.composition.CompositionService
import li.songe.gkd.composition.CompositionExt.useScope
import li.songe.gkd.utils.launchWhile
import li.songe.gkd.utils.Ext.createNotificationChannel

class KeepAliveService : CompositionService({
    createNotificationChannel(this)
    val scope = useScope()
    scope.launchWhile {
        delay(3_000)
    }
}) {
    companion object {
        fun start(context: Context = App.context) {
            context.startForegroundService(Intent(context, KeepAliveService::class.java))
        }

        fun stop(context: Context = App.context) {
            context.stopService(Intent(context, KeepAliveService::class.java))
        }
    }
}