package com.alice.hotel.api.common;

public class Constants {

    public static String PW_DEAULT = "dainls123!@#";

    public static String STATE_Y = "Y";

    public static String STATE_N = "N";

    public static int STATE_0 = 0;

    public static int STATE_1 = 1;

    public static String SORT_ASC = "asc";

    public static String SORT_DESC = "desc";

    public static int DELETE_FLAG_ACTIVE = 1;

    public static int DELETE_FLAG_INACTIVE = 0;

    public static String START_YEAR = "2024";

    public static String CHOOSE_RADIO_ALL = "ALL";

    public static String USER_DIV_STAFF = "stf";

    public static String USER_DIV_STD = "std";

    public static String USER_DIV_OUT = "OUT";

    public static String USER_DIV_ADM = "ADM";

    public static String USER_DIV_GUEST = "GUEST";

    public static String USER_DIV_OUT_CHECK = "o";

    public static String USER_DIV_ADM_CHECK = "a";

    public static String USER_DIV_GUEST_CHECK = "g";

    public static String USER_DIV_STD_CHECK = "s";

    public static String USER_DIV_PROF_CHECK = "p";

    public static String USER_DIV_OUT_TOKEN = "to";

    public static String USER_DIV_ADM_TOKEN = "ta";

    public static String PROCESS_STATUS = "PROST00";

    public static String TITLE = "TITLE00";

    public static String UNKNOWN = "Unknown";

    //Role member
    public static Long ROLE_PROFESSOR = 4L;

    public static Long ROLE_BUSINESS = 2L;

    public static String NO_LOGIN_USER = "NO LOGIN USER";

    public static String FORMAT_DATE_YYYY_MM_DD = "yyyy-MM-dd";

    public static String INTERNSHIP_PLAN_SECTION_NAME = "CORM_CI";

    public static String INTERN_PLAN_STATUS_ACCEPTED = "INTERN_PLAN_02";

    public static String INTERN_STUDENT_APPROVE_STATUS_PENDING = "STD_A01_P01";

    public static String INTERN_STUDENT_INTERNSHIP_STATUS_DEFAULT = "STD_I01_P01";

    public static String INTERN_STUDENT_APPROVE_STATUS_CANCEL = "STD_A01_P08";

    // Start CD ID Status approve intern plan
    public static String CD_INTERN_PLAN_WAIT_APPROVAL = "INTERN_PLAN_01";

    public static String CD_INTERN_PLAN_APPROVAL = "INTERN_PLAN_02";

    public static String CD_INTERN_PLAN_REJECT_APPROVAL = "INTERN_PLAN_07";
    // End CD ID Status approve intern plan

    // Start CD ID Status approve student intern
    public static String CD_STD_WAIT_APPROVAL = "STD_A01_P01";

    public static String CD_STD_APPROVAL = "STD_A01_P02";

    public static String CD_STD_NOT_APPROVAL = "STD_A01_P07";

    public static String CD_STD_CANCEL_JOIN = "STD_A01_P08";
    // End CD ID Status approve student intern

    //Role CORPORATE MEMBERS
    public static Long ROLE_CORPORATE_MEMBERS = 4L;

    //CORPORATE STATUS
    public static String CORM_APPROVE_STATUS_01 = "CORM_01";

    public static String INTERN_PLAN_01 = "INTERN_PLAN_01";

    //Constant status approve INTERN_STD
    public static String INTERN_STD_APPROVE_STATUS = "STD_A01_P02";

    public static String STATUS_APPROVE_02 = "STD_A01_P02";

    public static String IS_BLANK = "";

    public static String INTERN_STUDENT_FILE_CATEGORY = "img";

    public static String INTERN_STUDENT_SECTION_NAME = "INTERN_PLAN_THUMBNAIL";

    //Start status approve INTERNSHIP_STATUS
    public static String COMPLETE = "STD_I01_P02";

    public static String UNFINISHED = "STD_I01_P03";

    public static String RESERVE = "STD_I01_P04";

    public static String WAIT_COMPLETE = "STD_I01_P01";

    //End status approve INTERNSHIP_STATUS

    // Start CD ID Status approve corporate approval
    public static String CD_CORM_WAIT_APPROVAL = "CORM_01";

    public static String CD_CORM_APPROVAL = "CORM_02";
    // End CD ID Status approve corporate approval

    // Start CD ID Status std attendance
    public static String CD_STD_ATTENDANCE = "ATTENDANCE_01";

    public static String CD_STD_TARDINESS = "ATTENDANCE_02";

    public static String CD_STD_ABSENCE = "ATTENDANCE_03";

    public static String CD_STD_HOLIDAY = "ATTENDANCE_04";
    // End CD ID Status std attendance

    public static String FORMAT_DATE_MM_DD = "MM/dd";


    // File Mng Sign Corm
    public static String INTERNSHIP_PLAN_FILE_CATEGORY = "img";

    public static String SIGN_SECTION_NAME = "CORM_SIGN";

    public static String SIGN_STUDENT_SECTION_NAME = "STD_SIGN";

    //Constant status approve STD_ATTENDANCE
    public static String STATUS_ABSENCE_ATTENDANCE = "ATTENDANCE_03";

    public static String STATUS_HOLIDAY_ATTENDANCE = "ATTENDANCE_04";

    public static String TRIPARTITE_CORM_SIGN_CATEGORY = "img";

    public static String TRIPARTITE_CORM_SIGN_SECTION = "CORM_SIGN";

    // Start CD ID Status student intern
    public static String CD_STD_INTERN_WAIT_COMPLETE = "STD_I01_P01";

    public static String CD_STD_INTERN_COMPLETE = "STD_I01_P02";

    public static String CD_STD_INTERN_NOT_COMPLETE = "STD_I01_P03";

    public static String CD_STD_INTERN_CANCEL_BACKUPS = "STD_I01_P04";
    // End CD ID Status student intern

    //ROLE
    public static String ROLE_STUDENT = "ROLE_3";

    public static String ROLE_CORPORATE = "ROLE_4";
}
