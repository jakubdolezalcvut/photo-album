package com.dolezal.album

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import java.io.InputStream

@GlideModule
class DefaultGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val factory = InternalCacheDiskCacheFactory(context, DISK_CACHE_SIZE)
        builder.setDiskCache(factory)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()

        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(client)
        )
    }

    companion object {
        val DISK_CACHE_SIZE = 1024 * 1024 * 10L // 10 MB
    }
}