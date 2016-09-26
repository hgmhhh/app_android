package com.wemolian.app.widget.photoview;

import com.wemolian.app.widget.photoview.PhotoViewAttacher.OnMatrixChangedListener;
import com.wemolian.app.widget.photoview.PhotoViewAttacher.OnPhotoTapListener;
import com.wemolian.app.widget.photoview.PhotoViewAttacher.OnViewTapListener;

import android.content.Context;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class PhotoView extends ImageView implements IPhotoView {

	private final PhotoViewAttacher mAttacher;

	private ScaleType mPendingScaleType;

	public PhotoView(Context context) {
		this(context, null);
	}

	public PhotoView(Context context, AttributeSet attr) {
		this(context, attr, 0);
	}
	
	public PhotoView(final Context context, AttributeSet attr, int defStyle) {
		super(context, attr, defStyle);
		super.setScaleType(ScaleType.MATRIX);
		mAttacher = new PhotoViewAttacher(this);
		final Context con = context;
		mAttacher.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Drawable d = v.getBackground();
				Toast.makeText(con, "长按保存图片功能正在开发中", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		if (null != mPendingScaleType) {
			setScaleType(mPendingScaleType);
			mPendingScaleType = null;
		}
	}
//	public PhotoView(Context context) {
//		this(true,context);
//	}
//	public PhotoView(Boolean set,Context context) {
//		this(set,context, null);
//	}
//
//	public PhotoView(boolean set,Context context, AttributeSet attr) {
//		this(set,context, attr, 0);
//	}
//	
//	public PhotoView(Boolean setScaleType, Context context, AttributeSet attr, int defStyle) {
//		super(context, attr, defStyle);
//		//true时，表示图片可缩放
//		if(setScaleType){
//			super.setScaleType(ScaleType.MATRIX);
//		}
//		
//		final Context con = context;
//		mAttacher = new PhotoViewAttacher(this);
//		mAttacher.setOnLongClickListener(new OnLongClickListener() {
//			
//			@Override
//			public boolean onLongClick(View v) {
//				Toast.makeText(con, "点击111", Toast.LENGTH_SHORT).show();
//				return true;
//			}
//		});
//		if (null != mPendingScaleType) {
//			setScaleType(mPendingScaleType);
//			mPendingScaleType = null;
//		}
//	}

	@Override
	public boolean canZoom() {
		return mAttacher.canZoom();
	}

	@Override
	public RectF getDisplayRect() {
		return mAttacher.getDisplayRect();
	}

	@Override
	public float getMinScale() {
		return mAttacher.getMinScale();
	}

	@Override
	public float getMidScale() {
		return mAttacher.getMidScale();
	}

	@Override
	public float getMaxScale() {
		return mAttacher.getMaxScale();
	}

	@Override
	public float getScale() {
		return mAttacher.getScale();
	}

	@Override
	public ScaleType getScaleType() {
		return mAttacher.getScaleType();
	}

    @Override
    public void setAllowParentInterceptOnEdge(boolean allow) {
        mAttacher.setAllowParentInterceptOnEdge(allow);
    }

    @Override
	public void setMinScale(float minScale) {
		mAttacher.setMinScale(minScale);
	}

	@Override
	public void setMidScale(float midScale) {
		mAttacher.setMidScale(midScale);
	}

	@Override
	public void setMaxScale(float maxScale) {
		mAttacher.setMaxScale(maxScale);
	}

	@Override
	// setImageBitmap calls through to this method
	public void setImageDrawable(Drawable drawable) {
		super.setImageDrawable(drawable);
		if (null != mAttacher) {
			mAttacher.update();
		}
	}

	@Override
	public void setImageResource(int resId) {
		super.setImageResource(resId);
		if (null != mAttacher) {
			mAttacher.update();
		}
	}

	@Override
	public void setImageURI(Uri uri) {
		super.setImageURI(uri);
		if (null != mAttacher) {
			mAttacher.update();
		}
	}

	@Override
	public void setOnMatrixChangeListener(OnMatrixChangedListener listener) {
		mAttacher.setOnMatrixChangeListener(listener);
	}

	@Override
	public void setOnLongClickListener(OnLongClickListener l) {
		mAttacher.setOnLongClickListener(l);
	}

	@Override
	public void setOnPhotoTapListener(OnPhotoTapListener listener) {
		mAttacher.setOnPhotoTapListener(listener);
	}

	@Override
	public void setOnViewTapListener(OnViewTapListener listener) {
		mAttacher.setOnViewTapListener(listener);
	}

	@Override
	public void setScaleType(ScaleType scaleType) {
		if (null != mAttacher) {
			mAttacher.setScaleType(scaleType);
		} else {
			mPendingScaleType = scaleType;
		}
	}

	@Override
	public void setZoomable(boolean zoomable) {
		mAttacher.setZoomable(zoomable);
	}

	@Override
	public void zoomTo(float scale, float focalX, float focalY) {
		mAttacher.zoomTo(scale, focalX, focalY);
	}

	@Override
	protected void onDetachedFromWindow() {
		mAttacher.cleanup();
		super.onDetachedFromWindow();
	}

}