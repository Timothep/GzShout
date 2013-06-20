package com.twin.gzbox;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class AvatarCache {

	private AvatarCacheListener listener;
	private Map<String, Bitmap> nameToBitmap = new HashMap<String, Bitmap>();
	private Bitmap defaultAvatar;

	public AvatarCache(Context context) {
		defaultAvatar = BitmapFactory.decodeResource(context.getResources(), R.drawable.defaultavatar);
	}

	public void put(String author, String avatarUrl) {
		if (!nameToBitmap.containsKey(author)) {
			new DownloadAvatarTask().execute(author, avatarUrl);
		}
	}

	public void addListener(AvatarCacheListener listener) {
		this.listener = listener;
	}

	public Bitmap getAvatar(String name) {
		Bitmap avatar = nameToBitmap.get(name);
		if (avatar == null) {
			avatar = defaultAvatar;
		}
		return avatar;
	}

	/********************************/
	/* TASKS */
	/********************************/

	private class DownloadAvatarTask extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			try {
				URL url = new URL(params[1]);
				InputStream is = (InputStream) url.getContent();
				byte[] buffer = new byte[8192];
				int bytesRead;
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				while ((bytesRead = is.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
				}
				byte[] bytes = output.toByteArray();
				Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
				nameToBitmap.put(params[0], bitmap);
			} catch (MalformedURLException e) {
				e.printStackTrace();				
			} catch (IOException e) {
				e.printStackTrace();				
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void v) {
			if (listener != null) {
				listener.onAvatarUpdated();
			}
		}
	}

}
