package com.nolawiworkineh.pacepal

import androidx.lifecycle.ViewModel
import com.nolawiworkineh.core.domain.SessionStorage

//let's go to the app module and have a view model that is bound to the activity.
       // in here we will have the logic to check if we are already authenticated

class MainViewModel (
    private val sessionStorage : SessionStorage
) : ViewModel() {

}