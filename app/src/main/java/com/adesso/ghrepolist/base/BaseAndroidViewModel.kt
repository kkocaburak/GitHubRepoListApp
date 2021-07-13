package com.adesso.ghrepolist.base

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import com.adesso.ghrepolist.R
import com.adesso.ghrepolist.internal.popup.PopupModel
import com.adesso.ghrepolist.internal.util.Event
import com.adesso.ghrepolist.internal.util.Failure
import com.adesso.ghrepolist.navigation.NavigationCommand

abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

    private val _failurePopup = MutableLiveData<Event<PopupModel>>()
    val failurePopup: LiveData<Event<PopupModel>> get() = _failurePopup

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation

    protected open fun handleFailure(failure: Failure) {
        val message = failure.localizedMessage ?: getString(R.string.common_error)
        navigate(PopupModel(message = message))
    }

    fun navigate(directions: NavDirections) {
        _navigation.value = Event(NavigationCommand.ToDirection(directions))
    }

    fun navigate(popupModel: PopupModel) {
        _failurePopup.value = Event(popupModel)
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }

    private fun getString(@StringRes resId: Int): String {
        return getApplication<Application>().getString(resId)
    }
}
