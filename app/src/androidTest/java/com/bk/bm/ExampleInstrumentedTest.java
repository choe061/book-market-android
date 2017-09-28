package com.bk.bm;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DaggerBaseLayerComponent;
import android.support.test.espresso.base.BaseLayerModule;
import android.support.test.runner.AndroidJUnit4;

import com.bk.bm.model.repository.api.BookService;
import com.bk.bm.util.Constants;
import com.bk.bm.util.di.AppComponent;
import com.bk.bm.util.di.DaggerAppComponent;
import com.bk.bm.util.di.modules.AppModule;
import com.bk.bm.util.di.modules.NetModule;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Inject BookService bookService;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.bk.bm", appContext.getPackageName());
    }
}
