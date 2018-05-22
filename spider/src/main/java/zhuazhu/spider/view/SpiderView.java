package zhuazhu.spider.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import zhuazhu.spider.R;

/**
 * @author zhuazhu
 **/
public class SpiderView extends View {
    /**
     * 线的数量
     */
    private int mLineCount = 3;
    private int mAreaCount;
    private float mAngle;
    private double[] mValues;
    private int mNetCount = 4;
    /**
     * 线的宽度
     */
    private float mLineWidth = 5f;
    /**
     * 网线的宽度
     */
    private float mNetWidth = 5f;
    /**
     * 点的大小
     */
    private float mPointSize = 10f;
    /**
     * 线的颜色
     */
    private int mLineColor = Color.RED;
    /**
     * 网线的颜色
     */
    private int mNetColor = Color.BLUE;
    /**
     * 点的颜色
     */
    private int mPointColor = Color.GREEN;
     /**
     * 遮罩的颜色
     */
    private int mAreaColor = Color.parseColor("#57565865");

    private Point[] mPoints;

    private Paint mPaintLine;
    private Paint mPaintNet;
    private Paint mPaintPoint;
    private Paint mPaintArea;
    public SpiderView(Context context) {
        this(context,null);
    }

    public SpiderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SpiderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpiderView,defStyleAttr,0);
        initStyle(typedArray);
        initPaint();
    }
    private void initStyle(TypedArray array){
        mLineCount = array.getInteger(R.styleable.SpiderView_line_count,mLineCount);
        mNetCount = array.getInteger(R.styleable.SpiderView_net_count,mNetCount);
        mLineColor = array.getColor(R.styleable.SpiderView_line_color,mLineColor);
        mNetColor = array.getColor(R.styleable.SpiderView_net_color,mNetColor);
        mLineWidth = array.getDimension(R.styleable.SpiderView_line_width,mLineWidth);
        mNetWidth = array.getDimension(R.styleable.SpiderView_net_width,mNetWidth);
        mPointSize = array.getDimension(R.styleable.SpiderView_point_size,mPointSize);
        mPointColor = array.getColor(R.styleable.SpiderView_point_color,mPointColor);
        mAreaColor = array.getColor(R.styleable.SpiderView_area_color,mAreaColor);
        String value = array.getString(R.styleable.SpiderView_value);
        mAreaCount = mLineCount *2;
        mAngle = (float) 360/mAreaCount;
        if(value!=null&&(!"".equals(value))){
            String[] strs = value.split(",");
            mValues = new double[mAreaCount];
            for (int i = 0; i < strs.length; i++) {
                double v = Double.valueOf(strs[i]);
                mValues[i] = v;
            }
        }

        array.recycle();
    }
    private void initPaint(){
        mPaintLine = new Paint();
        mPaintLine.setColor(mLineColor);
        mPaintLine.setAntiAlias(true);
        mPaintLine.setStrokeWidth(mLineWidth);

        mPaintNet = new Paint();
        mPaintNet.setColor(mNetColor);
        mPaintNet.setAntiAlias(true);
        mPaintNet.setStrokeWidth(mNetWidth);

        mPaintPoint = new Paint();
        mPaintPoint.setColor(mPointColor);
        mPaintPoint.setAntiAlias(true);
        mPaintPoint.setStrokeWidth(mPointSize);

        mPaintArea = new Paint();
        mPaintArea.setColor(mAreaColor);
        mPaintArea.setAntiAlias(true);
        mPaintArea.setStrokeWidth(4);

    }
    public void setValue(@FloatRange(from = 0.0,to = 1.0) double ...values){
        mValues = values;
        invalidate();
    }
    private int mMinSize;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int centerPointX = width/2;
        int centerPointY = height/2;
        mMinSize = (int) (Math.min(centerPointX,centerPointY)-mPointSize*2);
        int saveCount = canvas.saveLayer(0,0,width,height,null,Canvas.ALL_SAVE_FLAG);
        canvas.translate(centerPointX,centerPointY);
        drawLine(canvas);
        drawNet(canvas);
        drawPoint(canvas);
        drawArea(canvas);
        canvas.restoreToCount(saveCount);
    }
    private void drawLine(Canvas canvas){
        for (int i = 0; i < mLineCount; i++) {
            canvas.rotate(mAngle);
            canvas.drawLine(-mMinSize,0,mMinSize,0,mPaintLine);
        }
        canvas.rotate(-mLineCount *mAngle);
    }

    private void drawNet(Canvas canvas){
        double cos = Math.cos(Math.toRadians(mAngle));
        double sin = Math.sin(Math.toRadians(mAngle));
        for (int i = 0; i < mAreaCount; i++) {
            for (int j = mNetCount; j > 0; j--) {
                float size = mMinSize*j/mNetCount;
                float x = (float) (cos*size);
                float y = (float) (sin*size);
                canvas.drawLine(-size,0,-x,-y,mPaintNet);
            }
            canvas.rotate(-mAngle);
        }
    }

    private void drawPoint(Canvas canvas){
        if(mValues==null){
            return;
        }
        mPoints = new Point[mAreaCount];
        for (int i = 0; i < mAreaCount; i++) {
            double size = (int) (mValues[i]*mMinSize);
            double cos = Math.cos(Math.toRadians(mAngle*i));
            double sin = Math.sin(Math.toRadians(mAngle*i));
            Point point = new Point();
            point.x = (int) (cos*size);
            point.y = (int) (sin*size);
            mPoints[i] = point;
            canvas.drawPoint(point.x,point.y,mPaintPoint);
        }
    }

    private void drawArea(Canvas canvas){
        if(mValues==null){
            return;
        }
        Path path = new Path();
        path.moveTo(mPoints[0].x,mPoints[0].y);
        for (int i = 1; i < mAreaCount; i++) {
            path.lineTo(mPoints[i].x,mPoints[i].y);
        }
        path.close();
        canvas.drawPath(path,mPaintArea);
    }
}
