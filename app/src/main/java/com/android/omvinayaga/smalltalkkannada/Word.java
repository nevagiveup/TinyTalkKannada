package com.android.omvinayaga.smalltalkkannada;

/**
 * Created by Manjunath on 12/9/2016.
 */

public class Word {

    private String mKannadaTranslation;
    private String mEnglishTranslation;
    private int mImageResourceId = IMAGE_NOT_AVAILABLE;
    public static final int IMAGE_NOT_AVAILABLE = -1;
    private int mAudioResourceId;

    public Word (String kannadaTranslation, String englishTranslation, int imageResourceId, int audioResourceId) {
        mKannadaTranslation = kannadaTranslation;
        mEnglishTranslation = englishTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    public Word (String kannadaTranslation, String englishTranslation, int audioResourceId) {
        mKannadaTranslation = kannadaTranslation;
        mEnglishTranslation = englishTranslation;
        mAudioResourceId = audioResourceId;
    }

    public String getKannadaTranslation() {
        return mKannadaTranslation;
    }

    public String getEnglishTranslation() {
        return mEnglishTranslation;
    }

    @Override
    public String toString() {
        return "Word{" +
                "mKannadaTranslation='" + mKannadaTranslation + '\'' +
                ", mEnglishTranslation='" + mEnglishTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public int getAudioResourceId() {
        return mAudioResourceId;
    }

    public boolean isImageAvailable() {
        if (mImageResourceId != IMAGE_NOT_AVAILABLE) {
            return true;
        } else {
            return false;
        }
    }
}
