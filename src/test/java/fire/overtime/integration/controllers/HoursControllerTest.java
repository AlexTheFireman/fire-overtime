package fire.overtime.integration.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fire.overtime.OvertimeApplication;
import fire.overtime.commands.HoursSaveCommand;
import fire.overtime.models.Enums.HourType;
import fire.overtime.models.Firefighter;
import fire.overtime.models.Hours;
import fire.overtime.models.Month;
import fire.overtime.repositories.FirefighterRepository;
import fire.overtime.repositories.HoursRepository;
import fire.overtime.repositories.MonthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.time.LocalDate;

import static fire.overtime.models.Enums.HourType.WORK;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = OvertimeApplication.class)
@WebAppConfiguration
@DirtiesContext
@Execution(ExecutionMode.CONCURRENT)
@Transactional
public class HoursControllerTest {
    private static final LocalDate DATE = LocalDate.of(2021, 8, 21);
    private MockMvc mockMvc;

    @Autowired
    private HoursRepository hoursRepository;

    @Autowired
    private FirefighterRepository firefighterRepository;

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private HoursSaveCommand testHoursSavedCommand;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

        Firefighter firefighter = givenSavedFirefighter();
        Month month = givenSavedMonth();
//        givenSavedHours(month, firefighter);
        this.testHoursSavedCommand = givenHoursSaveCommand(firefighter, month, 12, HourType.WORK);
    }

    @Test
    public void createHours_success() throws Exception {

        MockHttpServletRequestBuilder updateTargetBuilder = MockMvcRequestBuilders.post("/hours")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(testHoursSavedCommand));
        MvcResult mvcResult = this.mockMvc.perform(updateTargetBuilder).andDo(print()).andExpect(status().isCreated()).andReturn();
        Hours hours = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Hours.class);

        assertNotNull(hours);
        assertEquals(testHoursSavedCommand.getDate(), hours.getDate());
        assertEquals(testHoursSavedCommand.getFactHours(), hours.getFactHours());
        assertEquals(testHoursSavedCommand.getFirefighterId(), hours.getFirefighterId());
    }

    private Firefighter givenSavedFirefighter() {
        Firefighter firefighter = new Firefighter();
        firefighter.setFirstName("test-name");
        firefighter.setLastName("test-lastname");
        firefighter.setPatronymic("test-patronimic");
        firefighter.setPosition("test-position");
        return firefighterRepository.save(firefighter);
    }

    private Month givenSavedMonth() {
        Month month = new Month();
        month.setMonthName("June");
        month.setNormaHours(160);
        month.setYear(2021);
        return monthRepository.save(month);
    }

    public Hours givenSavedHours(Month savedMonth, Firefighter savedFighter) {
        Hours hours = new Hours();
        hours.setDate(LocalDate.now());
        hours.setFactHours(12);
        hours.setFirefighter(savedFighter);
        hours.setMonth(savedMonth);
        hours.setHoursType(WORK);
        return hoursRepository.save(hours);
    }
    private HoursSaveCommand givenHoursSaveCommand(
            Firefighter firefighter, Month month, int factHours, HourType hoursType) {
        HoursSaveCommand hoursSaveCommand = new HoursSaveCommand();
        hoursSaveCommand.setFirefighterId(firefighter.getId());
        hoursSaveCommand.setMonthId(month.getId());
        hoursSaveCommand.setFactHours(factHours);
        hoursSaveCommand.setHoursType(hoursType);
        hoursSaveCommand.setDate(DATE);
        return hoursSaveCommand;
    }
}

