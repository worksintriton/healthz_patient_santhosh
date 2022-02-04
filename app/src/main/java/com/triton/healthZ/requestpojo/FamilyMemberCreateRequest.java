package com.triton.healthz.requestpojo;

import java.util.List;

public class FamilyMemberCreateRequest {

    /**
     * anymedicalinfo : is good
     * covide_vac : No
     * dateofbirth : 03-02-2016
     * gender : Male
     * health_issue : ["Diabetes","OCD","Others"]
     * health_issue_others : fever
     * name : Deva
     * pic : [{"image":"http://35.165.75.97:3000/api/uploads/1643871957791.png"}]
     * relation_type : Father
     * user_id : 61b198a49e6f552291dbfa82
     * weight : 67
     */

    private String anymedicalinfo;
    private String covide_vac;
    private String dateofbirth;
    private String gender;
    private String health_issue_others;
    private String name;
    private String relation_type;
    private String user_id;
    private String weight;
    private List<String> health_issue;
    /**
     * image : http://35.165.75.97:3000/api/uploads/1643871957791.png
     */

    private List<PicBean> pic;

    public String getAnymedicalinfo() {
        return anymedicalinfo;
    }

    public void setAnymedicalinfo(String anymedicalinfo) {
        this.anymedicalinfo = anymedicalinfo;
    }

    public String getCovide_vac() {
        return covide_vac;
    }

    public void setCovide_vac(String covide_vac) {
        this.covide_vac = covide_vac;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealth_issue_others() {
        return health_issue_others;
    }

    public void setHealth_issue_others(String health_issue_others) {
        this.health_issue_others = health_issue_others;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelation_type() {
        return relation_type;
    }

    public void setRelation_type(String relation_type) {
        this.relation_type = relation_type;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<String> getHealth_issue() {
        return health_issue;
    }

    public void setHealth_issue(List<String> health_issue) {
        this.health_issue = health_issue;
    }

    public List<PicBean> getPic() {
        return pic;
    }

    public void setPic(List<PicBean> pic) {
        this.pic = pic;
    }

    public static class PicBean {
        private String image;



        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
