package tv.cloudwalker.lunalauncher.com.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import timber.log.Timber;

public class SkinResource {
    private final Context context;

    public SkinResource(Context context) {
        this.context = context;
    }

    public Resources getSkinApp() {
        Resources resources;
        try {
            resources = context.getPackageManager().getResourcesForApplication("tv.cloudwalker.skin");
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e);
            resources = null;
        }
        return resources;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "DiscouragedApi"})
    public Drawable getSkinDrawableOrLocal(String mDrawableKey, int localResId) {
        //Return a resource identifier for the given resource name.
        Resources r = getSkinApp();
        if (r == null) {
            return context.getResources().getDrawable(localResId, null);
        } else {
            Drawable drawable;
            drawable = getSkinApp().getDrawable(getSkinApp().getIdentifier(mDrawableKey, "drawable", "tv.cloudwalker.skin"), context.getResources().newTheme());
            return drawable;
        }
    }

    @SuppressLint("DiscouragedApi")
    public String getStringFromSKinApp(String mStringKey, int localResId) {
        Resources r = getSkinApp();
        if (r == null) {
            return context.getResources().getString(localResId);
        }
        String string;
        string = getSkinApp().getString(getSkinApp().getIdentifier(mStringKey, "string", "tv.cloudwalker.skin"), context.getResources().newTheme());
        return string;
    }

    @SuppressLint("DiscouragedApi")
    public Integer getIntegerFromSKinApp(String mIntegerKey) {
        return getSkinApp().getInteger(getSkinApp().getIdentifier(mIntegerKey, "integer", "tv.cloudwalker.skin"));
    }

    @SuppressLint("DiscouragedApi")
    public Integer getColorFromSKinApp(String mColorKey, int localResId) {
        Resources r = getSkinApp();
        if (r == null) {
            return context.getResources().getColor(localResId, null);
        }
        return getSkinApp().getColor(getSkinApp().getIdentifier(mColorKey, "color", "tv.cloudwalker.skin"), null);
    }

    @SuppressLint("DiscouragedApi")
    public Boolean getBooleanFromSKinApp(String mBooleanKey, int localResId) {
        Resources r = getSkinApp();
        if (r == null) {
            return context.getResources().getBoolean(localResId);
        }
        return getSkinApp().getBoolean(getSkinApp().getIdentifier(mBooleanKey, "bool", "tv.cloudwalker.skin"));

    }
}
