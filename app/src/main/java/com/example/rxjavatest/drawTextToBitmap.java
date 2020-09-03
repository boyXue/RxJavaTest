package com.example.rxjavatest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class drawTextToBitmap {
    public static Bitmap drawTextToBitmap(Bitmap bitmap, String text, Paint paint, int left, int top) {
        int mBitmapWidth = bitmap.getWidth();
        int mBitmapHeight = bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createBitmap(mBitmapWidth, mBitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawText(text,left,top,paint);
        canvas.save();
        canvas.restore();
        return newBitmap;
    }
}
