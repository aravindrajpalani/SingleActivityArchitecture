package me.aravindraj.androidfragments.features.base


open class BasePresenter<T : MvpView> : Presenter<T> {

    var mvpView: T? = null


    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
    }

    private val isViewAttached: Boolean
        get() = mvpView != null


    private class MvpViewNotAttachedException internal constructor() : RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")

}

