package it.amazingrecordingstudios.hippo.audioplayer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.FileDescriptor;
import java.io.IOException;

public abstract class MediaPlayerWrapperOneFileAtATime extends SafeLoggableMediaPlayer {

    public MediaPlayerWrapperOneFileAtATime() {
        super();
    }

    @RequiresApi(Build.VERSION_CODES.N)
    protected void setDataSource(MediaPlayer mp,
                                 AssetFileDescriptor assetFileDescriptor)
            throws IOException {
        mp.setDataSource(assetFileDescriptor);
    }

    @RequiresApi(Build.VERSION_CODES.BASE)
    protected void compatibleSetDataSource(
            MediaPlayer mp,
            AssetFileDescriptor assetFileDescriptor)
            throws  IOException{
        FileDescriptor fileDescriptor = assetFileDescriptor.getFileDescriptor();
        long offset = assetFileDescriptor.getStartOffset();
        long length = assetFileDescriptor.getLength();
        mp.setDataSource(
                fileDescriptor,
                offset,
                length);
    }
}
