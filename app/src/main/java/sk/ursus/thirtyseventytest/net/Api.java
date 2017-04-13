package sk.ursus.thirtyseventytest.net;

import io.reactivex.Single;
import retrofit2.http.GET;
import sk.ursus.thirtyseventytest.model.response.RecipeResponse;
import sk.ursus.thirtyseventytest.model.response.RecipeStepsResponse;

/**
 * Created by ursusursus on 12.4.2017.
 */

interface Api {

	@GET("recipes")
	Single<RecipeResponse> recipes();

	@GET("recipe_steps")
	Single<RecipeStepsResponse> recipeSteps();
}
