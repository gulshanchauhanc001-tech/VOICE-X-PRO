package com.voicex.pro;
public final class VoiceSignalMetrics {
 private VoiceSignalMetrics(){}
 public static double rms(float[] audio){if(audio==null||audio.length==0)return 0;double sum=0;for(float x:audio){if(!Float.isFinite(x))throw new IllegalArgumentException("Invalid PCM");sum+=(double)x*x;}return Math.sqrt(sum/audio.length);}
 public static double clippingRatio(float[] audio){if(audio==null||audio.length==0)return 0;int n=0;for(float x:audio){if(!Float.isFinite(x))throw new IllegalArgumentException("Invalid PCM");if(Math.abs(x)>=.999f)n++;}return (double)n/audio.length;}
 public static double peak(float[] audio){double p=0;if(audio!=null)for(float x:audio){if(!Float.isFinite(x))throw new IllegalArgumentException("Invalid PCM");p=Math.max(p,Math.abs(x));}return p;}
}
