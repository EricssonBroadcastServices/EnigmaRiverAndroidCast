package com.redbeemedia.enigma.cast.optionsprovider;

import android.content.Context;

import com.google.android.gms.cast.framework.CastOptions;
import com.google.android.gms.cast.framework.OptionsProvider;
import com.google.android.gms.cast.framework.SessionProvider;

import java.util.List;

/**
 * <p>To use this class you need to add the following tag to your AndroidManifest.xml: <br>
 * <pre>
 * &lt;application ...&gt;
 *   ...
 *   &lt;meta-data
 *     android:name="com.google.android.gms.cast.framework.OPTIONS_PROVIDER_CLASS_NAME"
 *     android:value="com.redbeemedia.enigma.cast.optionsprovider.EnigmaCastOptionsProvider" /&gt;
 *   ...
 * &lt;/application&gt;
 * </pre>
 * </p>
 * <p>If you need to add custom cast options for your app, you can extend this class, override build-methods, and use the subclass as the options provider:
 * <pre>
 * public class AppOptionsProvider extends EnigmaCastOptionsProvider {
 *  {@code @Overide}
 *   protected void buildCastOptions(CastOptions.Builder builder) {
 *       super.buildCastOptions(builder);
 *       builder.setEnableReconnectionService(false); //For example
 *   }
 * }
 * </pre>
 * Note that you also need to provide this subclass in the &lt;meta-data&gt;-tag in AndroidManifest.xml: <br>
 * <pre>
 * &lt;application ...&gt;
 *   ...
 *   &lt;meta-data
 *     android:name="com.google.android.gms.cast.framework.OPTIONS_PROVIDER_CLASS_NAME"
 *     android:value="com.yourpackagename.AppOptionsProvider" /&gt;
 *   ...
 * &lt;/application&gt;
 * </pre>
 * </p>
 * <p>As an alternative, you can use your own OptionsProvider and use the static method {@link #configureCastOptions(CastOptions.Builder)} to inject Enigma settings into your app.
 *
 * </p>
 *
 * <h3>Note</h3> <p>To change the receiverApplicationId from the default, use {@code CastContext.getSharedInstance(applicationContext).setReceiverApplicationId(newReceiverApplicationId)}</p>
 */
public class EnigmaCastOptionsProvider implements OptionsProvider {
    @Override
    public final CastOptions getCastOptions(Context context) {
        CastOptions.Builder castOptionsBuilder = new CastOptions.Builder();
        buildCastOptions(castOptionsBuilder);
        return castOptionsBuilder.build();
    }

    @Override
    public List<SessionProvider> getAdditionalSessionProviders(Context context) {
        return null;
    }

    protected void buildCastOptions(CastOptions.Builder builder) {
        configureCastOptions(builder);
    }

    public static void configureCastOptions(CastOptions.Builder builder) {
        builder.setReceiverApplicationId("5D5D5CE6");
    }
}
