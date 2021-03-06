package org.hacktivity.graffitiplum;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class CompanionSettings {

    //Define the list of accepted constants
    @IntDef({LOCAL, UTC, INTERACTIVE})

    //Tell the compiler not to store annotation data in the .class file
    @Retention(RetentionPolicy.SOURCE)

    //Declare the intdef annotation
    public @interface TimestampTimezone {

    }

    //Declare the constants
    public static final int LOCAL = 0;

    public static final int UTC = 1;

    public static final int INTERACTIVE = 2;

    public static final String KEY_CLOCK_SIZE = "clock_size";

    public static final int KEY_CLOCK_SIZE_DEF = 50;

    public static final String KEY_CLOCK_DIM = "clock_dim";

    public static final boolean KEY_CLOCK_DIM_DEF = true;

    public static final String KEY_MARKER_SIZE = "marker_size";

    public static final int KEY_MARKER_SIZE_DEF = 16;

    public static final String KEY_MARKER_DIM = "marker_dim";

    public static final boolean KEY_MARKER_DIM_DEF = true;

    public static final String KEY_TZ_SIZE = "tz_size";

    public static final int KEY_TZ_SIZE_DEF = 16;

    public static final String KEY_TZ_DIM = "tz_dim";

    public static final boolean KEY_TZ_DIM_DEF = false;

    public static final String KEY_DATE_SIZE = "date_size";

    public static final int KEY_DATE_SIZE_DEF = 18;

    public static final String KEY_DATE_DIM = "date_dim";

    public static final boolean KEY_DATE_DIM_DEF = false;

    public static final String KEY_TIME_SIZE = "time_size";

    public static final int KEY_TIME_SIZE_DEF = 18;

    public static final String KEY_TIME_DIM = "time_dim";

    public static final boolean KEY_TIME_DIM_DEF = false;

    public static final String KEY_EPOCH_SIZE = "epoch_size";

    public static final int KEY_EPOCH_SIZE_DEF = 18;

    public static final String KEY_EPOCH_DIM = "epoch_dim";

    public static final boolean KEY_EPOCH_SHOW_DEF = false;

    public static final String KEY_USE_SHORT_CARDS = "use_short_cards";

    public static final boolean KEY_USE_SHORT_CARDS_DEF = true;

    public static final String KEY_TIME_TZ = "timestamp_tz";

    public static final int KEY_TIME_TZ_DEF = INTERACTIVE;

    public static final String PATH_WITH_FEATURE = "/GraffitiPlum";


    public static int getInt(final Context context, final String key, final int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    public static boolean getBoolean(final Context context, final String key,
                                     final boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void setInt(final Context context, final String key, final int value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void setBoolean(final Context context, final String key, final boolean value) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void resetAllPrefs(final Context context) {
        final SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = settings.edit();

        editor.clear();
        editor.apply();
    }

    private CompanionSettings() {
        // static class
    }
}
