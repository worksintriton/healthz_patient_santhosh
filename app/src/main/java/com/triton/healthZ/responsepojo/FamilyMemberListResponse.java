package com.triton.healthz.responsepojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FamilyMemberListResponse implements Serializable {


    /**
     * Status : Success
     * Message : Family details
     * Data : [{"health_issue":[""],"pic":[{"image":"http://35.165.75.97:3000/api/uploads/1643865952185.jpg"}],"_id":"61fb676dd014ed6b74d3f0ba","user_id":"61fb66fad014ed6b74d3f0b6","name":"Lavanya","gender":"Female","relation_type":"Friend","dateofbirth":"06-04-2000","anymedicalinfo":"Have diabetes.","covide_vac":"","weight":"60","delete_status":false,"createdAt":"2022-02-03T05:26:05.648Z","updatedAt":"2022-02-03T06:18:57.712Z","__v":0},{"health_issue":["OCD"],"pic":[{"image":"http://35.165.75.97:3000/api/uploads/1643869990070.jpg"}],"_id":"61fb788c3c8d2e6bf634f934","user_id":"61fb66fad014ed6b74d3f0b6","name":"Ju","gender":"Female","relation_type":"Daughter","dateofbirth":"13-04-2007","anymedicalinfo":"Health","covide_vac":"Yes","weight":"60","delete_status":false,"createdAt":"2022-02-03T06:39:08.762Z","updatedAt":"2022-02-03T06:39:08.762Z","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * health_issue : [""]
     * pic : [{"image":"http://35.165.75.97:3000/api/uploads/1643865952185.jpg"}]
     * _id : 61fb676dd014ed6b74d3f0ba
     * user_id : 61fb66fad014ed6b74d3f0b6
     * name : Lavanya
     * gender : Female
     * relation_type : Friend
     * dateofbirth : 06-04-2000
     * anymedicalinfo : Have diabetes.
     * covide_vac :
     * weight : 60
     * delete_status : false
     * createdAt : 2022-02-03T05:26:05.648Z
     * updatedAt : 2022-02-03T06:18:57.712Z
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

    public static class DataBean {
        private String _id;
        private String user_id;
        private String name;
        private String gender;
        private String relation_type;
        private String dateofbirth;
        private String anymedicalinfo;
        private String covide_vac;
        private String weight;
        private boolean delete_status;
        private String createdAt;
        private String updatedAt;
        private int __v;
        private boolean selected;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        private ArrayList<String> health_issue;
        private String health_issue_others;

        public String getHealth_issue_others() {
            return health_issue_others;
        }

        public void setHealth_issue_others(String health_issue_others) {
            this.health_issue_others = health_issue_others;
        }

        /**
         * image : http://35.165.75.97:3000/api/uploads/1643865952185.jpg
         */

        private List<PicBean> pic;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getRelation_type() {
            return relation_type;
        }

        public void setRelation_type(String relation_type) {
            this.relation_type = relation_type;
        }

        public String getDateofbirth() {
            return dateofbirth;
        }

        public void setDateofbirth(String dateofbirth) {
            this.dateofbirth = dateofbirth;
        }

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

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public boolean isDelete_status() {
            return delete_status;
        }

        public void setDelete_status(boolean delete_status) {
            this.delete_status = delete_status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public ArrayList<String> getHealth_issue() {
            return health_issue;
        }

        public void setHealth_issue(ArrayList<String> health_issue) {
            this.health_issue = health_issue;
        }

        public List<PicBean> getPic() {
            return pic;
        }

        public void setPic(List<PicBean> pic) {
            this.pic = pic;
        }

        public static class PicBean implements Serializable {
            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }
        }
    }
}
