package li.songe.gkd.debug

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ServiceUtils
import li.songe.gkd.App
import li.songe.gkd.composition.CompositionExt.useLifeCycleLog
import li.songe.gkd.composition.CompositionService
import li.songe.gkd.utils.Ext
import li.songe.gkd.utils.ScreenshotUtil

class ScreenshotService : CompositionService({
    useLifeCycleLog()
    Ext.createNotificationChannel(this, 110)

    onStartCommand { intent, _, _ ->
        if (intent == null) return@onStartCommand
        screenshotUtil?.destroy()
        screenshotUtil = ScreenshotUtil(this, intent)
        LogUtils.d("screenshot restart")
    }
    onDestroy {
        screenshotUtil?.destroy()
        screenshotUtil = null
    }
}) {
    companion object {
        suspend fun screenshot() = screenshotUtil?.execute()
        private var screenshotUtil: ScreenshotUtil? = null

        fun start(context: Context = App.context, intent: Intent) {
            intent.component = ComponentName(context, ScreenshotService::class.java)
            context.startForegroundService(intent)
        }

        fun isRunning() = ServiceUtils.isServiceRunning(ScreenshotService::class.java)
        fun stop(context: Context = App.context) {
            if (isRunning()) {
                context.stopService(Intent(context, ScreenshotService::class.java))
            }
        }
    }
}