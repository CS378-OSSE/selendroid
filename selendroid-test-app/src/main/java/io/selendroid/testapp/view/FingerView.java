package io.selendroid.testapp.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Paint canvas view for manual testing on touch gestures.
 *
 * @author colindmurray
 * @author chooper9
 */
public class FingerView extends View {
  private Paint paint;
  private ArrayList<Path> paths;
  private ArrayList<Integer> mPointers;

  public FingerView(Context context, AttributeSet attrs) {
    super(context, attrs);
    paint = new Paint();
    paint.setColor(Color.WHITE);
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(12);
    paths = new ArrayList<Path>();
    mPointers = new ArrayList<Integer>();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    int id = event.getPointerId(event.getActionIndex());
    int idx;
    switch (event.getActionMasked()) {
      case MotionEvent.ACTION_DOWN:
      case MotionEvent.ACTION_POINTER_DOWN:
        mPointers.add(id);
        Path pth = new Path();
        pth.moveTo(event.getX(event.getActionIndex()), event.getY(event.getActionIndex()));
        paths.add(pth);
        break;
      case MotionEvent.ACTION_MOVE:
        idx = 0;
        for(Integer i : mPointers) {
          paths.get(idx).lineTo(event.getX(idx), event.getY(idx));
          idx++;
        }
        break;
      case MotionEvent.ACTION_UP:
      case MotionEvent.ACTION_POINTER_UP:
        idx = 0;
        for(Integer i : mPointers) {
          if(mPointers.get(idx) == id)
            break;
          idx++;
        }
        mPointers.remove(idx);
        paths.remove(idx);
        break;
    }
    invalidate();
    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    for(Path p : paths) {
      canvas.drawPath(p, paint);
    }
  }
}
