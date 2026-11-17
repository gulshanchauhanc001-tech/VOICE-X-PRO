package com.voicex.pro;
import android.content.Context;
import android.graphics.*;
import android.view.View;
public class WaveView extends View {
 private final Paint paint=new Paint(3);
 private boolean active;
 public WaveView(Context c){super(c);setContentDescription("Decorative voice activity animation");}
 public void setActive(boolean value){active=value;invalidate();}
 @Override protected void onDraw(Canvas c){super.onDraw(c);float w=getWidth(),h=getHeight();if(w<=0||h<=0)return;float spacing=w/29f;double phase=android.os.SystemClock.uptimeMillis()/190.0;
 for(int i=0;i<25;i++){float x=spacing*(i+2.5f), envelope=(float)Math.sin(Math.PI*(i+1)/26);float amp=active?(float)(.22+.65*Math.abs(Math.sin(phase-i*.47))):.14f+.10f*(i%3);float bar=Math.max(2,h*amp*envelope);paint.setColor(i<13?0xffa47aff:0xff66e8f4);paint.setAlpha(active?235:120);c.drawRoundRect(x-spacing*.22f,(h-bar)/2,x+spacing*.22f,(h+bar)/2,spacing*.22f,spacing*.22f,paint);}
 if(active&&isShown())postInvalidateDelayed(40);
 }
}
