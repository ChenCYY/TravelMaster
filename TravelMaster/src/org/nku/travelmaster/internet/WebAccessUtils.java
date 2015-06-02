package org.nku.travelmaster.internet;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WebAccessUtils {

	// 属性：自定义一个网络地址属性
	private static final String URI = "http://" + InternetConfig.IP + ":"
			+ InternetConfig.PORT + "/" + InternetConfig.PROJECT + "/";

	// 方法1：自定义一个不带参数的请求响应方法
	public static String httpRequest(final String webServiceName) {
		String uri = URI + webServiceName;
		System.out.println("URI:>" + uri);
		HttpPost httpPostRequest = new HttpPost(uri);
		try {
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpPostRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String data = EntityUtils.toString(httpResponse.getEntity());
				return data;
			} else {
				return "101";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "102";
	}

	// 方法2：自定义一个带参数的请求响应方法
	public static String httpRequest(final String webServiceName,
			final List<? extends NameValuePair> lstNameValuePairs) {
		String uri = URI + webServiceName;
		System.out.println("URI:>" + uri);
		HttpPost httpPostRequest = new HttpPost(uri);
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
					lstNameValuePairs, HTTP.UTF_8);
			httpPostRequest.setEntity(entity);

			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpPostRequest);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				String data = EntityUtils.toString(httpResponse.getEntity());
				return data;
			} else {
				return "101";
			}

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "102";
	}

	//根据图片的URL下载从服务器下载图片
	public static Bitmap DownloadImage(String URL) {
		Bitmap bitmap = null;
		InputStream in;
		try {
			in = OpenHttpConnection(URL);
			BufferedInputStream bis = new BufferedInputStream(in, 3 * 1024);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}
			byte[] imageData = baf.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(imageData, 0,
					imageData.length);
			return bitmap;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	private static InputStream OpenHttpConnection(String urlString) throws IOException {
		InputStream in = null;

		URL url = new URL(URI + urlString);
		URLConnection conn = url.openConnection();

		if (!(conn instanceof HttpURLConnection))
			throw new IOException("Not an HTTP connection");

		
		try {
			HttpURLConnection httpConn;
			httpConn = (HttpURLConnection) conn;
			//httpConn.setAllowUserInteraction(false);
			//httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.connect();
			int response = httpConn.getResponseCode();
			if (response == HttpURLConnection.HTTP_OK) {
				in = httpConn.getInputStream();
			}
		} catch (Exception ex) {
			System.err.println("IOException: " + ex);
			throw new IOException("Error connecting");
		}
		
		return in;
	}
}
