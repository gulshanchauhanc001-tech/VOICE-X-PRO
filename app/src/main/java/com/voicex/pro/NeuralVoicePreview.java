package com.voicex.pro;
import java.io.*;
public final class NeuralVoicePreview {
 private NeuralVoicePreview(){}
 // Input is signed little-endian PCM16 mono at the model sample rate.
 public static void convert(File pcm,File wav,NeuralVoiceEngine engine)throws Exception{
 if(!engine.isReady())throw new IllegalStateException("Model unavailable");
 long size=pcm.length();
 int rate=engine.sampleRate();
 if(size<2||size%2!=0||size>rate*120L)throw new IOException("Invalid PCM or recording exceeds 60 seconds");
 float[] input=new float[(int)(size/2)];
 try(DataInputStream in=new DataInputStream(new BufferedInputStream(new FileInputStream(pcm)))){
 for(int i=0;i<input.length;i++)input[i]=Short.reverseBytes(in.readShort())/32768f;
 }
 float[] output=engine.convert(input);
 if(output.length==0||output.length>rate*120L)throw new IOException("Unexpected model output length");
 for(float v:output)if(!Float.isFinite(v))throw new IOException("Invalid audio output");
 File pending=File.createTempFile("voice-preview-",".wav",wav.getAbsoluteFile().getParentFile());
 try{
 try(DataOutputStream out=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(pending)))){
 out.writeBytes("RIFF");i32(out,36+output.length*2);out.writeBytes("WAVEfmt ");i32(out,16);
 i16(out,1);i16(out,1);i32(out,rate);i32(out,rate*2);i16(out,2);i16(out,16);
 out.writeBytes("data");i32(out,output.length*2);
 for(float v:output)i16(out,Math.round(Math.max(-1f,Math.min(32767f/32768f,v))*32768f));
 }
 java.nio.file.Files.move(pending.toPath(),wav.toPath(),java.nio.file.StandardCopyOption.REPLACE_EXISTING);
 }finally{pending.delete();}
 }
 private static void i32(DataOutputStream o,int x)throws IOException{o.writeInt(Integer.reverseBytes(x));}
 private static void i16(DataOutputStream o,int x)throws IOException{o.writeShort(Short.reverseBytes((short)x));}
}
