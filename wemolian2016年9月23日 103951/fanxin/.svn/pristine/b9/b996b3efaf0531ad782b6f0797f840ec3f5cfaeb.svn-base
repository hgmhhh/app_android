package com.wemolian.app.wml.others;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 圈子的gridView重写
 * @author zhangyun
 *
 */
public class CircleGridView extends GridView {

	public CircleGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public CircleGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	public CircleGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
    @Override     
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {     
    
        int expandSpec = MeasureSpec.makeMeasureSpec(     
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);     
        super.onMeasure(widthMeasureSpec, expandSpec);     
    }     

}
