package com.devcomentry.photogallery.presention.common

import android.os.Bundle

internal interface Initialization {
    /**
     * override this function and fetch data or init variables at here.
     * This function called at onCreate() (Activity) and onCreateView() (Fragment).
     */
    fun initControls(savedInstanceState: Bundle?) {}

    /**
     * override this function and set on click, long click, view event ... at here.
     * This function called at onCreate() (Activity) and onCreateView() (Fragment).
     */
    fun initEvents() {}
}