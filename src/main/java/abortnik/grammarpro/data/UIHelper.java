package abortnik.grammarpro.data;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by abortnik on 2.12.17.
 */

public class UIHelper {
    private static Typeface demi;
    private static Typeface light;
    private static Typeface medium;
    private static Typeface regular;
    private static Typeface ultra;
    private static Typeface thin;

    public static Typeface getLightFont(final Context context) {
        if(demi == null) {
            demi = Typeface.createFromAsset(context.getAssets(), "light.ttf");
        }
        return demi;
    }
    public static Typeface getBoldFont(final Context context) {
        if(light == null) {
            light = Typeface.createFromAsset(context.getAssets(), "bold.ttf");
        }
        return light;
    }
    public static Typeface getMediumFont(final Context context) {
        if(medium == null) {
            medium = Typeface.createFromAsset(context.getAssets(), "medium.ttf");
        }
        return medium;
    }
    public static Typeface getRegularFont(final Context context) {
        if(regular == null) {
            regular = Typeface.createFromAsset(context.getAssets(), "regular.ttf");
        }
        return regular;
    }
    public static Typeface getUltraLight(final Context context) {
        if(ultra == null) {
            ultra = Typeface.createFromAsset(context.getAssets(), "ultralight1.otf");
        }
        return ultra;
    }
    public static Typeface getThinFont(final Context context) {
        if(thin == null) {
            thin = Typeface.createFromAsset(context.getAssets(), "thin.ttf");
        }
        return thin;
    }
}
