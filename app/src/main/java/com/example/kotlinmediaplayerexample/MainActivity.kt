package com.example.kotlinmediaplayerexample

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {

    val arrSong = ArrayList<Song>()
    var position = 0
    lateinit var mediaPlayer: MediaPlayer
    val simpleDateFormat = SimpleDateFormat("mm : ss")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initMediaPlayer()


        tv_play.setOnClickListener(View.OnClickListener {
            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
                tv_play.text = "Pause"
            } else {
                mediaPlayer.pause()
                tv_play.text = "Play"
            }

            setTimeTotal()
            updateTimeSong()
        })

        tv_stop.setOnClickListener(View.OnClickListener {
            mediaPlayer.stop()
            mediaPlayer.release()
            initMediaPlayer()
            tv_play.text = "Play"
        })

        tv_next.setOnClickListener(View.OnClickListener {
            onNextSong()
        })

        tv_previous.setOnClickListener(View.OnClickListener {
            position--
            if (position < 0)
                position = arrSong.size - 1
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.release()
            }
            initMediaPlayer()
            mediaPlayer.start()
            tv_play.text = "Pause"
            setTimeTotal()
            updateTimeSong()
        })

        sb_music.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                mediaPlayer.seekTo(sb_music.progress)
            }

        })
    }

    fun initData() {
        arrSong.add(Song("Lonely", R.raw.lonely))
        arrSong.add(Song("doihoamattroi", R.raw.doihoamattroi))
        arrSong.add(Song("xaodong", R.raw.xaodong))
    }

    fun initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, arrSong.get(position).file)
        tv_title_song.text = arrSong.get(position).title
    }

    fun setTimeTotal() {
        tv_time_total.text = simpleDateFormat.format(mediaPlayer.duration)

        sb_music.max = mediaPlayer.duration
    }

    fun updateTimeSong() {
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                tv_time_life.text = simpleDateFormat.format(mediaPlayer.currentPosition)
                sb_music.progress = mediaPlayer.currentPosition
                handler.postDelayed(this, 500)
                if (sb_music.progress == sb_music.max) {
                    Log.d("001", "onCompletion: sssssss")
                }
//

                mediaPlayer.setOnCompletionListener(OnCompletionListener {
                    onNextSong()

                })
            }

        }, 100)


    }

    fun onNextSong() {
        position++
        if (position > arrSong.size - 1)
            position = 0
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        initMediaPlayer()
        mediaPlayer.start()
        tv_play.text = "Pause"
        setTimeTotal()
        updateTimeSong()
    }
}

