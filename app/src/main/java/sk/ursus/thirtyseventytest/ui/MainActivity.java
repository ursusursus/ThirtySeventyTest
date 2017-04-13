package sk.ursus.thirtyseventytest.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.realm.Realm;
import sk.ursus.thirtyseventytest.Injection;
import sk.ursus.thirtyseventytest.R;
import sk.ursus.thirtyseventytest.model.Recipe;
import sk.ursus.thirtyseventytest.net.SyncHelper;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.recipeTitleTextView) TextView mTitleTextView;
	@BindView(R.id.recipeStepTextView) TextView mStepTextView;

	private SyncHelper mSyncHelper;
	private Realm mRealm;
	private Recipe mRecipe;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		mSyncHelper = Injection.provideSyncHelper();
		mRealm = Injection.provideRealm();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		mRecipe = loadRecipe();
		if (mRecipe == null) {
			fetchRecipes();
		} else {
			displayRecipe(mRecipe);
		}
	}

	private Recipe loadRecipe() {
		return mRealm.where(Recipe.class).findFirst();
	}

	private void displayRecipe(Recipe recipe) {
		if (recipe == null) return;
		mTitleTextView.setText(recipe.getTitle());
		mStepTextView.setText(!recipe.getSteps().isEmpty() ?
				recipe.getSteps().first().getDescription() :
				"This recipe has no steps");
	}

	private void fetchRecipes() {
		// TODO: rotacie poriesit
		mSyncHelper.sync()
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(() -> {
					mRecipe = loadRecipe();
					displayRecipe(mRecipe);
				}, t -> {
					Log.e("Default", t.getMessage(), t);
					Toast.makeText(this, toErrorMessage(t), Toast.LENGTH_SHORT).show();
				});
	}

	private String toErrorMessage(Throwable t) {
		if (t instanceof IOException) {
			return "Error connecting to server";
		} else {
			return "Unexpected error";
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mRealm != null && !mRealm.isClosed()) {
			mRealm.close();
			mRealm = null;
		}
	}
}
