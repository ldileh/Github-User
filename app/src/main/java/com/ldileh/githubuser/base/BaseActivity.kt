package com.ldileh.githubuser.base

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.ldileh.githubuser.utils.collectFlow
import com.ldileh.githubuser.utils.defaultScreenConfiguration
import timber.log.Timber
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB: ViewBinding, VM: BaseViewModel> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = inflateBinding()
            .also { setContentView(it.root) }
            .also { it.root.defaultScreenConfiguration() }
            .also { it.setupView() }
    }

    protected fun VM.observeDefault(){
        messageError.collectFlow(this@BaseActivity){ message ->
            if (message != null){
                Toast.makeText(
                    this@BaseActivity,
                    message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    abstract fun VB.setupView()

    @Suppress("UNCHECKED_CAST")
    private fun inflateBinding(): VB {
        val method: Method = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments
            ?.get(0)
            ?.let { it as Class<VB> }
            ?.getMethod("inflate", LayoutInflater::class.java)
            ?: throw IllegalArgumentException("ViewBinding class not found")

        return method.invoke(null, layoutInflater) as VB
    }
}