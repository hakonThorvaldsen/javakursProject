package no.visma.V2;

import no.visma.Domain.WeatherResponse;
//import org.junit.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WeatherControllerV2Test {

    @Mock
    private WeatherFetcher weatherFetcher;

    @InjectMocks
    private WeatherControllerV2 controller;

    @Test
    void scenario1(){
        WeatherResponse weatherResponse = new WeatherResponse();
        WeatherResponse.Properties properties = new WeatherResponse.Properties();
        List<WeatherResponse.Properties.TimeSerie> timeSerieList = new ArrayList<WeatherResponse.Properties.TimeSerie>();
        WeatherResponse.Properties.TimeSerie timeSerie = new WeatherResponse.Properties.TimeSerie();
        WeatherResponse.Properties.TimeSerie.WeatherData weatherData = new WeatherResponse.Properties.TimeSerie.WeatherData();
        WeatherResponse.Properties.TimeSerie.WeatherData.Instant instant = new WeatherResponse.Properties.TimeSerie.WeatherData.Instant();
        WeatherResponse.Properties.TimeSerie.WeatherData.Instant.Details details = new WeatherResponse.Properties.TimeSerie.WeatherData.Instant.Details();
        weatherResponse.setProperties(properties);
        properties.setTimeseries(timeSerieList);
        timeSerieList.add(timeSerie);
        timeSerie.setData(weatherData);
        weatherData.setInstant(instant);
        instant.setDetails(details);
        details.setAirTemperature(1000.);
        details.setCloudAreaFraction(101.);
        details.setWindSpeed(9001.);
        timeSerie.setTime(LocalDateTime.now().plusHours(1));

        when(weatherFetcher.getCurrent()).thenReturn(weatherResponse);

        String result = controller.get();

        assertThat(result, containsString("1000"));
        assertThat(result, containsString("101."));
        assertThat(result, containsString("9001."));
    }

}