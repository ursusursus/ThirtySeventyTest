package sk.ursus.thirtyseventytest.model.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * Created by ursusursus on 13.4.2017.
 */

@JsonObject
@Accessors(prefix = "m")
public abstract class Response<T> {
	@Getter @JsonField(name = "status") String mStatus;
	@Getter @JsonField(name = "error") Error mError;
	@Getter @JsonField(name = "data") T mData;
}
