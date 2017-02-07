package com.hanyee.geekzone.presenter.contract;

import com.hanyee.androidlib.base.BasePresenter;
import com.hanyee.androidlib.base.BaseView;
import com.hanyee.geekzone.model.bean.zhihu.ColumnAuthorDetailBean;
import com.hanyee.geekzone.model.bean.zhihu.ColumnsBean;

import java.util.List;

/**
 * Created by Hanyee on 16/10/14.
 */
public interface ColumnListContract {

    interface View extends BaseView {
        void showColumnAuthorInfo(ColumnAuthorDetailBean author);

        void showColumnList(List<ColumnsBean> list);
    }

    interface Presenter extends BasePresenter<View> {
        void loadColumnAuthorInfo(String name);

        void loadColumnList(String name);

        void likeZhiHuColumnAuthor(String title, String picUrl, String desc, String authorHref, int followersCount, int postsCount, String type);

        boolean isZhiHuColumnAuthorLiked(String authorHref);

        void unlikeZhiHuColumnAuthor(String authorHref);
    }
}
