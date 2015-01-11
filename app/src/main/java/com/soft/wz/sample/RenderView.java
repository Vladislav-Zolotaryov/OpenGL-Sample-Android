package com.soft.wz.sample;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by wz on 01/02/2015.
 */
public class RenderView extends GLSurfaceView {

    private final static String TAG = RenderView.class.getSimpleName();

    private BasicRenderer renderer;

    private float previousX;
    private float previousY;

    private int nextShapeCounter = 0;

    public RenderView(Context context) {
        super(context);
        renderer = new BasicRenderer();
        setEGLContextClientVersion(2);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
        float x = e.getX();
        float y = e.getY();


        Log.d(TAG, String.format("Touch at x: %s y: %s", x, y));
        if (x < 150 && y < 150) {
            nextShapeCounter++;
            if (nextShapeCounter > 2) {
                renderer.setNextShape();
                nextShapeCounter = 0;
            }
        }

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - previousX;

                if (x > previousX) {
                    renderer.incAngle(dx);
                    renderer.setYRot(1.0f);
                } else {
                    renderer.incAngle(-dx);
                    renderer.setYRot(-1.0f);
                }
        }

        previousX = x;
        previousY = y;
        return true;
    }

}
