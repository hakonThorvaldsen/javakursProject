package no.visma.V3;

import lombok.RequiredArgsConstructor;
import no.visma.Domain.WeatherResponse;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
@RequiredArgsConstructor
public class Fetcher {
    private final RestTemplate restTemplate;
    private final WeatherRepository weatherRepository;

    @PostConstruct
    public void onBoot(){
        WeatherResponse weatherResponse = getCurrent();
        List<Weather> weatherList = mapToWeather(weatherResponse);
        weatherRepository.saveAll(weatherList);
    }

    private WeatherResponse getCurrent() {
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=51.5&lon=0"))
                .header(USER_AGENT, UUID.randomUUID().toString())
                .build();
        return restTemplate.exchange(requestEntity, WeatherResponse.class).getBody();
        //result.getStatusCode();
        //return result.getBody();

    }

    private List<Weather> mapToWeather(WeatherResponse weatherResponse) {
        return weatherResponse.getProperties().getTimeseries().stream()
                .map(t -> Weather.builder()
                .time(t.getTime())
                .temperatur(t.getData().getInstant().getDetails().getAirTemperature().toString())
                .vindhastighet(t.getData().getInstant().getDetails().getWindSpeed().toString())
                .broek(t.getData().getInstant().getDetails().getCloudAreaFraction().toString())
                .build()
        ).collect(Collectors.toList());

    }
}
