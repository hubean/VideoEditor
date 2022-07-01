package com.ijoysoft.mediasdk.module.opengl.transition;

import android.opengl.GLES20;

import com.ijoysoft.mediasdk.R;
import com.ijoysoft.mediasdk.common.utils.LogUtils;
import com.ijoysoft.mediasdk.module.opengl.gpufilter.utils.OpenGlUtils;

/**
 * 如果着色器有两个纹理的话，那么在绑定纹理的时候，从0开始，然后+1
 * GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + textureType);
 */
public class OvalZoomTransitionFilter extends TransitionFilter {
    private int mPrograssLocation;
    private float progress;

    public OvalZoomTransitionFilter(TransitionType transitionType) {
        super(transitionType, NO_FILTER_VERTEX_SHADER, OpenGlUtils.readShaderFromRawResource(R.raw.transition_oval_zoom));

    }

    @Override
    protected void onCreate() {
        super.onCreate();
        mPrograssLocation = GLES20.glGetUniformLocation(mProgram, "progress");
        progress = 0;

    }

    @Override
    public void onSizeChanged(int width, int height) {
        super.onSizeChanged(width, height);

    }

    @Override
    protected void onDrawArraysPre() {
        super.onDrawArraysPre();
        GLES20.glUniform1f(mPrograssLocation, progress);
        progress += 0.03;
        LogUtils.i(TAG, "onDrawArraysPre->" + progress);
    }

    @Override
    public void seekTo(int duration) {
        float d = (float) duration / 1000.0f;
        if (d >= 2.0f) {
            progress = 1.0f;
        } else {
            progress = d / 2;
        }

        LogUtils.i(TAG, "seekTo:" + progress + ",origin:" + duration);
        GLES20.glUniform1f(mPrograssLocation, progress);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void setProgress(float progress) {
        this.progress = progress;
    }
}
