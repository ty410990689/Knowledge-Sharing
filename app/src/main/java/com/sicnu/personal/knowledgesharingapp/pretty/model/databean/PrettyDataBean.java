package com.sicnu.personal.knowledgesharingapp.pretty.model.databean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3 0003.
 */

public class PrettyDataBean implements Serializable {

    /**
     * error : false
     * results : [{"imageUrl":"http://pic.meituba.com/uploads/allimg/2017/08/17/38_12876.jpg"},{"imageUrl":"http://pic.meituba.com/uploads/allimg/2017/08/17/38_12878.jpg"},{"imageUrl":"http://pic.meituba.com/uploads/allimg/2017/08/17/38_12880.jpg"},{"imageUrl":"http://pic.meituba.com/uploads/allimg/2017/08/17/38_12881.jpg"},{"imageUrl":"http://pic.meituba.com/uploads/allimg/2017/08/01/38_400.jpg"},{"imageUrl":"http://pic.meituba.com/uploads/allimg/2017/08/17/38_13483.jpg"},{"imageUrl":"http://pic.meituba.com/uploads/allimg/2017/08/17/38_13484.jpg"},{"imageUrl":"http://pic.meituba.com/uploads/allimg/2017/08/17/38_13486.jpg"},{"imageUrl":"http://pic.meituba.com/uploads/allimg/2016/04/19/1228.jpg"},{"imageUrl":"http://pic.meituba.com/uploads/allimg/2016/04/19/1229.jpg"}]
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

    public static class ResultsBean implements Serializable{
        /**
         * imageUrl : http://pic.meituba.com/uploads/allimg/2017/08/17/38_12876.jpg
         */

        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }
}
