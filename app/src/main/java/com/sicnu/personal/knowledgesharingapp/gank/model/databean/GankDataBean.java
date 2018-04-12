package com.sicnu.personal.knowledgesharingapp.gank.model.databean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/6 0006.
 */

public class GankDataBean {

    /**
     * error : false
     * results : [{"_id":"5a7c42c8421aa90d24a065d4","createdAt":"2018-02-08T20:30:00.798Z","desc":"一个动画效果的播放控件，播放，暂停，停止之间的动画切换","images":["http://img.gank.io/c1ee3231-648d-4449-a455-04a13731b2e1"],"publishedAt":"2018-02-22T08:24:35.209Z","source":"web","type":"Android","url":"https://github.com/SwiftyWang/AnimatePlayButton","used":true,"who":null},{"_id":"5a7c6094421aa90d21aa114a","createdAt":"2018-02-08T22:37:08.833Z","desc":"漂亮的本地多媒体选择器","publishedAt":"2018-02-22T08:24:35.209Z","source":"web","type":"Android","url":"https://github.com/TonnyL/Charles","used":true,"who":"黎赵太郎"},{"_id":"5a7cf9e7421aa90d21aa114b","createdAt":"2018-02-09T09:31:19.687Z","desc":"开源的 markdown 编辑器","images":["http://img.gank.io/760970ea-daae-4b98-9f87-deecdd3fe1f7","http://img.gank.io/ea49dc41-3435-4126-ab5b-d7b3357ab517"],"publishedAt":"2018-02-22T08:24:35.209Z","source":"chrome","type":"Android","url":"https://github.com/zeleven/mua","used":true,"who":"蒋朋"},{"_id":"5a81333c421aa90d264d0eba","createdAt":"2018-02-12T14:25:00.318Z","desc":"A slider widget with a popup bubble displaying the precise value selected.","images":["http://img.gank.io/fe3c723f-643d-4466-91d5-86d9ed4ca88e"],"publishedAt":"2018-02-22T08:24:35.209Z","source":"web","type":"Android","url":"https://github.com/Ramotion/fluid-slider-android","used":true,"who":"Alex Mikhnev"},{"_id":"5a685120421aa911548992ab","createdAt":"2018-01-24T17:25:52.341Z","desc":"Android 下的音乐可视化","images":["http://img.gank.io/e0d29181-282e-4465-9965-1da81e0557d9"],"publishedAt":"2018-01-29T07:53:57.676Z","source":"web","type":"Android","url":"https://github.com/nekocode/MusicVisualization","used":true,"who":"nekocode"},{"_id":"5a69608a421aa9115b9306a2","createdAt":"2018-01-25T12:43:54.642Z","desc":"插件化理解与实现 \u2014\u2014 加载 Activity「类加载篇」","images":["http://img.gank.io/a861c69f-02d2-46e8-b120-58ba4b3d97bf"],"publishedAt":"2018-01-29T07:53:57.676Z","source":"web","type":"Android","url":"https://fashare2015.github.io/2018/01/24/dynamic-load-learning-load-activity/","used":true,"who":"梁山boy"},{"_id":"5a6c2351421aa9115696004b","createdAt":"2018-01-27T14:59:29.299Z","desc":"This library generate an Mp4 movie using Android MediaCodec API and apply filter, scale, and rotate Mp4.","images":["http://img.gank.io/6fe115da-20d7-4774-8f87-0b776ec7885c"],"publishedAt":"2018-01-29T07:53:57.676Z","source":"web","type":"Android","url":"https://github.com/MasayukiSuda/Mp4Composer-android","used":true,"who":null},{"_id":"5a6da1e0421aa911548992b5","createdAt":"2018-01-28T18:11:44.726Z","desc":"使用MVP模式，基于高德地图开发，实现毛玻璃特效","publishedAt":"2018-01-29T07:53:57.676Z","source":"web","type":"Android","url":"https://github.com/JoshuaRogue/BetterWay","used":true,"who":null},{"_id":"5a6dc688421aa9115b9306ae","createdAt":"2018-01-28T20:48:08.141Z","desc":"AccessibilityService经常被黑产用来制作外挂影响正常的竞争环境，本文通过对AccessibilityService源码分析介绍其运行原理并给出相应的防御措施","publishedAt":"2018-01-29T07:53:57.676Z","source":"web","type":"Android","url":"https://lizhaoxuan.github.io/2018/01/27/AccessibilityService分析与防御/","used":true,"who":"lizhaoxuan"},{"_id":"5a5e0fbc421aa91154899281","createdAt":"2018-01-16T22:44:12.875Z","desc":"全面总结WebView遇到的坑及优化","publishedAt":"2018-01-23T08:46:45.132Z","source":"web","type":"Android","url":"https://www.jianshu.com/p/2b2e5d417e10","used":true,"who":"阿韦"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5a7c42c8421aa90d24a065d4
         * createdAt : 2018-02-08T20:30:00.798Z
         * desc : 一个动画效果的播放控件，播放，暂停，停止之间的动画切换
         * images : ["http://img.gank.io/c1ee3231-648d-4449-a455-04a13731b2e1"]
         * publishedAt : 2018-02-22T08:24:35.209Z
         * source : web
         * type : Android
         * url : https://github.com/SwiftyWang/AnimatePlayButton
         * used : true
         * who : null
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private Object who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public Object getWho() {
            return who;
        }

        public void setWho(Object who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
