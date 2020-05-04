package com.doi.himachal.enums;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public enum TaskType {
    UPLOAD_SCANNED_PASS(1),
    VERIFY_DETAILS(2);

    int value; private TaskType(int value) { this.value = value; }
}
