package dciproject.backend.requestedData;


import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class DataRequestController {

    private final DataRequestService dataRequestService;

    public DataRequestController(DataRequestService dataRequestService) {
        this.dataRequestService = dataRequestService;
    }

    @PostMapping("/registration-api/21526D14653648DF9DED5FB5558B00B35B776E7F")
    private void saveRegistrationData() throws URISyntaxException, IOException, InterruptedException, ParseException, org.json.simple.parser.ParseException {
        dataRequestService.saveClassRegistrationData();
    }
    @PostMapping("/subject-plan-api/21526D14653648DF9DED5FB5558B00B35B776E7F")
    private void saveEntireSubjectData() throws URISyntaxException, IOException, InterruptedException, ParseException, org.json.simple.parser.ParseException {
        dataRequestService.saveEntireSubjectData();
    }
}
