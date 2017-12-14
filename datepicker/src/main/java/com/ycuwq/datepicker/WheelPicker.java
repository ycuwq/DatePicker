package com.ycuwq.datepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;

import java.util.List;

/**
 * 滚动选择器
 * Created by yangchen on 2017/12/12.
 */
public class WheelPicker<T> extends View {

	private List<T> mDataList;
	private int mTextColor = Color.BLACK;
	private Paint mPaint;
	private Camera mCamera;
	private int mTextSize = 80;
	private int mTextMaxWidth, mTextMaxHeight;
	private int mVisibleItemCount = 5;
	private int mItemSpace = 8;

	private int mScrollOffsetY;

	private int mItemHeight;

	private int mCurrentItemPosition;

	private Rect mRectDrawn;

	private int mItemDrawX, mItemDrawY;

	private Scroller mScroller;

	private int mTouchSlop;

	private VelocityTracker mTracker;

	private int mTouchDownY;

	public WheelPicker(Context context) {
		this(context, null);
	}

	public WheelPicker(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public WheelPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelPicker);
		a.recycle();
		initPaint();
		mRectDrawn = new Rect();
		mScroller = new Scroller(context);
		ViewConfiguration configuration = ViewConfiguration.get(context);
		mTouchSlop = configuration.getScaledTouchSlop();

	}

	public void setDataList(@NonNull List<T> dataList) {
		mDataList = dataList;
		if (dataList.size() == 0) {
			return;
		}
		computeTextSize();
	}

	public void computeTextSize() {
		mTextMaxWidth = mTextMaxHeight = 0;
		if (mDataList.size() == 0) {
			return;
		}
		mTextMaxWidth = (int) mPaint.measureText(mDataList.get(0).toString());
		Paint.FontMetrics metrics = mPaint.getFontMetrics();
		mTextMaxHeight = (int) (metrics.bottom - metrics.top);
	}

	private void initPaint() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG | Paint.LINEAR_TEXT_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setTextAlign(Paint.Align.CENTER);
		mPaint.setColor(mTextColor);
		mPaint.setTextSize(mTextSize);
	}

	/**
	 *  计算实际的大小
	 * @param specMode 测量模式
	 * @param specSize 测量的大小
	 * @param size     需要的大小
	 * @return 返回的数值
	 */
	private int measureSize(int specMode, int specSize, int size) {
		if (specMode == MeasureSpec.EXACTLY) {
			return specSize;
		} else {
			return Math.min(specSize, size);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int specWidthSize = MeasureSpec.getSize(widthMeasureSpec);
		int specWidthMode = MeasureSpec.getMode(widthMeasureSpec);
		int specHeightSize = MeasureSpec.getSize(heightMeasureSpec);
		int specHeightMode = MeasureSpec.getMode(heightMeasureSpec);

		int width = mTextMaxWidth;
		int height = mTextMaxHeight * mVisibleItemCount + mItemSpace * (mVisibleItemCount - 1);

		width += getPaddingLeft() + getPaddingRight();
		height += getPaddingTop() + getPaddingBottom();
		setMeasuredDimension(measureSize(specWidthMode, specWidthSize, width),
				measureSize(specHeightMode, specHeightSize, height));
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mRectDrawn.set(getPaddingLeft(), getPaddingTop(),
				getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
		mItemHeight = mRectDrawn.height() / mVisibleItemCount;
		mItemDrawX = mRectDrawn.centerX();
		mItemDrawY = (int) ((mItemHeight - (mPaint.ascent() + mPaint.descent())) / 2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int drawnDataStartPos = - mScrollOffsetY / mItemHeight;
		for (int i = 0; i < mDataList.size(); i++) {
			T t = mDataList.get(i);
			int drawY = mItemDrawY + i * mItemHeight;
			canvas.drawText(t.toString(), mItemDrawX, drawY, mPaint);
		}

	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (mTracker == null) {
					mTracker = VelocityTracker.obtain();
				} else {
					mTracker.clear();
				}

				mTracker.addMovement(event);
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();

				}
				mTouchDownY = (int) event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	public void computeScroll() {
		super.computeScroll();
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}
}
