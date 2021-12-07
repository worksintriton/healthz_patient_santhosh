package com.triton.healthz.interfaces;

public interface SPServiceChckedListener {

    void onItemSPServiceCheck(int position, String specValue);

    void onItemSPServiceUnCheck(int position, String specValue);

}