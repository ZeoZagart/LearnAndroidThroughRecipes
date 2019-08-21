package com.example.myapplication.View;

import android.graphics.*;
import com.example.myapplication.Constants;
import com.squareup.picasso.Transformation;

public class CircleTransform implements Transformation {
    private final int BORDER_COLOR = Color.rgb(90, 80, 135);
    private final int BORDER_RADIUS = 0;

    public static Path RoundedRect(
            float left, float top, float right, float bottom, float rx, float ry,
            boolean tl, boolean tr, boolean br, boolean bl
    ) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        if (tr)
            path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        else {
            path.rLineTo(0, -ry);
            path.rLineTo(-rx, 0);
        }
        path.rLineTo(-widthMinusCorners, 0);
        if (tl)
            path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        else {
            path.rLineTo(-rx, 0);
            path.rLineTo(0, ry);
        }
        path.rLineTo(0, heightMinusCorners);

        if (bl)
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
        else {
            path.rLineTo(0, ry);
            path.rLineTo(rx, 0);
        }

        path.rLineTo(widthMinusCorners, 0);
        if (br)
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        else {
            path.rLineTo(rx, 0);
            path.rLineTo(0, -ry);
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

        return path;
    }


    @Override
    public String key() {
        return "circle";
    }

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 20f;

        // Prepare the background
        Paint paintBg = new Paint();
        paintBg.setColor(BORDER_COLOR);
        paintBg.setAntiAlias(true);

        // Draw the background circle
        //canvas.drawCircle(r, r, r, paintBg);

        // Draw the image smaller than the background so a little border will be seen
        Integer radius = 4 * Constants.INSTANCE.getDp() / 160;

        Path path = RoundedRect(0, 0, source.getWidth(), source.getHeight(), radius, radius, true, true, false, false);
        //canvas.drawRoundRect(new RectF(0, 0, source.getWidth(), source.getHeight()), 0, r - BORDER_RADIUS, paint);
        canvas.drawPath(path, paint);


        squaredBitmap.recycle();
        return bitmap;
    }


}

