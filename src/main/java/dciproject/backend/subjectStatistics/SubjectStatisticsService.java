package dciproject.backend.subjectStatistics;


import dciproject.backend.SingleToneResources;
import dciproject.backend.classRegistration.ClassRegistration;
import dciproject.backend.classRegistration.ClassRegistrationListStrategy;
import dciproject.backend.classRegistration.ClassRegistrationService;
import dciproject.backend.entireSubjects.EntireSubject;
import dciproject.backend.entireSubjects.EntireSubjectListStrategy;
import dciproject.backend.entireSubjects.EntireSubjectService;
import dciproject.backend.entireSubjects.entireSubject_2020.EntireSubject_2020;
import dciproject.backend.entireSubjects.entireSubject_2021.EntireSubject_2021;
import dciproject.backend.entireSubjects.entireSubject_2022.EntireSubject_2022;
import dciproject.backend.keywordSet.KeywordSet;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
@Slf4j
public class SubjectStatisticsService {
    private final SubjectStatisticsRepository subjectStatisticsRepository;
    private final EntityManager entityManager;


    public SubjectStatisticsService(SubjectStatisticsRepository subjectStatisticsRepository, EntityManager entityManager) {
        this.subjectStatisticsRepository = subjectStatisticsRepository;
        this.entityManager = entityManager;
    }

    public SubjectStatistics save(SubjectStatistics entity) {
        return subjectStatisticsRepository.save(entity);
    }



    private String date(long date) {
        return String.valueOf(date);
    }

    public void databaseBuild() {
        DecimalFormat formatter=new DecimalFormat("#.##");

        EntireSubjectListStrategy subjectStrategy=SingleToneResources.entireSubjectListStrategy;
        ClassRegistrationListStrategy classStrategy=SingleToneResources.classRegistrationListStrategy;

        for (int year = 2020; year < 2023; year++) {
            subjectStrategy.switchStrategy(year);
            classStrategy.switchStrategy(year); // year 정보에 대해 strategy 선택

            List<EntireSubject> entireSubjectList = subjectStrategy.getStrategy();
            List<ClassRegistration> classRegistrations = classStrategy.getStrategy();

            log.info("STRATEGE subject: {}", entireSubjectList.size());
            log.info("STRATEGE registration: {}", classRegistrations.size());

            for (EntireSubject subject : entireSubjectList) { // 모든 수강 과목에 대해서 진행
                String subjectID = subject.getSubjectID(); // 현재 과목 ID
                List<ClassRegistration> registrationListById = // 현재 과목 ID의 수강신청 정보
                        classRegistrations.stream().filter(registration -> registration.getSubjectID().equals(subjectID)).toList();

                log.info("listsize: {}", registrationListById.size());

                if (registrationListById.size() < 1) continue;

                int SHTM = subject.getShtm().charAt(0) - '0'; // 현재 학기
                String[] correctedPeriod = Period.getPeriod(year, SHTM, 6); // 현재 년도 학기의 정정기간

                int correctedNum = // 현재 년도 학기의 정정인원
                        getRegistrationNumInRange(registrationListById, correctedPeriod[0], correctedPeriod[1]); // 정정인원

                int competitionLevel = 0; // 선호도 수준
                double totalCompetitionRate = 0; // 선호도, A구간 / 총 과목 수

                int[] totalRegistrationNum = {0}; // 해당 과목의 모든 수강신청 수
                registrationListById.forEach(registration -> totalRegistrationNum[0] += registration.getRegistrationNumber());

                int totalRegistrationOfA1 = 0; //A1 신청 인원
                int totalRegistrationOfA2 = 0; //A2 신청 인원
                int totalRegistrationOfA3 = 0; //A3 신청 인원
                int totalRegistration_A = 0;// A구간 총 신청 인원

                int totalRegistrationOfB1 = 0; // B1 신청 인원
                int totalRegistrationOfB2 = 0; // B2 신청 인원
                int totalRegistration_B = 0; // B구간 총 신청 인원

                String[] periodOfSHYR;

                if (subject.getCptnDivNm().contains("전공")) { // 전공일 경우
                    int SHYR = Integer.parseInt(subject.getTrgtShyr());
                    boolean isEntireMode = false;

                    while (SHYR != 5) {
                        if (isEntireMode) SHYR = 5;
                        periodOfSHYR = Period.getPeriod(year, SHTM, SHYR);

                        if (periodOfSHYR == null) continue;

                        long startDate = Long.parseLong(periodOfSHYR[0]); // 해당 학년의 수강신청 시작, 혹은 전학년 수강신청 시작
                        long endDate = Long.parseLong(periodOfSHYR[1]); // 해당 학년의 수강신청 종료, 혹은 전학년 수강신청 종료

                        long[] rangeOfA1 = {startDate, startDate + 15}; // ex)20220217093015 -> 09:30:15
                        long[] rangeOfA2 = {startDate + 15 + 1, startDate + 30}; // ex)20220217093015 -> 09:30:15
                        long[] rangeOfA3 = {startDate + 30 + 1, startDate + 100}; // ex)20220217093100 -> 09:31:00
                        // 시작시간으로 부터 1분대 구간을 확인, 이를 집중요청기간으로 정의
                        // 집중요청구간은 A로 정의, A1(00초~15초), A2(16초~30초), A3(30초~60초)

                        long[] rangeOfB1 = {rangeOfA3[1] + 1, rangeOfA3[1] + 500};
                        long[] rangeOfB2 = {rangeOfB1[1] + 1, endDate};

                        // 집중요청구간 이후의 구간을 B로 정의, B1(1분~5분) B2(5분~마감)

                        int registrationOfA1 = //A1 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfA1[0]), date(rangeOfA1[1]));
                        int registrationOfA2 = //A2 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfA2[0]), date(rangeOfA2[1]));
                        int registrationOfA3 = //A3 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfA3[0]), date(rangeOfA3[1]));
                        int registration_A = // A구간 총 신청 인원
                                registrationOfA1 + registrationOfA2 + registrationOfA3;

                        int registrationOfB1 = // B1 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfB1[0]), date(rangeOfB1[1]));
                        int registrationOfB2 = // B2 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfB2[0]), date(rangeOfB2[1]));
                        int registration_B = // B구간 총 신청 인원
                                registrationOfB1 + registrationOfB2;

                        double currentCompetitionRate =
                                (double) registration_A / totalRegistrationNum[0]; // 현재 학년의 선호도

                        totalCompetitionRate += currentCompetitionRate;

                        totalRegistrationOfA1 += registrationOfA1;
                        totalRegistrationOfA2 += registrationOfA2;
                        totalRegistrationOfA3 += registrationOfA3;
                        totalRegistration_A += registration_A;

                        totalRegistrationOfB1 += registrationOfB1;
                        totalRegistrationOfB2 += registrationOfB2;
                        totalRegistration_B += registration_B;

                        isEntireMode = true;
                    }

                    int[] valueSetOfA = {
                            totalRegistrationOfA1,
                            totalRegistrationOfA2,
                            totalRegistrationOfA3,
                            totalRegistration_A,
                    };
                    int[] valueSetOfB = {
                            totalRegistrationOfB1,
                            totalRegistrationOfB2,
                            totalRegistration_B,
                    };

                    competitionLevel =
                            calculateCompetitionValue(valueSetOfA, valueSetOfB, totalRegistrationNum[0], totalCompetitionRate).getLevel();

                } else {
                    for (int SHYR = 1; SHYR < 6; SHYR++) {
                        log.info("period: {}, {}, {}", year, SHTM, SHYR);
                        periodOfSHYR = Period.getPeriod(year, SHTM, SHYR);

                        if (periodOfSHYR == null) continue;

                        long startDate = Long.parseLong(periodOfSHYR[0]); // 해당 학년의 수강신청 시작, 혹은 전학년 수강신청 시작
                        long endDate = Long.parseLong(periodOfSHYR[1]); // 해당 학년의 수강신청 종료, 혹은 전학년 수강신청 종료

                        long[] rangeOfA1 = {startDate, startDate + 15}; // ex)20220217093015 -> 09:30:15
                        long[] rangeOfA2 = {startDate + 15 + 1, startDate + 30}; // ex)20220217093015 -> 09:30:15
                        long[] rangeOfA3 = {startDate + 30 + 1, startDate + 100}; // ex)20220217093100 -> 09:31:00
                        // 시작시간으로 부터 1분대 구간을 확인, 이를 집중요청기간으로 정의
                        // 집중요청구간은 A로 정의, A1(00초~15초), A2(16초~30초), A3(30초~60초)

                        long[] rangeOfB1 = {rangeOfA3[1] + 1, rangeOfA3[1] + 500};
                        long[] rangeOfB2 = {rangeOfB1[1] + 1, endDate};

                        // 집중요청구간 이후의 구간을 B로 정의, B1(1분~5분) B2(5분~마감)

                        int registrationOfA1 = //A1 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfA1[0]), date(rangeOfA1[1]));
                        int registrationOfA2 = //A2 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfA2[0]), date(rangeOfA2[1]));
                        int registrationOfA3 = //A3 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfA3[0]), date(rangeOfA3[1]));
                        int registration_A = // A구간 총 신청 인원
                                registrationOfA1 + registrationOfA2 + registrationOfA3;

                        int registrationOfB1 = // B1 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfB1[0]), date(rangeOfB1[1]));
                        int registrationOfB2 = // B2 신청 인원
                                getRegistrationNumInRange(registrationListById, date(rangeOfB2[0]), date(rangeOfB2[1]));
                        int registration_B = // B구간 총 신청 인원
                                registrationOfB1 + registrationOfB2;

                        double currentCompetitionRate =
                                (double) registration_A / totalRegistrationNum[0]; // 현재 학년의 선호도

                        totalCompetitionRate += currentCompetitionRate;

                        totalRegistrationOfA1 += registrationOfA1;
                        totalRegistrationOfA2 += registrationOfA2;
                        totalRegistrationOfA3 += registrationOfA3;
                        totalRegistration_A += registration_A;

                        totalRegistrationOfB1 += registrationOfB1;
                        totalRegistrationOfB2 += registrationOfB2;
                        totalRegistration_B += registration_B;
                    }
                    // 교양 및 일선의 경우 (해당 학년 집중요청기간 신청인원) / (해당 학년 총 신청 인원 수) 를 구하고
                    //1,2,3,4,전학년(5) 순으로 기간 얻는다.
                    int[] valueSetOfA = {
                            totalRegistrationOfA1,
                            totalRegistrationOfA2,
                            totalRegistrationOfA3,
                            totalRegistration_A,
                    };
                    int[] valueSetOfB = {
                            totalRegistrationOfB1,
                            totalRegistrationOfB2,
                            totalRegistration_B,
                    };
                    competitionLevel =
                            calculateCompetitionValue(valueSetOfA, valueSetOfB, totalRegistrationNum[0],totalCompetitionRate).getLevel();
                    // (각 학년의 A구간 합) / (현재 과목의 전체 수강신청 수) = 현재 과목의 선호도
                }
                SubjectStatistics newSubjectStatistics =
                        SubjectStatistics.builder().subjectID(subjectID).
                                comp_rate(Double.parseDouble(formatter.format(totalCompetitionRate))).
                                comp_level(competitionLevel).
                                correctedNum(correctedNum).
                                correctedRate(Double.parseDouble(formatter.format((double) correctedNum / totalRegistrationNum[0]))).
                                totalNum(totalRegistrationNum[0]).build();

                log.info("ENTITY: {}", newSubjectStatistics);
                this.save(newSubjectStatistics); // 통계정보 엔티티 저장
            }
        }
    }


    public CompetitionRate calculateCompetitionValue(int[] valueSetOfA, int[] valueSetOfB, int totalOfEntire, double totalCompetitionRate) {
        int A1 = valueSetOfA[0];
        int A2 = valueSetOfA[1];
        int A3 = valueSetOfA[2];
        int totalOfA = valueSetOfA[3];

        int B1 = valueSetOfB[0];
        int B2 = valueSetOfB[1];
        int totalOfB = valueSetOfB[2];

        double N = 0.5; // 최소 선호도를 만족하는 A의 비율 N
        int K= 20; // 최소 표본을 만족하는 K

        // 단 A가 최종인원의 N% 이상이어야 함.

        if(K > totalOfEntire){
            return CompetitionRate.LEVEL0; // 최소 수준을 만족하지 못함
        } else if (totalOfA <= totalOfB) {
            return CompetitionRate.LEVEL1; // A<=B일 때 낮음
        } else {
            if (N > totalCompetitionRate) {
                return CompetitionRate.LEVEL1; // A의 비율이 N%가 안될 경우, 낮음
            } else if (((double) A1 / totalOfA) >= 0.7) {
                return CompetitionRate.LEVEL4; // A1의 비율이 A의 70% 이상 일 경우, 매우 높음 (0~15초)
            } else if (((double) (A1 + A2) / totalOfA) >= 0.7) {
                return CompetitionRate.LEVEL3; // (A1+A2)의 비율이 A의 70% 이상 일 경우, 높음 (15~30초)
            } else {
                return CompetitionRate.LEVEL2; // 그 외의 경우, 보통
            }
        }
    }

    public int getRegistrationNumInRange(List<ClassRegistration> registrationList, String start, String end) {
        long startTime = Long.parseLong(start);
        long endTime = Long.parseLong(end);
        int result = 0;

        for (ClassRegistration currentRegistration : registrationList) {
            long currentTime = Long.parseLong(currentRegistration.getTLSN_APLY_DT());
            if (startTime <= currentTime && endTime >= currentTime)
                result += currentRegistration.getRegistrationNumber();
        }
        return result;
    } // 특정 구간의 registration number 구하기

    public boolean isEqualOpener(String subjectID, String opener){
        int year=Integer.parseInt(subjectID.split("-")[0]);

        EntireSubject entireSubject=switch (year) {
            case 2020 -> entityManager.find(EntireSubject_2020.class, subjectID);
            case 2021 -> entityManager.find(EntireSubject_2021.class, subjectID);
            case 2022 -> entityManager.find(EntireSubject_2022.class, subjectID);
            default -> null;
        };
        log.info("isequal?: {}, {}",subjectID, entireSubject!=null ? entireSubject.getDegrNmSust() : "null");
        return entireSubject!=null && opener.equals(entireSubject.getDegrNmSust());
    }
    public SubjectStatistics findBySubjectID(String subjectID){
        return subjectStatisticsRepository.findById(subjectID).orElse(null);
    }


    public List<SubjectStatistics> getRankedList(int rank, int order){
        List<SubjectStatistics> list=subjectStatisticsRepository.findAll();

        if(order==0) // ASC
            list.sort((s1,s2)-> {
                if(s1.getComp_level()!=s2.getComp_level()) { // 경쟁률의 레벨을 비교
                    return s1.getComp_level()-s2.getComp_level();
                }else{
                    if(s1.getComp_rate()>s2.getComp_rate()){ // 경쟁률의 수치를 비교
                        return 1;
                    }else if(s1.getComp_rate()<s2.getComp_rate()){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });
        else // DESC
            list.sort((s1,s2)-> {
                if(s1.getComp_level()!=s2.getComp_level()) {
                    return s2.getComp_level()-s1.getComp_level();
                }else{
                    if(s2.getComp_rate()>s1.getComp_rate()){
                        return 1;
                    }else if(s2.getComp_rate()<s1.getComp_rate()){
                        return -1;
                    }else{
                        return 0;
                    }
                }
            });

        return list.subList(0, Math.min(list.size(), rank));
    }
}
