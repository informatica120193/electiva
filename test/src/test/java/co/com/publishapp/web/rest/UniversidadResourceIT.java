package co.com.publishapp.web.rest;

import co.com.publishapp.TestApp;
import co.com.publishapp.domain.Universidad;
import co.com.publishapp.repository.UniversidadRepository;
import co.com.publishapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static co.com.publishapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UniversidadResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
public class UniversidadResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    @Autowired
    private UniversidadRepository universidadRepository;

    @Mock
    private UniversidadRepository universidadRepositoryMock;

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

    private MockMvc restUniversidadMockMvc;

    private Universidad universidad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UniversidadResource universidadResource = new UniversidadResource(universidadRepository);
        this.restUniversidadMockMvc = MockMvcBuilders.standaloneSetup(universidadResource)
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
    public static Universidad createEntity(EntityManager em) {
        Universidad universidad = new Universidad()
            .nombre(DEFAULT_NOMBRE)
            .direccion(DEFAULT_DIRECCION);
        return universidad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Universidad createUpdatedEntity(EntityManager em) {
        Universidad universidad = new Universidad()
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION);
        return universidad;
    }

    @BeforeEach
    public void initTest() {
        universidad = createEntity(em);
    }

    @Test
    @Transactional
    public void createUniversidad() throws Exception {
        int databaseSizeBeforeCreate = universidadRepository.findAll().size();

        // Create the Universidad
        restUniversidadMockMvc.perform(post("/api/universidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universidad)))
            .andExpect(status().isCreated());

        // Validate the Universidad in the database
        List<Universidad> universidadList = universidadRepository.findAll();
        assertThat(universidadList).hasSize(databaseSizeBeforeCreate + 1);
        Universidad testUniversidad = universidadList.get(universidadList.size() - 1);
        assertThat(testUniversidad.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testUniversidad.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    public void createUniversidadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = universidadRepository.findAll().size();

        // Create the Universidad with an existing ID
        universidad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniversidadMockMvc.perform(post("/api/universidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universidad)))
            .andExpect(status().isBadRequest());

        // Validate the Universidad in the database
        List<Universidad> universidadList = universidadRepository.findAll();
        assertThat(universidadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUniversidads() throws Exception {
        // Initialize the database
        universidadRepository.saveAndFlush(universidad);

        // Get all the universidadList
        restUniversidadMockMvc.perform(get("/api/universidads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(universidad.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUniversidadsWithEagerRelationshipsIsEnabled() throws Exception {
        UniversidadResource universidadResource = new UniversidadResource(universidadRepositoryMock);
        when(universidadRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUniversidadMockMvc = MockMvcBuilders.standaloneSetup(universidadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUniversidadMockMvc.perform(get("/api/universidads?eagerload=true"))
        .andExpect(status().isOk());

        verify(universidadRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUniversidadsWithEagerRelationshipsIsNotEnabled() throws Exception {
        UniversidadResource universidadResource = new UniversidadResource(universidadRepositoryMock);
            when(universidadRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUniversidadMockMvc = MockMvcBuilders.standaloneSetup(universidadResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUniversidadMockMvc.perform(get("/api/universidads?eagerload=true"))
        .andExpect(status().isOk());

            verify(universidadRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUniversidad() throws Exception {
        // Initialize the database
        universidadRepository.saveAndFlush(universidad);

        // Get the universidad
        restUniversidadMockMvc.perform(get("/api/universidads/{id}", universidad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(universidad.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION));
    }

    @Test
    @Transactional
    public void getNonExistingUniversidad() throws Exception {
        // Get the universidad
        restUniversidadMockMvc.perform(get("/api/universidads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUniversidad() throws Exception {
        // Initialize the database
        universidadRepository.saveAndFlush(universidad);

        int databaseSizeBeforeUpdate = universidadRepository.findAll().size();

        // Update the universidad
        Universidad updatedUniversidad = universidadRepository.findById(universidad.getId()).get();
        // Disconnect from session so that the updates on updatedUniversidad are not directly saved in db
        em.detach(updatedUniversidad);
        updatedUniversidad
            .nombre(UPDATED_NOMBRE)
            .direccion(UPDATED_DIRECCION);

        restUniversidadMockMvc.perform(put("/api/universidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUniversidad)))
            .andExpect(status().isOk());

        // Validate the Universidad in the database
        List<Universidad> universidadList = universidadRepository.findAll();
        assertThat(universidadList).hasSize(databaseSizeBeforeUpdate);
        Universidad testUniversidad = universidadList.get(universidadList.size() - 1);
        assertThat(testUniversidad.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testUniversidad.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void updateNonExistingUniversidad() throws Exception {
        int databaseSizeBeforeUpdate = universidadRepository.findAll().size();

        // Create the Universidad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniversidadMockMvc.perform(put("/api/universidads")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(universidad)))
            .andExpect(status().isBadRequest());

        // Validate the Universidad in the database
        List<Universidad> universidadList = universidadRepository.findAll();
        assertThat(universidadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUniversidad() throws Exception {
        // Initialize the database
        universidadRepository.saveAndFlush(universidad);

        int databaseSizeBeforeDelete = universidadRepository.findAll().size();

        // Delete the universidad
        restUniversidadMockMvc.perform(delete("/api/universidads/{id}", universidad.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Universidad> universidadList = universidadRepository.findAll();
        assertThat(universidadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
