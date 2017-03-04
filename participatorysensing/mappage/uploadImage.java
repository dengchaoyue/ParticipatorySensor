/**
 * class name: 
 * class description: 
 * author: dengchaoyue 
 * version: 1.0
 */
package com.example.participatorysensing.mappage;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * @author dengchaoyue
 *
 */
public class uploadImage {

	public static void sentHttpData(final String address, final String uploadFilePath){		
		new Thread(new Runnable(){
			public void run(){
			File file = new File(uploadFilePath);
			final String TAG = "uploadFile";
			final String CHARSET = "utf-8";
			HttpURLConnection connection = null;
			int res=0;
			String result = null;
			String BOUNDARY = UUID.randomUUID().toString();
			String PREFIX = "--", LINE_END = "\r\n";
			String CONTENT_TYPE = "multipart/form-data";
			try{
				URL url = new URL(address);
				connection = (HttpURLConnection)url.openConnection();
				connection.setRequestMethod("POST");
				connection.setConnectTimeout(8000);
				connection.setReadTimeout(8000);
				connection.setDoInput(true);
				connection.setRequestProperty("Accept","image/jpeg");
				connection.setDoOutput(true);
				connection.setRequestProperty("Connection", "Keep-Alive");  
	            connection.setRequestProperty("Charset", "UTF-8");  
	            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
	            connection.setRequestProperty("boundary",BOUNDARY);
				if (file != null) {

					DataOutputStream dos = new DataOutputStream(connection.getOutputStream());

					/*
					StringBuffer sb = new StringBuffer();
					sb.append(PREFIX);
					sb.append(BOUNDARY);
					sb.append(LINE_END);
					*/

					/*

					sb.append("Content-Disposition: form-data; name=\"pic\"; filename=\""
							+ file.getName() + "\"" + LINE_END);
					sb.append("Content-Type: application/octet-stream; charset="
							+ CHARSET + LINE_END);
					sb.append(LINE_END);
					dos.write(sb.toString().getBytes());*/

					InputStream is = new FileInputStream(file);
					byte[] bytes = new byte[1024];
					int len = 0;
					while ((len = is.read(bytes)) != -1) {
						dos.write(bytes, 0, len);
					}
					is.close();
	/*				dos.write(LINE_END.getBytes());
					byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
							.getBytes();
					dos.write(end_data);*/
					dos.flush();

					res = connection.getResponseCode();
					Log.e(TAG, "response code:" + res);
					if (res == 200) {
						Log.e(TAG, "request success");
						InputStream input = connection.getInputStream();
						StringBuffer partResult = new StringBuffer();
						int ss;
						while ((ss = input.read()) != -1) {
							partResult.append((char) ss);
						}
						result = partResult.toString();
						Log.e(TAG, "result : " + result);
					} else {
						Log.e(TAG, "request error");
						Log.e(TAG, connection.getErrorStream().toString());
					}
				}else{
					Log.e(TAG, "file not exits");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			}
			}).start();
		}

}
