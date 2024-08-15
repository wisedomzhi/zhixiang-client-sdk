package com.wisewind.zhixiangclientsdk.client;

import cn.hutool.core.lang.Dict;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import com.wisewind.zhixiangclientsdk.common.ErrorCode;
import com.wisewind.zhixiangclientsdk.common.HeaderConstant;
import com.wisewind.zhixiangclientsdk.exception.BusinessException;
import com.wisewind.zhixiangclientsdk.utils.SignUtils;

import java.util.HashMap;
import java.util.List;


public class ZhiXiangInterfaceClient {
    private static final String GATE_WAY_HOST = "http://localhost:8900";
    private String accessKey;
    private String secretKey;

    public ZhiXiangInterfaceClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String request(String apiUrl, String requestParams) throws BusinessException{
        HashMap<String, Object> paramMap = null;
        String res = null;
        if (requestParams != null) {
            List<Dict> PARAMS = JSONUtil.parseArray(requestParams).toList(Dict.class);
            paramMap = new HashMap<>();
            for (Dict param : PARAMS) {
                String paramName = (String) param.get("paramName");
                Object paramValue = param.get("paramValue");
                paramMap.put(paramName, paramValue);
            }
        }
        HashMap<String, String> headers = setHeaders();
        if (apiUrl.equals(GATE_WAY_HOST + "/name")) {
            if (paramMap == null || !paramMap.containsKey("name")) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            res =  getName(paramMap, headers);
        }
        if (apiUrl.equals(GATE_WAY_HOST + "/loveTalk")) {
            res =  getLoveTalk(headers);
        }
        if (apiUrl.equals(GATE_WAY_HOST + "/bdics")) {
            if (paramMap == null || !paramMap.containsKey("domain")) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            res =  getBaiDuIndexNum(paramMap, headers);
        }
        if (apiUrl.equals(GATE_WAY_HOST + "/bilibili")) {
            res =  getBilibilHotList(headers);
        }
        if (apiUrl.equals(GATE_WAY_HOST + "/weather")) {
            res =  getWeather(headers);
        }
        if (apiUrl.equals(GATE_WAY_HOST + "/bing")) {
            res =  getBingImg(paramMap, headers);
        }
        if (apiUrl.equals(GATE_WAY_HOST + "/ipInfo")) {
            res =  getIpInfo(paramMap, headers);
        }
        if (apiUrl.equals(GATE_WAY_HOST + "/visitor")) {
            res =  getVisitorInfo(headers);
        }
        return res;
    }

    public String getName(HashMap<String, Object> paramMap, HashMap<String, String> headers){
        return HttpRequest.get(GATE_WAY_HOST + "/name").form(paramMap).addHeaders(headers).execute().body();
    }

    public String getLoveTalk(HashMap<String, String> headers){
        return HttpRequest.get(GATE_WAY_HOST + "/loveTalk").addHeaders(headers).execute().body();
    }

    public String getBaiDuIndexNum(HashMap<String, Object> paramMap, HashMap<String, String> headers){
        return HttpRequest.get(GATE_WAY_HOST + "/bdics").form(paramMap).addHeaders(headers).execute().body();
    }

    public String getBilibilHotList(HashMap<String, String> headers){
        return HttpRequest.get(GATE_WAY_HOST + "/bilibili").addHeaders(headers).execute().body();
    }

    public String getWeather(HashMap<String, String> headers){
        return HttpRequest.get(GATE_WAY_HOST + "/weather").addHeaders(headers).execute().body();
    }

    public String getBingImg(HashMap<String, Object> paramMap, HashMap<String, String> headers){
        return HttpRequest.get(GATE_WAY_HOST + "/bing").form(paramMap).addHeaders(headers).execute().body();
    }

    public String getIpInfo(HashMap<String, Object> paramMap, HashMap<String, String> headers){
        return HttpRequest.get(GATE_WAY_HOST + "/ipInfo").form(paramMap).addHeaders(headers).execute().body();
    }

    public String getVisitorInfo(HashMap<String, String> headers){
        return HttpRequest.get(GATE_WAY_HOST + "/visitor").addHeaders(headers).execute().body();
    }

    //    public String postName(String name) {
//        HashMap<String, Object> paramMap = new HashMap<>();
//        paramMap.put("name", name);
//
//        String result = HttpUtil.post(GATE_WAY_HOST + "/name/", paramMap);
//        System.out.println(String.format("client-post:{%s}", result));
//        return "post 你的名字是: " + result;
//    }
//
    private HashMap<String, String> setHeaders() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HeaderConstant.ACCESS_KEY, accessKey);
        // todo 使用redis缓存防止重放攻击
        // hashMap.put("nonce", "1");
        hashMap.put(HeaderConstant.TIME_STAMP, String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put(HeaderConstant.SIGN, SignUtils.getSign(accessKey, secretKey));
        return hashMap;
    }
//
//    public String postByJsonName(User user) {
//
//        String json = JSONUtil.toJsonStr(user);
//        Map<String, String> headers = this.setHeaders(accessKey, json);
//        HttpResponse execute = HttpRequest.post(GATE_WAY_HOST + "/name/byJson")
//                .body(json).addHeaders(headers)
//                .execute();
//        int status = execute.getStatus();
//        String result = execute.body();
//        System.out.println(String.format("client-post:{响应码：%d,响应体：%s}", status, result));
//        return "post-json 你的名字是: " + user.getName();
//    }
}
