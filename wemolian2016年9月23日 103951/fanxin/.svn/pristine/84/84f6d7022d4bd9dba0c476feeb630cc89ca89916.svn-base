package com.wemolian.app.lock;

import java.util.ArrayList;
import java.util.List;

import com.wemolian.app.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class LockPatternView extends View {

	//选中点的数量
	private static final int POINT_SIZE = 5;
	 
	//矩阵   用来处理线的缩放
	private Matrix matrix = new Matrix(); 
	
	/**
	 * 创建画笔
	 * 
	 */
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * 设置三行三列的点坐标数组
	 * 
	 * @param context
	 */
	private Point[][] points = new Point[3][3];

	// 创建点的集合
	private List<Point> pointList = new ArrayList<Point>();

	private boolean isInit,isSelect,isFinish,movingNoPoint;
	// 屏幕宽 高 x偏移量 y偏移量 移动的X 移动的Y
	private float width, height, offSetsX, offSetsY, bitMapR, movingX, movingY;

	private Bitmap pointNormal, pointPressed, pointError, lineError,
			linePressed;

	public LockPatternView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public LockPatternView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public LockPatternView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 重写绘制方法
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		if (!isInit) {
			initPoints();
		}
		//画点
		points2Canvas(canvas);
		
		//画线
		if(pointList.size() > 0){
			Point a = pointList.get(0);
			//绘制九宫格里的坐标掉
			for(int i = 0;i<pointList.size();i++){
				Point b = pointList.get(i);
				line2Canvas(canvas, a, b);
				a = b;
			}
			//绘制鼠标的坐标点
			if(movingNoPoint){
				line2Canvas(canvas, a, new Point(movingX,movingY));
			}
		}
	}

	/**
	 * 将点绘制到画布
	 * 
	 * @param canvas
	 *            画布
	 */
	private void points2Canvas(Canvas canvas) {
		// TODO Auto-generated method stub
		for (int i = 0; i < points.length; i++) {
			for (int j = 0; j < points[i].length; j++) {
				Point point = points[i][j];
				if (point.state == Point.STATE_PRESSED) {
					canvas.drawBitmap(pointPressed, point.x - bitMapR, point.y
							- bitMapR, paint);
				} else if (point.state == Point.STATE_ERROR) {
					canvas.drawBitmap(pointError, point.x - bitMapR, point.y
							- bitMapR, paint);
				} else {
					canvas.drawBitmap(pointNormal, point.x - bitMapR, point.y
							- bitMapR, paint);
				}
			}
		}
	}

	/**
	 * 画线
	 * @param canvas 画布
	 * @param a 第一个点
	 * @param b 第二个点 
	 */
	private void line2Canvas(Canvas canvas,Point a,Point b){
		//计算线的长度
		float lineLength =  (float) Point.distance(a, b);
		
		//绘制角度
		float degrees = getDegrees(a,b);
		canvas.rotate(degrees,a.x,a.y);
		//正常的线
		if(a.state == Point.STATE_PRESSED){
			//x轴缩放  y轴不变
			matrix.setScale(lineLength/linePressed.getWidth(), 1);
			matrix.postTranslate(a.x - linePressed.getWidth() /2 , a.y - linePressed.getHeight() / 2);
			canvas.drawBitmap(linePressed,matrix,paint);
		//错误的方法
		}else{
			matrix.setScale(lineLength/lineError.getWidth(), 1);
			matrix.postTranslate(a.x - lineError.getWidth() /2 , a.y - lineError.getHeight() / 2);
			canvas.drawBitmap(lineError,matrix,paint);
			
		}
		canvas.rotate(-degrees,a.x,a.y);
	}
	
	


	/**
	 * 初始化点
	 */
	private void initPoints() {
		// 获取布局的宽和高
		width = getWidth();
		height = getHeight();

		// 偏移量
		// 横屏
		if (width > height) {
			offSetsX = (width - height) / 2;
			//
			width = height;
			// 竖屏
		} else {
			offSetsY = (height - width) / 2;
			height = width;
		}

		// 处理图片资源
//		pointNormal = BitmapFactory.decodeResource(getResources(),
//				R.drawable.lock_point_normal);
//		pointPressed = BitmapFactory.decodeResource(getResources(),
//				R.drawable.lock_point_pressed);
//		pointError = BitmapFactory.decodeResource(getResources(),
//				R.drawable.lock_point_error);
		linePressed = BitmapFactory.decodeResource(getResources(),
				R.drawable.lock_line_pressed);
		lineError = BitmapFactory.decodeResource(getResources(),
				R.drawable.lock_line_error);

		// 点的坐标

		// 第一行
		points[0][0] = new Point(offSetsX + width / 4, offSetsY + width / 4);
		points[0][1] = new Point(offSetsX + width / 2, offSetsY + width / 4);
		points[0][2] = new Point(offSetsX + width - width / 4, offSetsY + width
				/ 4);

		// 第二行
		points[1][0] = new Point(offSetsX + width / 4, offSetsY + width / 2);
		points[1][1] = new Point(offSetsX + width / 2, offSetsY + width / 2);
		points[1][2] = new Point(offSetsX + width - width / 4, offSetsY + width
				/ 2);

		// 第三行
		points[2][0] = new Point(offSetsX + width / 4, offSetsY + width - width
				/ 4);
		points[2][1] = new Point(offSetsX + width / 2, offSetsY + width - width
				/ 4);
		points[2][2] = new Point(offSetsX + width - width / 4, offSetsY + width
				- width / 4);

		// 计算图片资源的半径
		bitMapR = pointNormal.getHeight() / 2;
		
		//初始化完成
		isInit = true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		movingNoPoint = false;
		isFinish = false;
		movingX = event.getX();
		movingY = event.getY();
		
		Point point = null;
		switch (event.getAction()) {
		//鼠标按下，绘制开始
		case MotionEvent.ACTION_DOWN:
			resetPoint();
			
			point = checkSelectPoint();
			if(point != null){
				isSelect = true;
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			if(isSelect){
				point = checkSelectPoint();
				if(point == null){
					movingNoPoint = true;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			isFinish = true;
			isSelect = false;
			break;
		}
		
		//选中重复检查
		if(!isFinish && isSelect && point != null){
			//选中的点为交叉点
			if(crossPoint(point)){
			/**
			 * 交叉点不会添加到集合
			 */
			movingNoPoint = true;
				
			//新点
			}else{
				point.state = Point.STATE_PRESSED;
				pointList.add(point);
			}
		}
		
		//绘制结束
		if(isFinish){
			//只选了一个点  绘制不成立
			if(pointList.size() == 1){
				resetPoint();
				
				//绘制错误
			}else if(pointList.size() < POINT_SIZE && pointList.size()>2){
				errorPoint();
			}
		}
		//刷新view
		postInvalidate();
		return true;
	}
	/**
	 * 交叉点的检查
	 * @return 
	 */
	private boolean crossPoint(Point point){
		if(pointList.contains(point)){
			return true;
		}else{
			return false;
		}
	}
	
	
	

	/**
	 * 绘制不成立
	 */
	public void resetPoint(){
		for (int i = 0; i < pointList.size(); i++) {
			Point point = pointList.get(i);
			point.state = Point.STATE_NORMAL;
		}
		pointList.clear();
	}
	/**
	 * 绘制错误
	 */
	public void errorPoint(){
		for(Point point : pointList){
			point.state = Point.STATE_ERROR;
		}
	}
	
	/**
	 * 检查是否选中
	 * @return
	 */
	private Point checkSelectPoint(){
		for(int i= 0; i < points.length;i++){
			for(int j= 0; j < points[i].length;j++){
				Point point = points[i][j];
				if(Point.with(point.x, point.y, bitMapR, movingX, movingY)){
					return point;
				}
			}
		}
		return null;
	}

	
	/**
	 * 获取角度
	 * @param a
	 * @param b
	 */
	private float getDegrees(Point a, Point b) {
		float ax = a.x;
		float ay = a.y;
		float bx = b.x;
		float by = b.y;
		float degrees = 0;
		if(bx == ax){ //y轴相等 90度或是270度
			if(by > ay){//在y轴下面 90
				degrees = 90;
			}else if(by < ay){//在y轴上面 270度
				degrees =270;
			}
		}else if(by == ay){ //y轴相等 0度或180度
			if(bx > ax){ //在y轴下面 0
				degrees = 0;
			}else if(bx < ax){//在y轴上面180
				degrees = 180;
			}
		}else{
			if(bx > ax){
				if(by > ay){
					degrees = 0;
					degrees = degrees + switchDegrees(Math.abs(by - ay),Math.abs(bx - ax));
				}else if(by < ay){
					degrees = 360;
					degrees = degrees - switchDegrees(Math.abs(by - ay),Math.abs(bx - ax));
				}
			}else if(bx < ax){
				if(by > ay){
					degrees = 90;
					degrees = degrees + switchDegrees(Math.abs(bx - ax),Math.abs(by - ay));
				}else if(by > ay){
					degrees = 270;
					degrees = degrees - switchDegrees(Math.abs(bx - ax),Math.abs(by - ay));
				}
			}
		}
		return degrees;
		
	}
	
	
	
	
	
	
	
	private float switchDegrees(float x, float y) {
		return (float) Math.toDegrees(Math.atan2(x, y));
	}







	/**
	 * 自定义的点
	 * 
	 * @author zhangyun
	 *
	 */
	public static class Point {
		// 正常 默认
		public static int STATE_NORMAL = 0;
		// 选中状态
		public static int STATE_PRESSED = 1;
		// 错误状态
		public static int STATE_ERROR = 2;

		public float x, y;
		public int index = 0, state = 0;

		public Point() {

		}

		public Point(float x, float y) {
			this.x = x;
			this.y = y;
		}

		/**
		 * 计算点之间的距离
		 * 
		 * @param a
		 *            点a
		 * @param b
		 *            点b
		 * @return a b 的距离
		 */
		public static double distance(Point a, Point b) {
			return Math.sqrt(Math.abs(a.x - b.x) * Math.abs(a.x - b.x)
					+ Math.abs(a.y - b.y) * Math.abs(a.y - b.y));
		}

		/**
		 * 检查移动的点，是否和屏幕中参考的点重合
		 * @param pointX 参考点的x
		 * @param pointY 参考点的y
		 * @param r      圆的半径
		 * @param movingX 移动点的x
		 * @param movingY 移动点的y
		 * @return 是否重合
		 */
		public static boolean with(float pointX, float pointY, float r,
				float movingX, float movingY) {
			return Math.sqrt((pointX - movingX) * (pointX - movingX)
					+ (pointY - movingY) * (pointY - movingY)) < r;
		}
		
		
		

	}

}
