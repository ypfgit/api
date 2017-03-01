package test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;

public class AuthTest extends TestCase {

	CookieStore httpCookieStore = new BasicCookieStore();

	HttpClientBuilder builder = HttpClientBuilder.create()
			.setDefaultCookieStore(httpCookieStore);

	CloseableHttpClient client = builder.build();
	String BASEURl = "http://sso.zxks.com:8980";
	String PLATFORM = "REST";

	@Ignore
	public void testNotLogin() {
		HttpGet httpRequest = new HttpGet(BASEURl + "/sys");
		httpRequest.setHeader("PLATFORM", PLATFORM);
		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpRequest);
		} catch (Throwable error) {
			throw new RuntimeException(error);
		}

		/* check cookies */
		//System.out.println(httpResponse);
	}

	/**
	 * 测试后台web登录，期待跳转至登录页面。 状态200 OK
	 */
	@Ignore
	public void testLogin() {
		HttpGet httpRequest = new HttpGet(BASEURl + "/account/login");
		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpRequest);
		} catch (Throwable error) {
			throw new RuntimeException(error);
		}

		/* check cookies */
		//System.out.println(httpResponse);
	}
	
	/**
	 * 测试api/auth，期待返回JSON格式响应 状态200 ERROR
	 */
	@Ignore
	public void testLogout(){
		HttpGet httpRequest = new HttpGet(BASEURl + "/api/auth/logout");
		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpRequest);
			HttpEntity entity = httpResponse.getEntity();
			String content = EntityUtils.toString(entity);
			System.out.println(content);
		} catch (Throwable error) {
			throw new RuntimeException(error);
		}
	}

	/**
	 * 测试api/auth，期待返回JSON格式响应 状态200 ERROR
	 */
	@Ignore
	public void testApiAuth() {
		HttpPost httpRequest = new HttpPost(BASEURl +"/api/auth/login");
		httpRequest.setHeader("PLATFORM", PLATFORM);

		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("account", "admin"));
		params.add(new BasicNameValuePair("password", "1"));

		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpRequest);
			HttpEntity entity = httpResponse.getEntity();
			String content = EntityUtils.toString(entity);
			//System.out.println(httpResponse.getStatusLine().getStatusCode());
			System.out.println(content);
		} catch (Throwable error) {
			throw new RuntimeException(error);
		}
		
		testOperation();
		testLogout();
		testOperation();
	}

	/**
	 * 测试api请求业务，期待返回业务响应。 状态200
	 */
	@Ignore
	public void testOperation() {
		//testApiAuth();
		
		HttpGet httpRequest = new HttpGet(BASEURl+"/api/test/t1");
		httpRequest.setHeader("PLATFORM", PLATFORM);
		HttpResponse httpResponse = null;
		try {
			httpResponse = client.execute(httpRequest);
			HttpEntity entity = httpResponse.getEntity();
			String content = EntityUtils.toString(entity);
			//System.out.println(httpResponse.getStatusLine());
			System.out.println(content);
		} catch (Throwable error) {
			throw new RuntimeException(error);
		}
	}
}
