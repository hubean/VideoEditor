package com.ijoysoft.mediasdk.module.opengl.transition;

import android.opengl.GLES20;

import com.ijoysoft.mediasdk.common.global.ConstantMediaSize;
import com.ijoysoft.mediasdk.module.entity.DurationInterval;
import com.ijoysoft.mediasdk.module.opengl.filter.AFilter;

import java.io.Serializable;

/**
 * 转场应该作用于全显示范围，即showwidth、showHeight，所以定点坐标应该是满的，然后viewport应该赋值为showWidth,showHeight
 */
public class TransitionFilter extends AFilter implements Serializable {
    protected static final String TAG = "TransitionFilter";
    private String mVertexShader;
    private String mFragmentShader;
    private int previewTextureId;
    private int previewTexture;
    private TransitionType transitionType;
    private float[] cube;

    public static final String NO_FILTER_VERTEX_SHADER = "" + "attribute vec4 vPosition;\n" + "attribute vec2 vCoord;\n"
            + "uniform mat4 vMatrix; \n" + "varying vec2 textureCoordinate;\n" + " \n" + "void main()\n" + "{\n"
            + "    gl_Position =  vMatrix*vPosition;\n" + "    textureCoordinate = vCoord.xy;\n" + "}";

    public static final String NO_FILTER_FRAGMENT_SHADER = "" + " precision mediump float;\n" + " \n"
            + " varying vec2 textureCoordinate;\n" + " uniform sampler2D vTexture;\n" + "void main()\n" + "{\n"
            + "     gl_FragColor = texture2D( vTexture, textureCoordinate );\n" + "}";

    public TransitionFilter(TransitionType transitionType, String mVertexShader, String mFragmentShader) {
        this.transitionType = transitionType;
        this.mVertexShader = mVertexShader;
        this.mFragmentShader = mFragmentShader;
        durationInterval = new DurationInterval(0, ConstantMediaSize.TRANSITION_DURATION);
    }

    public TransitionFilter() {
        transitionType = TransitionType.NONE;
        this.mVertexShader = NO_FILTER_VERTEX_SHADER;
        this.mFragmentShader = NO_FILTER_FRAGMENT_SHADER;
        durationInterval = new DurationInterval(0, ConstantMediaSize.TRANSITION_DURATION);
    }

    @Override
    protected void onCreate() {
        createProgramByAssetsFile(mVertexShader, mFragmentShader);
        float[] coord = new float[] { 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, };
        mTexBuffer.clear();
        mTexBuffer.put(coord);
        mTexBuffer.position(0);
        previewTexture = GLES20.glGetUniformLocation(mProgram, "vTexture1");
    }

    @Override
    protected void onDrawArraysPre() {
        // GLES20.glViewport(offsetX + (ConstantMediaSize.showViewWidth - (int) imageWidthNew), offsetY +
        // (ConstantMediaSize.showViewHeight - (int) imageHeightNew), (int) imageWidthNew, (int) imageHeightNew);
        super.onDrawArraysPre();
        // GLES20.glUniformMatrix4fv(mHMatrix, 1, false, mMVPMatrix, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, previewTextureId);
        GLES20.glUniform1i(previewTexture, 1);

    }

    @Override
    protected void onDrawArraysAfter() {
        super.onDrawArraysAfter();
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
    }

    @Override
    public void onSizeChanged(int width, int height) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GLES20.glDeleteTextures(1, new int[] { previewTextureId }, 0);
        previewTextureId = -1;
    }

    /**
     * 设置跳转，每一个转场的实现不一样，
     * 具体业务放在子类去实现
     */
    public void seekTo(int duration) {

    }

    public TransitionType getTransitionType() {
        return transitionType;
    }

    public void setPreviewTextureId(int previewTextureId) {
        this.previewTextureId = previewTextureId;
    }

    public int getPreviewTextureId() {
        return previewTextureId;
    }

    /**
     * 让图像居中显示
     */
    public void layoutCenter() {
        mVerBuffer.clear();
        mVerBuffer.put(cube).position(0);
    }

    public void layoutFill() {
        mVerBuffer.clear();
        mVerBuffer.put(pos).position(0);
    }

    public void setProgress(float progress) {

    }

}
