package com.bozhengjianshe.shenghuobang.bean;

public class AboutInfoBean {

    /**
     * state : 100
     * detail : {"seotitle":"生活邦是全球领先的信息与通信技术(ICT)解决方案供应商，专注于ICT领域，坚持稳健经营、持续创新、开放合作，在电信运营商、企业、终端和云计算等领域构筑了端到端的解决方案优势，为运营商客户、企业客户和消费者提供有竞争力的ICT解决方案、产品和服务，并致力于使能未来信息社会、构建更美好的全联接世界。目前，华为约有18万名员工，业务遍及全球170多个国家和地区，服务全世界三分之一以上的人口","copyright":"    version1.1.0","keywords":"     www.laosijigzs.com/life","beian":"   京PC 111 000 8888","description":"在法律允许的范围内，本网站在此声明,不承担用户或任何人士就使用或未能使用本网站所提供的信息或任何链接或项目所引致的任何直接、间接、附带、从属、特殊、惩罚性或惩戒性的损害赔偿（包括但不限于收益、预期利润的损失或失去的业务、未实现预期的节省）。","company":"    生活邦服务科技有限公司","servcietel":"    18888888888"}
     * message : 查询关于我们成功
     */

    private String state;
    private DetailBean detail;
    private String message;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DetailBean {
        /**
         * seotitle : 生活邦是全球领先的信息与通信技术(ICT)解决方案供应商，专注于ICT领域，坚持稳健经营、持续创新、开放合作，在电信运营商、企业、终端和云计算等领域构筑了端到端的解决方案优势，为运营商客户、企业客户和消费者提供有竞争力的ICT解决方案、产品和服务，并致力于使能未来信息社会、构建更美好的全联接世界。目前，华为约有18万名员工，业务遍及全球170多个国家和地区，服务全世界三分之一以上的人口
         * copyright :     version1.1.0
         * keywords :      www.laosijigzs.com/life
         * beian :    京PC 111 000 8888
         * description : 在法律允许的范围内，本网站在此声明,不承担用户或任何人士就使用或未能使用本网站所提供的信息或任何链接或项目所引致的任何直接、间接、附带、从属、特殊、惩罚性或惩戒性的损害赔偿（包括但不限于收益、预期利润的损失或失去的业务、未实现预期的节省）。
         * company :     生活邦服务科技有限公司
         * servcietel :     18888888888
         */

        private String seotitle;
        private String copyright;
        private String keywords;
        private String beian;
        private String description;
        private String company;
        private String servcietel;

        public String getSeotitle() {
            return seotitle;
        }

        public void setSeotitle(String seotitle) {
            this.seotitle = seotitle;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getBeian() {
            return beian;
        }

        public void setBeian(String beian) {
            this.beian = beian;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getServcietel() {
            return servcietel;
        }

        public void setServcietel(String servcietel) {
            this.servcietel = servcietel;
        }
    }
}
