package com.triton.healthz.requestpojo;

public class FetchByIdRequest {

    /**
     * user_id : 603e27792c2b43125f8cb802
     * product_id : 6034d6a5888af7628e7e17d4
     */

    private String user_id;
    private String product_id;
    private String cat_id;
    private String search_string;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getSearch_string() {
        return search_string;
    }

    public void setSearch_string(String search_string) {
        this.search_string = search_string;
    }
}
