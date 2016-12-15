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
public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.list, container, false);
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word("Ninna hesaru enu?", "What is your name?", R.raw.phrases_what_is_your_name));
        words.add(new Word("Ellige hogtha idhya?", "Where are you going?", R.raw.phrases_where_are_you_going));
        words.add(new Word("Hegidhya? Aarama?", "How are you?", R.raw.phrases_how_are_you));
        words.add(new Word("Adhu eshtu?", "How much is that?", R.raw.phrases_how_much_is_that));
        words.add(new Word("Barthira?", "Are you coming?", R.raw.phrases_are_you_coming));
        words.add(new Word("Idhu eshtu?", "How much is this?", R.raw.phrases_how_much_is_this));
        words.add(new Word("Adhu enu?", "What is that?", R.raw.phrases_whats_that));
        words.add(new Word("Idhu enu?", "What is this?", R.raw.phrases_whats_this));
        words.add(new Word("Thindi aaytha?", "Did you have your breakfast?", R.raw.phrases_did_you_have_breakfast));
        words.add(new Word("Oota aaytha?", "Did you have your lunch?", R.raw.phrases_did_you_have_lunch));
        words.add(new Word("Howdhu, barthini", "Yes, I'll come", R.raw.phrases_yes_i_will_come));
        words.add(new Word("Naanu bartha idhini", "I am coming", R.raw.phrases_im_coming));
        words.add(new Word("Nanna hesaru...", "My name is...", R.raw.phrases_my_name_is));
        words.add(new Word("Illi banni", "Come here", R.raw.phrases_come_here));
        words.add(new Word("Oota aaythu...", "I had lunch...", R.raw.phrases_i_had_lunch));
        words.add(new Word("Nadi hogona..", "Come let's go..", R.raw.phrases_lets_go));


        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_phrases);
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
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

}
