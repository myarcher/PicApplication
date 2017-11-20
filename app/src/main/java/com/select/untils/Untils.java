package com.select.untils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jyjin on 2017/10/31.
 */

public class Untils {

    //获取屏幕的亮度

    public static int getScreenBrightness(Activity activity) {
        int value = 0;
        ContentResolver cr = activity.getContentResolver();
        try {
            value = Settings.System.getInt(cr, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {

        }
        return value;
    }

    //设置屏幕亮度：
    public static void setScreenBrightness(Activity activity, int value) {
        Window window = activity.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        if (value == -1) {
            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
        } else {
            lp.screenBrightness = (value <= 0 ? 1 : value) / 255f;
        }
        window.setAttributes(lp);
}

    public static BitmapDrawable getBitmap(Context context, int homeHeadHdpi) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(homeHeadHdpi);
        Bitmap bm = BitmapFactory.decodeStream(is, null, opt);
        BitmapDrawable bd = new BitmapDrawable(context.getResources(), bm);
        return bd;
    }

    public static List<Beans> getBeans() {
        List<Beans> beans = new ArrayList<>();
        beans.add(new Beans("10%", 0.1f));
        beans.add(new Beans("20%", 0.2f));
        beans.add(new Beans("40%", 0.4f));
        beans.add(new Beans("60%", 0.6f));
        beans.add(new Beans("80%", 0.8f));
        beans.add(new Beans("100%", 1f));
        return beans;
    }
}
