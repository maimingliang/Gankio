package com.maiml.gankio.http.rx;

import com.maiml.gankio.http.HttpResult;

/**
 * Created by maimingliang on 2016/12/21.
 * 判断网络请求是否成功
 */

public class ErrorHandler {


    /**
     * 请求是否成功的处理逻辑
     * @param code   这里假设 code == 0 请求成功
     * @return
     */
    public static boolean isRequestSuccussful(int code){


        if(code == 0){
            return true;
        }

        return false;
    }

}
