package com.triton.healthZ.interfaces;

import com.triton.healthZ.responsepojo.DropDownListResponse;

import java.util.List;

public interface SpecTypeChckedListener {

    void onItemSpecCheck(int position, String specValue, List<DropDownListResponse.DataBean.SpecialzationBean> spectypedataBeanList);

    void onItemSpecUnCheck(int position, String specValue);

}