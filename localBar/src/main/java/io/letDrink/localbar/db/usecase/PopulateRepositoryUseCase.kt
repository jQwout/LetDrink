package io.letDrink.localbar.db.usecase

import androidx.annotation.WorkerThread
import io.letDrink.localbar.db.repository.CocktailRepository
import io.letDrink.localbar.db.storage.CocktailsLocalStorage

class PopulateRepositoryUseCase(
    val cocktailRepository: CocktailRepository,
    val localStorage: CocktailsLocalStorage
) {

    @WorkerThread
    suspend fun populate() {
        cocktailRepository.addAll(localStorage.get())
    }
}