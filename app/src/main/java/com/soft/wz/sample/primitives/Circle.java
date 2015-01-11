package com.soft.wz.sample.primitives;

import android.opengl.GLES20;

import com.soft.wz.sample.utils.GlHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by wz on 01/02/2015.
 */
public class Circle implements Shape {
    private int program;
    private int positionHandle;
    private int colorHandle;
    private int mvpMatrixHandle;

    private FloatBuffer vertexBuffer;
    private float vertices[] = new float[364 * 3];

    private final String vertexShaderCode =
            "uniform mat4 mvpMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = mvpMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 color;" +
                    "void main() {" +
                    "  gl_FragColor = color;" +
                    "}";

    public Circle() {
        vertices[0] = 0;
        vertices[1] = 0;
        vertices[2] = 0;

        for (int i = 1; i < 364; i++) {
            vertices[(i * 3) + 0] = (float) (1.5 * Math.cos((3.14 / 180) * (float) i));
            vertices[(i * 3) + 1] = (float) (1.5 * Math.sin((3.14 / 180) * (float) i));
            vertices[(i * 3) + 2] = 0;
        }

        ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
        vertexByteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = vertexByteBuffer.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);
        int vertexShader = GlHelper.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = GlHelper.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
    }

    public void draw(float[] mvpMatrix) {
        GLES20.glUseProgram(program);

        positionHandle = GLES20.glGetAttribLocation(program, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);

        GLES20.glVertexAttribPointer(positionHandle, 3,
                GLES20.GL_FLOAT, false, 12
                , vertexBuffer);

        colorHandle = GLES20.glGetUniformLocation(program, "color");

        GLES20.glUniform4fv(colorHandle, 1, Colors.VIOLET, 0);

        mvpMatrixHandle = GLES20.glGetUniformLocation(program, "mvpMatrix");
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 364);

        GLES20.glUniform4fv(colorHandle, 1, Colors.LIGHTLY_BLUE, 0);
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 364);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }

}
