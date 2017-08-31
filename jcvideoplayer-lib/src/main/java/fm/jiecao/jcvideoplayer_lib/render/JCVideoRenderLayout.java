package fm.jiecao.jcvideoplayer_lib.render;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;

import fm.jiecao.jcvideoplayer_lib.R;
import fm.jiecao.jcvideoplayer_lib.manager.JCMediaManager;

/**
 * Created by jun.sheng on 2017/7/27.
 */

public class JCVideoRenderLayout extends FrameLayout implements TextureView.SurfaceTextureListener {

    private JCResizeImageView mImageView;
    private JCResizeTextureView mTextureView;

    private Bitmap mBitmap;
    private boolean mSizeChanged;

    private TextureView.SurfaceTextureListener mListener;

    public JCVideoRenderLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public JCVideoRenderLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JCVideoRenderLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.jc_video_render_view, this);
        mImageView = (JCResizeImageView) findViewById(R.id.image_view);
        mTextureView = (JCResizeTextureView) findViewById(R.id.texture_view);
        mTextureView.setSurfaceTextureListener(this);
    }

    public TextureView.SurfaceTextureListener getSurfaceTextureListener() {
        return mListener;
    }

    public void setSurfaceTextureListener(TextureView.SurfaceTextureListener listener) {
        mListener = listener;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (mListener != null) {
            mListener.onSurfaceTextureAvailable(surface, width, height);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // 如果SurfaceTexture还没有更新Image，则记录SizeChanged事件，否则忽略
        mSizeChanged = true;
        if (mListener != null) {
            mListener.onSurfaceTextureSizeChanged(surface, width, height);
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mImageView.setVisibility(VISIBLE);
        mTextureView.setHasUpdated(false);
        if (mListener != null) {
            return mListener.onSurfaceTextureDestroyed(surface);
        }
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // 如果mSizeChanged=true，则说明此次Updated事件不是渲染更新引起的
        // 应该是TextureSizeChanged引起的 所以不需要更新 mImageView
        if (!mSizeChanged) {
            if (mImageView.getVisibility() == VISIBLE) {
                if (JCMediaManager.instance().mediaPlayer.isPlaying()) {
                    mImageView.setVisibility(INVISIBLE);
                    mTextureView.setHasUpdated(true);
                }
            }
        } else {
            mSizeChanged = false;
        }
        if (mListener != null) {
            mListener.onSurfaceTextureUpdated(surface);
        }
    }

    @Override
    public void setRotation(float rotation) {
        super.setRotation(rotation);
    }

    public void setVideoSize(Point videoSize) {
        mTextureView.setVideoSize(videoSize);
        mImageView.setVideoSize(videoSize);
    }

    public void cacheTextureImage() {
        Bitmap bitmap = mTextureView.getBitmap();
        if (bitmap != null) {
            mBitmap = bitmap;
            mImageView.setImageBitmap(bitmap);
        } else {
            mImageView.setImageBitmap(mBitmap);
        }
    }
}
