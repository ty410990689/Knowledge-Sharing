package com.sicnu.personal.knowledgesharingapp.flash.databean;

/**
 * Created by Administrator on 2018/4/10 0010.
 */

public class FlashPicDataBean {


    /**
     * error : false
     * results : {"imageName":"flash_image.jpg","showMode":"local","imageUrl":"http://pic.meituba.com/uploads/allimg/2017/08/17/38_12876.jpg","imageMd5":"ASDASDASDSADASASDSAASDASD","imageVersion":"v2.6.2"}
     */

    private boolean error;
    private ResultsBean results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * imageName : flash_image.jpg
         * showMode : local
         * imageUrl : http://pic.meituba.com/uploads/allimg/2017/08/17/38_12876.jpg
         * imageMd5 : ASDASDASDSADASASDSAASDASD
         * imageVersion : v2.6.2
         */

        private String imageName;
        private String showMode;
        private String imageUrl;
        private String imageMd5;
        private String imageVersion;

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

        public String getShowMode() {
            return showMode;
        }

        public void setShowMode(String showMode) {
            this.showMode = showMode;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageMd5() {
            return imageMd5;
        }

        public void setImageMd5(String imageMd5) {
            this.imageMd5 = imageMd5;
        }

        public String getImageVersion() {
            return imageVersion;
        }

        public void setImageVersion(String imageVersion) {
            this.imageVersion = imageVersion;
        }
    }
}
