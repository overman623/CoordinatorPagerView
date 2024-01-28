package com.overman.coordinator.viewpager

import android.util.Log

internal object ALog {

    private val tag = "CoordinatorViewPager"

    var available: Boolean = false

    fun i(tag: String = ALog.tag, trace: () -> String) = let {
        if (!available) {
            return@let
        }
        Log.i(tag, trace())
    }
    fun d(tag: String = ALog.tag, trace: () -> String) = let {
        if (!available) {
            return@let
        }
        Log.d(tag, trace())
    }
    fun v(tag: String = ALog.tag, trace: () -> String) = let {
        if (!available) {
            return@let
        }
        Log.v(tag, trace())
    }
    fun w(tag: String = ALog.tag, trace: () -> String) = let {
        if (!available) {
            return@let
        }
        Log.w(tag, trace())
    }
    fun e(tag: String = ALog.tag, trace: () -> String) = let {
        if (!available) {
            return@let
        }
        Log.e(tag, trace())
    }
}