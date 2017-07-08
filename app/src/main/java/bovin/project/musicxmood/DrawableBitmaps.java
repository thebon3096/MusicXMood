package bovin.project.musicxmood;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

/**
 * Created by Bonny Haveliwala on 001 1 Apr 2016.
 */
public class DrawableBitmaps {

    static Context context;
    private static int reqWidthInDP;
    private static int reqHeightInDP;
    private static Bitmap letterAlbumArt;

    public DrawableBitmaps(Context context, int reqWidthInDP, int reqHeightInDP) {
        this.context = context;
        this.reqWidthInDP = reqWidthInDP;
        this.reqHeightInDP = reqHeightInDP;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap getLetterAlbumArt(String name) {
        letterAlbumArt = decodeSampledBitmapFromResource(context.getResources(),
                getLetterAlbumArtResourceID(name), dpToPx(reqWidthInDP), dpToPx(reqHeightInDP));
        return letterAlbumArt;
    }

    public static int getLetterAlbumArtResourceID(String name) {
        int i = 0;
        char firstLetter = name.charAt(i);
        while (!(Character.isLetter(firstLetter)) && i < name.length() - 1) {
            firstLetter = name.charAt(++i);
        }
        if (!(Character.isLetter(firstLetter))) {
            return R.drawable.a;
        }

        switch (firstLetter) {
            case 'a':
            case 'A':
                return R.drawable.a;
            case 'b':
            case 'B':
                return R.drawable.b;
            case 'c':
            case 'C':
                return R.drawable.c;
            case 'd':
            case 'D':
                return R.drawable.d;
            case 'e':
            case 'E':
                return R.drawable.e;
            case 'f':
            case 'F':
                return R.drawable.f;
            case 'g':
            case 'G':
                return R.drawable.g;
            case 'h':
            case 'H':
                return R.drawable.h;
            case 'i':
            case 'I':
                return R.drawable.i;
            case 'j':
            case 'J':
                return R.drawable.j;
            case 'k':
            case 'K':
                return R.drawable.k;
            case 'l':
            case 'L':
                return R.drawable.l;
            case 'm':
            case 'M':
                return R.drawable.m;
            case 'n':
            case 'N':
                return R.drawable.n;
            case 'o':
            case 'O':
                return R.drawable.o;
            case 'p':
            case 'P':
                return R.drawable.p;
            case 'q':
            case 'Q':
                return R.drawable.q;
            case 'r':
            case 'R':
                return R.drawable.r;
            case 's':
            case 'S':
                return R.drawable.s;
            case 't':
            case 'T':
                return R.drawable.t;
            case 'u':
            case 'U':
                return R.drawable.u;
            case 'v':
            case 'V':
                return R.drawable.v;
            case 'w':
            case 'W':
                return R.drawable.w;
            case 'x':
            case 'X':
                return R.drawable.x;
            case 'y':
            case 'Y':
                return R.drawable.y;
            case 'z':
            case 'Z':
                return R.drawable.z;
        }
        return R.drawable.a;
    }
}
