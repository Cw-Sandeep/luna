package tv.cloudwalker.lunalauncher.com.utils;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;

import timber.log.Timber;

public class StaticUtils {

    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            return packageManager.getApplicationInfo(packageName, 0).enabled;
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e);
            return false;
        }
    }

    @SuppressLint("PrivateApi")
    public static String getSystemProperty(String key) {
        String value = "";
        try {
            value = (String) Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class).invoke(null, key);
        } catch (Exception e) {
           Timber.e(e);
        }
        return value;
    }
}
