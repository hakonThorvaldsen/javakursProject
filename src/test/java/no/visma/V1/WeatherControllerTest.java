package no.visma.V1;



import no.visma.V1.WeatherControllerV1;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.Is.is;


class WeatherControllerTest {

    WeatherControllerV1 weatherController = new WeatherControllerV1();
    @Test
    public void test(){
        String result = weatherController.getNow();
        assert result.equals("pent");
        assertThat(result, is("pent"));
        assertThat(result, containsString( "pe")); //importer constainsString?

    }

}