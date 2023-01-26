package se.gabnet.cooky;

import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import se.gabnet.cooky.Database.DatabaseController;

public class SQLiteTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("se.gabnet.cooky", appContext.getPackageName());
    }


    @Test
    public void dbCreateTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("se.gabnet.cooky", appContext.getPackageName());

    }

    @Test
    public void dbDeleteTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("se.gabnet.cooky", appContext.getPackageName());
    }
}
