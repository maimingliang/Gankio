package com.maiml.gankio.common;

/**
 * Created by maimingliang on 2016/12/22.
 */

public enum SearchType {

    NEW(0,"刷新"),
    OLD(1,"加载更多");


    private int code;

    SearchType(int code, String message) {

    }
}
