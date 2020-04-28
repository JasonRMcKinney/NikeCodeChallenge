package com.example.nikecodechallenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nikecodechallenge.model.DescriptionResponse
import com.example.nikecodechallenge.viewmodel.UrbanDictionaryViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ViewModelUnitTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var viewModel: UrbanDictionaryViewModel
    var response: DescriptionResponse? = null

    @Mock
    lateinit var mockResponse: DescriptionResponse

    @Mock
    lateinit var observer: Observer<String>

    @Mock
    lateinit var successObserver: Observer<DescriptionResponse>


    @Test
    fun errorObservableChanged() {
        viewModel.getUdDescriptionError().observeForever(observer)
        viewModel.updateObservable(response)

        verify(observer).onChanged("There was an Error, Try again later")
    }

    @Test
    fun sucessObservableChanged() {
        viewModel.getUdDescription().observeForever(successObserver)
        viewModel.updateObservable(mockResponse)

        verify(successObserver).onChanged(mockResponse)
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = UrbanDictionaryViewModel()
    }
}
