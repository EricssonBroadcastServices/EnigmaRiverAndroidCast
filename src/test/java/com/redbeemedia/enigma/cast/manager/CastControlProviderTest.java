package com.redbeemedia.enigma.cast.manager;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CastControlProviderTest {
    @Test
    public void testPullMessage() throws Exception {
        final List<JSONObject> sentMessages = new ArrayList<>();
        CastControlProvider castControlProvider = new CastControlProvider() {
            @Override
            protected void sendMessage(JSONObject jsonObject) throws Exception {
                sentMessages.add(jsonObject);
            }
        };

        castControlProvider.pull();
        Assert.assertEquals(1, sentMessages.size());
        JSONObject expected = new JSONObject("{type: 'pull', data: {}}");
        assertJsonEquals(expected, sentMessages.get(0));
    }

    @Test
    public void testShowTextTrack() throws Exception {
        final List<JSONObject> sentMessages = new ArrayList<>();
        CastControlProvider castControlProvider = new CastControlProvider() {
            @Override
            protected void sendMessage(JSONObject jsonObject) throws Exception {
                sentMessages.add(jsonObject);
            }
        };

        {
            castControlProvider.showTextTrack("mockLang", null);
            Assert.assertEquals(1, sentMessages.size());
            JSONObject expected = new JSONObject("{type: 'showtexttrack', data: {language: 'mockLang'}}");
            assertJsonEquals(expected, sentMessages.get(0));
        }

        {
            castControlProvider.showTextTrack("mockLang", "not_so_kind");
            Assert.assertEquals(2, sentMessages.size());
            JSONObject expected = new JSONObject("{type: 'showtexttrack', data: {language: 'mockLang', kind: 'not_so_kind'}}");
            assertJsonEquals(expected, sentMessages.get(1));
        }
    }

    @Test
    public void testHideTextTrack() throws Exception {
        final List<JSONObject> sentMessages = new ArrayList<>();
        CastControlProvider castControlProvider = new CastControlProvider() {
            @Override
            protected void sendMessage(JSONObject jsonObject) throws Exception {
                sentMessages.add(jsonObject);
            }
        };

        castControlProvider.hideTextTrack();
        Assert.assertEquals(1, sentMessages.size());
        JSONObject expected = new JSONObject("{type: 'hidetexttrack'}");
        assertJsonEquals(expected, sentMessages.get(0));
    }

    @Test
    public void testSelectAudioTrack() throws Exception {
        final List<JSONObject> sentMessages = new ArrayList<>();
        CastControlProvider castControlProvider = new CastControlProvider() {
            @Override
            protected void sendMessage(JSONObject jsonObject) throws Exception {
                sentMessages.add(jsonObject);
            }
        };

        {
            castControlProvider.selectAudioTrack("java", null);
            Assert.assertEquals(1, sentMessages.size());
            JSONObject expected = new JSONObject("{type: 'selectaudiotrack', data: {language: 'java'}}");
            assertJsonEquals(expected, sentMessages.get(0));
        }

        {
            castControlProvider.selectAudioTrack("kotlin", "canary");
            Assert.assertEquals(2, sentMessages.size());
            JSONObject expected = new JSONObject("{type: 'selectaudiotrack', data: {language: 'kotlin', kind: 'canary'}}");
            assertJsonEquals(expected, sentMessages.get(1));
        }
    }

    @Test
    public void testSetPlayheadTime() throws Exception {
        final List<JSONObject> sentMessages = new ArrayList<>();
        CastControlProvider castControlProvider = new CastControlProvider() {
            @Override
            protected void sendMessage(JSONObject jsonObject) throws Exception {
                sentMessages.add(jsonObject);
            }
        };

        castControlProvider.setPlayaheadTime(1020304050607L);
        Assert.assertEquals(1, sentMessages.size());
        JSONObject expected = new JSONObject("{type: 'playheadtime', data: 1020304050607}");
        assertJsonEquals(expected, sentMessages.get(0));
    }

    @Test
    public void testPlayNextProgram() throws Exception {
        final List<JSONObject> sentMessages = new ArrayList<>();
        CastControlProvider castControlProvider = new CastControlProvider() {
            @Override
            protected void sendMessage(JSONObject jsonObject) throws Exception {
                sentMessages.add(jsonObject);
            }
        };

        castControlProvider.playNextProgram();
        Assert.assertEquals(1, sentMessages.size());
        JSONObject expected = new JSONObject("{type: 'playnextprogram'}");
        assertJsonEquals(expected, sentMessages.get(0));
    }

    @Test
    public void testPlayPreviousProgram() throws Exception {
        final List<JSONObject> sentMessages = new ArrayList<>();
        CastControlProvider castControlProvider = new CastControlProvider() {
            @Override
            protected void sendMessage(JSONObject jsonObject) throws Exception {
                sentMessages.add(jsonObject);
            }
        };

        {
            castControlProvider.playPreviousProgram(false);
            Assert.assertEquals(1, sentMessages.size());
            JSONObject expected = new JSONObject("{type: 'playpreviousprogram', data: {end: false}}");
            assertJsonEquals(expected, sentMessages.get(0));
        }

        {
            castControlProvider.playPreviousProgram(true);
            Assert.assertEquals(2, sentMessages.size());
            JSONObject expected = new JSONObject("{type: 'playpreviousprogram', data: {end: true}}");
            assertJsonEquals(expected, sentMessages.get(1));
        }
    }

    @Test
    public void testGoToLive() throws Exception {
        final List<JSONObject> sentMessages = new ArrayList<>();
        CastControlProvider castControlProvider = new CastControlProvider() {
            @Override
            protected void sendMessage(JSONObject jsonObject) throws Exception {
                sentMessages.add(jsonObject);
            }
        };

        castControlProvider.goToLive();
        Assert.assertEquals(1, sentMessages.size());
        JSONObject expected = new JSONObject("{type: 'gotolive'}");
        assertJsonEquals(expected, sentMessages.get(0));
    }

    @Test
    public void testLoadNextSource() throws Exception {
        final List<JSONObject> sentMessages = new ArrayList<>();
        CastControlProvider castControlProvider = new CastControlProvider() {
            @Override
            protected void sendMessage(JSONObject jsonObject) throws Exception {
                sentMessages.add(jsonObject);
            }
        };

        castControlProvider.loadNextSource();
        Assert.assertEquals(1, sentMessages.size());
        JSONObject expected = new JSONObject("{type: 'loadnextsource'}");
        assertJsonEquals(expected, sentMessages.get(0));
    }

    private static void assertJsonEquals(JSONObject expected, JSONObject actual) {
        Assert.assertEquals(expected.toString(), actual.toString());
    }
}
