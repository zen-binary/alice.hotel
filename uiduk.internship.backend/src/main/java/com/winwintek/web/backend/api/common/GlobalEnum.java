package com.winwintek.web.backend.api.common;

public enum GlobalEnum {

    OWNER("DainLeaders"),
    /*************** [response default 정보] start ***************/
    DEFAULT_RESPONSE_CODE("200"),
    DEFAULT_RESPONSE_MESSAGE("정상 처리되었습니다."),
    /*************** [response default 정보] end ***************/

    /*************** [dbms name 정보] start ***************/
    DBMS_ORACLE("oracle"),
    DBMS_MYSQL("mysql"),
    DBMS_MARIADB("mariadb"),
    DBMS_SQL_SERVER("sqlserver"),
    DBMS_POSTGRESQL("postgresql"),
    /*************** [dbms name 정보] end ***************/

    /*************** [dbms name 정보] start ***************/
    USER_TYPE_CLIENT("CLIENT"), // 사용자(로그인 사용자)
    USER_TYPE_ADMINISTRATOR("ADMINISTRATOR"), // 관리자 (개발자)
    USER_TYPE_SPONSOR("SPONSOR"), // 제로
    USER_TYPE_CANTEENER("CANTEENER"), // 제로
    USER_TYPE_ZERO("ZERO"), // 제로
    /*************** [dbms name 정보] end ***************/



    STATE_Y("Y"),
    STATE_N("N"),

    STATE_P("처리"),
    STATE_U("미처리"),


    /*************** [Event 정보 마일리지 mileage] start ***************/
    ATTENDANCE("attendance"), // 출석체크
    /*************** [Event 정보 마일리지 mileage] end ***************/

    AnomyousUser_String("anonymousUser"),
    AnomyousUser_Long(1111111111111111111L),

    LOGIN_DIV_DODREAM("DD"),
    LOGIN_DIV_FACE_BOOK("FB"),
    LOGIN_DIV_KAKAOTALK("KT"),
    LOGIN_DIV_CANTEENER("CT"),
    LOGIN_DIV_SPONSOR("SP"),

    /************ Start Table/Prefix SYSTEM **************/

    // table SYS_LOGIN_HISTORY
    TABLE_SYS_LOGIN_HISTORY("SYS_LOGIN_HISTORY"),
    PREFIX_SYS_LOGIN_HISTORY("SLH"),

    // table SYS_API_USE_HIS
    TABLE_SYS_API_USE_HIS("SYS_API_USE_HIS"),
    PREFIX_SYS_API_USE_HIS("SAUH"),

    // table Category
    TABLE_CATEGORY("CATEGORY"),
    PREFIX_CATEGORY("CAT"),

    // table FILE_MNG
    TABLE_FILE_MNG("FILE_MNG"),
    PREFIX_FILE_MNG("FIM"),

    // table CPS_MENU_CONN_HIS
    TABLE_CPS_MENU_CONN_HIS("CPS_MENU_CONN_HIS"),
    PREFIX_CPS_MENU_CONN_HIS("CMCH"),

    // Login Event
    LOGIN_EVENT_SUCCESS_MSG("authentication success"),
    LOGIN_EVENT_FAIL_MSG("userId: "),

    // table PRE_TEST_ANS
    TABLE_PRE_TEST_ANS("PRE_TEST_ANS"),
    PREFIX_PRE_TEST_ANS("PTA"),

    // table CORPORATE_PRE_TEST
    TABLE_CORPORATE_PRE_TEST("CORPORATE_PRE_TEST"),
    PREFIX_CORPORATE_PRE_TEST("CPT"),

    /************ End Table/Prefix SYSTEM **************/


    /************ Start Table/Prefix Domain **************/

    //EVALUATION_STD_SEQ
    TABLE_EVALUATION_STD("EVALUATION_STD"),
    PREFIX_EVALUATION_STD("ESS"),

    //EVALUATION_STD_SEQ
    TABLE_STUDENT_EVALUATION("STUDENT_EVALUATION"),
    PREFIX_STUDENT_EVALUATION("SES"),

    // Intern Student
    TABLE_INTERN_STD("INTERN_STD"),
    PREFIX_INTERN_STD("INTE"),

    // CORPORATE_MEMBERS
    TABLE_CORPORATE_MEMBERS("CORPORATE_MEMBERS"),
    PREFIX_CORPORATE_MEMBERS("CORM"),
    // table STD_SUMMARY_REPORT
    TABLE_STD_SUMMARY_REPORTS("STD_SUMMARY_REPORT"),
    PREFIX_STD_SUMMARY_REPORTS("SSR"),

    // CORPORATE_MEMBERS
    TABLE_CORPORATE_MEMBERS_MANAGER("CORPORATE_MEMBERS_MANAGER"),
    PREFIX_CORPORATE_MEMBERS_MANAGER("COMM"),

    // Intern Plan
    TABLE_INTERN_PLAN("INTERN_PLAN"),
    PREFIX_INTERN_PLAN("IPL"),

    // Intern Plan Detail
    TABLE_INTERN_PLAN_DETAIL("INTERN_PLAN_DETAIL"),
    PREFIX_INTERN_PLAN_DETAIL("IPD"),

    // Intern Corporate Survey
    TABLE_INTERN_CORPORATE_SURVEY("INTERN_CORPORATE_SURVEY"),
    PREFIX_INTERN_CORPORATE_SURVEY("ICS"),

    // Intern Corporate Survey Ans
    TABLE_CORPORATE_SURVEY_ANS("CORPORATE_SURVEY_ANS"),
    PREFIX_CORPORATE_SURVEY_ANS("CSA"),

    // table GUIDANCE_REPORT
    TABLE_GUIDANCE_REPORT("GUIDANCE_REPORT"),
    PREFIX_GUIDANCE_REPORT("GRS"),

    // table INTERN_PROCESS_EVALUATION
    TABLE_INTERN_PROCESS_EVALUATION("INTERN_PROCESS_EVALUATION"),
    PREFIX_INTERN_PROCESS_EVALUATION("IPES"),

    // STD_SUMMARY_REPORT
    TABLE_STD_SUMMARY_REPORT("STD_SUMMARY_REPORT"),
    PREFIX_STD_SUMMARY_REPORT("SSR"),

    //table file board
    TABLE_DOC_ROOM("DOC_ROOM"),
    PREFIX_DOC_ROOM("DOCR"),


    //Notice
    TABLE_NOTICE("NOTICE"),
    PREFIX_NOTICE("NOTI"),

    // table INTERN_STD_SURVEY
    TABLE_INTERN_STD_SURVEY("INTERN_STD_SURVEY"),
    PREFIX_INTERN_STD_SURVEY("ISSS"),

    // table STD_SURVEY_QUS
    TABLE_STD_SURVEY_QUS("STD_SURVEY_QUS"),
    PREFIX_STD_SURVEY_QUS("SSQS"),

    // table file board
    TABLE_STD_SURVEY_ANS("STD_SURVEY_ANS"),
    PREFIX_STD_SURVEY_ANS("SSAS"),


    // table STD ATTENDANCE
    TABLE_STD_ATTENDANCE("STD_ATTENDANCE"),
    PREFIX_STD_ATTENDANCE("SATT"),

    // table STD_WEEK_REPORT
    TABLE_STD_WEEK_REPORT("STD_WEEK_REPORT"),
    PREFIX_STD_WEEK_REPORT("SWR"),

    // Table QnA
    TABLE_QNA("QNA"),
    PREFIX_QNA("QNA"),

    // table TRIPARTITE_AGREEMENT
    TABLE_TRIPARTITE_AGREEMENT("TRIPARTITE_AGREEMENT"),
    PREFIX_TRIPARTITE_AGREEMENT("TRIA"),

    /************ End Table/Prefix Domain **************/

    // Ip address
    INTERNAL_IP("Internal"),
    EXTERNAL_IP("External"),
    ;


    private Object value;

    private GlobalEnum(Object s) {
        value = s;
    }

    public <T> T getValue() {
        return (T) value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
