package sk.ursus.thirtyseventytest.model;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import sk.ursus.thirtyseventytest.model.response.RecipeIdWrapper;

/**
 * Created by ursusursus on 12.4.2017.
 */

@JsonObject
@Accessors(prefix = "m")
public class RecipeStep extends RealmObject {
	@Getter @JsonField(name = "id") @PrimaryKey long mId;
	@Getter @JsonField(name = "description") String mDescription;
	@Getter @JsonField(name = "recipe") @Ignore RecipeIdWrapper mRecipeIdWrapper; // TODO: toto sa neda skipnut nejako jak v SimpleXML?
	@Getter @Setter Recipe mRecipe;
}
