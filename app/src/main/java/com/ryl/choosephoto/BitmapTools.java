package com.ryl.choosephoto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BitmapTools {

    public static Bitmap revitionImageSize(String path) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 400) && (options.outHeight >> i <= 400)) {
                in = new BufferedInputStream(new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    public static Bitmap compressImageSize(File inFile, double maxSize) {
        Bitmap bitmap = null;
        try {
            // Cover to KB
            double mid = inFile.length() / 1024;
            BitmapFactory.Options options = new BitmapFactory.Options();
            BufferedInputStream in;
            in = new BufferedInputStream(new FileInputStream(inFile));
            options.inJustDecodeBounds = false;
            if (mid > maxSize) {
                double i = mid / maxSize;
                options.inSampleSize = (int) i;
            }
            bitmap = BitmapFactory.decodeStream(in, null, options);
            Log.e("picSizeIs:", bitmap.getWidth() + ";" + bitmap.getHeight() + ";" + bitmap.getByteCount() / 1024);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap compressImageSize(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//这个参数设置为true才有效，
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        int width = options.outWidth;
        int zoom = width > 850 ? width / 720 : 1;
        options.inJustDecodeBounds = false;
        options.inSampleSize = zoom;
        bmp = BitmapFactory.decodeFile(path, options);
        Log.e("picSizeIs:", bmp.getWidth() + ";" + bmp.getHeight() + ";" + bmp.getByteCount() / 1024);
        return bmp;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // BEGIN_INCLUDE (calculate_sample_size)
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and
            // keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger
            // inSampleSize).

            long totalPixels = width * height / inSampleSize;

            // Anything more than 2x the requested pixels we'll sample down
            // further
            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
        // END_INCLUDE (calculate_sample_size)
    }
}
