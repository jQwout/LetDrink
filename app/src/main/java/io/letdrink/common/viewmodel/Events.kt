package io.letdrink.common.viewmodel

sealed class VmEvents {

    override fun equals(other: Any?): Boolean {
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    class Loading(val isLoading: Boolean) : VmEvents()

    class Fail(val t: Throwable) : VmEvents()
}