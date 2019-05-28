package com.example.proyectopdm.Audio;
import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectopdm.R;

import java.io.IOException;

public class ReproducirAudio extends AppCompatActivity {
    private Context mContext;
    private Activity mActivity;

    private Button mButtonPlay;
    private Button mButtonStop;

    private SeekBar mSeekBar;

    private TextView mPass;
    private TextView mDuration;
    private TextView mDue;

    private MediaPlayer mPlayer;
    private Handler mHandler;
    private Runnable mRunnable;
     Uri videoUri;
    String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reproduciraudio);

        // Get the application context
        mContext = getApplicationContext();
        mActivity = ReproducirAudio.this;
         uri = getIntent().getStringExtra("Aduio");
        videoUri = Uri.parse(uri);
        // Get the widget reference from xml layout
        mButtonPlay = findViewById(R.id.btn_play);
        mButtonStop = findViewById(R.id.btn_stop);
        mSeekBar = findViewById(R.id.seek_bar);
        mPass = findViewById(R.id.tv_pass);
        mDuration = findViewById(R.id.tv_duration);
        mDue = findViewById(R.id.tv_due);

        // Initialize the handler
        mHandler = new Handler();


        // Click listener for playing button
        mButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // If media player another instance already running then stop it first
                Reproducir(view);


            }
        });

        // Set a click listener for top playing button
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopPlaying();
            }
        });

        /*
            SeekBar.OnSeekBarChangeListener
                A callback that notifies clients when the progress level has been changed. This
                includes changes that were initiated by the user through a touch gesture or
                arrow key/trackball as well as changes that were initiated programmatically.
        */

        // Set a change listener for seek bar
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /*
                void onProgressChanged (SeekBar seekBar, int progress, boolean fromUser)
                    Notification that the progress level has changed. Clients can use the fromUser
                    parameter to distinguish user-initiated changes from those that occurred programmatically.

                Parameters
                    seekBar SeekBar : The SeekBar whose progress has changed
                    progress int : The current progress level. This will be in the range min..max
                                   where min and max were set by setMin(int) and setMax(int),
                                   respectively. (The default values for min is 0 and max is 100.)
                    fromUser boolean : True if the progress change was initiated by the user.
            */
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mPlayer!=null && b){
                    /*
                        void seekTo (int msec)
                            Seeks to specified time position. Same as seekTo(long, int)
                            with mode = SEEK_PREVIOUS_SYNC.

                        Parameters
                            msec int: the offset in milliseconds from the start to seek to

                        Throws
                            IllegalStateException : if the internal player engine has not been initialized
                    */
                    mPlayer.seekTo(i*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    /* ----------------------------------------------------------------------------*
Función:Reproducir*
Paràmetros:view, y videoUri
Descripción:Se reproduce el Audio que se ha indicado en el file explorer
* -------------------------------------------------------------------------*/
    public void Reproducir(View view) {

        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this,videoUri);
            mediaPlayer.prepare();
        } catch (IOException e){
        }

        mediaPlayer.start();

        Toast.makeText(getApplicationContext(), "Reproduciendo audio", Toast.LENGTH_SHORT).show();
    }
    /* ----------------------------------------------------------------------------*
Función:stopPlaying*
Paràmetros:
Descripción:Se para la reproduccion indicada si la el audio no es null
* -------------------------------------------------------------------------*/
    protected void stopPlaying(){
        // If media player is not null then try to stop it
        if(mPlayer!=null){
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
            Toast.makeText(mContext,"Stop playing.",Toast.LENGTH_SHORT).show();
            if(mHandler!=null){
                mHandler.removeCallbacks(mRunnable);
            }
        }
    }


    /* ----------------------------------------------------------------------------*
Función:getAudioStats*
Paràmetros:
Descripción:Se obtiene los datos del audio para ponerlo en los textviews indicado dando informacion al usuario.
* -------------------------------------------------------------------------*/
    protected void getAudioStats(){
        int duration  = mPlayer.getDuration()/1000; // In milliseconds
        int due = (mPlayer.getDuration() - mPlayer.getCurrentPosition())/1000;
        int pass = duration - due;

        mPass.setText("" + pass + " seconds");
        mDuration.setText("" + duration + " seconds");
        mDue.setText("" + due + " seconds");
    }


    /* ----------------------------------------------------------------------------*
Función:initializeSeekBar*
Paràmetros:
Descripción:Inicializa la barra Seek con los datos obtenidos de la funcion getAudioStats();,
No esta puesta ya que si la pongo la pantalla de reproducior no funciona
* -------------------------------------------------------------------------*/
    protected void initializeSeekBar(){
        mSeekBar.setMax(mPlayer.getDuration()/1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if(mPlayer!=null){
                    int mCurrentPosition = mPlayer.getCurrentPosition()/1000; // In milliseconds
                    mSeekBar.setProgress(mCurrentPosition);
                    getAudioStats();
                }
                mHandler.postDelayed(mRunnable,1000);
            }
        };
        mHandler.postDelayed(mRunnable,1000);
    }
}