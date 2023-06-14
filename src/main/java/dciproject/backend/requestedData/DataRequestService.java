package dciproject.backend.requestedData;


import dciproject.backend.classRegistration.ClassRegistration;
import dciproject.backend.classRegistration.ClassRegistrationService;
import dciproject.backend.classRegistration.classRegistration_2020.ClassRegistration_2020;
import dciproject.backend.classRegistration.classRegistration_2021.ClassRegistration_2021;
import dciproject.backend.classRegistration.classRegistration_2022.ClassRegistration_2022;
import dciproject.backend.entireSubjects.EntireSubject;
import dciproject.backend.entireSubjects.EntireSubjectService;
import dciproject.backend.entireSubjects.entireSubject_2020.EntireSubject_2020;
import dciproject.backend.entireSubjects.entireSubject_2021.EntireSubject_2021;
import dciproject.backend.entireSubjects.entireSubject_2022.EntireSubject_2022;
import dciproject.backend.subjectStatistics.SubjectStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
@Slf4j
public class DataRequestService {

    private final EntireSubjectService entireSubjectService;
    private final ClassRegistrationService registrationService;
    private final SubjectStatisticsService subjectStatisticsService;
    public DataRequestService(EntireSubjectService entireSubjectService, ClassRegistrationService registrationService, SubjectStatisticsService subjectStatisticsService) {
        this.entireSubjectService = entireSubjectService;
        this.registrationService = registrationService;
        this.subjectStatisticsService = subjectStatisticsService;
    }

    private String getJSONValue(JSONObject jsonObject, String key) {
        return String.valueOf(jsonObject.get(key)); // 한 강의의 JSON 을 가져와서, 특정 키를 검색
    }

    private JSONArray getResultJSONArray(String src) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(src); // JSON String을 JSON으로 변환
        return (JSONArray) jsonObject.get("RESULT"); // "RESULT" 부분만 추려냄
    }

    public void saveClassRegistrationData() throws URISyntaxException, IOException, InterruptedException, ParseException {
        HttpClient httpClient = HttpClient.newHttpClient();

        int year = 2020;
        int pageNum = 1;
        int MAX_PAGE = pageNum;

        for (; year < 2023; year++) {
            HashMap<String, HashMap<String, Integer>> hashMap = new HashMap<>();
            for (; pageNum <= MAX_PAGE; pageNum++) {
                String requestBody = new StringBuilder().append("row=300&P_OPEN_YR=")
                        .append(year)
                        .append("&page=")
                        .append(pageNum)
                        .append("&AUTH_KEY=21526D14653648DF9DED5FB5558B00B35B776E7F").toString();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("https://api.cnu.ac.kr/svc/offcam/pub/SugangInfo"))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                String responseBody = response.body();

                JSONArray jsonArray = getResultJSONArray(responseBody); // "RESULT" 부분만 추려냄

                if(jsonArray.size()<1) continue; // 비어있는게 존재

                MAX_PAGE = Integer.parseInt((String) ((JSONObject) jsonArray.get(0)).get("PAGECNT"));

                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject json = ((JSONObject) jsonArray.get(i));

                    int shtm = getJSONValue(json, "OPEN_SHTM").charAt(0) - '0'; // 학기추출

                    if (shtm > 2 || !getJSONValue(json, "OGDP_ORGN_CLSF").equals("학부")) continue;

                    String id = getJSONValue(json, "OPEN_YR") + "-" + shtm +
                            "-" + getJSONValue(json, "OPEN_SBJT_NO") +
                            "-" + getJSONValue(json, "OPEN_DCLSS"); // id 추출
                    String originTime = getJSONValue(json, "TLSN_APLY_DT"); // 실제로 얻어온 시간값
                    String subjectName = getJSONValue(json, "SBJT_NM");


                    if (originTime.length() < 15) {
                        StringTokenizer stk = new StringTokenizer(originTime, "/");
                        StringBuilder builder = new StringBuilder();
                        while (stk.hasMoreTokens())
                            builder.append(stk.nextToken());
                        builder.append("090000"); // 09:00:00
                        originTime = builder.toString();
                    }

                    String time = originTime.substring(0, 14); // time 추출

                    int num = 1;

                    if (!hashMap.containsKey(id)) {
                        hashMap.put(id, new HashMap<>());
                        hashMap.get(id).put(time,1);
                    } else {
                        HashMap<String, Integer> hashmapForId = hashMap.get(id);
                        num += hashmapForId.getOrDefault(time, 0);
                        hashmapForId.put(time, num);
                        if(num!=1) {
                            registrationService.modifyRegistrationNumberById(year, id,time,num);
                            continue;
                        }
                    }
                    ClassRegistration classRegistration = switch (year) {
                        case 2020 -> ClassRegistration_2020.builder().
                                subjectID(id).
                                TLSN_APLY_DT(time).
                                registrationNumber(num).build();
                        case 2021 -> ClassRegistration_2021.builder().
                                subjectID(id).
                                TLSN_APLY_DT(time).
                                registrationNumber(num).build();
                        case 2022 -> ClassRegistration_2022.builder().
                                subjectID(id).
                                TLSN_APLY_DT(time).
                                registrationNumber(num).build();
                        default -> null;
                    };
                    registrationService.save(classRegistration);
                }
                log.info("\n{}, page {}/{}",year,pageNum,MAX_PAGE);
            }
            System.out.println("Complete");
        }
    }

    public void saveEntireSubjectData() throws URISyntaxException, IOException, InterruptedException, ParseException {
        HttpClient httpClient = HttpClient.newHttpClient();

        int year = 2020;

        for (; year < 2023; year++) { // 년도
            HashSet<String> set = new HashSet<>();
            int shtm = 1;
            for (; shtm < 3; shtm++) { // 학기
                int pageNum = 1;
                int MAX_PAGE = 1;
                for (; pageNum <= MAX_PAGE; pageNum++) { // 페이지
                    String requestBody = new StringBuilder()
                            .append("row=300")
                            .append("&P_YR=")
                            .append(year)
                            .append("&P_OPEN_SHTM_CD=")
                            .append(shtm)
                            .append("&page=")
                            .append(pageNum)
                            .append("&AUTH_KEY=21526D14653648DF9DED5FB5558B00B35B776E7F").toString();
                    // POST 요청 보내기
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(new URI("https://api.cnu.ac.kr/svc/offcam/pub/lsnSmry"))
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                            .build();

                    HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                    String responseBody = response.body();

                    JSONArray jsonArray = getResultJSONArray(responseBody); // "RESULT" 부분만 추려냄

                    MAX_PAGE = Integer.parseInt((String) ((JSONObject) jsonArray.get(0)).get("PAGECNT"));


                    for (int i = 0; i < jsonArray.size(); i++) {
                        JSONObject json = ((JSONObject) jsonArray.get(i));
                        String id = getJSONValue(json, "OPEN_YR") + "-" + shtm +
                                "-" + getJSONValue(json, "OPEN_SBJT_NO") +
                                "-" + getJSONValue(json, "OPEN_DCLSS");

                        if (!getJSONValue(json, "ORGN_CLSF_CD").equals("학부") || set.contains(id)) continue;
                        set.add(id);


                        EntireSubject entireSubject = switch (year) {
                            case 2020 -> EntireSubject_2020.builder().
                                    subjectID(id).
                                    openYr(getJSONValue(json, "OPEN_YR")).
                                    shtm(getJSONValue(json, "SHTM")).
                                    trgtShyr(getJSONValue(json, "TRGT_SHYR")).
                                    orgnClsfCd(getJSONValue(json, "ORGN_CLSF_CD")).
                                    colg(getJSONValue(json, "COLG")).
                                    degrNmSust(getJSONValue(json, "DEGR_NM_SUST")).
                                    openSbjtNo(getJSONValue(json, "OPEN_SBJT_NO")).
                                    openDclss(getJSONValue(json, "OPEN_DCLSS")).
                                    openSbjtNm(getJSONValue(json, "OPEN_SBJT_NM")).
                                    cptnDivNm(getJSONValue(json, "CPTN_DIV_NM")).
                                    profInfo(getJSONValue(json, "PROF_INFO")).
                                    tmtblInfo(getJSONValue(json, "TMTBL_INFO")).build();
                            case 2021 -> EntireSubject_2021.builder().
                                    subjectID(id).
                                    openYr(getJSONValue(json, "OPEN_YR")).
                                    shtm(getJSONValue(json, "SHTM")).
                                    trgtShyr(getJSONValue(json, "TRGT_SHYR")).
                                    orgnClsfCd(getJSONValue(json, "ORGN_CLSF_CD")).
                                    colg(getJSONValue(json, "COLG")).
                                    degrNmSust(getJSONValue(json, "DEGR_NM_SUST")).
                                    openSbjtNo(getJSONValue(json, "OPEN_SBJT_NO")).
                                    openDclss(getJSONValue(json, "OPEN_DCLSS")).
                                    openSbjtNm(getJSONValue(json, "OPEN_SBJT_NM")).
                                    cptnDivNm(getJSONValue(json, "CPTN_DIV_NM")).
                                    profInfo(getJSONValue(json, "PROF_INFO")).
                                    tmtblInfo(getJSONValue(json, "TMTBL_INFO")).build();
                            case 2022 -> EntireSubject_2022.builder().
                                    subjectID(id).
                                    subjectID(id).
                                    openYr(getJSONValue(json, "OPEN_YR")).
                                    shtm(getJSONValue(json, "SHTM")).
                                    trgtShyr(getJSONValue(json, "TRGT_SHYR")).
                                    orgnClsfCd(getJSONValue(json, "ORGN_CLSF_CD")).
                                    colg(getJSONValue(json, "COLG")).
                                    degrNmSust(getJSONValue(json, "DEGR_NM_SUST")).
                                    openSbjtNo(getJSONValue(json, "OPEN_SBJT_NO")).
                                    openDclss(getJSONValue(json, "OPEN_DCLSS")).
                                    openSbjtNm(getJSONValue(json, "OPEN_SBJT_NM")).
                                    cptnDivNm(getJSONValue(json, "CPTN_DIV_NM")).
                                    profInfo(getJSONValue(json, "PROF_INFO")).
                                    tmtblInfo(getJSONValue(json, "TMTBL_INFO")).build();
                            default -> null;
                        };
                        entireSubjectService.save(entireSubject);
                    }
                    System.out.println(String.format("%d-%d page : %d/%d", year, shtm, pageNum, MAX_PAGE));
                }
            }
        }
        System.out.println("Complete");
    }



}
