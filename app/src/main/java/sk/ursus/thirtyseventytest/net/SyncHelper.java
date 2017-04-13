package sk.ursus.thirtyseventytest.net;

import android.annotation.SuppressLint;

import org.javatuples.Pair;

import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import sk.ursus.thirtyseventytest.model.Recipe;
import sk.ursus.thirtyseventytest.model.RecipeStep;
import sk.ursus.thirtyseventytest.model.response.RecipeIdWrapper;

/**
 * Created by ursusursus on 12.4.2017.
 */

public class SyncHelper {

	private RestClient mRestClient;

	public SyncHelper(RestClient restClient) {
		mRestClient = restClient;
	}

	public Completable sync() {
		return Single.zip(
				mRestClient.recipes().subscribeOn(Schedulers.io()),
				mRestClient.recipeSteps().subscribeOn(Schedulers.io()),
				Pair::with)
				.map(this::mapRecipeStepsToRecipes)
				.doOnSuccess(this::saveToDb)
				.toCompletable();
	}

	@android.support.annotation.NonNull
	private Recipe[] mapRecipeStepsToRecipes(Pair<Recipe[], RecipeStep[]> recipeAndRecipeStepsPair) {
		Recipe[] recipes = recipeAndRecipeStepsPair.getValue0();
		if (recipes == null) recipes = new Recipe[0];

		RecipeStep[] recipeSteps = recipeAndRecipeStepsPair.getValue1();
		if (recipeSteps == null) recipeSteps = new RecipeStep[0];

		HashMap<Long, Recipe> recipesMap = new HashMap<>();
		for (int i = 0; i < recipes.length; i++) {
			final Recipe r = recipes[i];
			recipesMap.put(r.getId(), r);
		}

		// Iterate all recipe steps and map them their recipe parent
		// and also add a backlink
		for (int i = 0; i < recipeSteps.length; i++) {
			final RecipeStep recipeStep = recipeSteps[i];
			RecipeIdWrapper riw = recipeStep.getRecipeIdWrapper();
			if (riw != null) {
				Recipe recipe = recipesMap.get(riw.getId());
				if (recipe != null) {
					recipe.addRecipeStep(recipeStep);
					recipeStep.setRecipe(recipe);
				}
			}
		}
		return recipes;
	}

	@SuppressLint("NewApi")
	private void saveToDb(Recipe[] recipes) {
		try (Realm realm = Realm.getDefaultInstance()) {
			for (int i = 0; i < recipes.length; i++) {
				int ii = i;
				realm.executeTransaction(realm1 -> {
					realm1.copyToRealm(recipes[ii]);
				});
			}
		}
	}
}
