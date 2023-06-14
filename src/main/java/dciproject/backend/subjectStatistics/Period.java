package dciproject.backend.subjectStatistics;

import java.util.HashMap;

public enum Period {

    //////////////////////////////////////2020-1///////////////////////////////////////
    CORRECT_DATE_START_2020_01("20200316090000"),
    CORRECT_DATE_END_2020_01("20200320180000"),
    SHYR_2_DATE_START_2020_01("20200204083000"),
    SHYR_2_DATE_END_2020_01("20200204113000"),
    SHYR_3_DATE_START_2020_01("20200203143000"),
    SHYR_3_DATE_END_2020_01("20200203173000"),
    SHYR_4_DATE_START_2020_01("20200203083000"),
    SHYR_4_DATE_END_2020_01("20200203113000"),

    //////////////////////////////////////2020-2///////////////////////////////////////
    CORRECT_DATE_START_2020_02("20200901090000"),
    CORRECT_DATE_END_2020_02("20200907180000"),

    SHYR_1_DATE_START_2020_02("20200804143000"),
    SHYR_1_DATE_END_2020_02("20200804173000"),
    SHYR_2_DATE_START_2020_02("20200804083000"),
    SHYR_2_DATE_END_2020_02("20200804113000"),
    SHYR_3_DATE_START_2020_02("20200803143000"),
    SHYR_3_DATE_END_2020_02("20200803173000"),
    SHYR_4_DATE_START_2020_02("20200803083000"),
    SHYR_4_DATE_END_2020_02("20200803113000"),
    SHYR_ENTIRE_START_2020_02("20200805090000"),
    SHYR_ENTIRE_END_2020_02("20200807180000"),

    //////////////////////////////////////2021-1///////////////////////////////////////
    CORRECT_DATE_START_2021_01("20210302090000"),
    CORRECT_DATE_END_2021_01("20210308180000"),
    SHYR_1_DATE_START_2021_01("20210218143000"),
    SHYR_1_DATE_END_2021_01("20210218173000"),
    SHYR_2_DATE_START_2021_01("20210218083000"),
    SHYR_2_DATE_END_2021_01("20210218113000"),
    SHYR_3_DATE_START_2021_01("20210217143000"),
    SHYR_3_DATE_END_2021_01("20210217173000"),
    SHYR_4_DATE_START_2021_01("20210217083000"),
    SHYR_4_DATE_END_2021_01("20210217113000"),
    SHYR_ENTIRE_START_2021_01("20210219090000"),
    SHYR_ENTIRE_END_2021_01("20210222180000"),

    //////////////////////////////////////2021-2///////////////////////////////////////
    CORRECT_DATE_START_2021_02("20210901090000"),
    CORRECT_DATE_END_2021_02("20210907235900"),
    SHYR_1_DATE_START_2021_02("20210810150000"),
    SHYR_1_DATE_END_2021_02("20210810170000"),
    SHYR_2_DATE_START_2021_02("20210810093000"),
    SHYR_2_DATE_END_2021_02("20210810113000"),
    SHYR_3_DATE_START_2021_02("20210809150000"),
    SHYR_3_DATE_END_2021_02("20210809170000"),
    SHYR_4_DATE_START_2021_02("20210809093000"),
    SHYR_4_DATE_END_2021_02("20210809113000"),
    SHYR_ENTIRE_START_2021_02("20210811090000"),
    SHYR_ENTIRE_END_2021_02("20210813180000"),

    //////////////////////////////////////2022-1///////////////////////////////////////
    CORRECT_DATE_START_2022_01("20220302090000"),
    CORRECT_DATE_END_2022_01("20220308235900"),
    SHYR_1_DATE_START_2022_01("20220208150000"),
    SHYR_1_DATE_END_2022_01("20220208170000"),
    SHYR_2_DATE_START_2022_01("20220208093000"),
    SHYR_2_DATE_END_2022_01("20220208113000"),
    SHYR_3_DATE_START_2022_01("20220207150000"),
    SHYR_3_DATE_END_2022_01("20220207170000"),
    SHYR_4_DATE_START_2022_01("20220207093000"),
    SHYR_4_DATE_END_2022_01("20220207113000"),
    SHYR_ENTIRE_START_2022_01("20220209090000"),
    SHYR_ENTIRE_END_2022_01("20220211180000"),

    //////////////////////////////////////2022-1///////////////////////////////////////
    CORRECT_DATE_START_2022_02("20220901090000"),
    CORRECT_DATE_END_2022_02("20220907180000"),
    SHYR_1_DATE_START_2022_02("20220809150000"),
    SHYR_1_DATE_END_2022_02("20220809170000"),
    SHYR_2_DATE_START_2022_02("20220809093000"),
    SHYR_2_DATE_END_2022_02("20220809113000"),
    SHYR_3_DATE_START_2022_02("20220808150000"),
    SHYR_3_DATE_END_2022_02("20220808170000"),
    SHYR_4_DATE_START_2022_02("20220808093000"),
    SHYR_4_DATE_END_2022_02("20220808113000"),
    SHYR_ENTIRE_START_2022_02("20220810150000"),
    SHYR_ENTIRE_END_2022_02("20220812180000");

    final private String date;

    public String getDate(){
        return date;
    }

    public static String[] getPeriod(int year, int shtm, int shyr){
        return switch (shyr){
            case 1 -> switch (year){
                case 2020 ->
                        new String[] {SHYR_1_DATE_START_2020_02.getDate(),SHYR_1_DATE_END_2020_02.getDate()};
                case 2021 -> shtm==1 ?
                        new String[] {SHYR_1_DATE_START_2021_01.getDate(),SHYR_1_DATE_END_2021_01.getDate()}:
                        new String[] {SHYR_1_DATE_START_2021_02.getDate(),SHYR_1_DATE_END_2021_02.getDate()};
                case 2022 -> shtm==1 ?
                        new String[] {SHYR_1_DATE_START_2022_01.getDate(),SHYR_1_DATE_END_2022_01.getDate()}:
                        new String[] {SHYR_1_DATE_START_2022_02.getDate(),SHYR_1_DATE_END_2022_02.getDate()};
                default -> null;
            };
            case 2 ->switch (year){
                case 2020 -> shtm==1 ?
                        new String[] {SHYR_2_DATE_START_2020_01.getDate(),SHYR_2_DATE_END_2020_01.getDate()}:
                        new String[] {SHYR_2_DATE_START_2020_02.getDate(),SHYR_2_DATE_END_2020_02.getDate()};
                case 2021 -> shtm==1 ?
                        new String[] {SHYR_2_DATE_START_2021_01.getDate(),SHYR_2_DATE_END_2021_01.getDate()}:
                        new String[] {SHYR_2_DATE_START_2021_02.getDate(),SHYR_2_DATE_END_2021_02.getDate()};
                case 2022 -> shtm==1 ?
                        new String[] {SHYR_2_DATE_START_2022_01.getDate(),SHYR_2_DATE_END_2022_01.getDate()}:
                        new String[] {SHYR_2_DATE_START_2022_02.getDate(),SHYR_2_DATE_END_2022_02.getDate()};
                default -> null;
            };
            case 3 ->switch (year){
                case 2020 -> shtm==1 ?
                        new String[] {SHYR_3_DATE_START_2020_01.getDate(),SHYR_3_DATE_END_2020_01.getDate()}:
                        new String[] {SHYR_3_DATE_START_2020_02.getDate(),SHYR_3_DATE_END_2020_02.getDate()};
                case 2021 -> shtm==1 ?
                        new String[] {SHYR_3_DATE_START_2021_01.getDate(),SHYR_3_DATE_END_2021_01.getDate()}:
                        new String[] {SHYR_3_DATE_START_2021_02.getDate(),SHYR_3_DATE_END_2021_02.getDate()};
                case 2022 -> shtm==1 ?
                        new String[] {SHYR_3_DATE_START_2022_01.getDate(),SHYR_3_DATE_END_2022_01.getDate()}:
                        new String[] {SHYR_3_DATE_START_2022_02.getDate(),SHYR_3_DATE_END_2022_02.getDate()};
                default -> null;
            };
            case 4 ->switch (year){
                case 2020 -> shtm==1 ?
                        new String[] {SHYR_4_DATE_START_2020_01.getDate(),SHYR_4_DATE_END_2020_01.getDate()}:
                        new String[] {SHYR_4_DATE_START_2020_02.getDate(),SHYR_4_DATE_END_2020_02.getDate()};
                case 2021 -> shtm==1 ?
                        new String[] {SHYR_4_DATE_START_2021_01.getDate(),SHYR_4_DATE_END_2021_01.getDate()}:
                        new String[] {SHYR_4_DATE_START_2021_02.getDate(),SHYR_4_DATE_END_2021_02.getDate()};
                case 2022 -> shtm==1 ?
                        new String[] {SHYR_4_DATE_START_2022_01.getDate(),SHYR_4_DATE_END_2022_01.getDate()}:
                        new String[] {SHYR_4_DATE_START_2022_02.getDate(),SHYR_4_DATE_END_2022_02.getDate()};
                default -> null;
            };
            case 5 ->switch (year){ // 2020 entire 없음
                case 2021 -> shtm==1 ?
                        new String[] {SHYR_ENTIRE_START_2021_01.getDate(),SHYR_ENTIRE_END_2021_01.getDate()}:
                        new String[] {SHYR_ENTIRE_START_2021_02.getDate(),SHYR_ENTIRE_END_2021_02.getDate()};
                case 2022 -> shtm==1 ?
                        new String[] {SHYR_ENTIRE_START_2022_01.getDate(),SHYR_ENTIRE_END_2022_01.getDate()}:
                        new String[] {SHYR_ENTIRE_START_2022_02.getDate(),SHYR_ENTIRE_END_2022_02.getDate()};
                default -> null;
            };
            case 6 ->switch (year){ // 정정기간
                case 2020 -> shtm==1 ?
                        new String[] {CORRECT_DATE_START_2020_01.getDate(),CORRECT_DATE_END_2020_01.getDate()}:
                        new String[] {CORRECT_DATE_START_2020_02.getDate(),CORRECT_DATE_END_2020_02.getDate()};
                case 2021 -> shtm==1 ?
                        new String[] {CORRECT_DATE_START_2021_01.getDate(),CORRECT_DATE_END_2021_01.getDate()}:
                        new String[] {CORRECT_DATE_START_2021_02.getDate(),CORRECT_DATE_END_2021_02.getDate()};
                case 2022 -> shtm==1 ?
                        new String[] {CORRECT_DATE_START_2022_01.getDate(),CORRECT_DATE_END_2022_01.getDate()}:
                        new String[] {CORRECT_DATE_START_2022_02.getDate(),CORRECT_DATE_END_2022_02.getDate()};
                default -> null;
            };
            default -> null;
        };
    }

    private Period(String date){
        this.date=date;
    }

}
