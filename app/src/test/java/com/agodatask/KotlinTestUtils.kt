package com.agodatask

import org.mockito.Mockito

/**
 * Created by Sid on 28/06/2018.
 */

class KotlinTestUtils {

    companion object {
        /**
         * As Kotlin doesn't accept null values and when we use any() it supplies null so to overcome null we need to do this
         */
        public fun <T> any(): T {
            Mockito.any<T>()
            return uninitialized()
        }

        private fun <T> uninitialized(): T = null as T
    }
}