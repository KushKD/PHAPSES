package com.doi.himachal.enums;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public enum TaskType {
    UPLOAD_SCANNED_PASS(1),
    VERIFY_DETAILS(2),
    MANUAL_FORM_UPLOAD(3),
    GET_DISTRICT_VIA_STATE(4),
    GET_TEHSIL_VIA_DISTRICT(5),
    GET_BLOCK_VIA_DISTRICT(6),
    GET_GP_VIA_DISTRICT(7),
    GET_CATEGORIES(8),
    GET_BARRIER_VIA_DISTRICTS(9),
    GET_STATES(10),
    GET_DISTRICT_VIA_STATE_LOCAL(11),
    UPLOAD_SCANNED_PASS_OUT(12),
    GET_SUBCATEGORY(13);

    int value; private TaskType(int value) { this.value = value; }
}
