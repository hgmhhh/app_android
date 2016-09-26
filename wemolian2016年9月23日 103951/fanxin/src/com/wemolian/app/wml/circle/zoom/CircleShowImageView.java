package com.wemolian.app.wml.circle.zoom;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.view.View.OnTouchListener;



/**
 * 预览图片功能     --图片多点触控放大缩小，双击方法缩小
 * @author Administrator
 *
 */
public class CircleShowImageView extends ImageView implements 
OnGlobalLayoutListener, OnScaleGestureListener,OnTouchListener {

	
	private boolean mOnce = false;
	
	/**
	 * 初始化时缩放的值
	 */
	private float mInitScale;
	
	/**
	 * 双击放大时到达的缩放值
	 */
	private float mMidScale;
	
	/**
	 * 放大的最大缩放值
	 */
	private float mMaxScale;
	
	/**
	 * 捕获用户多点触控时的缩放比例
	 */
	private ScaleGestureDetector mScaleGestureDetector;
	
	
	private Matrix mScaleMatrix;
	
	
	
	/**自由移动
	 */
	/**
	 * 记录上一次多点触控的数量
	 */
	private int mLastPointerCount;
	
	/**
	 * 多点触控中心点位置
	 */
	private float mLastX;
	private float mLastY;
	private int mTouchSlop;
	private boolean isCanDrag;
	
	private boolean isCheckLeftAndRight;
	private boolean isCheckTopAndBottom;
	
	/**
	 * 双击放大缩小
	 */
	private GestureDetector mGestureDetector;
	
	private boolean isAutoScale;
	
	@SuppressWarnings("deprecation")
	public CircleShowImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScaleMatrix = new Matrix();
		setScaleType(ScaleType.MATRIX);
		
		mScaleGestureDetector = new ScaleGestureDetector(context, this);
		
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mGestureDetector = new GestureDetector(context,
				new GestureDetector.SimpleOnGestureListener(){
			@Override
			public boolean onDoubleTap(MotionEvent e) {
				if(isAutoScale){
					return true;
				}
				float x =e.getX();
				float y = e.getY();
				if(getScale() < mMidScale){
//					mScaleMatrix.postScale(mMidScale /getScale(), mMidScale/getScale(),x,y);
//					setImageMatrix(mScaleMatrix);
					postDelayed(new AutoSclaRunnable(mMidScale, x, y), 16);
					isAutoScale=true;
				}else{
//					mScaleMatrix.postScale(mInitScale /getScale(), mInitScale/getScale(),x,y);
//					setImageMatrix(mScaleMatrix);
					postDelayed(new AutoSclaRunnable(mInitScale, x, y), 16);
					isAutoScale=true;
				}
				
				
				
				return true;
			}
		});
		setOnTouchListener(this);
	}
	/**
	 * 自动放大缩小
	 * @author Administrator
	 *
	 */
	private class AutoSclaRunnable implements Runnable{

		/**
		 * 缩放的目标值
		 */
		private float mTargetScale;
		/**
		 * 缩放的中心点
		 */
		private float x;
		private float y;
		
		//缩放梯度
		private final float BIGGER = 1.07f;
		private final float SMALL = 0.93f;
		
		
		private float tmpScale;
		
		public AutoSclaRunnable(float mTargetScale, float x, float y) {
			super();
			this.mTargetScale = mTargetScale;
			this.x = x;
			this.y = y;
			
			if(getScale() < mTargetScale){
				tmpScale= BIGGER;
			}
			if(getScale() > mTargetScale){
				tmpScale = SMALL;
			}
			
		}




		@Override
		public void run() {
			/**
			 * 进行缩放
			 */
			mScaleMatrix.postScale(tmpScale, tmpScale,x,y);
			checkBorderAndCenterWhenScale();
			setImageMatrix(mScaleMatrix);
			
			float currentScale =getScale();
			if((tmpScale > 1.0f && currentScale < mTargetScale) 
					|| (tmpScale < 1.0f && currentScale > mTargetScale)){
				postDelayed(this, 16);
			}else{//设置为目标值
				float scale = mTargetScale / currentScale;
				mScaleMatrix.postScale(scale, scale, x, y);
				checkBorderWhenTranslate();
				setImageMatrix(mScaleMatrix);
				isAutoScale = false;
			}
		}
		
	}
	public CircleShowImageView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}

	public CircleShowImageView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}

	
	//view添加到Window上时执行的代码
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}
	
	
	//view从Window移除时执行的代码
	@SuppressWarnings("deprecation")
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		getViewTreeObserver().removeGlobalOnLayoutListener(this);

	}
	
	/**
	 * 获取imageview加载完成的图片
	 */
	@Override
	public void onGlobalLayout() {
		//第一次加载时
		if(!mOnce){
			//得到控件的宽喝高
			int width = getWidth();
			int height = getHeight();
			//得到图片  以及宽和高
			Drawable draw = getDrawable();
			if(draw == null){
				return;
			}
			
			//默认缩放值为1.0
			float scale = 1.0f;
			//图片的宽和高
			int imgWidth = draw.getIntrinsicWidth();
			int imgHeight = draw.getIntrinsicHeight();
			/**
			 * 图片宽度大于屏幕宽度，高度小于屏幕高度，将图片缩小
			 */
			if(imgWidth > width && imgHeight < height){
				scale = width * 1.0f /imgWidth;
			}
			/**
			 * 图片宽度小于屏幕宽度，高度大于屏幕高度，将图片缩小
			 */
			if(imgWidth < width && imgHeight > height){
				scale = height * 1.0f / imgHeight;
			}
			/**
			 * 图片宽度大于屏幕宽度，高度大于屏幕高度
			 * 图片宽度小于屏幕宽度，高度小于屏幕高度
			 * 取缩放最小值
			 */
			if((imgWidth > width && imgHeight > height) || (imgWidth < width && imgHeight < height)){
				scale = Math.min(width * 1.0f / imgWidth, height * 1.0f / imgHeight);
			}
			
			/**
			 * 得到初始化时的缩放比例
			 */
			mInitScale = scale;
			mMidScale = scale * 2;
			mMaxScale = scale * 4;
			
			/**
			 * 将图片移动到当前控件的中心
			 */
			int dx = getWidth() / 2 - imgWidth / 2;
			int dy = getHeight() / 2 - imgHeight /2;
			
			//平移
			mScaleMatrix.postTranslate(dx, dy);
			//缩放
//			mScaleMatrix.postScale(mInitScale, mInitScale ,width /2,height /2);
			mScaleMatrix.postScale(mInitScale, mInitScale ,width /2,height /2);
			mOnce = true;
		}
	}

	/**
	 * 获得当前图片缩放值
	 * @return
	 */
	public float getScale(){
		float[] values = new float[9];
		mScaleMatrix.getValues(values);
		return values[Matrix.MSCALE_X];
	}
	
	/**
	 * 缩放中
	 */
	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		float scale = getScale();
		
		float scaleFactor = detector.getScaleFactor();
		if(getDrawable() == null){
			return true;
		}
		
		
		//缩放控制
		if((scale < mMaxScale && scaleFactor > 1.0f)
				|| (scale > mInitScale && scaleFactor < 1.0f)){
			if(scale * scaleFactor < mInitScale){
				scaleFactor = mInitScale / scale;
			}
			
			if(scale * scaleFactor > mMaxScale){
				scale = mMaxScale /scale;
			}
			
			mScaleMatrix.postScale(scaleFactor, scaleFactor,detector.getFocusX(),detector.getFocusY());
			checkBorderAndCenterWhenScale();
			setImageMatrix(mScaleMatrix);
		}
		
		
		return true;
	}
	
	/**
	 * 获得图片放大缩小后的宽和高
	 * @return
	 */
	private RectF getMatrixRectF(){
		Matrix matrix = mScaleMatrix;
		RectF rectF = new RectF();
		Drawable d = getDrawable();
		if(d != null){
			rectF.set(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());
			matrix.mapRect(rectF);
		}
		return rectF;
	}
	
	/**
	 * 在缩放时，进行边界控制
	 */
	private void checkBorderAndCenterWhenScale() {
		RectF rect =  getMatrixRectF();
		float deltaX = 0;
		float deltaY = 0;
		int width = getWidth();
		int height = getHeight();
		
		
		//缩放时，进行辩解检测，防止出险白边
		if (rect.width() >= width) {
			if(rect.left > 0){
				deltaX = -rect.left;
			}
			if(rect.right < width){
				deltaX = width - rect.right;
			}
		}
		if(rect.height() >= height){
			if(rect.top > 0){
				deltaY = -rect.top;
			}
			if(rect.bottom < height ){
				deltaY = height - rect.bottom;
			}
		}
		
		//如果宽度或是宽度小于控件的宽或是高，居中
		if(rect.width() < width){
			deltaX = width / 2f - rect.right+ rect.width()/2f;
		}
		if(rect.height() < height){
			deltaY = height /2f - rect.bottom + rect.height() /2f;
		}
		
		mScaleMatrix.postTranslate(deltaX, deltaY);
	}

	/**
	 * 缩放开始
	 */
	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * 缩放结束
	 */
	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (mGestureDetector.onTouchEvent(event)) {
			return true;
		}
		mScaleGestureDetector.onTouchEvent(event);
		
		float x = 0;
		float y = 0;
		//拿到多点触控的数量
		int pointerCount = event.getPointerCount();
		for(int i= 0;i<pointerCount;i++){
			x += event.getX();
			y += event.getY();
		}
		
		if(mLastPointerCount != pointerCount){
			isCanDrag = false;
			mLastX = x;
			mLastY = y;
		}
		mLastPointerCount = pointerCount;
		
		
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			float dx = x - mLastX;
			float dy = y - mLastY;
			if(!isCanDrag){
				isCanDrag = isMoveAction(dx,dy);
			}
			if(isCanDrag){
				//完成图片移动
				RectF rectF = getMatrixRectF();
				if(getDrawable() != null){
					isCheckLeftAndRight = isCheckTopAndBottom = true;
					//宽度小于控件宽度，不允许横向移动
					if(rectF.width() < getWidth()){
						isCheckLeftAndRight =false;
						dx = 0;
					}
					//高度小于孔家高度，不允许纵向移动
					if(rectF.height() < getHeight()){
						isCheckTopAndBottom =false;
						dy = 0;
					}
					mScaleMatrix.postTranslate(dx, dy);
					
					checkBorderWhenTranslate();
					setImageMatrix(mScaleMatrix);
				}
			}
			
			mLastX = x;
			mLastY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			mLastPointerCount = 0;
			break;
			

		default:
			break;
		}
		return true;
	}

	/**
	 * 当移动时，进行边界检查
	 */
	private void checkBorderWhenTranslate() {
		RectF rectF = getMatrixRectF();
		
		float deltaX = 0;
		float deltaY = 0;
		
		int width = getWidth();
		int height = getHeight();
		if(rectF.top > 0 && isCheckTopAndBottom ){
			deltaY = - rectF.top;
		}
		
		if(rectF.bottom < height && isCheckTopAndBottom){
			deltaY = height - rectF.bottom;
		}
		if(rectF.left > 0 && isCheckLeftAndRight){
			deltaX = - rectF.left;
		}
		if(rectF.right < width && isCheckLeftAndRight){
			deltaX = width - rectF.right;
		}
		mScaleMatrix.postTranslate(deltaX, deltaY);
	}

	/**
	 * 判断是否是移动图片
	 * @param dx
	 * @param dy
	 * @return
	 */
	private boolean isMoveAction(float dx, float dy) {
		
		return Math.sqrt(dx * dx + dy *dy) > mTouchSlop;
	}

}
