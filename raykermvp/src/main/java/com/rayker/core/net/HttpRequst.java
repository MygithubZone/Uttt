package com.rayker.core.net;

import com.rayker.core.manager.ActivityManager;

import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 功能：<br>
 * 作者：RaykerTeam
 * 时间： 2016/11/9 0009<br>.
 * 版本：1.0.0
 */

public class HttpRequst {
    /**
     * 请求集合: key=Activity value=Call集合
     */
    private static Map<String, List<Call>> callsMap = new ConcurrentHashMap<String, List<Call>>();

    private static class HttpRequstInstance {
        public static final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");
        private static OkHttpClient client = new OkHttpClient();
    }

    /**
     * @param url
     * @param params
     * @param callback
     * @throws IOException
     */
    public static void post(String tag, String url, Map<String, Object> params, Callback callback, boolean isCache) throws IOException {
        RequestBody body = RequestBody.create(HttpRequstInstance.JSON, new JSONObject(params).toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call requstCall = HttpRequstInstance.client.newCall(request);
        putCall(tag, requstCall);
        requstCall.enqueue(callback);
    }

    public static void get(String tag, String url, Callback callback) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call requstCall = HttpRequstInstance.client.newCall(request);
        putCall(tag, requstCall);
        requstCall.enqueue(callback);
    }

    /**
     * 保存请求集合
     *
     * @param tag
     * @param call
     */
    private static void putCall(String tag, Call call) {
        if (null != tag) {
            List<Call> callList = callsMap.get(tag);
            if (null == callList) {
                callList = new LinkedList<Call>();
                callList.add(call);
                callsMap.put(tag, callList);
            } else {
                callList.add(call);
            }
        }
    }

    /**
     * 取消请求
     *
     * @param tag
     */
    public static void cancelCall(String tag) {
        List<Call> callList = callsMap.get(tag);
        if (null != callList) {
            for (Call call : callList) {
                if (!call.isCanceled())
                    call.cancel();
            }
            callsMap.remove(tag);
        }
    }
}
