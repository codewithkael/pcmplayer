package com.react;
import android.media.AudioAttributes;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//converter factory class
public class AudioConverter {

    private final AudioTrack audioTrack;
    ExecutorService executorService = Executors.newFixedThreadPool(1);

    //constructor
    public AudioConverter(Integer rate) {
        int bufferSize = AudioTrack.getMinBufferSize(
                rate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
        );
               audioTrack = new AudioTrack.Builder()
                       .setAudioAttributes(new AudioAttributes.Builder()
                               .setUsage(AudioAttributes.USAGE_MEDIA)
                               .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                               .build())
                       .setAudioFormat(new AudioFormat.Builder()
                               .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                               .setSampleRate(rate)
                               .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                               .build())
                       .setBufferSizeInBytes(bufferSize)
                       .build();

       //        audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, rate, AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize, AudioTrack.MODE_STREAM);
               audioTrack.play();
    }

//thats where all the magic happens, wrap the bytes and adds them on a buffer to be written on an audioTrack
    public void writeArrayToPlayer(byte[] bytes) {
        try {

            ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
            byteBuffer = byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

            ByteBuffer finalByteBuffer = byteBuffer;
            Runnable runnable = () -> {
                audioTrack.write(finalByteBuffer.array(), 0, finalByteBuffer.array().length);

            };
            executorService.submit(runnable);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
