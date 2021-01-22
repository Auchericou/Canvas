import android.content.Context
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.view.View.OnTouchListener
import fr.esimed.exemple.canvas.GameEngine
import java.lang.Thread.sleep

class GameView(context: Context?) : SurfaceView(context),
        SurfaceHolder.Callback, OnTouchListener {
    private val surface: SurfaceHolder
    private val game = GameEngine(context!!)
    private var running = true

    init {
        surface = holder
        surface.addCallback(this)
        isFocusable = true
    }

    private val Loop : Runnable = object : Runnable {
        override fun run() {
            running = true
            while (running) {
                val canvas = surface.lockCanvas()
                game.update(canvas,width, height)
                if (canvas != null) {
                    game.draw(canvas)
                    surface.unlockCanvasAndPost(canvas)

                }
                Thread.sleep(1)
            }
        }
    }

    private var loopThread = Thread(Loop)

    override fun surfaceChanged(
            holder: SurfaceHolder, format: Int, width: Int,
            height: Int
    ) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        if (!running) {
            loopThread = Thread(Loop)
            loopThread.start()
        } else {
            loopThread.start()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

        var retry = true
        running = false
        while (retry) {
            try {
                loopThread.join()
                retry = false
            } catch (e: InterruptedException) {
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        TODO("Not yet implemented")
    }
}