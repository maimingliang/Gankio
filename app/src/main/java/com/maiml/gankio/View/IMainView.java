package com.maiml.gankio.View;

import com.maiml.gankio.bean.GankIoBean;
import com.maiml.gankio.common.SearchType;

import java.util.List;

/**
 * Created by maimingliang on 2016/12/22.
 */

public interface IMainView {

    void notifyDataChange(List<GankIoBean> list, SearchType searchType);

    String getCategory();
}
