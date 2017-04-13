package sk.ursus.thirtyseventytest.model.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Getter;
import lombok.experimental.Accessors;
import sk.ursus.thirtyseventytest.model.RecipeStep;

/**
 * Created by ursusursus on 12.4.2017.
 */

@JsonObject
@Accessors(prefix = "m")
public class RecipeStepsResponse extends Response<RecipeStepsResponse.Data> {

	@JsonObject
	@Accessors(prefix = "m")
	public static class Data {
		@Getter @JsonField(name = "recipe_steps") RecipeStep[] mRecipeSteps;
	}
}
