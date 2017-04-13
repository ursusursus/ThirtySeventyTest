package sk.ursus.thirtyseventytest.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by ursusursus on 12.4.2017.
 */

@JsonObject
@Accessors(prefix = "m")
public class Recipe extends RealmObject {
	@Getter @JsonField(name = "id") @PrimaryKey long mId;
	@Getter @JsonField(name = "title") String mTitle;
	@Getter RealmList<RecipeStep> mSteps = new RealmList<>();

	public void addRecipeStep(RecipeStep step) {
		mSteps.add(step);
	}
}
