package com.redbeemedia.enigma.cast.message;

import com.google.android.gms.cast.CastDevice;
import com.redbeemedia.enigma.cast.listeners.IEnigmaCastListener;
import com.redbeemedia.enigma.cast.models.MediaTrack;
import com.redbeemedia.enigma.cast.models.ReceiverError;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EnigmaMessageConverterTest {
    @Test
    public void testMessageConversion() throws JSONException {
        Logger logger = new Logger();
        StringBuilder expectedLog = new StringBuilder();
        EnigmaMessageConverter enigmaMessageConverter = new EnigmaMessageConverter(new LoggingEnigmaCastListener(logger)) {
            @Override
            protected void onConversionFailed(CastDevice sender, String message, Exception exception) {
                exception.printStackTrace();
                Assert.fail("Failed to parse message: "+message);
            }
        };

        Assert.assertEquals(expectedLog.toString(), logger.getLog());
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'tracksupdated',");
            message.append("  data: {");
            message.append("    tracksInfo: {");
            message.append("      activeTrackIds: [0, 2],");
            message.append("      tracks: [");
            message.append("		{");
            message.append("			label: 'label',");
            message.append("			type: 'text',");
            message.append("			trackId: 0,");
            message.append("			language: 'sv',");
            message.append("			kind: 'subtitles'");
            message.append("		},");
            message.append("		{");
            message.append("			label: 'label2',");
            message.append("			type: 'text',");
            message.append("			trackId: 1,");
            message.append("			language: 'en',");
            message.append("			kind: 'subtitles'");
            message.append("		},");
            message.append("		{");
            message.append("			label: 'ljud',");
            message.append("			type: 'audio',");
            message.append("			trackId: 2,");
            message.append("			language: 'se',");
            message.append("			kind: 'audio'");
            message.append("		},");
            message.append("		{");
            message.append("			label: 'label3',");
            message.append("			type: 'text',");
            message.append("			trackId: 3,");
            message.append("			language: 'fr',");
            message.append("			kind: 'subtitles'");
            message.append("		},");
            message.append("		{");
            message.append("			label: 'audio',");
            message.append("			type: 'audio',");
            message.append("			trackId: 4,");
            message.append("			language: 'en',");
            message.append("			kind: 'audio'");
            message.append("		}");
            message.append("      ]");
            message.append("    }");
            message.append("  }");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onTracksUpdated(2,3)]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'timeShiftEnabled',");
            message.append("  data: true");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onTimeshiftEnabled(true)]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'volumechange',");
            message.append("  data: {");
            message.append("    volume: 0.5,");
            message.append("    muted: false");
            message.append("  }");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onVolumeChange(0.5,false)]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'startTimeLive',");
            message.append("  data: 8935345345");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onStartTimeLive(8935345345)]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'isLive',");
            message.append("  data: true");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onLive(true)]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'liveDelay',");
            message.append("  data: 2999");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onLiveDelay(2999.0)]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'autoplay',");
            message.append("  data: false");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onAutoplay(false)]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'programchanged',");
            message.append("  data: {");
            message.append("    program: {");
            message.append("      exampleData: 23");
            message.append("    }");
            message.append("  }");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onProgramChanged({\"exampleData\":23})]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'assetchanged',");
            message.append("  data: {");
            message.append("    asset: {");
            message.append("      assetId: 'theAssetId'");
            message.append("    }");
            message.append("  }");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onAssetChanged({\"assetId\":\"theAssetId\"})]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'entitlementchange',");
            message.append("  data: {");
            message.append("    entitlement: {");
            message.append("      entitlementData: 2834.5");
            message.append("    }");
            message.append("  }");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onEntitlementChange({\"entitlementData\":2834.5})]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'error',");
            message.append("  data: {");
            message.append("    type: 'TEST',");
            message.append("    code: 666,");
            message.append("    message: 'bread'");
            message.append("  }");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onError("+new ReceiverError(new JSONObject("{type: 'TEST', code: 666, message: 'bread'}"))+")]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'segmentmissing',");
            message.append("  data: 724535");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onSegmentMissing(724535)]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
        {
            StringBuilder message = new StringBuilder();
            message.append("{");
            message.append("  type: 'undocumentedtype',");
            message.append("  data: 'secret'");
            message.append("}");
            enigmaMessageConverter.onMessageReceived(null, "mockNamespace", message.toString());
            expectedLog.append("[onUnhandledMessageType(undocumentedtype,"+new JSONObject(message.toString()).toString()+")]");
            Assert.assertEquals(expectedLog.toString(), logger.getLog());
        }
    }

    private interface ILogger {
        ILogMessage start();
        void log(String text);
        interface ILogMessage {
            ILogMessage append(Object object);
            void end();
        }
    }

    private static class Logger implements ILogger {
        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public ILogMessage start() {
            return new LogMessage();
        }

        @Override
        public void log(String text) {
            stringBuilder.append("["+text+"]");
        }

        public String getLog() {
            return stringBuilder.toString();
        }

        private class LogMessage implements ILogMessage {
            private StringBuilder buffer = new StringBuilder();
            @Override
            public ILogMessage append(Object object) {
                buffer.append(object);
                return this;
            }

            @Override
            public void end() {
                log(buffer.toString());
            }
        }
    }

    private static class LoggingEnigmaCastListener implements IEnigmaCastListener {
        private final ILogger logger;

        public LoggingEnigmaCastListener(ILogger logger) {
            this.logger = logger;
        }

        private void logMethodCall(String methodName, Object ... arguments) {
            ILogger.ILogMessage logMessage = logger.start().append(methodName).append("(");

            boolean first = true;
            for(Object argument : arguments) {
                if(first) {
                    first = false;
                } else {
                    logMessage.append(",");
                }
                logMessage.append(argument);
            }

            logMessage.append(")").end();
        }

        @Override
        public void onTracksUpdated(List<MediaTrack> audioTracks, List<MediaTrack> subtitleTracks) {
            logMethodCall("onTracksUpdated", audioTracks.size(), subtitleTracks.size());
        }

        @Override
        public void onVolumeChange(float volume, boolean muted) {
            logMethodCall("onVolumeChange", volume, muted);
        }

        @Override
        public void onTimeshiftEnabled(boolean timeshiftEnabled) {
            logMethodCall("onTimeshiftEnabled", timeshiftEnabled);
        }

        @Override
        public void onAutoplay(boolean autoplay) {
            logMethodCall("onAutoplay", autoplay);
        }

        @Override
        public void onLive(boolean live) {
            logMethodCall("onLive", live);
        }

        @Override
        public void onProgramChanged(JSONObject program) {
            logMethodCall("onProgramChanged", program);
        }

        @Override
        public void onEntitlementChange(JSONObject entitlementJson) {
            logMethodCall("onEntitlementChange", entitlementJson);
        }

        @Override
        public void onAssetChanged(JSONObject asset) {
            logMethodCall("onAssetChanged", asset);
        }

        @Override
        public void onError(ReceiverError error) {
            logMethodCall("onError", error);
        }

        @Override
        public void onLiveDelay(float liveDelay) {
            logMethodCall("onLiveDelay", liveDelay);
        }

        @Override
        public void onStartTimeLive(long utcMillis) {
            logMethodCall("onStartTimeLive", utcMillis);
        }

        @Override
        public void onSegmentMissing(long position) {
            logMethodCall("onSegmentMissing", position);
        }

        @Override
        public void onUnhandledMessageType(String type, JSONObject message) {
            logMethodCall("onUnhandledMessageType", type, message);
        }

        @Override
        public final void _dont_implement_IEnigmaCastListener___instead_extend_BaseEnigmaCastListener_() {
            throw new UnsupportedOperationException();
        }
    }
}
