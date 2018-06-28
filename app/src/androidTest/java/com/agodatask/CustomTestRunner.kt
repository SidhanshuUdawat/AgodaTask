package com.agodatask

import android.app.Application
import android.content.Context
import io.appflate.restmock.android.RESTMockTestRunner

/**
 * Created by Sid on 28/06/2018.
 */

class CustomTestRunner : RESTMockTestRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader,
                                className: String,
                                context: Context): Application {

        // Needed to initiate with RESTMock's url.
        val testApplicationClassName = TestApplication::class.java.canonicalName
        return super.newApplication(cl, testApplicationClassName, context)
    }
}