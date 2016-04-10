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
    Bitmap a;
    Bitmap b;
    Bitmap c;
    Bitmap d;
     Bitmap e;
     Bitmap f;
     Bitmap g;
     Bitmap h;
     Bitmap i;
     Bitmap j;
     Bitmap k;
     Bitmap l;
     Bitmap m;
     Bitmap n;
     Bitmap o;
     Bitmap p;
     Bitmap q;
     Bitmap r;
     Bitmap s;
     Bitmap t;
     Bitmap u;
     Bitmap v;
     Bitmap w;
     Bitmap x;
     Bitmap y;
     Bitmap z;
    Context context;

    public DrawableBitmaps(Context context, int reqWidthInDP, int reqHeightInDP){
        this.context = context;
        this.a = decodeSampledBitmapFromResource(context.getResources(), R.drawable.a,dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.b = decodeSampledBitmapFromResource(context.getResources(), R.drawable.b, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.c = decodeSampledBitmapFromResource(context.getResources(), R.drawable.c, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.d = decodeSampledBitmapFromResource(context.getResources(), R.drawable.d, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.e = decodeSampledBitmapFromResource(context.getResources(), R.drawable.e, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.f = decodeSampledBitmapFromResource(context.getResources(), R.drawable.f, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.g = decodeSampledBitmapFromResource(context.getResources(), R.drawable.g, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.h = decodeSampledBitmapFromResource(context.getResources(), R.drawable.h, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.i = decodeSampledBitmapFromResource(context.getResources(), R.drawable.i, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.j = decodeSampledBitmapFromResource(context.getResources(), R.drawable.j, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.k = decodeSampledBitmapFromResource(context.getResources(), R.drawable.k, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.l = decodeSampledBitmapFromResource(context.getResources(), R.drawable.l, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.m = decodeSampledBitmapFromResource(context.getResources(), R.drawable.m, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.n = decodeSampledBitmapFromResource(context.getResources(), R.drawable.n, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.o = decodeSampledBitmapFromResource(context.getResources(), R.drawable.o, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.p = decodeSampledBitmapFromResource(context.getResources(), R.drawable.p, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.q = decodeSampledBitmapFromResource(context.getResources(), R.drawable.q, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.r = decodeSampledBitmapFromResource(context.getResources(), R.drawable.r, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.s = decodeSampledBitmapFromResource(context.getResources(), R.drawable.s, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.t = decodeSampledBitmapFromResource(context.getResources(), R.drawable.t, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.u = decodeSampledBitmapFromResource(context.getResources(), R.drawable.u, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.v = decodeSampledBitmapFromResource(context.getResources(), R.drawable.v, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.w = decodeSampledBitmapFromResource(context.getResources(), R.drawable.w, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.x = decodeSampledBitmapFromResource(context.getResources(), R.drawable.x, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.y = decodeSampledBitmapFromResource(context.getResources(), R.drawable.y, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        this.z = decodeSampledBitmapFromResource(context.getResources(), R.drawable.z, dpToPx(reqWidthInDP),dpToPx(reqHeightInDP));
        /*this.a = BitmapFactory.decodeResource(context.getResources(), R.drawable.a);
        this.b = BitmapFactory.decodeResource(context.getResources(), R.drawable.b);
        this.c = BitmapFactory.decodeResource(context.getResources(), R.drawable.c);
        this.d = BitmapFactory.decodeResource(context.getResources(), R.drawable.d);
        this.e = BitmapFactory.decodeResource(context.getResources(), R.drawable.e);
        this.f = BitmapFactory.decodeResource(context.getResources(), R.drawable.f);
        this.g = BitmapFactory.decodeResource(context.getResources(), R.drawable.g);
        this.h = BitmapFactory.decodeResource(context.getResources(), R.drawable.h);
        this.i = BitmapFactory.decodeResource(context.getResources(), R.drawable.i);
        this.j = BitmapFactory.decodeResource(context.getResources(), R.drawable.j);
        this.k = BitmapFactory.decodeResource(context.getResources(), R.drawable.k);
        this.l = BitmapFactory.decodeResource(context.getResources(), R.drawable.l);
        this.m = BitmapFactory.decodeResource(context.getResources(), R.drawable.m);
        this.n = BitmapFactory.decodeResource(context.getResources(), R.drawable.n);
        this.o = BitmapFactory.decodeResource(context.getResources(), R.drawable.o);
        this.p = BitmapFactory.decodeResource(context.getResources(), R.drawable.p);
        this.q = BitmapFactory.decodeResource(context.getResources(), R.drawable.q);
        this.r = BitmapFactory.decodeResource(context.getResources(), R.drawable.r);
        this.s = BitmapFactory.decodeResource(context.getResources(), R.drawable.s);
        this.t = BitmapFactory.decodeResource(context.getResources(), R.drawable.t);
        this.u = BitmapFactory.decodeResource(context.getResources(), R.drawable.u);
        this.v = BitmapFactory.decodeResource(context.getResources(), R.drawable.v);
        this.w = BitmapFactory.decodeResource(context.getResources(), R.drawable.w);
        this.x = BitmapFactory.decodeResource(context.getResources(), R.drawable.x);
        this.y = BitmapFactory.decodeResource(context.getResources(), R.drawable.y);
        this.z = BitmapFactory.decodeResource(context.getResources(), R.drawable.z);*/
    }

    public  Bitmap getA() {
        return a;
    }

    public  Bitmap getB() {
        return b;
    }

    public  Bitmap getC() {
        return c;
    }

    public  Bitmap getD() {
        return d;
    }

    public  Bitmap getE() {
        return e;
    }

    public  Bitmap getF() {
        return f;
    }

    public  Bitmap getG() {
        return g;
    }

    public  Bitmap getH() {
        return h;
    }

    public  Bitmap getI() {
        return i;
    }

    public  Bitmap getJ() {
        return j;
    }

    public  Bitmap getK() {
        return k;
    }

    public  Bitmap getL() {
        return l;
    }

    public  Bitmap getM() {
        return m;
    }

    public  Bitmap getN() {
        return n;
    }

    public  Bitmap getO() {
        return o;
    }

    public  Bitmap getP() {
        return p;
    }

    public  Bitmap getQ() {
        return q;
    }

    public  Bitmap getR() {
        return r;
    }

    public  Bitmap getS() {
        return s;
    }

    public  Bitmap getT() {
        return t;
    }

    public  Bitmap getU() {
        return u;
    }

    public  Bitmap getV() {
        return v;
    }

    public  Bitmap getW() {
        return w;
    }

    public  Bitmap getX() {
        return x;
    }

    public  Bitmap getY() {
        return y;
    }

    public  Bitmap getZ() {
        return z;
    }

    //taken from Source
    public int dpToPx(int dp){
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
}
