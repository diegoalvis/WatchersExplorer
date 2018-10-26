package com.example.diegoalvis.watchersexplorer

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import org.mockito.Mockito
import org.mockito.stubbing.OngoingStubbing

inline fun <reified T> mock() = Mockito.mock(T::class.java)
inline fun <T> whenever(methodCall: T) : OngoingStubbing<T> = Mockito.`when`(methodCall)
fun <T> LiveData<T>.testObserver() = TestObserver<T>().also { observeForever(it) }


class TestObserver<T> : Observer<T> {
    val observedValues = mutableListOf<T?>()
    override fun onChanged(value: T?) {
        observedValues.add(value)
    }
}