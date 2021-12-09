package com.triton.healthz.responsepojo;

import java.util.List;

public class PetAppointmentResponse {


    /**
     * Status : Success
     * Message : New Appointment List
     * Data : [{"_id":"61b1b31d6aefa963177a6c00","Booking_Id":"SP-1639035677015","appointment_for":"SP","photo":"","clinic_name":"","name":"Sample","appointment_type":"","cost":"500","appointment_time":"10-12-2021 02:00 AM","createdAt":"2021-12-09T07:41:17.031Z","updatedAt":"2021-12-09T07:41:17.039Z","relation_type":"Others","type":"","service_provider_name":"Sri so","Service_name":"Yoga","service_cost":"500","Booked_at":"10-12-2021 02:00 AM","missed_at":"","completed_at":"","user_rate":0,"user_feedback":"","status":"Incomplete","payment_method":"Online","appoint_patient_st":"","communication_type":"","doctor_name":"","start_appointment_status":"","user_id":"61aef2c658c631738117edf9","sp_id":"61af0c0958c631738117ee08","doctor_id":""},{"_id":"61b1b28e07b7cf610dfa9580","Booking_Id":"SP-1639035534401","appointment_for":"SP","photo":"","clinic_name":"","name":"Sample","appointment_type":"","cost":"500","appointment_time":"10-12-2021 02:15 AM","createdAt":"2021-12-09T07:38:54.413Z","updatedAt":"2021-12-09T07:38:54.417Z","relation_type":"Others","type":"","service_provider_name":"Sri so","Service_name":"Yoga","service_cost":"500","Booked_at":"10-12-2021 02:15 AM","missed_at":"","completed_at":"","user_rate":0,"user_feedback":"","status":"Incomplete","payment_method":"Online","appoint_patient_st":"","communication_type":"","doctor_name":"","start_appointment_status":"","user_id":"61aef2c658c631738117edf9","sp_id":"61af0c0958c631738117ee08","doctor_id":""},{"_id":"61b1b12707b7cf610dfa9579","Booking_Id":"HEL-1639035175184","appointment_for":"Doctor","photo":"","clinic_name":"Maddy Sam Clinic","name":"Sample","appointment_type":"Normal","communication_type":"Online","cost":"200","appointment_time":"10-12-2021 12:00 AM","createdAt":"2021-12-09T07:32:55.200Z","updatedAt":"2021-12-09T07:32:55.202Z","relation_type":"Others","payment_method":"Online","start_appointment_status":"Not Started","type":"","doctor_name":"Maddy Sam","service_provider_name":"","Service_name":"","service_cost":"","Booked_at":"10-12-2021 12:00 AM","missed_at":"","completed_at":"","user_rate":0,"user_feedback":"","status":"Incomplete","appoint_patient_st":"","user_id":"61aef2c658c631738117edf9","doctor_id":"61af0ec058c631738117ee0b","location_id":"","visit_type":"Clinic Visit","sp_id":""}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    /**
     * _id : 61b1b31d6aefa963177a6c00
     * Booking_Id : SP-1639035677015
     * appointment_for : SP
     * photo :
     * clinic_name :
     * name : Sample
     * appointment_type :
     * cost : 500
     * appointment_time : 10-12-2021 02:00 AM
     * createdAt : 2021-12-09T07:41:17.031Z
     * updatedAt : 2021-12-09T07:41:17.039Z
     * relation_type : Others
     * type :
     * service_provider_name : Sri so
     * Service_name : Yoga
     * service_cost : 500
     * Booked_at : 10-12-2021 02:00 AM
     * missed_at :
     * completed_at :
     * user_rate : 0
     * user_feedback :
     * status : Incomplete
     * payment_method : Online
     * appoint_patient_st :
     * communication_type :
     * doctor_name :
     * start_appointment_status :
     * user_id : 61aef2c658c631738117edf9
     * sp_id : 61af0c0958c631738117ee08
     * doctor_id :
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
        private String Booking_Id;
        private String appointment_for;
        private String photo;
        private String clinic_name;
        private String name;
        private String appointment_type;
        private String cost;
        private String appointment_time;
        private String createdAt;
        private String updatedAt;
        private String relation_type;
        private String type;
        private String service_provider_name;
        private String Service_name;
        private String service_cost;
        private String Booked_at;
        private String missed_at;
        private String completed_at;
        private int user_rate;
        private String user_feedback;
        private String status;
        private String payment_method;
        private String appoint_patient_st;
        private String communication_type;
        private String doctor_name;
        private String start_appointment_status;
        private String user_id;
        private String sp_id;
        private String doctor_id;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getBooking_Id() {
            return Booking_Id;
        }

        public void setBooking_Id(String Booking_Id) {
            this.Booking_Id = Booking_Id;
        }

        public String getAppointment_for() {
            return appointment_for;
        }

        public void setAppointment_for(String appointment_for) {
            this.appointment_for = appointment_for;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getClinic_name() {
            return clinic_name;
        }

        public void setClinic_name(String clinic_name) {
            this.clinic_name = clinic_name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAppointment_type() {
            return appointment_type;
        }

        public void setAppointment_type(String appointment_type) {
            this.appointment_type = appointment_type;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getAppointment_time() {
            return appointment_time;
        }

        public void setAppointment_time(String appointment_time) {
            this.appointment_time = appointment_time;
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

        public String getRelation_type() {
            return relation_type;
        }

        public void setRelation_type(String relation_type) {
            this.relation_type = relation_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getService_provider_name() {
            return service_provider_name;
        }

        public void setService_provider_name(String service_provider_name) {
            this.service_provider_name = service_provider_name;
        }

        public String getService_name() {
            return Service_name;
        }

        public void setService_name(String Service_name) {
            this.Service_name = Service_name;
        }

        public String getService_cost() {
            return service_cost;
        }

        public void setService_cost(String service_cost) {
            this.service_cost = service_cost;
        }

        public String getBooked_at() {
            return Booked_at;
        }

        public void setBooked_at(String Booked_at) {
            this.Booked_at = Booked_at;
        }

        public String getMissed_at() {
            return missed_at;
        }

        public void setMissed_at(String missed_at) {
            this.missed_at = missed_at;
        }

        public String getCompleted_at() {
            return completed_at;
        }

        public void setCompleted_at(String completed_at) {
            this.completed_at = completed_at;
        }

        public int getUser_rate() {
            return user_rate;
        }

        public void setUser_rate(int user_rate) {
            this.user_rate = user_rate;
        }

        public String getUser_feedback() {
            return user_feedback;
        }

        public void setUser_feedback(String user_feedback) {
            this.user_feedback = user_feedback;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }

        public String getAppoint_patient_st() {
            return appoint_patient_st;
        }

        public void setAppoint_patient_st(String appoint_patient_st) {
            this.appoint_patient_st = appoint_patient_st;
        }

        public String getCommunication_type() {
            return communication_type;
        }

        public void setCommunication_type(String communication_type) {
            this.communication_type = communication_type;
        }

        public String getDoctor_name() {
            return doctor_name;
        }

        public void setDoctor_name(String doctor_name) {
            this.doctor_name = doctor_name;
        }

        public String getStart_appointment_status() {
            return start_appointment_status;
        }

        public void setStart_appointment_status(String start_appointment_status) {
            this.start_appointment_status = start_appointment_status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getSp_id() {
            return sp_id;
        }

        public void setSp_id(String sp_id) {
            this.sp_id = sp_id;
        }

        public String getDoctor_id() {
            return doctor_id;
        }

        public void setDoctor_id(String doctor_id) {
            this.doctor_id = doctor_id;
        }
    }
}
