package lee.module.video

import android.content.Context
import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.net.Uri
import android.util.AttributeSet
import android.view.Surface
import android.view.TextureView
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner


/**
 * This class is used for raw video files.
 * Xml:
 *      <com.zipmex.view.videoview.RawVideoView
 *          app:video_raw_res="@raw/..."
 *          app:video_loop="true" />
 * Kotlin: lifecycle.addObserver(viewBackground)
 *
 */
class RawVideoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : TextureView(context, attrs, defStyle), DefaultLifecycleObserver, TextureView.SurfaceTextureListener {

    private var isLoopEnabled: Boolean = true
    private lateinit var mediaPlayer: MediaPlayer

    init {
        surfaceTextureListener = this

        val styleAttr = context.theme.obtainStyledAttributes(attrs, R.styleable.RawVideoView, defStyle, 0)
        isLoopEnabled = styleAttr.getBoolean(R.styleable.RawVideoView_video_loop, isLoopEnabled)

        styleAttr.getResourceId(R.styleable.RawVideoView_video_raw_res, -1).takeIf { it != -1 }?.let { rawRes ->
            setRawRes(rawRes)
        }

        (context as? AppCompatActivity)?.lifecycle?.addObserver(this)
    }

    override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
        val surface = Surface(surfaceTexture)
        mediaPlayer.setSurface(surface)
    }

    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) = Unit
    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean = true
    override fun onSurfaceTextureUpdated(p0: SurfaceTexture) = Unit

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        if (mediaPlayer.isPlaying.not()) mediaPlayer.start()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        if (mediaPlayer.isPlaying) mediaPlayer.pause()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        release()
    }

    private fun setRawRes(@RawRes rawId: Int) {
        mediaPlayer = MediaPlayer.create(context, rawId)
        initMediaPlayer()
    }

    private fun setUri(uri: Uri) {
        mediaPlayer = MediaPlayer.create(context, uri)
        initMediaPlayer()
    }

    private fun initMediaPlayer() {
        with(mediaPlayer) {
            isLooping = isLoopEnabled
            if (isAvailable) {
                setSurface(Surface(surfaceTexture))
            }
        }
    }

    fun release() {
        mediaPlayer.stop()
        mediaPlayer.setSurface(null)
        mediaPlayer.release()
    }
}
