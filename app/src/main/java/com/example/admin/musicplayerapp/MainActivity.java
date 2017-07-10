package com.example.admin.musicplayerapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;



    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener(){
                public void onAudioFocusChange(int focusChange){
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    }else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                        mMediaPlayer.start();

                    }else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                        releaseMediaPlayer();

                    }
                }
            };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer mp) {

        }


        public void  OnCompletion(MediaPlayer mp){

        }

    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        final ImageButton pause = (ImageButton) findViewById(R.id.pause);
        pause.setVisibility(View.INVISIBLE);
        final ImageButton play = (ImageButton) findViewById(R.id.play);
        play.setVisibility(View.INVISIBLE);
        final ImageButton skip = (ImageButton) findViewById(R.id.skip);
        skip.setVisibility(View.INVISIBLE);
        final ImageButton previous = (ImageButton) findViewById(R.id.previous);
        previous.setVisibility(View.INVISIBLE);

        // Create a list of words
        final ArrayList<Music> words = new ArrayList<Music>();

        words.add(new Music("daft punk", "one more time", R.drawable.loves,R.raw.daft_punk));
        words.add(new Music("blaze","when i fall in love", R.drawable.loves,R.raw.blaze));
        words.add(new Music("amanda","amazulu", R.drawable.loves,R.raw.amanda));
        words.add(new Music("teddy pendergrass","can we be lovers", R.drawable.loves,R.raw.can));
        words.add(new Music("jaheim","everywhere", R.drawable.loves,R.raw.jaheim));
        words.add(new Music("R Kelly","if i could turn", R.drawable.loves,R.raw.kelly));
        words.add(new Music("lebo sekgobela","lion of juda", R.drawable.loves,R.raw.lion));
        words.add(new Music("lady zamar","love is blind", R.drawable.loves,R.raw.love_is));
        words.add(new Music("sam salter","there you are", R.drawable.loves,R.raw.sam));
        words.add(new Music("theo","uphathe kahle", R.drawable.loves,R.raw.theo));










        MusicAdapter adapter =
                new MusicAdapter(this, words, R.color.tan_background);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Music word = words.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(MainActivity.this, word.getmAudioResourceId());
                    mMediaPlayer.start();
                    play.setVisibility(View.VISIBLE);
                    pause.setVisibility(View.VISIBLE);
                   

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);

                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.pause();
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMediaPlayer.start();


            }
        });
    }





    protected void onStop(){
        super.onStop();
        releaseMediaPlayer();

    }


    private void releaseMediaPlayer(){
        if (mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


}