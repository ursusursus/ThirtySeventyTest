package sk.ursus.thirtyseventytest.model.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by ursusursus on 12.4.2017.
 */

@JsonObject
@Accessors(prefix = "m")
public class RecipeIdWrapper {
	@Getter @JsonField(name = "id") long mId;
}
