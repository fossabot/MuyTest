package com.muy

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.muy.data.DataSource
import com.muy.data.dto.EmployeerResponse
import com.muy.data.dto.EmployeesItem
import com.muy.data.service.OperationCallback
import com.muy.ui.fragments.AllEmployeesViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class MVVMUnitTest {

    @Mock
    private lateinit var repository: DataSource
    @Mock private lateinit var context: Application

    @Captor
    private lateinit var operationCallbackCaptor: ArgumentCaptor<OperationCallback<EmployeerResponse>>

    private lateinit var viewModel: AllEmployeesViewModel

    private lateinit var isViewLoadingObserver:Observer<Boolean>
    private lateinit var onMessageErrorObserver:Observer<Any>
    private lateinit var emptyListObserver:Observer<Boolean>
    private lateinit var onRenderEmployeeObserver:Observer<List<EmployeesItem>>

    private lateinit var employeerList:EmployeerResponse
    private lateinit var employeerEmpty:EmployeerResponse

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)

        viewModel= AllEmployeesViewModel(repository)

        mockData()
        setupObservers()
    }

    @Test
    fun `retrieve eployees with ViewModel and Repository returns empty data`(){
        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            isEmptyList.observeForever(emptyListObserver)
            employeers.observeForever(onRenderEmployeeObserver)
        }

        verify(repository, times(1)).retrieveEmployeers(
            capture(
                operationCallbackCaptor
            )
        )
        operationCallbackCaptor.value.onSuccess(employeerList)

        Assert.assertNull(viewModel.isEmptyList.value)

    }

    @Test
    fun `retrieve museums with ViewModel and Repository returns full data`(){
        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            employeers.observeForever(onRenderEmployeeObserver)
        }

        verify(repository, times(1)).retrieveEmployeers(
            capture(
                operationCallbackCaptor
            )
        )
        operationCallbackCaptor.value.onSuccess(employeerList)

        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertTrue(viewModel.employeers.value?.size!! > 0)
    }

    @Test
    fun `retrieve employee with ViewModel and Repository returns an error`(){
        with(viewModel){
            loadMuseums()
            isViewLoading.observeForever(isViewLoadingObserver)
            onMessageError.observeForever(onMessageErrorObserver)
        }
        verify(repository, times(1)).retrieveEmployeers(
            capture(
                operationCallbackCaptor
            )
        )
        operationCallbackCaptor.value.onError("An error occurred")
        Assert.assertNotNull(viewModel.isViewLoading.value)
        Assert.assertNotNull(viewModel.onMessageError.value)
    }

    private fun setupObservers(){
        isViewLoadingObserver= mock(Observer::class.java) as Observer<Boolean>
        onMessageErrorObserver= mock(Observer::class.java) as Observer<Any>
        emptyListObserver= mock(Observer::class.java) as Observer<Boolean>
        onRenderEmployeeObserver= mock(Observer::class.java)as Observer<List<EmployeesItem>>
    }

    private fun mockData(){
        val json = "{\n" +
                "\"company_name\": \"los yoyos\",\n" +
                "\"address\": \"Carrera 123 # 45 - 67. Oficina: 89\",\n" +
                "\"employees\":[\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"Juanpis Gonzalez\",\n" +
                "      \"position\": \"CEO\",\n" +
                "      \"wage\": 4500000000,\n" +
                "      \"employees\": [\n" +
                "        {\n" +
                "          \"id\": 90,\n" +
                "          \"name\": \"Pepito Pérez\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 45,\n" +
                "          \"name\": \"El capo\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 555,\n" +
                "          \"name\": \"Carlangas\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 90,\n" +
                "      \"name\": \"Pepito Pérez\",\n" +
                "      \"position\": \"CFO\",\n" +
                "      \"wage\": 1000,\n" +
                "      \"employees\": [\n" +
                "        {\n" +
                "          \"id\": 754,\n" +
                "          \"name\": \"El profesor súper O\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 9875,\n" +
                "          \"name\": \"El rano rené\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 754,\n" +
                "      \"name\": \"El profesor súper O\",\n" +
                "      \"position\": \"Financiero asociado\",\n" +
                "      \"wage\": 23,\n" +
                "      \"employees\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 9875,\n" +
                "      \"name\": \"El rano rené\",\n" +
                "      \"position\": \"Contador\",\n" +
                "      \"wage\": 1,\n" +
                "      \"employees\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 555,\n" +
                "      \"name\": \"Carlangas\",\n" +
                "      \"position\": \"CTO\",\n" +
                "      \"wage\": 123456789,\n" +
                "      \"employees\": [\n" +
                "        {\n" +
                "          \"id\": 123354,\n" +
                "          \"name\": \"The viking\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 1234521,\n" +
                "          \"name\": \"Il Puccino Pio\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 123354,\n" +
                "      \"name\": \"The viking\",\n" +
                "      \"position\": \"Flutter developer\",\n" +
                "      \"wage\": 546,\n" +
                "      \"employees\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 1234521,\n" +
                "      \"name\": \"Il Puccino Pio\",\n" +
                "      \"position\": \"Android developer\",\n" +
                "      \"wage\": 321,\n" +
                "      \"employees\": []\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 45,\n" +
                "      \"name\": \"El capo\",\n" +
                "      \"position\": \"CPO\",\n" +
                "      \"wage\": 852,\n" +
                "      \"employees\": [\n" +
                "        {\n" +
                "          \"id\": 123123,\n" +
                "          \"name\": \"Nombre que hace notar que los papas no lo quisieron porque lo nombraron así\"\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 123123,\n" +
                "      \"name\": \"Nombre que hace notar que los papas no lo quisieron porque lo nombraron así\",\n" +
                "      \"position\": \"Todero\",\n" +
                "      \"wage\": 963,\n" +
                "      \"employees\": []\n" +
                "    }\n" +
                "  ]\n" +
                "}"

        var gson = GsonBuilder().setPrettyPrinting().create()
        val listPersonType = object : TypeToken<EmployeerResponse>() {}.type

        val data: EmployeerResponse = gson.fromJson(json,listPersonType)

        employeerList = data

    }
}