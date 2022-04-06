package com.eugenics.bloodpressuremonitor.ui.common.context

import android.content.Context

object ContextProviderFactory {
    fun build(context: Context) = ContextProvider(context)
}