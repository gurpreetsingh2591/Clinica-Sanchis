package com.eurosalud.pdp_cs.annotation;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({FragmentType.HOME_FRAGMENT, FragmentType.LUNCH_MENU_FRAGMENT, FragmentType.SNACK_MENU_FRAGMENT, FragmentType.MEETING_WITH_OFFICE_FRAGMENT,
        FragmentType.MEETING_WITH_TEACHER_FRAGMENT, FragmentType.MESSAGE_FROM_SCHOOL_FRAGMENT, FragmentType.MESSAGE_TO_TEACHER_FRAGMENT,
        FragmentType.MESSAGE_TO_OFFICE_FRAGMENT, FragmentType.SCHOOL_CALENDER_FRAGMENT})

public @interface FragmentType {
    String HOME_FRAGMENT = "HomeFragment";
    String LUNCH_MENU_FRAGMENT = "LunchMenuFragment";
    String SNACK_MENU_FRAGMENT = "SnackMenuFragment";
    String MEETING_WITH_OFFICE_FRAGMENT = "MeetingWithOfficeFragment";
    String MEETING_WITH_TEACHER_FRAGMENT = "MeetingWithTeacherFragment";
    String MESSAGE_FROM_SCHOOL_FRAGMENT = "MessageFromSchoolFragment";
    String MESSAGE_TO_TEACHER_FRAGMENT = "MessageToTeacherFragment";
    String MESSAGE_TO_OFFICE_FRAGMENT = "MessageToOfficeFragment";
    String SCHOOL_CALENDER_FRAGMENT = "SchoolCalenderFragment";

}
