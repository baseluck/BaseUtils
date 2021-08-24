package com.luck.basemodule.utils;

import com.blankj.utilcode.util.GsonUtils;
import com.lzy.okgo.OkGo;
import com.luck.basemodule.base.BaseCallback;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/6/2 17:23
 * @描述:
 */
public class OkGoUtils {

    public static void get(String url, Map map, BaseCallback baseCallback) {
        OkGo.<String>get(url)
                .headers("NeedToken", "true")
                .params(map)
                .execute(baseCallback);
    }

    public static void post(String url, Map map, BaseCallback baseCallback) {
        OkGo.<String>post(url)
                .headers("NeedToken", "true")
                .upJson(GsonUtils.toJson(map))
                .execute(baseCallback);
    }

    public static void postJson(String url, String json, BaseCallback baseCallback) {
        OkGo.<String>post(url)
                .headers("NeedToken", "true")
                .upJson(json)
                .execute(baseCallback);
    }


    public static void upFile(String url, File file, BaseCallback baseCallback) {
        OkGo.<String>post(url)
                .headers("NeedToken", "true")
                .params("file", file)
                .execute(baseCallback);
    }

    public static void upFileMap(String url, File file, Map map, BaseCallback baseCallback) {
        OkGo.<String>post(url)
                .headers("NeedToken", "true")
                .params(map)
                .params("file", file)
                .execute(baseCallback);
    }


    public static void upFiles(String url, List<File> files, BaseCallback baseCallback) {
        OkGo.<String>post(url)
                .headers("NeedToken", "true")
                .addFileParams("files", files)
                .execute(baseCallback);
    }

    public static void delete(String url, Map map, BaseCallback baseCallback) {
        OkGo.<String>delete(url)
                .headers("NeedToken", "true")
                .params(map)
                .execute(baseCallback);
    }

    public static void put(String url, Map map, BaseCallback baseCallback) {
        OkGo.<String>put(url)
                .headers("NeedToken", "true")
                .upJson(GsonUtils.toJson(map))
                .execute(baseCallback);
    }


}
