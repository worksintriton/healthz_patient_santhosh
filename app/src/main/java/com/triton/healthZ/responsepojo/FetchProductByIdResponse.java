package com.triton.healthz.responsepojo;

import java.util.List;

public class FetchProductByIdResponse {


    /**
     * Status : Success
     * Message : product list
     * Product_details : {"_id":"61af46069e6f552291dbfa6f","product_img":[{"product_img":"http://35.164.43.170:3000/api/uploads/1638876621297.jpg"}],"product_title":"price food","product_price":200,"product_discount":0,"product_discount_price":0,"thumbnail_image":"http://35.164.43.170:3000/api/uploads/1638876621297.jpg","cat_id":{"_id":"6198b5a2518ad4520ab14793","img_path":"http://35.164.43.170:3000/api/uploads/1639030679281.png","product_cate":"Men Care","img_index":0,"show_status":true,"date_and_time":"Thu Dec 09 2021 11:46:43 GMT+0530 (India Standard Time)","delete_status":false,"updatedAt":"2021-12-09T06:18:01.379Z","createdAt":"2021-11-20T08:45:22.893Z","__v":0},"threshould":"66","product_discription":"product description","product_fav":false,"product_rating":5,"product_review":0,"product_related":[{"_id":"61af46069e6f552291dbfa6f","product_img":{"product_img":"http://35.164.43.170:3000/api/uploads/1638876621297.jpg"},"product_title":"price food","product_price":200,"thumbnail_image":"http://35.164.43.170:3000/api/uploads/1638876621297.jpg","product_discount":0,"product_discount_price":0,"product_fav":false,"product_rating":5,"product_review":0,"condition":"organic","price_type":"unit price","addition_detail":["additional details"]}],"product_cart_count":0,"condition":"organic","price_type":"unit price","addition_detail":["additional details"]}
     * vendor_details : {"bussiness_gallery":[{"bussiness_gallery":"http://35.164.43.170:3000/api/uploads/1638874330799.jpg"}],"certifi":[{"certifi":"http://35.164.43.170:3000/api/uploads/1638874337520.pdf"}],"_id":"61af3cea7a64122107fc8e49","user_id":"61af3c907a64122107fc8e48","user_name":"Maddy","user_email":"maddykrishh4h@gmail.com","bussiness_name":"Maddy Sam","bussiness_email":"maddysan66@gmail.com","bussiness":"Maddy","bussiness_phone":"9876543210","business_reg":"12345678","photo_id_proof":"http://35.164.43.170:3000/api/uploads/1638874321232.pdf","govt_id_proof":"http://35.164.43.170:3000/api/uploads/1638874344157.pdf","date_and_time":"07/12/2021 04:22 PM","mobile_type":"Android","profile_status":true,"profile_verification_status":"Verified","bussiness_loc":"Salem 125J, Vmr Theatre Back Side Rd, Swaminathapuram, Salem, Tamil Nadu 636002, India","bussiness_lat":11.661722999999999,"bussiness_long":78.13756059999999,"delete_status":false,"updatedAt":"2021-12-07T10:52:49.539Z","createdAt":"2021-12-07T10:52:26.855Z","__v":0}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * _id : 61af46069e6f552291dbfa6f
     * product_img : [{"product_img":"http://35.164.43.170:3000/api/uploads/1638876621297.jpg"}]
     * product_title : price food
     * product_price : 200
     * product_discount : 0
     * product_discount_price : 0
     * thumbnail_image : http://35.164.43.170:3000/api/uploads/1638876621297.jpg
     * cat_id : {"_id":"6198b5a2518ad4520ab14793","img_path":"http://35.164.43.170:3000/api/uploads/1639030679281.png","product_cate":"Men Care","img_index":0,"show_status":true,"date_and_time":"Thu Dec 09 2021 11:46:43 GMT+0530 (India Standard Time)","delete_status":false,"updatedAt":"2021-12-09T06:18:01.379Z","createdAt":"2021-11-20T08:45:22.893Z","__v":0}
     * threshould : 66
     * product_discription : product description
     * product_fav : false
     * product_rating : 5
     * product_review : 0
     * product_related : [{"_id":"61af46069e6f552291dbfa6f","product_img":{"product_img":"http://35.164.43.170:3000/api/uploads/1638876621297.jpg"},"product_title":"price food","product_price":200,"thumbnail_image":"http://35.164.43.170:3000/api/uploads/1638876621297.jpg","product_discount":0,"product_discount_price":0,"product_fav":false,"product_rating":5,"product_review":0,"condition":"organic","price_type":"unit price","addition_detail":["additional details"]}]
     * product_cart_count : 0
     * condition : organic
     * price_type : unit price
     * addition_detail : ["additional details"]
     */

    private ProductDetailsBean Product_details;
    /**
     * bussiness_gallery : [{"bussiness_gallery":"http://35.164.43.170:3000/api/uploads/1638874330799.jpg"}]
     * certifi : [{"certifi":"http://35.164.43.170:3000/api/uploads/1638874337520.pdf"}]
     * _id : 61af3cea7a64122107fc8e49
     * user_id : 61af3c907a64122107fc8e48
     * user_name : Maddy
     * user_email : maddykrishh4h@gmail.com
     * bussiness_name : Maddy Sam
     * bussiness_email : maddysan66@gmail.com
     * bussiness : Maddy
     * bussiness_phone : 9876543210
     * business_reg : 12345678
     * photo_id_proof : http://35.164.43.170:3000/api/uploads/1638874321232.pdf
     * govt_id_proof : http://35.164.43.170:3000/api/uploads/1638874344157.pdf
     * date_and_time : 07/12/2021 04:22 PM
     * mobile_type : Android
     * profile_status : true
     * profile_verification_status : Verified
     * bussiness_loc : Salem 125J, Vmr Theatre Back Side Rd, Swaminathapuram, Salem, Tamil Nadu 636002, India
     * bussiness_lat : 11.661722999999999
     * bussiness_long : 78.13756059999999
     * delete_status : false
     * updatedAt : 2021-12-07T10:52:49.539Z
     * createdAt : 2021-12-07T10:52:26.855Z
     * __v : 0
     */

    private VendorDetailsBean vendor_details;
    private int Code;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public ProductDetailsBean getProduct_details() {
        return Product_details;
    }

    public void setProduct_details(ProductDetailsBean Product_details) {
        this.Product_details = Product_details;
    }

    public VendorDetailsBean getVendor_details() {
        return vendor_details;
    }

    public void setVendor_details(VendorDetailsBean vendor_details) {
        this.vendor_details = vendor_details;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public static class ProductDetailsBean {
        private String _id;
        private String product_title;
        private int product_price;
        private int product_discount;
        private int product_discount_price;
        private String thumbnail_image;
        /**
         * _id : 6198b5a2518ad4520ab14793
         * img_path : http://35.164.43.170:3000/api/uploads/1639030679281.png
         * product_cate : Men Care
         * img_index : 0
         * show_status : true
         * date_and_time : Thu Dec 09 2021 11:46:43 GMT+0530 (India Standard Time)
         * delete_status : false
         * updatedAt : 2021-12-09T06:18:01.379Z
         * createdAt : 2021-11-20T08:45:22.893Z
         * __v : 0
         */

        private CatIdBean cat_id;
        private String threshould;
        private String product_discription;
        private boolean product_fav;
        private int product_rating;
        private int product_review;
        private int product_cart_count;
        private String condition;
        private String price_type;
        /**
         * product_img : http://35.164.43.170:3000/api/uploads/1638876621297.jpg
         */

        private List<ProductImgBean> product_img;
        /**
         * _id : 61af46069e6f552291dbfa6f
         * product_img : {"product_img":"http://35.164.43.170:3000/api/uploads/1638876621297.jpg"}
         * product_title : price food
         * product_price : 200
         * thumbnail_image : http://35.164.43.170:3000/api/uploads/1638876621297.jpg
         * product_discount : 0
         * product_discount_price : 0
         * product_fav : false
         * product_rating : 5
         * product_review : 0
         * condition : organic
         * price_type : unit price
         * addition_detail : ["additional details"]
         */

        private List<ProductRelatedBean> product_related;
        private List<String> addition_detail;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getProduct_title() {
            return product_title;
        }

        public void setProduct_title(String product_title) {
            this.product_title = product_title;
        }

        public int getProduct_price() {
            return product_price;
        }

        public void setProduct_price(int product_price) {
            this.product_price = product_price;
        }

        public int getProduct_discount() {
            return product_discount;
        }

        public void setProduct_discount(int product_discount) {
            this.product_discount = product_discount;
        }

        public int getProduct_discount_price() {
            return product_discount_price;
        }

        public void setProduct_discount_price(int product_discount_price) {
            this.product_discount_price = product_discount_price;
        }

        public String getThumbnail_image() {
            return thumbnail_image;
        }

        public void setThumbnail_image(String thumbnail_image) {
            this.thumbnail_image = thumbnail_image;
        }

        public CatIdBean getCat_id() {
            return cat_id;
        }

        public void setCat_id(CatIdBean cat_id) {
            this.cat_id = cat_id;
        }

        public String getThreshould() {
            return threshould;
        }

        public void setThreshould(String threshould) {
            this.threshould = threshould;
        }

        public String getProduct_discription() {
            return product_discription;
        }

        public void setProduct_discription(String product_discription) {
            this.product_discription = product_discription;
        }

        public boolean isProduct_fav() {
            return product_fav;
        }

        public void setProduct_fav(boolean product_fav) {
            this.product_fav = product_fav;
        }

        public int getProduct_rating() {
            return product_rating;
        }

        public void setProduct_rating(int product_rating) {
            this.product_rating = product_rating;
        }

        public int getProduct_review() {
            return product_review;
        }

        public void setProduct_review(int product_review) {
            this.product_review = product_review;
        }

        public int getProduct_cart_count() {
            return product_cart_count;
        }

        public void setProduct_cart_count(int product_cart_count) {
            this.product_cart_count = product_cart_count;
        }

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getPrice_type() {
            return price_type;
        }

        public void setPrice_type(String price_type) {
            this.price_type = price_type;
        }

        public List<ProductImgBean> getProduct_img() {
            return product_img;
        }

        public void setProduct_img(List<ProductImgBean> product_img) {
            this.product_img = product_img;
        }

        public List<ProductRelatedBean> getProduct_related() {
            return product_related;
        }

        public void setProduct_related(List<ProductRelatedBean> product_related) {
            this.product_related = product_related;
        }

        public List<String> getAddition_detail() {
            return addition_detail;
        }

        public void setAddition_detail(List<String> addition_detail) {
            this.addition_detail = addition_detail;
        }

        public static class CatIdBean {
            private String _id;
            private String img_path;
            private String product_cate;
            private int img_index;
            private boolean show_status;
            private String date_and_time;
            private boolean delete_status;
            private String updatedAt;
            private String createdAt;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getImg_path() {
                return img_path;
            }

            public void setImg_path(String img_path) {
                this.img_path = img_path;
            }

            public String getProduct_cate() {
                return product_cate;
            }

            public void setProduct_cate(String product_cate) {
                this.product_cate = product_cate;
            }

            public int getImg_index() {
                return img_index;
            }

            public void setImg_index(int img_index) {
                this.img_index = img_index;
            }

            public boolean isShow_status() {
                return show_status;
            }

            public void setShow_status(boolean show_status) {
                this.show_status = show_status;
            }

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }

            public boolean isDelete_status() {
                return delete_status;
            }

            public void setDelete_status(boolean delete_status) {
                this.delete_status = delete_status;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }
        }

        public static class ProductImgBean {
            private String product_img;

            public String getProduct_img() {
                return product_img;
            }

            public void setProduct_img(String product_img) {
                this.product_img = product_img;
            }
        }

        public static class ProductRelatedBean {
            private String _id;
            /**
             * product_img : http://35.164.43.170:3000/api/uploads/1638876621297.jpg
             */

            private ProductImgBean product_img;
            private String product_title;
            private int product_price;
            private String thumbnail_image;
            private int product_discount;
            private int product_discount_price;
            private boolean product_fav;
            private int product_rating;
            private int product_review;
            private String condition;
            private String price_type;
            private List<String> addition_detail;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public ProductImgBean getProduct_img() {
                return product_img;
            }

            public void setProduct_img(ProductImgBean product_img) {
                this.product_img = product_img;
            }

            public String getProduct_title() {
                return product_title;
            }

            public void setProduct_title(String product_title) {
                this.product_title = product_title;
            }

            public int getProduct_price() {
                return product_price;
            }

            public void setProduct_price(int product_price) {
                this.product_price = product_price;
            }

            public String getThumbnail_image() {
                return thumbnail_image;
            }

            public void setThumbnail_image(String thumbnail_image) {
                this.thumbnail_image = thumbnail_image;
            }

            public int getProduct_discount() {
                return product_discount;
            }

            public void setProduct_discount(int product_discount) {
                this.product_discount = product_discount;
            }

            public int getProduct_discount_price() {
                return product_discount_price;
            }

            public void setProduct_discount_price(int product_discount_price) {
                this.product_discount_price = product_discount_price;
            }

            public boolean isProduct_fav() {
                return product_fav;
            }

            public void setProduct_fav(boolean product_fav) {
                this.product_fav = product_fav;
            }

            public int getProduct_rating() {
                return product_rating;
            }

            public void setProduct_rating(int product_rating) {
                this.product_rating = product_rating;
            }

            public int getProduct_review() {
                return product_review;
            }

            public void setProduct_review(int product_review) {
                this.product_review = product_review;
            }

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
            }

            public String getPrice_type() {
                return price_type;
            }

            public void setPrice_type(String price_type) {
                this.price_type = price_type;
            }

            public List<String> getAddition_detail() {
                return addition_detail;
            }

            public void setAddition_detail(List<String> addition_detail) {
                this.addition_detail = addition_detail;
            }

            public static class ProductImgBean {
                private String product_img;

                public String getProduct_img() {
                    return product_img;
                }

                public void setProduct_img(String product_img) {
                    this.product_img = product_img;
                }
            }
        }
    }

    public static class VendorDetailsBean {
        private String _id;
        private String user_id;
        private String user_name;
        private String user_email;
        private String bussiness_name;
        private String bussiness_email;
        private String bussiness;
        private String bussiness_phone;
        private String business_reg;
        private String photo_id_proof;
        private String govt_id_proof;
        private String date_and_time;
        private String mobile_type;
        private boolean profile_status;
        private String profile_verification_status;
        private String bussiness_loc;
        private double bussiness_lat;
        private double bussiness_long;
        private boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private int __v;
        /**
         * bussiness_gallery : http://35.164.43.170:3000/api/uploads/1638874330799.jpg
         */

        private List<BussinessGalleryBean> bussiness_gallery;
        /**
         * certifi : http://35.164.43.170:3000/api/uploads/1638874337520.pdf
         */

        private List<CertifiBean> certifi;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getBussiness_name() {
            return bussiness_name;
        }

        public void setBussiness_name(String bussiness_name) {
            this.bussiness_name = bussiness_name;
        }

        public String getBussiness_email() {
            return bussiness_email;
        }

        public void setBussiness_email(String bussiness_email) {
            this.bussiness_email = bussiness_email;
        }

        public String getBussiness() {
            return bussiness;
        }

        public void setBussiness(String bussiness) {
            this.bussiness = bussiness;
        }

        public String getBussiness_phone() {
            return bussiness_phone;
        }

        public void setBussiness_phone(String bussiness_phone) {
            this.bussiness_phone = bussiness_phone;
        }

        public String getBusiness_reg() {
            return business_reg;
        }

        public void setBusiness_reg(String business_reg) {
            this.business_reg = business_reg;
        }

        public String getPhoto_id_proof() {
            return photo_id_proof;
        }

        public void setPhoto_id_proof(String photo_id_proof) {
            this.photo_id_proof = photo_id_proof;
        }

        public String getGovt_id_proof() {
            return govt_id_proof;
        }

        public void setGovt_id_proof(String govt_id_proof) {
            this.govt_id_proof = govt_id_proof;
        }

        public String getDate_and_time() {
            return date_and_time;
        }

        public void setDate_and_time(String date_and_time) {
            this.date_and_time = date_and_time;
        }

        public String getMobile_type() {
            return mobile_type;
        }

        public void setMobile_type(String mobile_type) {
            this.mobile_type = mobile_type;
        }

        public boolean isProfile_status() {
            return profile_status;
        }

        public void setProfile_status(boolean profile_status) {
            this.profile_status = profile_status;
        }

        public String getProfile_verification_status() {
            return profile_verification_status;
        }

        public void setProfile_verification_status(String profile_verification_status) {
            this.profile_verification_status = profile_verification_status;
        }

        public String getBussiness_loc() {
            return bussiness_loc;
        }

        public void setBussiness_loc(String bussiness_loc) {
            this.bussiness_loc = bussiness_loc;
        }

        public double getBussiness_lat() {
            return bussiness_lat;
        }

        public void setBussiness_lat(double bussiness_lat) {
            this.bussiness_lat = bussiness_lat;
        }

        public double getBussiness_long() {
            return bussiness_long;
        }

        public void setBussiness_long(double bussiness_long) {
            this.bussiness_long = bussiness_long;
        }

        public boolean isDelete_status() {
            return delete_status;
        }

        public void setDelete_status(boolean delete_status) {
            this.delete_status = delete_status;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public List<BussinessGalleryBean> getBussiness_gallery() {
            return bussiness_gallery;
        }

        public void setBussiness_gallery(List<BussinessGalleryBean> bussiness_gallery) {
            this.bussiness_gallery = bussiness_gallery;
        }

        public List<CertifiBean> getCertifi() {
            return certifi;
        }

        public void setCertifi(List<CertifiBean> certifi) {
            this.certifi = certifi;
        }

        public static class BussinessGalleryBean {
            private String bussiness_gallery;

            public String getBussiness_gallery() {
                return bussiness_gallery;
            }

            public void setBussiness_gallery(String bussiness_gallery) {
                this.bussiness_gallery = bussiness_gallery;
            }
        }

        public static class CertifiBean {
            private String certifi;

            public String getCertifi() {
                return certifi;
            }

            public void setCertifi(String certifi) {
                this.certifi = certifi;
            }
        }
    }
}
