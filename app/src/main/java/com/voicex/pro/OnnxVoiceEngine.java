package com.voicex.pro;
import ai.onnxruntime.*;
import java.util.Collections;
import java.nio.FloatBuffer;
public final class OnnxVoiceEngine implements NeuralVoiceEngine {
 private final OrtEnvironment env=OrtEnvironment.getEnvironment();
 private final OrtSession session;
 private final String input;
 private final int rate;
 private boolean closed;
 // Requires an end-to-end waveform model with float32 [1,N] input/output.
 // Standard RVC generator exports require additional encoders and are not interchangeable.
 public OnnxVoiceEngine(String modelPath,int rate) throws OrtException {
 if(rate<16000||rate>48000)throw new IllegalArgumentException("Unsupported sample rate");
 this.rate=rate;
 try(OrtSession.SessionOptions options=new OrtSession.SessionOptions()){
 options.setIntraOpNumThreads(2);
 session=env.createSession(modelPath,options);
 }
 if(session.getInputNames().size()!=1||session.getOutputNames().size()!=1){session.close();throw new IllegalArgumentException("Expected single waveform input and output");}
 input=session.getInputNames().iterator().next();
 }
 public int sampleRate(){return rate;}
 public synchronized boolean isReady(){return !closed;}
 public synchronized float[] convert(float[] source)throws Exception{
 if(closed)throw new IllegalStateException("Engine closed");
 if(source==null||source.length==0||source.length>rate*60)throw new IllegalArgumentException("Invalid audio length");
 for(float x:source)if(!Float.isFinite(x)||Math.abs(x)>1)throw new IllegalArgumentException("Invalid normalized PCM");
 try(OnnxTensor tensor=OnnxTensor.createTensor(env,FloatBuffer.wrap(source),new long[]{1,source.length});OrtSession.Result result=session.run(Collections.singletonMap(input,tensor))){
 Object value=result.get(0).getValue();
 if(!(value instanceof float[][]))throw new IllegalStateException("Expected float32 [1,N] output");
 float[][] rows=(float[][])value;
 if(rows.length!=1||rows[0].length==0)throw new IllegalStateException("Invalid output shape");
 float[] audio=rows[0];
 for(float x:audio)if(!Float.isFinite(x))throw new IllegalStateException("Non-finite model output");
 return audio;
 }
 }
 public synchronized void close(){if(!closed){closed=true;try{session.close();}catch(OrtException ignored){}}}
}
