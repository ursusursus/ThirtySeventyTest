package sk.ursus.thirtyseventytest.net;

import com.github.aurae.retrofit2.LoganSquareConverterFactory;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import sk.ursus.thirtyseventytest.BuildConfig;
import sk.ursus.thirtyseventytest.model.Recipe;
import sk.ursus.thirtyseventytest.model.RecipeStep;
import sk.ursus.thirtyseventytest.model.response.Error;
import sk.ursus.thirtyseventytest.model.response.Response;

/**
 * Created by ursusursus on 12.4.2017.
 */

public class RestClient {

	private static final String BASE_URL = "http://aeg-starter-kit-dev.herokuapp.com/api/v3/";
	private static final String ACCESS_TOKEN = "2aef4afa228e26ca80919e488b5caf7c";

	private final Api mApi;

	public RestClient() {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
						.addHeader("access_token", ACCESS_TOKEN)
						.build()))
				.addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ?
						HttpLoggingInterceptor.Level.BODY :
						HttpLoggingInterceptor.Level.NONE))
				.build();

		Retrofit retrofit = new Retrofit.Builder()
				.baseUrl(BASE_URL)
				.client(okHttpClient)
				.addConverterFactory(LoganSquareConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();

		mApi = retrofit.create(Api.class);
	}

	public Single<Recipe[]> recipes() {
		return mApi.recipes()
				.compose(defaultChecks())
				.flatMap(response -> Single.just(response.getData().getRecipes()));
	}

	public Single<RecipeStep[]> recipeSteps() {
		return mApi.recipeSteps()
				.compose(defaultChecks())
				.flatMap(response -> Single.just(response.getData().getRecipeSteps()));
	}

	private <T extends Response> SingleTransformer<T, T> defaultChecks() {
		return single -> single.doOnSuccess(response -> {
			if (!response.getStatus().equalsIgnoreCase("ok")) {
				final Error error = response.getError();
				if (error != null) {
					throw error.toException();
				}
				throw new IllegalStateException("status not OK and error field is null");
			}
			if (response.getData() == null) {
				throw new IllegalStateException("api data field is null");
			}
		});
	}
}
