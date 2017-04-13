package sk.ursus.thirtyseventytest.model.response;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import lombok.Getter;
import lombok.experimental.Accessors;
import sk.ursus.thirtyseventytest.net.ApiErrorException;

/**
 * Created by ursusursus on 13.4.2017.
 */

@JsonObject
@Accessors(prefix = "m")
public class Error {
	@Getter @JsonField(name = "code") String mCode;
	@Getter @JsonField(name = "message") String mMessage;

	public ApiErrorException toException() {
		return new ApiErrorException(mCode, mMessage);
	}
}
