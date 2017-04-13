package sk.ursus.thirtyseventytest.net;

/**
 * Created by ursusursus on 13.4.2017.
 */

public class ApiErrorException extends IllegalStateException {
	public ApiErrorException(String code, String message) {
		super(code + "; " + message);
	}
}
