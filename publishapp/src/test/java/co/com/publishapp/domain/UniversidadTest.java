package co.com.publishapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.com.publishapp.web.rest.TestUtil;

public class UniversidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Universidad.class);
        Universidad universidad1 = new Universidad();
        universidad1.setId(1L);
        Universidad universidad2 = new Universidad();
        universidad2.setId(universidad1.getId());
        assertThat(universidad1).isEqualTo(universidad2);
        universidad2.setId(2L);
        assertThat(universidad1).isNotEqualTo(universidad2);
        universidad1.setId(null);
        assertThat(universidad1).isNotEqualTo(universidad2);
    }
}
