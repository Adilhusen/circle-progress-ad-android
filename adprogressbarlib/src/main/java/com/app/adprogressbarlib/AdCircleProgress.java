package com.app.adprogressbarlib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by bruce on 14-10-30.
 */
public class AdCircleProgress extends View {

    private Paint finishedPaint;
    private Paint unfinishedPaint;
    private Paint innerCirclePaint;

    protected Paint textPaint;
    protected Paint innerBottomTextPaint;

    private RectF finishedOuterRect = new RectF();
    private RectF unfinishedOuterRect = new RectF();

    private int attributeResourceId = 0;
    private boolean showText;
    private float textSize;
    private int textColor;
    private int innerBottomTextColor;
    private float progress = 0;
    private int max;
    private int finishedStrokeColor;
    private int gradientColorOne=0;
    private int gradientColorTwo=0;


    private int unfinishedStrokeColor = Color.WHITE;
    private int startingDegree;
    private float finishedStrokeWidth;
    private float unfinishedStrokeWidth;
    private int innerBackgroundColor;
    private String prefixText = "";
    private String suffixText = "%";
    private String text = null;
    private float innerBottomTextSize;
    private String innerBottomText;
    private float innerBottomTextHeight;

    private final float default_stroke_width;
    private final int default_finished_color = Color.rgb(66, 145, 241);
    private final int default_unfinished_color = Color.rgb(204, 204, 204);
    private final int default_text_color = Color.rgb(66, 145, 241);
    private final int default_inner_bottom_text_color = Color.rgb(66, 145, 241);
    private final int default_inner_background_color = Color.parseColor("#71130a0d");
    private final int default_max = 100;
    private final int default_startingDegree = -90;
    private final float default_text_size;
    private final float default_inner_bottom_text_size;
    private final int min_size;


    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_TEXT = "text";
    private static final String INSTANCE_INNER_BOTTOM_TEXT_SIZE = "inner_bottom_text_size";
    private static final String INSTANCE_INNER_BOTTOM_TEXT = "inner_bottom_text";
    private static final String INSTANCE_INNER_BOTTOM_TEXT_COLOR = "inner_bottom_text_color";
    private static final String INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color";
    private static final String INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color";
    private static final String INSTANCE_GRADIENT_COLOR_ONE = "gradientColorOne";
    private static final String INSTANCE_GRADIENT_COLOR_TWO = "gradientColorTwo";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_FINISHED_STROKE_WIDTH = "finished_stroke_width";
    private static final String INSTANCE_UNFINISHED_STROKE_WIDTH = "unfinished_stroke_width";
    private static final String INSTANCE_BACKGROUND_COLOR = "inner_background_color";
    private static final String INSTANCE_STARTING_DEGREE = "starting_degree";
    private static final String INSTANCE_INNER_DRAWABLE = "inner_drawable";

    public AdCircleProgress(Context context) {
        this(context, null);
    }

    public AdCircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        default_text_size = Utils.sp2px(getResources(), 15);
        min_size = (int) Utils.dp2px(getResources(), 100);
        default_stroke_width = Utils.dp2px(getResources(), 10);
        default_inner_bottom_text_size = Utils.sp2px(getResources(), 18);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AdCircleProgress, defStyleAttr, 0);
        initByAttributes(attributes);
        attributes.recycle();

        initPainters();
    }

    protected void initPainters() {
        if (showText) {
            textPaint = new TextPaint();
            textPaint.setColor(textColor);
            textPaint.setTextSize(textSize);
            textPaint.setAntiAlias(true);

            innerBottomTextPaint = new TextPaint();
            innerBottomTextPaint.setColor(innerBottomTextColor);
            innerBottomTextPaint.setTextSize(innerBottomTextSize);
            innerBottomTextPaint.setAntiAlias(true);
        }

        finishedPaint = new Paint();

        //finishedPaint.setShader(new SweepGradient(0, 0,  Color.BLUE, Color.YELLOW));
        if (gradientColorOne != 0 && gradientColorTwo != 0) {
            finishedPaint.setShader(new LinearGradient(0, 0, 0, getHeight(), gradientColorOne, gradientColorTwo, Shader.TileMode.MIRROR));
        } else {
            finishedPaint.setColor(finishedStrokeColor);
        }

        finishedPaint.setStyle(Paint.Style.STROKE);
        finishedPaint.setAntiAlias(true);
        finishedPaint.setStrokeWidth(finishedStrokeWidth);

        unfinishedPaint = new Paint();
        unfinishedPaint.setColor(unfinishedStrokeColor);
        unfinishedPaint.setStyle(Paint.Style.STROKE);
        unfinishedPaint.setAntiAlias(true);
        unfinishedPaint.setStrokeWidth(unfinishedStrokeWidth);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(innerBackgroundColor);
        innerCirclePaint.setAntiAlias(true);

    }


    protected void initByAttributes(TypedArray attributes) {
        gradientColorOne = attributes.getColor(R.styleable.AdCircleProgress_adpgb_gradient_color_one, 0);
        gradientColorTwo = attributes.getColor(R.styleable.AdCircleProgress_adpgb_gradient_color_two, 0);

        finishedStrokeColor = attributes.getColor(R.styleable.AdCircleProgress_adpgb_finished_color, default_finished_color);
        unfinishedStrokeColor = attributes.getColor(R.styleable.AdCircleProgress_adpgb_unfinished_color, default_unfinished_color);
        showText = attributes.getBoolean(R.styleable.AdCircleProgress_adpgb_show_text, true);
        attributeResourceId = attributes.getResourceId(R.styleable.AdCircleProgress_adpgb_inner_drawable, 0);

        setMax(attributes.getInt(R.styleable.AdCircleProgress_adpgb_max, default_max));
        setProgress(attributes.getFloat(R.styleable.AdCircleProgress_adpgb_progress, 0));
        finishedStrokeWidth = attributes.getDimension(R.styleable.AdCircleProgress_adpgb_finished_stroke_width, default_stroke_width);
        unfinishedStrokeWidth = attributes.getDimension(R.styleable.AdCircleProgress_adpgb_unfinished_stroke_width, default_stroke_width);

        if (showText) {
            if (attributes.getString(R.styleable.AdCircleProgress_adpgb_prefix_text) != null) {
                prefixText = attributes.getString(R.styleable.AdCircleProgress_adpgb_prefix_text);
            }
            if (attributes.getString(R.styleable.AdCircleProgress_adpgb_suffix_text) != null) {
                suffixText = attributes.getString(R.styleable.AdCircleProgress_adpgb_suffix_text);
            }
            if (attributes.getString(R.styleable.AdCircleProgress_adpgb_text) != null) {
                text = attributes.getString(R.styleable.AdCircleProgress_adpgb_text);
            }

            textColor = attributes.getColor(R.styleable.AdCircleProgress_adpgb_text_color, default_text_color);
            textSize = attributes.getDimension(R.styleable.AdCircleProgress_adpgb_text_size, default_text_size);
            innerBottomTextSize = attributes.getDimension(R.styleable.AdCircleProgress_adpgb_inner_bottom_text_size, default_inner_bottom_text_size);
            innerBottomTextColor = attributes.getColor(R.styleable.AdCircleProgress_adpgb_inner_bottom_text_color, default_inner_bottom_text_color);
            innerBottomText = attributes.getString(R.styleable.AdCircleProgress_adpgb_inner_bottom_text);
        }

        innerBottomTextSize = attributes.getDimension(R.styleable.AdCircleProgress_adpgb_inner_bottom_text_size, default_inner_bottom_text_size);
        innerBottomTextColor = attributes.getColor(R.styleable.AdCircleProgress_adpgb_inner_bottom_text_color, default_inner_bottom_text_color);
        innerBottomText = attributes.getString(R.styleable.AdCircleProgress_adpgb_inner_bottom_text);

        startingDegree = attributes.getInt(R.styleable.AdCircleProgress_adpgb_circle_starting_degree, default_startingDegree);
        innerBackgroundColor = attributes.getColor(R.styleable.AdCircleProgress_adpgb_background_color, default_inner_background_color);
    }

    @Override
    public void invalidate() {
        initPainters();
        super.invalidate();
    }

    public boolean isShowText() {
        return showText;
    }

    public void setShowText(boolean showText) {
        this.showText = showText;
    }

    public float getFinishedStrokeWidth() {
        return finishedStrokeWidth;
    }

    public void setFinishedStrokeWidth(float finishedStrokeWidth) {
        this.finishedStrokeWidth = finishedStrokeWidth;
        this.invalidate();
    }

    public float getUnfinishedStrokeWidth() {
        return unfinishedStrokeWidth;
    }

    public void setUnfinishedStrokeWidth(float unfinishedStrokeWidth) {
        this.unfinishedStrokeWidth = unfinishedStrokeWidth;
        this.invalidate();
    }

    private float getProgressAngle() {
        return getProgress() / (float) max * 360f;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        if (this.progress > getMax()) {
            this.progress %= getMax();
        }
        invalidate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max > 0) {
            this.max = max;
            invalidate();
        }
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        this.invalidate();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        this.invalidate();
    }

    public int getFinishedStrokeColor() {
        return finishedStrokeColor;
    }

    public int getGradientColorOne() {
        return gradientColorOne;
    }

    public void setGradientColorOne(int gradientColorOne) {
        this.gradientColorOne = gradientColorOne;
        this.invalidate();
    }

    public int getGradientColorTwo() {
        return gradientColorTwo;
    }

    public void setGradientColorTwo(int gradientColorTwo) {
        this.gradientColorTwo = gradientColorTwo;
        this.invalidate();
    }

    public void setFinishedStrokeColor(int finishedStrokeColor) {
        this.finishedStrokeColor = finishedStrokeColor;
        this.invalidate();
    }

    public int getUnfinishedStrokeColor() {
        return unfinishedStrokeColor;
    }

    public void setUnfinishedStrokeColor(int unfinishedStrokeColor) {
        this.unfinishedStrokeColor = unfinishedStrokeColor;
        this.invalidate();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.invalidate();
    }

    public String getSuffixText() {
        return suffixText;
    }

    public void setSuffixText(String suffixText) {
        this.suffixText = suffixText;
        this.invalidate();
    }

    public String getPrefixText() {
        return prefixText;
    }

    public void setPrefixText(String prefixText) {
        this.prefixText = prefixText;
        this.invalidate();
    }

    public int getInnerBackgroundColor() {
        return innerBackgroundColor;
    }

    public void setInnerBackgroundColor(int innerBackgroundColor) {
        this.innerBackgroundColor = innerBackgroundColor;
        this.invalidate();
    }


    public String getInnerBottomText() {
        return innerBottomText;
    }

    public void setInnerBottomText(String innerBottomText) {
        this.innerBottomText = innerBottomText;
        this.invalidate();
    }


    public float getInnerBottomTextSize() {
        return innerBottomTextSize;
    }

    public void setInnerBottomTextSize(float innerBottomTextSize) {
        this.innerBottomTextSize = innerBottomTextSize;
        this.invalidate();
    }

    public int getInnerBottomTextColor() {
        return innerBottomTextColor;
    }

    public void setInnerBottomTextColor(int innerBottomTextColor) {
        this.innerBottomTextColor = innerBottomTextColor;
        this.invalidate();
    }

    public int getStartingDegree() {
        return startingDegree;
    }

    public void setStartingDegree(int startingDegree) {
        this.startingDegree = startingDegree;
        this.invalidate();
    }

    public int getAttributeResourceId() {
        return attributeResourceId;
    }

    public void setAttributeResourceId(int attributeResourceId) {
        this.attributeResourceId = attributeResourceId;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec));

        //TODO calculate inner circle height and then position bottom text at the bottom (3/4)
        innerBottomTextHeight = getHeight() - (getHeight() * 3) / 4;
    }

    private int measure(int measureSpec) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = min_size;
            if (mode == MeasureSpec.AT_MOST) {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float delta = Math.max(finishedStrokeWidth, unfinishedStrokeWidth);
        finishedOuterRect.set(delta,
                delta,
                getWidth() - delta,
                getHeight() - delta);
        unfinishedOuterRect.set(delta,
                delta,
                getWidth() - delta,
                getHeight() - delta);

        float innerCircleRadius = (getWidth() - Math.min(finishedStrokeWidth, unfinishedStrokeWidth) + Math.abs(finishedStrokeWidth - unfinishedStrokeWidth)) / 2f;
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, innerCircleRadius, innerCirclePaint);
        canvas.drawArc(finishedOuterRect, getStartingDegree(), getProgressAngle(), false, finishedPaint);
        canvas.drawArc(unfinishedOuterRect, getStartingDegree() + getProgressAngle(), 360 - getProgressAngle(), false, unfinishedPaint);

        if (showText) {
            String text = this.text != null ? this.text : prefixText + progress + suffixText;
            if (!TextUtils.isEmpty(text)) {
                float textHeight = textPaint.descent() + textPaint.ascent() - 10;
                canvas.drawText(text, (getWidth() - textPaint.measureText(text)) / 2.0f, (getWidth() - textHeight) / 2.0f, textPaint);

            }

            if (!TextUtils.isEmpty(getInnerBottomText())) {
                innerBottomTextPaint.setTextSize(innerBottomTextSize);
                float bottomTextBaseline = getHeight() - innerBottomTextHeight - (textPaint.descent() + textPaint.ascent()) / 2;
                canvas.drawText(getInnerBottomText(), (getWidth() - innerBottomTextPaint.measureText(getInnerBottomText())) / 2.0f, bottomTextBaseline, innerBottomTextPaint);
            }
        }

        if (attributeResourceId != 0) {
            Bitmap bitmap = null;

            bitmap = BitmapFactory.decodeResource(getResources(), attributeResourceId);

            if (bitmap == null)
                bitmap = getBitmapFromVectorDrawable(getContext(), attributeResourceId);


            if (bitmap != null)
                canvas.drawBitmap(bitmap, (getWidth() - bitmap.getWidth()) / 2.0f, (getHeight() - bitmap.getHeight()) / 2.0f, null);

        }
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize());
        bundle.putFloat(INSTANCE_INNER_BOTTOM_TEXT_SIZE, getInnerBottomTextSize());
        bundle.putFloat(INSTANCE_INNER_BOTTOM_TEXT_COLOR, getInnerBottomTextColor());
        bundle.putString(INSTANCE_INNER_BOTTOM_TEXT, getInnerBottomText());
        bundle.putInt(INSTANCE_INNER_BOTTOM_TEXT_COLOR, getInnerBottomTextColor());
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor());
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR, getUnfinishedStrokeColor());
        bundle.putInt(INSTANCE_GRADIENT_COLOR_ONE, getGradientColorOne());
        bundle.putInt(INSTANCE_GRADIENT_COLOR_TWO, getGradientColorTwo());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_STARTING_DEGREE, getStartingDegree());
        bundle.putFloat(INSTANCE_PROGRESS, getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffixText());
        bundle.putString(INSTANCE_PREFIX, getPrefixText());
        bundle.putString(INSTANCE_TEXT, getText());
        bundle.putFloat(INSTANCE_FINISHED_STROKE_WIDTH, getFinishedStrokeWidth());
        bundle.putFloat(INSTANCE_UNFINISHED_STROKE_WIDTH, getUnfinishedStrokeWidth());
        bundle.putInt(INSTANCE_BACKGROUND_COLOR, getInnerBackgroundColor());
        bundle.putInt(INSTANCE_INNER_DRAWABLE, getAttributeResourceId());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            textColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            textSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            innerBottomTextSize = bundle.getFloat(INSTANCE_INNER_BOTTOM_TEXT_SIZE);
            innerBottomText = bundle.getString(INSTANCE_INNER_BOTTOM_TEXT);
            innerBottomTextColor = bundle.getInt(INSTANCE_INNER_BOTTOM_TEXT_COLOR);
            finishedStrokeColor = bundle.getInt(INSTANCE_FINISHED_STROKE_COLOR);
            unfinishedStrokeColor = bundle.getInt(INSTANCE_UNFINISHED_STROKE_COLOR);
            gradientColorOne = bundle.getInt(INSTANCE_GRADIENT_COLOR_ONE);
            gradientColorTwo = bundle.getInt(INSTANCE_GRADIENT_COLOR_TWO);
            finishedStrokeWidth = bundle.getFloat(INSTANCE_FINISHED_STROKE_WIDTH);
            unfinishedStrokeWidth = bundle.getFloat(INSTANCE_UNFINISHED_STROKE_WIDTH);
            innerBackgroundColor = bundle.getInt(INSTANCE_BACKGROUND_COLOR);
            attributeResourceId = bundle.getInt(INSTANCE_INNER_DRAWABLE);
            initPainters();
            setMax(bundle.getInt(INSTANCE_MAX));
            setStartingDegree(bundle.getInt(INSTANCE_STARTING_DEGREE));
            setProgress(bundle.getFloat(INSTANCE_PROGRESS));
            prefixText = bundle.getString(INSTANCE_PREFIX);
            suffixText = bundle.getString(INSTANCE_SUFFIX);
            text = bundle.getString(INSTANCE_TEXT);
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void setAdProgress(int percent) {
        if (percent >= 0) {
            setProgress(percent);
        }
    }
}
