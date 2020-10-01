package no.visma.V2;

import no.visma.Domain.WeatherResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

@RestController
@RequestMapping("v2")
public class WeatherControllerV2 {
    private WeatherFetcher weatherFetcher;

    @GetMapping("now")
    public String get(){
        WeatherResponse weatherResponse = weatherFetcher.getCurrent();
        WeatherResponse.Properties.TimeSerie timeSerie = weatherResponse.getProperties().getTimeseries()
                .stream().filter(t ->t.getTime().isAfter(LocalDateTime.now()))
                .min(comparing(t -> t.getTime()))
                .orElseThrow(RuntimeException::new);
        return formatResponse(timeSerie);
    }

    private String formatResponse(WeatherResponse.Properties.TimeSerie timeSeries) {
        WeatherResponse.Properties.TimeSerie.WeatherData.Instant.Details details = timeSeries.getData().getInstant().getDetails();
        return String.format("temperatur: %s C, vindfart: %s m/s, skyarealbrøk: %s",
                details.getAirTemperature(),
                details.getWindSpeed(),
                details.getCloudAreaFraction());
    }
}
