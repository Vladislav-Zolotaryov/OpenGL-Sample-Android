package com.soft.wz.sample;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import com.soft.wz.sample.primitives.Circle;
import com.soft.wz.sample.primitives.Cube;
import com.soft.wz.sample.primitives.Shape;
import com.soft.wz.sample.utils.CircularIterator;

import java.util.LinkedList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by wz on 01/02/2015.
 */
public class BasicRenderer implements GLSurfaceView.Renderer {

    private final float[] mvpMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];

    private float angle;
    private float zView = -8f;
    private float yRot = 1.0f;

    private List<Shape> shapes = new LinkedList<Shape>();
    private CircularIterator<Shape> shapeIterator = new CircularIterator<>(shapes);
    private Shape currentShape;

    private static float DEFAULT_MAGNITUDE = 0.1f;
    private float magnitude = DEFAULT_MAGNITUDE;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        shapes.add(new Cube());
        shapes.add(new Circle());
        setNextShape();
    }

    public void setNextShape() {
        currentShape = shapeIterator.next();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 12);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        float[] scratch = new float[16];

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, zView, 0, 0f, 0f, 0f, 1.0f, 0.0f);

        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        Matrix.setRotateM(rotationMatrix, 0, angle, 0, yRot, 0);
        Matrix.multiplyMM(scratch, 0, mvpMatrix, 0, rotationMatrix, 0);

        currentShape.draw(scratch);

        angle += magnitude;
        if (magnitude > DEFAULT_MAGNITUDE) {
            magnitude -= DEFAULT_MAGNITUDE;
        }
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void incAngle(float angle) {
        this.angle += angle;
        magnitude += Math.abs(angle/10);
    }

    public void setYRot(float yRot) {
        this.yRot = yRot;
    }
}
