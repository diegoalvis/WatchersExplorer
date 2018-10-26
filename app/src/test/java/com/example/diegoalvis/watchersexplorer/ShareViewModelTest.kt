package com.example.diegoalvis.watchersexplorer

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import com.diegoalvis.android.newsapp.api.ApiInterface
import com.diegoalvis.android.newsapp.api.SearchResponse
import com.example.diegoalvis.watchersexplorer.models.Owner
import com.example.diegoalvis.watchersexplorer.models.Repo
import com.example.diegoalvis.watchersexplorer.utils.applyTestSchedulers
import com.example.diegoalvis.watchersexplorer.viewmodels.SharedViewModel
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mockito

class ShareViewModelTest {

    val apiInterface = mock<ApiInterface>()
    val observableBoolean = mock<ObservableBoolean>()
    val sharedViewModel = mock<SharedViewModel>()
    val repoData = mock<MutableLiveData<Repo>>()

    @Before
    fun setUp() {
        sharedViewModel.isLoading = observableBoolean
        sharedViewModel.apiInterface = apiInterface
    }

    @Test
    fun searchRepositories() {
        val searchResponse = Flowable.just(SearchResponse(1, mutableListOf()))
        given(apiInterface.searchRepos("android")).willReturn(searchResponse)

        whenever(sharedViewModel.searchRepositories("android").applyTestSchedulers()).thenReturn(searchResponse)
    }

    @Test
    fun getRepositoryWatchers() {
        sharedViewModel.selected = repoData
        given(repoData.value).willReturn(repoTest)

        val watcherResponse = Flowable.just(mutableListOf<Owner>())
        given(apiInterface.getWatchers(repoTest.owner.login, repoTest.name)).willReturn(watcherResponse)

        whenever(sharedViewModel.getWatchers()?.applyTestSchedulers()).thenReturn(watcherResponse)
    }

    @Test
    fun verifyLoaderVisibility() {
        sharedViewModel.selected = repoData
        given(repoData.value).willReturn(repoTest)
        given(apiInterface.getWatchers(repoTest.owner.login, repoTest.name)).willReturn(Flowable.just(mutableListOf()))
        given(apiInterface.searchRepos("android")).willReturn(Flowable.just(SearchResponse(1, mutableListOf())))

        sharedViewModel.getWatchers()?.applyTestSchedulers()
        sharedViewModel.searchRepositories("android").applyTestSchedulers()

        // verify loader visibility
        verify(sharedViewModel.isLoading, Mockito.times(2)).set(true)
    }

    // Testing classes
    private val ownerTest = Owner("test", "www.test.com", "www.test.com")
    private val repoTest = Repo("android", "www.example.com", ownerTest, "description", "kotlin",100)
}
