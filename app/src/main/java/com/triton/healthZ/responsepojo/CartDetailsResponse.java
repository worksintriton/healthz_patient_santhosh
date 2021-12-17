package com.triton.healthz.responsepojo;

import java.io.Serializable;
import java.util.List;

public class CartDetailsResponse {


    /**
     * Status : Success
     * Message : Fetch cart details
     * Data : [{"_id":"61af540b9e6f552291dbfa70","user_id":"61aef2c658c631738117edf9","product_id":{"product_img":[{"product_img":"http://35.164.43.170:3000/api/uploads/1638874408182.jpg"}],"addition_detail":["additional details"],"_id":"61af3d669e6f552291dbfa6e","user_id":"61af3cea7a64122107fc8e49","cat_id":"6198b594518ad4520ab14792","thumbnail_image":"http://35.164.43.170:3000/api/uploads/1638874408182.jpg","product_name":"Sample","cost":192,"product_discription":"sample","condition":"organic","price_type":"unit","date_and_time":"07/12/2021 04:24 PM","threshould":"66","mobile_type":"Android","related":"","count":0,"status":"true","verification_status":"Not Verified","delete_status":false,"fav_status":false,"today_deal":true,"discount":1,"discount_amount":194,"discount_status":true,"discount_cal":194,"discount_start_date":"07-12-2021","discount_end_date":"15-12-2021","product_rating":5,"product_review":0,"updatedAt":"2021-12-07T11:14:22.213Z","createdAt":"2021-12-07T10:54:30.340Z","__v":0},"product_count":2,"updatedAt":"2021-12-07T12:31:07.478Z","createdAt":"2021-12-07T12:31:07.478Z","__v":0}]
     * prodouct_total : 388
     * shipping_charge : 0
     * discount_price : 4
     * grand_total : 384
     * prodcut_count : 1
     * prodcut_item_count : 2
     * Code : 200
     */


    private String user_id;
    private String shipping_details_id;
    private String date_of_booking;
    private String coupon_code;
    private String shipping_address_id;
    private String billling_address_id;
    private String shipping_address;
    private String billing_address;

    private String date_of_booking_display;
    private String payment_id ;

    private int original_price;
    private int coupon_discount_price;
    private int total_price;
    private String coupon_status;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getShipping_details_id() {
        return shipping_details_id;
    }

    public void setShipping_details_id(String shipping_details_id) {
        this.shipping_details_id = shipping_details_id;
    }

    public String getDate_of_booking() {
        return date_of_booking;
    }

    public void setDate_of_booking(String date_of_booking) {
        this.date_of_booking = date_of_booking;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getShipping_address_id() {
        return shipping_address_id;
    }

    public void setShipping_address_id(String shipping_address_id) {
        this.shipping_address_id = shipping_address_id;
    }

    public String getBillling_address_id() {
        return billling_address_id;
    }

    public void setBillling_address_id(String billling_address_id) {
        this.billling_address_id = billling_address_id;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getBilling_address() {
        return billing_address;
    }

    public void setBilling_address(String billing_address) {
        this.billing_address = billing_address;
    }

    public String getDate_of_booking_display() {
        return date_of_booking_display;
    }

    public void setDate_of_booking_display(String date_of_booking_display) {
        this.date_of_booking_display = date_of_booking_display;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public int getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(int original_price) {
        this.original_price = original_price;
    }

    public int getCoupon_discount_price() {
        return coupon_discount_price;
    }

    public void setCoupon_discount_price(int coupon_discount_price) {
        this.coupon_discount_price = coupon_discount_price;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getCoupon_status() {
        return coupon_status;
    }

    public void setCoupon_status(String coupon_status) {
        this.coupon_status = coupon_status;
    }

    private String Status;
    private String Message;
    private int prodouct_total;
    private int shipping_charge;
    private int discount_price;
    private int grand_total;
    private int prodcut_count;
    private int prodcut_item_count;
    private int Code;


    /**
     * _id : 61af540b9e6f552291dbfa70
     * user_id : 61aef2c658c631738117edf9
     * product_id : {"product_img":[{"product_img":"http://35.164.43.170:3000/api/uploads/1638874408182.jpg"}],"addition_detail":["additional details"],"_id":"61af3d669e6f552291dbfa6e","user_id":"61af3cea7a64122107fc8e49","cat_id":"6198b594518ad4520ab14792","thumbnail_image":"http://35.164.43.170:3000/api/uploads/1638874408182.jpg","product_name":"Sample","cost":192,"product_discription":"sample","condition":"organic","price_type":"unit","date_and_time":"07/12/2021 04:24 PM","threshould":"66","mobile_type":"Android","related":"","count":0,"status":"true","verification_status":"Not Verified","delete_status":false,"fav_status":false,"today_deal":true,"discount":1,"discount_amount":194,"discount_status":true,"discount_cal":194,"discount_start_date":"07-12-2021","discount_end_date":"15-12-2021","product_rating":5,"product_review":0,"updatedAt":"2021-12-07T11:14:22.213Z","createdAt":"2021-12-07T10:54:30.340Z","__v":0}
     * product_count : 2
     * updatedAt : 2021-12-07T12:31:07.478Z
     * createdAt : 2021-12-07T12:31:07.478Z
     * __v : 0
     */

    private List<DataBean> Data;

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

    public int getProdouct_total() {
        return prodouct_total;
    }

    public void setProdouct_total(int prodouct_total) {
        this.prodouct_total = prodouct_total;
    }

    public int getShipping_charge() {
        return shipping_charge;
    }

    public void setShipping_charge(int shipping_charge) {
        this.shipping_charge = shipping_charge;
    }

    public int getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(int discount_price) {
        this.discount_price = discount_price;
    }

    public int getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(int grand_total) {
        this.grand_total = grand_total;
    }

    public int getProdcut_count() {
        return prodcut_count;
    }

    public void setProdcut_count(int prodcut_count) {
        this.prodcut_count = prodcut_count;
    }

    public int getProdcut_item_count() {
        return prodcut_item_count;
    }

    public void setProdcut_item_count(int prodcut_item_count) {
        this.prodcut_item_count = prodcut_item_count;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable {
        private String _id;
        private String user_id;
        /**
         * product_img : [{"product_img":"http://35.164.43.170:3000/api/uploads/1638874408182.jpg"}]
         * addition_detail : ["additional details"]
         * _id : 61af3d669e6f552291dbfa6e
         * user_id : 61af3cea7a64122107fc8e49
         * cat_id : 6198b594518ad4520ab14792
         * thumbnail_image : http://35.164.43.170:3000/api/uploads/1638874408182.jpg
         * product_name : Sample
         * cost : 192
         * product_discription : sample
         * condition : organic
         * price_type : unit
         * date_and_time : 07/12/2021 04:24 PM
         * threshould : 66
         * mobile_type : Android
         * related :
         * count : 0
         * status : true
         * verification_status : Not Verified
         * delete_status : false
         * fav_status : false
         * today_deal : true
         * discount : 1
         * discount_amount : 194
         * discount_status : true
         * discount_cal : 194
         * discount_start_date : 07-12-2021
         * discount_end_date : 15-12-2021
         * product_rating : 5
         * product_review : 0
         * updatedAt : 2021-12-07T11:14:22.213Z
         * createdAt : 2021-12-07T10:54:30.340Z
         * __v : 0
         */

        private ProductIdBean product_id;
        private CatIdBean cat_id;
        private int product_count;
        private String updatedAt;
        private String createdAt;
        private int __v;

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

        public ProductIdBean getProduct_id() {
            return product_id;
        }

        public void setProduct_id(ProductIdBean product_id) {
            this.product_id = product_id;
        }

        public int getProduct_count() {
            return product_count;
        }

        public void setProduct_count(int product_count) {
            this.product_count = product_count;
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


        public CatIdBean getCat_id() {
            return cat_id;
        }

        public void setCat_id(CatIdBean cat_id) {
            this.cat_id = cat_id;
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

        public static class ProductIdBean implements Serializable{
            private String _id;
            private String user_id;
            private String cat_id;
            private String thumbnail_image;
            private String product_name;
            private int cost;
            private String product_discription;
            private String condition;
            private String price_type;
            private String date_and_time;
            private String threshould;
            private String mobile_type;
            private String related;
            private int count;
            private String status;
            private String verification_status;
            private boolean delete_status;
            private boolean fav_status;
            private boolean today_deal;
            private int discount;
            private int discount_amount;
            private boolean discount_status;
            private int discount_cal;
            private String discount_start_date;
            private String discount_end_date;
            private int product_rating;
            private int product_review;
            private String updatedAt;
            private String createdAt;
            private int __v;
            /**
             * product_img : http://35.164.43.170:3000/api/uploads/1638874408182.jpg
             */

            private List<ProductImgBean> product_img;
            private List<String> addition_detail;

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

            public String getCat_id() {
                return cat_id;
            }

            public void setCat_id(String cat_id) {
                this.cat_id = cat_id;
            }

            public String getThumbnail_image() {
                return thumbnail_image;
            }

            public void setThumbnail_image(String thumbnail_image) {
                this.thumbnail_image = thumbnail_image;
            }

            public String getProduct_name() {
                return product_name;
            }

            public void setProduct_name(String product_name) {
                this.product_name = product_name;
            }

            public int getCost() {
                return cost;
            }

            public void setCost(int cost) {
                this.cost = cost;
            }

            public String getProduct_discription() {
                return product_discription;
            }

            public void setProduct_discription(String product_discription) {
                this.product_discription = product_discription;
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

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }

            public String getThreshould() {
                return threshould;
            }

            public void setThreshould(String threshould) {
                this.threshould = threshould;
            }

            public String getMobile_type() {
                return mobile_type;
            }

            public void setMobile_type(String mobile_type) {
                this.mobile_type = mobile_type;
            }

            public String getRelated() {
                return related;
            }

            public void setRelated(String related) {
                this.related = related;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getVerification_status() {
                return verification_status;
            }

            public void setVerification_status(String verification_status) {
                this.verification_status = verification_status;
            }

            public boolean isDelete_status() {
                return delete_status;
            }

            public void setDelete_status(boolean delete_status) {
                this.delete_status = delete_status;
            }

            public boolean isFav_status() {
                return fav_status;
            }

            public void setFav_status(boolean fav_status) {
                this.fav_status = fav_status;
            }

            public boolean isToday_deal() {
                return today_deal;
            }

            public void setToday_deal(boolean today_deal) {
                this.today_deal = today_deal;
            }

            public int getDiscount() {
                return discount;
            }

            public void setDiscount(int discount) {
                this.discount = discount;
            }

            public int getDiscount_amount() {
                return discount_amount;
            }

            public void setDiscount_amount(int discount_amount) {
                this.discount_amount = discount_amount;
            }

            public boolean isDiscount_status() {
                return discount_status;
            }

            public void setDiscount_status(boolean discount_status) {
                this.discount_status = discount_status;
            }

            public int getDiscount_cal() {
                return discount_cal;
            }

            public void setDiscount_cal(int discount_cal) {
                this.discount_cal = discount_cal;
            }

            public String getDiscount_start_date() {
                return discount_start_date;
            }

            public void setDiscount_start_date(String discount_start_date) {
                this.discount_start_date = discount_start_date;
            }

            public String getDiscount_end_date() {
                return discount_end_date;
            }

            public void setDiscount_end_date(String discount_end_date) {
                this.discount_end_date = discount_end_date;
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

            public List<ProductImgBean> getProduct_img() {
                return product_img;
            }

            public void setProduct_img(List<ProductImgBean> product_img) {
                this.product_img = product_img;
            }

            public List<String> getAddition_detail() {
                return addition_detail;
            }

            public void setAddition_detail(List<String> addition_detail) {
                this.addition_detail = addition_detail;
            }

            public static class ProductImgBean implements Serializable{
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
}
