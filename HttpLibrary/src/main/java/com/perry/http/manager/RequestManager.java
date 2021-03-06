package com.perry.http.manager;

import android.app.Activity;
import android.content.Context;

import com.perry.http.Listener.LoadingDialogImpl;
import com.perry.http.Listener.LoadingInterface;
import com.perry.http.parser.ConfigXmlParser;
import com.perry.http.proxy.HttpClientProxy;

/**
 * Created by perry on 16/11/28.
 */
public class RequestManager {
    private static RequestManager ourInstance = new RequestManager();

    private Activity activity;
    private Context context;
    public static RequestManager getInstance(Activity activity) {
        if(activity != null){
            if(ourInstance.activity == null){
                //第一次解析url地址
                ConfigXmlParser parser = new ConfigXmlParser();
                parser.parse(activity);
            }
            ourInstance.activity = activity;
        }
        return ourInstance;
    }

    public static RequestManager getInstance(Context context) {
        if(context != null){
            if(ourInstance.activity == null){
                //第一次解析url地址
                ConfigXmlParser parser = new ConfigXmlParser();
                parser.parse(context);
            }
            ourInstance.context = context;
        }
        return ourInstance;
    }

    private RequestManager() {
        //需要传入上下文 context

    }


    /**
     *
     * @param xmlName
     */
    public void setParserXml(String xmlName){
        ConfigXmlParser parser = new ConfigXmlParser();
        parser.parse(activity,xmlName);
    }
    public void sendRequest(Request request) {
        HttpClientProxy proxy = null;
        if(activity != null) {
             proxy = new HttpClientProxy(request.url(), activity);
        }else if(context != null){
            proxy = new HttpClientProxy(request.url(), context);
        }
        if(proxy != null) {
            proxy.request(request.method(), request.getParameter(), request.getFileParameter(), request.getResponse().getCls(), request.getResponse().getCallback(), null);
        }
    }

    public void sendRequest(Request request, Activity activity,String showText ) {
        if(activity == null){
            activity = this.activity;
        }
        HttpClientProxy proxy = new HttpClientProxy(request.url(), activity);
        proxy.request(request.method(), request.getParameter(), request.getFileParameter(), request.getResponse().getCls(), request.getResponse().getCallback(), new LoadingDialogImpl(activity,showText));

    }

    public void sendRequest(Request request, LoadingInterface l) {
        HttpClientProxy proxy = new HttpClientProxy(request.url(), activity);
        proxy.request(request.method(), request.getParameter(), request.getFileParameter(), request.getResponse().getCls(), request.getResponse().getCallback(), l);

    }

    public void sendRequest(Request request, LoadingInterface l, boolean isValidatesSign) {
        HttpClientProxy proxy = new HttpClientProxy(request.url(), activity);
        proxy.request(request.method(), request.getParameter(), request.getFileParameter(), request.getResponse().getCls(), request.getResponse().getCallback(), l, isValidatesSign);

    }

    public void sendRequest(Request request, boolean isValidatesSign) {
        HttpClientProxy proxy = new HttpClientProxy(request.url(), activity);
        proxy.request(request.method(), request.getParameter(), request.getFileParameter(), request.getResponse().getCls(), request.getResponse().getCallback(), null, isValidatesSign);

    }
}
