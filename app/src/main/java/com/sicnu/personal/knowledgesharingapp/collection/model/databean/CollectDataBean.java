package com.sicnu.personal.knowledgesharingapp.collection.model.databean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2018/4/26 0026.
 */

public class CollectDataBean extends BmobObject{
    private String articleTitle;
    private String articleDesc;
    private String articleType;
    private String articleMold;
    private String articleLink;
    private String userName;
    private String articleAuthor;

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getArticleMold() {
        return articleMold;
    }

    public void setArticleMold(String articleMold) {
        this.articleMold = articleMold;
    }

    public String getArticleLink() {
        return articleLink;
    }

    public void setArticleLink(String articleLink) {
        this.articleLink = articleLink;
    }
}
