package com.android.omvinayaga.smalltalkkannada;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static android.media.AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaysFragment extends Fragment {

    private MediaPlayer mediaPlayer;

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    private AudioManager mAudioManager;
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback
                        mediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Stop playback
                        releaseMediaPlayer();
                    }
                }
            };

    public DaysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list, container, false);
        mAudioManager  = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Somavaara", "Monday", R.drawable.days_monday, R.raw.days_monday));
        words.add(new Word("MangaLavaara", "Tuesday", R.drawable.days_thursday, R.raw.days_tuesday));
        words.add(new Word("Budhavaara", "Wednesday", R.drawable.days_wednesday, R.raw.days_wednesday));
        words.add(new Word("Guruvaara", "Thursday", R.drawable.days_thursday, R.raw.days_thursday));
        words.add(new Word("Shukravaara", "Friday", R.drawable.days_friday, R.raw.days_friday));
        words.add(new Word("Shanivaara", "Saturday", R.drawable.days_sunday, R.raw.days_saturday));
        words.add(new Word("Bhanuvaara", "Sunday", R.drawable.days_sunday, R.raw.days_sunday));
        words.add(new Word("Ivatthu", "Today", R.drawable.days_thursday, R.raw.days_today));
        words.add(new Word("Ninne", "Yesterday", R.drawable.days_yesterday, R.raw.days_yesterday));
        words.add(new Word("NaaLe", "Tomorrow", R.drawable.days_thursday, R.raw.days_tomorrow));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_days);
        ListView rootView = (ListView) root.findViewById(R.id.list);
        rootView.setAdapter(adapter);

        rootView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word currentWord = words.get(position);
                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(afChangeListener,
                        // Use the audio stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback.
                    mediaPlayer = MediaPlayer.create(getActivity(), currentWord.getAudioResourceId());

                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        });
        return root;
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }
    @Override
    public void onStop () {
        super.onStop();
        releaseMediaPlayer();
    }

}
