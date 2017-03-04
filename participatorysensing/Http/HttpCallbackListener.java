/**
 * class name: 
 * class description: 
 * author: dengchaoyue 
 * version: 1.0
 */
package com.example.participatorysensing.Http;

public interface HttpCallbackListener {

	void onFinish(String response);
	void onError(Exception e);
}
