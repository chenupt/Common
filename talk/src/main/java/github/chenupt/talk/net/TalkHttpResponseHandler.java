package github.chenupt.talk.net;

import android.util.Log;

import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by chenupt@gmail.com on 2014/5/27.
 * Description : TODO
 */
public abstract class TalkHttpResponseHandler extends TextHttpResponseHandler {

    public final static String TAG = "TalkHttpResponseHandler";

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseBody) {
        Log.d(TAG, "onSuccess : " + responseBody);
//        TalkHttpEntity<String> talkHttpEntity = new Gson().fromJson(responseBody, new TypeToken<TalkHttpEntity<String>>(){}.getType());
//        TalkHttpEntity talkHttpEntity = new Gson().fromJson(responseBody, TalkHttpEntity.class);
//        TalkHttpEntity talkHttpEntity = JsonUtil.fromJsonToObject(responseBody, TalkHttpEntity.class);
        try {
            TalkHttpEntity talkHttpEntity = new TalkHttpEntity();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONObject bodyJsonObject = null;
            if (!jsonObject.isNull("body")) {
                bodyJsonObject = jsonObject.getJSONObject("body");
                Log.d(TAG, "jo: " + bodyJsonObject.toString());
            }
            talkHttpEntity.setBody(bodyJsonObject);
            Log.d(TAG, "onSuccess : " + talkHttpEntity.getBody());
            onSuccess(statusCode, headers, responseBody,
                    talkHttpEntity.getAction(),
                    talkHttpEntity.getStatus(),
                    talkHttpEntity.getBody(),
                    talkHttpEntity.getMsg());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
        Log.d(TAG, "onFailure : " + error.getMessage());
    }


    public abstract void onSuccess(int statusCode, Header[] headers, String responseBody, String action, int status, Object body, String msg);

}
