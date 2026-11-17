package com.voicex.pro;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaRecorder;
final class VoiceCaptureQuality {
 private VoiceCaptureQuality(){}
 static int source(Context c){
 AudioManager manager=(AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
 if(manager!=null&&"true".equalsIgnoreCase(manager.getProperty(AudioManager.PROPERTY_SUPPORT_AUDIO_SOURCE_UNPROCESSED)))return MediaRecorder.AudioSource.UNPROCESSED;
 return MediaRecorder.AudioSource.VOICE_RECOGNITION;
 }
}
