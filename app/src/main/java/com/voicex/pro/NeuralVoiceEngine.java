package com.voicex.pro;
public interface NeuralVoiceEngine extends AutoCloseable {
 int sampleRate();
 boolean isReady();
 // PCM mono, normalized [-1,1]. Implementations must preserve linguistic content.
 float[] convert(float[] source) throws Exception;
 void close();
}
