package io.letDrink.localbar.db.entity

import androidx.room.*

@Entity
data class Ingredient(
    @PrimaryKey
    val ingredient: String,
    val measure: String,
    val quantity: String
) {
    val count get() = measure + quantity
}

@Entity(primaryKeys = ["name", "ingredient"])
class CocktailIngredientCrossRef(
    val name: String,
    val ingredient: String
)

data class CocktailWithIngredients(
    @Embedded val cocktail: Cocktail,
    @Relation(
        parentColumn = "name",
        entityColumn = "ingredient",
        associateBy = Junction(CocktailIngredientCrossRef::class)
    )
    val ingredients: List<Ingredient>
)

data class IngredientsWithCocktails(
    @Embedded val ingredient: Ingredient,
    @Relation(
        parentColumn = "ingredient",
        entityColumn = "name",
        associateBy = Junction(CocktailIngredientCrossRef::class)
    )
    val cocktails: List<Cocktail>
)