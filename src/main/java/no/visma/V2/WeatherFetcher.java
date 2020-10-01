package no.visma.V2;

import lombok.RequiredArgsConstructor;
import no.visma.Domain.WeatherResponse;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
@RequiredArgsConstructor
public class WeatherFetcher {
    private final RestTemplate restTemplate;

    public WeatherResponse getCurrent() {
        RequestEntity<Void> requestEntity = RequestEntity.get(URI.create("https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=51.5&lon=0"))
                .header(USER_AGENT, UUID.randomUUID().toString())
                .build();
        return restTemplate.exchange(requestEntity, WeatherResponse.class).getBody();
        //result.getStatusCode();
        //return result.getBody();

    }
}
