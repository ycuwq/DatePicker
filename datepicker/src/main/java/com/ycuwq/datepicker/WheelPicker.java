package com.ycuwq.datepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
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
@SuppressWarnings("FieldCanBeLocal")
public class WheelPicker<T> extends View {
	private final String TAG = getClass().getSimpleName();

	/**
	 * 数据集合
	 */
	private List<T> mDataList;
	/**
	 * Item的Text的颜色
	 */
	@ColorInt
	private int mTextColor;

	private int mTextSize;

	/**
	 * 选中的Item的Text颜色
	 */
	@ColorInt
	private int mSelectedItemTextColor;


	private Paint mPaint;

	/**
	 * 最大的一个Item的文本的宽高
	 */
	private int mTextMaxWidth, mTextMaxHeight;
	/**
	 * 输入的一段文字，可以用来测量 mTextMaxWidth
	 */
	private String mItemMaximumWidthText;

	/**
	 * 显示的Item一半的数量（中心Item上下两边分别的数量）
	 * 总显示的数量为 mHalfVisibleItemCount * 2 + 1
	 */
	private int mHalfVisibleItemCount;

	/**
	 * 两个Item之间的高度间隔
	 */
	private int mItemSpace = 8;

	private int mItemHeight;

	/**
	 * 初始化被选中的Item的位置
	 */
	private int mInitSelectedPosition;

	/**
	 * 整个控件的可绘制面积
	 */
	private Rect mDrawnRect;

	/**
	 * 中心被选中的Item的坐标矩形
	 */
	private Rect mSelectedItemRect;

	/**
	 * 中心被选中的Item的绘制坐标
	 */
	private int mSelectedItemDrawX, mSelectedItemDrawY;

	private Scroller mScroller;

	private int mTouchSlop;

	private VelocityTracker mTracker;

	private int mTouchDownY;
	/**
	 * Y轴Scroll滚动的位移
	 */
	private int mScrollOffsetY;

	/**
	 * 最后手指Down事件的Y轴坐标，用于计算拖动距离
	 */
	private int mLastDownY;

	/**
	 * 是否循环读取
	 */
	private boolean mIsCyclic = true;

	/**
	 * 最大可以Fling的距离
	 */
	private int mMaxFlingY, mMinFlingY;

	/**
	 * 滚轮滑动时的最小/最大速度
	 */
	private int mMinimumVelocity = 50, mMaximumVelocity = 12000;


	private boolean mIsCurved;

	private Camera mCamera;
	private Matrix mMatrixRotate, mMatrixDepth;


	private Handler mHandler = new Handler();

	private OnWheelChangeListener mOnWheelChangeListener;

	private int mLastSelectedPosition = -1;

	private Runnable mScrollerRunnable = new Runnable() {
		@Override
		public void run() {

			if (mScroller.computeScrollOffset()) {
				int scrollerCurrY = mScroller.getCurrY();
                if (mIsCyclic) {
					int visibleItemCount = 2 * mHalfVisibleItemCount + 1;
					//判断超过上下限直接令其恢复到初始坐标的值

                    while (scrollerCurrY > visibleItemCount * mItemHeight) {
                        scrollerCurrY -= mDataList.size() * mItemHeight;
                    }
                    while (scrollerCurrY < -(visibleItemCount + mDataList.size()) * mItemHeight) {
                        scrollerCurrY += mDataList.size() * mItemHeight;
                    }
				}
                mScrollOffsetY = scrollerCurrY;
				postInvalidate();
				mHandler.postDelayed(this, 16);
			}
			if (mScroller.isFinished()) {
				if (mOnWheelChangeListener == null) {
					return;
				}
				if (mItemHeight == 0) {
					return;
				}
				int position = -mScrollOffsetY / mItemHeight;
				if (mIsCyclic) {
					//当是循环状态时，根据循环效果的实现机制，mScrollOffsetY会超过list的大小，这里将position修正。
					while (position >= mDataList.size()) {
						position -= mDataList.size();
					}
					while (position < 0) {
						position += mDataList.size();
					}
				}
				if (mLastSelectedPosition != position) {
					mOnWheelChangeListener.onWheelSelected(position);
				}
				mLastSelectedPosition = position;
			}
		}
	};

	public WheelPicker(Context context) {
		this(context, null);
	}

	public WheelPicker(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs,0);
	}

	public WheelPicker(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initAttrs(context, attrs);
		initPaint();
		mDrawnRect = new Rect();
		mSelectedItemRect = new Rect();
		mScroller = new Scroller(context);

		mCamera = new Camera();

		mMatrixRotate = new Matrix();
		mMatrixDepth = new Matrix();

		ViewConfiguration configuration = ViewConfiguration.get(context);
		mTouchSlop = configuration.getScaledTouchSlop();
	}

	private void initAttrs(Context context, @Nullable AttributeSet attrs) {
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WheelPicker);
		mTextSize = a.getDimensionPixelSize(R.styleable.WheelPicker_itemTextSize,
				getResources().getDimensionPixelSize(R.dimen.WheelItemTextSize));
		mTextColor = a.getColor(R.styleable.WheelPicker_itemTextColor,
				Color.BLACK);
		mIsCyclic = a.getBoolean(R.styleable.WheelPicker_wheelCyclic, false);
		mHalfVisibleItemCount = a.getInteger(R.styleable.WheelPicker_halfVisibleItemCount, 3);
		mItemMaximumWidthText = a.getString(R.styleable.WheelPicker_itemMaximumWidthText);
		mSelectedItemTextColor = a.getColor(R.styleable.WheelPicker_selectedTextColor, Color.RED);
        mInitSelectedPosition = a.getInteger(R.styleable.WheelPicker_selectedItemPosition, 0);
		a.recycle();
	}


	public void setOnWheelChangeListener(OnWheelChangeListener onWheelChangeListener) {
		mOnWheelChangeListener = onWheelChangeListener;
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
		if (!TextUtils.isEmpty(mItemMaximumWidthText)) {
            mTextMaxWidth = (int) mPaint.measureText(mItemMaximumWidthText);
		} else {
			mTextMaxWidth = (int) mPaint.measureText(mDataList.get(0).toString());
		}
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
	 * 显示的个数等于上下两边Item的个数+ 中间的ITem
	 * @return
	 */
	private int getVisibleItemCount() {
		return mHalfVisibleItemCount * 2 + 1;
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
		int height = mTextMaxHeight * getVisibleItemCount() + mItemSpace * (mHalfVisibleItemCount - 1);
		if (mIsCurved) {
			height = (int) (2 * height / Math.PI);
		}
		width += getPaddingLeft() + getPaddingRight();
		height += getPaddingTop() + getPaddingBottom();
		setMeasuredDimension(measureSize(specWidthMode, specWidthSize, width),
				measureSize(specHeightMode, specHeightSize, height));
	}

	/**
	 * 计算Fling极限
	 * 如果为Cyclic模式则为Integer的极限值，如果正常模式，则为一整个数据集的上下限。
	 */
	private void computeFlingLimitY() {
		mMinFlingY = mIsCyclic ? Integer.MIN_VALUE :
				- mItemHeight * (mDataList.size() - 1);
		mMaxFlingY = mIsCyclic ? Integer.MAX_VALUE : 0;
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mDrawnRect.set(getPaddingLeft(), getPaddingTop(),
				getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
		mItemHeight = mDrawnRect.height() / getVisibleItemCount();
		mSelectedItemDrawX = mDrawnRect.centerX();
		mSelectedItemDrawY = (int) ((mItemHeight - (mPaint.ascent() + mPaint.descent())) / 2);
		//中间的Item边框
		mSelectedItemRect.set(getPaddingLeft(), mItemHeight * mHalfVisibleItemCount,
				getWidth() - getPaddingRight(), mItemHeight + mItemHeight * mHalfVisibleItemCount);
		computeFlingLimitY();

		mScrollOffsetY = -mItemHeight * mInitSelectedPosition;
	}

	private int computeSpace(int degree) {
		return (int) (Math.sin(Math.toRadians(degree)) * (mDrawnRect.height() / 2));
	}

	private int computeDepth(int degree) {
		return (int) (mDrawnRect.height() / 2 - Math.cos(Math.toRadians(degree)) * mDrawnRect.height() / 2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int drawnSelectedPos = - mScrollOffsetY / mItemHeight;
		mPaint.setStyle(Paint.Style.FILL);
		//首尾各多绘制一个用于缓冲
		for (int drawDataPos = drawnSelectedPos - mHalfVisibleItemCount - 1;
            drawDataPos <= drawnSelectedPos + mHalfVisibleItemCount + 1; drawDataPos ++) {
			int pos = drawDataPos;
			if (mIsCyclic) {
				if (pos < 0) {
					//将数据集限定在0 ~ mDataList.size()-1之间
					while (pos < 0) {
						pos += mDataList.size();
					}
				} else if (pos > mDataList.size() - 1) {
					//将数据集限定在0 ~ mDataList.size()-1之间
					while (pos >= mDataList.size()) {
						pos -= mDataList.size();
					}
				}
			} else {
				if (pos < 0 || pos > mDataList.size() - 1) {
					continue;
				}
			}
			if (drawnSelectedPos == drawDataPos) {
				mPaint.setColor(mSelectedItemTextColor);
			} else {
				mPaint.setColor(mTextColor);
			}



			T t = mDataList.get(pos);
			int itemDrawY = mSelectedItemDrawY + (drawDataPos + mHalfVisibleItemCount) * mItemHeight + mScrollOffsetY;

			canvas.drawText(t.toString(), mSelectedItemDrawX, itemDrawY, mPaint);
		}
		mPaint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(mSelectedItemRect, mPaint);

	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mTracker == null) {
			mTracker = VelocityTracker.obtain();
		}
		mTracker.addMovement(event);
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();
				}
				mTracker.clear();
				mTouchDownY = mLastDownY = (int) event.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				if (Math.abs(mTouchDownY - event.getY()) < mTouchSlop) {
					break;
				}
				float move = event.getY() - mLastDownY;
				mScrollOffsetY += move;
				mLastDownY = (int) event.getY();
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				mTracker.computeCurrentVelocity(1000, mMaximumVelocity);
				int velocity = (int) mTracker.getYVelocity();
                Log.d(TAG, "onTouchEvent: " + velocity);
                if (Math.abs(velocity) > mMinimumVelocity) {
					mScroller.fling(0, mScrollOffsetY, 0, velocity,
							0, 0, mMinFlingY, mMaxFlingY);
					mScroller.setFinalY(mScroller.getFinalY() +
							computeDistanceToEndPoint(mScroller.getFinalY() % mItemHeight));
				} else {
					mScroller.startScroll(0, mScrollOffsetY, 0,
							computeDistanceToEndPoint(mScrollOffsetY % mItemHeight));
				}
				if (!mIsCyclic) {
					if (mScroller.getFinalY() > mMaxFlingY) {
						mScroller.setFinalY(mMaxFlingY);
					} else if (mScroller.getFinalY() < mMinFlingY) {
						mScroller.setFinalY(mMinFlingY);
					}
				}
				mHandler.post(mScrollerRunnable);
				mTracker.recycle();
				mTracker = null;
				break;
		}
		return true;
	}

	private int computeDistanceToEndPoint(int remainder) {
		if (Math.abs(remainder) > mItemHeight / 2)
			if (mScrollOffsetY < 0)
				return -mItemHeight - remainder;
			else
				return mItemHeight - remainder;
		else
			return -remainder;
	}

	@Override
	public boolean performClick() {
		return super.performClick();
	}

	public interface OnWheelChangeListener {
		void onWheelSelected(int position);
	}
}
