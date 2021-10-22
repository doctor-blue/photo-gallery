package com.devcomentry.photogallery.presention.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController


abstract class BaseFragment<T : ViewDataBinding> constructor(
    @LayoutRes val layoutId: Int
) : Fragment(), Initialization {

    val localDataViewModel: LocalDataViewModel by activityViewModels()
    lateinit var navController: NavController

    /**
     * [DataBindingComponent] is generatved during compilation
     */
    protected var bindingComponent: DataBindingComponent? = DataBindingUtil.getDefaultComponent()

    /** A backing field for providing an immutable [binding] property.  */
    private var _binding: T? = null

    /**
     * A data-binding property will be initialized in [onCreateView].
     * And provide the inflated view which depends on [layoutId].
     */
    val binding: T
        get() = checkNotNull(_binding) {
            "Fragment $this binding cannot be accessed before onCreateView() or after onDestroyView()"
        }

    inline fun binding(block: T.() -> Unit): T {
        return binding.apply(block)
    }

    /**
     * Inflate [layoutId]
     */
    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false, bindingComponent)
        navController = findNavController()
        initControls(savedInstanceState)
        initEvents()
        return binding.root
    }

    /**
     * Unbind
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.unbind()
        _binding = null
    }

    fun safeNav(currentDestination: Int, action: Int) {
        if (navController.currentDestination?.id == currentDestination) {
            lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_RESUME) {
                        lifecycle.removeObserver(this)
                        try {
                            navController.navigate(action)
                        } catch (e: IllegalArgumentException) {
//                            Log.e(TAG, "safeNav: ${e.message}")
                        }
                    }
                }
            })
        }
    }

    fun safeNav(currentDestination: Int, navDirections: NavDirections) {
        if (navController.currentDestination?.id == currentDestination) {
            lifecycle.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_RESUME) {
                        lifecycle.removeObserver(this)
                        try {
                            navController.navigate(navDirections)
                        } catch (e: IllegalArgumentException) {
//                            Log.e(TAG, "safeNav: ${e.message}")
                        }
                    }
                }
            })
        }
    }
}