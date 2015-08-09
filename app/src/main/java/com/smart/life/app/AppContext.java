package com.smart.life.app;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.smart.life.domain.AllCategories;
import com.smart.life.domain.City;
import com.smart.life.domain.Good;
import com.smart.life.domain.HomeMsg;
import com.smart.life.domain.Seller;
import com.smart.life.domain.SpecificCategories;
import com.smart.life.domain.User;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;

public class AppContext extends Application {
	private JSONArray address_info;
	private List<City> cities;
	private List<Good> goods;
	private List<Seller> seller;
	private ArrayList<HomeMsg> homeMsgs;
	private List<SpecificCategories>  specificCategories;
	private List<AllCategories>  allCategories;
	private String city;
	private SharedPreferences preferences;
	private User user;
	private int screenWidth;
	private int screenHeight;

	public List<SpecificCategories> getSpecificCategories() {
		return specificCategories;
	}

	public void setSpecificCategories(List<SpecificCategories> specificCategories) {
		this.specificCategories = specificCategories;
	}

	public List<AllCategories> getAllCategories() {
		return allCategories;
	}

	public void setAllCategories(List<AllCategories> allCategories) {
		this.allCategories = allCategories;
	}

	public int getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenHeight() {
		return screenHeight;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

	public List<City> getCities() {
		return cities;
	}
	

	public List<Good> getGoods() {
		return goods;
	}

	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}
	public List<Seller> getSeller() {
		return seller;
	}

	public void setSeller(List<Seller> seller) {
		this.seller = seller;
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		initImageLoader(getApplicationContext());
		this.address_info = null;
		String as;
		try {
			as = getA();
			this.address_info = new JSONArray(as);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by
		//  ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
		config.threadPriority(Thread.NORM_PRIORITY - 2);
		config.denyCacheImageMultipleSizesInMemory();
		config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
		config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
		config.tasksProcessingOrder(QueueProcessingType.LIFO);
		config.writeDebugLogs(); // Remove for release app

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config.build());
	}

	public String getA() throws IOException {
		AssetManager assetManager = this.getAssets();
		InputStream is = assetManager.open("address_info.txt");
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = -1;
		while ((length = is.read(buffer)) != -1) {
			stream.write(buffer, 0, length);
		}
		return stream.toString();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public SharedPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if (preferences == null)
			preferences = getSharedPreferences("user", Activity.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString("username", user.getUsername());
		editor.putString("password", user.getPassword());
		editor.commit();

	}

	public ArrayList<HomeMsg> getHomeMsgs() {
		return homeMsgs;
	}

	public void setHomeMsgs(ArrayList<HomeMsg> homeMsgs) {
		this.homeMsgs = homeMsgs;
	}

	public JSONArray getAddress_info() {
		return address_info;
	}

	public void setAddress_info(JSONArray address_info) {
		this.address_info = address_info;
	}
}
