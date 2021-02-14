package io.letdrink.section.search.featured

import com.google.gson.reflect.TypeToken
import io.letdrink.common.DataFlowEvent
import io.letdrink.common.DataFlowRepository
import io.letdrink.common.DataFlowSource
import io.letdrink.common.Response
import io.letdrink.common.storage.FilesStorage
import io.letdrink.common.utils.TimeUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val FEATURED_NAME = "featured.json"

class FeaturedRepository @Inject constructor(
    private val api: FeaturedApi,
    private val filesStorage: FilesStorage
) : DataFlowRepository<List<CategoryModel>>() {

    private val jsonToken = TypeToken.getParameterized(List::class.java, CategoryModel::class.java).type

    override fun get(response: Response?): Flow<DataFlowEvent<out List<CategoryModel>>> {
        return flow<DataFlowEvent<out List<CategoryModel>>> {
            try {
                emit(DataFlowEvent.Loading(DataFlowSource.LOCAL))
                if (TimeUtils.isMoreThan24HAgo(filesStorage.getFromCacheAge(FEATURED_NAME))) {
                    loadRemote()
                } else {
                    val local =
                        filesStorage.getFromCache<List<CategoryModel>>(FEATURED_NAME, jsonToken)
                    if (local.isNullOrEmpty()) {
                        loadRemote()
                    } else {
                        emit(
                            DataFlowEvent.Content(
                                DataFlowSource.LOCAL, local
                            )
                        )
                    }
                }
            } catch (e: Throwable) {
                emit(
                    DataFlowEvent.Content(
                        DataFlowSource.LOCAL,
                        filesStorage.getFromAsset(FEATURED_NAME, jsonToken)
                    )
                )
            }
        }
    }

    private suspend fun FlowCollector<DataFlowEvent<out List<CategoryModel>>>.loadRemote() {
        emit(DataFlowEvent.Loading(DataFlowSource.REMOTE))
        val remoteList = api.getListCategories()
        filesStorage.putToCache(FEATURED_NAME, remoteList)
        emit(DataFlowEvent.Content(DataFlowSource.REMOTE, remoteList))
    }

    override fun put(data: List<CategoryModel>) {
        filesStorage.putToCache(FEATURED_NAME, data)
    }
}