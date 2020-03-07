package co.com.publishapp.web.rest;

import co.com.publishapp.PublishappApp;
import co.com.publishapp.domain.Publicacion;
import co.com.publishapp.repository.PublicacionRepository;
import co.com.publishapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static co.com.publishapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PublicacionResource} REST controller.
 */
@SpringBootTest(classes = PublishappApp.class)
public class PublicacionResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPublicacionMockMvc;

    private Publicacion publicacion;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicacionResource publicacionResource = new PublicacionResource(publicacionRepository);
        this.restPublicacionMockMvc = MockMvcBuilders.standaloneSetup(publicacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publicacion createEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .fecha(DEFAULT_FECHA)
            .numero(DEFAULT_NUMERO);
        return publicacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Publicacion createUpdatedEntity(EntityManager em) {
        Publicacion publicacion = new Publicacion()
            .fecha(UPDATED_FECHA)
            .numero(UPDATED_NUMERO);
        return publicacion;
    }

    @BeforeEach
    public void initTest() {
        publicacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicacion() throws Exception {
        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();

        // Create the Publicacion
        restPublicacionMockMvc.perform(post("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicacion)))
            .andExpect(status().isCreated());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate + 1);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testPublicacion.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createPublicacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicacionRepository.findAll().size();

        // Create the Publicacion with an existing ID
        publicacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicacionMockMvc.perform(post("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicacion)))
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPublicacions() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get all the publicacionList
        restPublicacionMockMvc.perform(get("/api/publicacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)));
    }
    
    @Test
    @Transactional
    public void getPublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", publicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(publicacion.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO));
    }

    @Test
    @Transactional
    public void getNonExistingPublicacion() throws Exception {
        // Get the publicacion
        restPublicacionMockMvc.perform(get("/api/publicacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Update the publicacion
        Publicacion updatedPublicacion = publicacionRepository.findById(publicacion.getId()).get();
        // Disconnect from session so that the updates on updatedPublicacion are not directly saved in db
        em.detach(updatedPublicacion);
        updatedPublicacion
            .fecha(UPDATED_FECHA)
            .numero(UPDATED_NUMERO);

        restPublicacionMockMvc.perform(put("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublicacion)))
            .andExpect(status().isOk());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
        Publicacion testPublicacion = publicacionList.get(publicacionList.size() - 1);
        assertThat(testPublicacion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testPublicacion.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicacion() throws Exception {
        int databaseSizeBeforeUpdate = publicacionRepository.findAll().size();

        // Create the Publicacion

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicacionMockMvc.perform(put("/api/publicacions")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(publicacion)))
            .andExpect(status().isBadRequest());

        // Validate the Publicacion in the database
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublicacion() throws Exception {
        // Initialize the database
        publicacionRepository.saveAndFlush(publicacion);

        int databaseSizeBeforeDelete = publicacionRepository.findAll().size();

        // Delete the publicacion
        restPublicacionMockMvc.perform(delete("/api/publicacions/{id}", publicacion.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Publicacion> publicacionList = publicacionRepository.findAll();
        assertThat(publicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
