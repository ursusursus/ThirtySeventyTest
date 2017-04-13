package sk.ursus.thirtyseventytest;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by ursusursus on 12.4.2017.
 */

public class App extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Realm.init(this);
	}
}
