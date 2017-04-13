package sk.ursus.thirtyseventytest;

import io.realm.Realm;
import sk.ursus.thirtyseventytest.net.RestClient;
import sk.ursus.thirtyseventytest.net.SyncHelper;

/**
 * Created by ursusursus on 12.4.2017.
 */
// TODO: dagger
public class Injection {

	private static RestClient sRestClient;

	public static RestClient provideRestClient() {
		if (sRestClient == null) {
			sRestClient = new RestClient();
		}
		return sRestClient;
	}

	public static SyncHelper provideSyncHelper() {
		return new SyncHelper(provideRestClient());
	}

	public static Realm provideRealm() {
		return Realm.getDefaultInstance();
	}
}
