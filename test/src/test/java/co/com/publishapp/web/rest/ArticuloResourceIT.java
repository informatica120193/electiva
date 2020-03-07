package co.com.publishapp.web.rest;

import co.com.publishapp.TestApp;
import co.com.publishapp.domain.Articulo;
import co.com.publishapp.repository.ArticuloRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static co.com.publishapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ArticuloResource} REST controller.
 */
@SpringBootTest(classes = TestApp.class)
public class ArticuloResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    @Autowired
    private ArticuloRepository articuloRepository;

    @Mock
    private ArticuloRepository articuloRepositoryMock;

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

    private MockMvc restArticuloMockMvc;

    private Articulo articulo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticuloResource articuloResource = new ArticuloResource(articuloRepository);
        this.restArticuloMockMvc = MockMvcBuilders.standaloneSetup(articuloResource)
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
    public static Articulo createEntity(EntityManager em) {
        Articulo articulo = new Articulo()
            .fecha(DEFAULT_FECHA)
            .titulo(DEFAULT_TITULO);
        return articulo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Articulo createUpdatedEntity(EntityManager em) {
        Articulo articulo = new Articulo()
            .fecha(UPDATED_FECHA)
            .titulo(UPDATED_TITULO);
        return articulo;
    }

    @BeforeEach
    public void initTest() {
        articulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticulo() throws Exception {
        int databaseSizeBeforeCreate = articuloRepository.findAll().size();

        // Create the Articulo
        restArticuloMockMvc.perform(post("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isCreated());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeCreate + 1);
        Articulo testArticulo = articuloList.get(articuloList.size() - 1);
        assertThat(testArticulo.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testArticulo.getTitulo()).isEqualTo(DEFAULT_TITULO);
    }

    @Test
    @Transactional
    public void createArticuloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articuloRepository.findAll().size();

        // Create the Articulo with an existing ID
        articulo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticuloMockMvc.perform(post("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isBadRequest());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllArticulos() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        // Get all the articuloList
        restArticuloMockMvc.perform(get("/api/articulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllArticulosWithEagerRelationshipsIsEnabled() throws Exception {
        ArticuloResource articuloResource = new ArticuloResource(articuloRepositoryMock);
        when(articuloRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restArticuloMockMvc = MockMvcBuilders.standaloneSetup(articuloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArticuloMockMvc.perform(get("/api/articulos?eagerload=true"))
        .andExpect(status().isOk());

        verify(articuloRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllArticulosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ArticuloResource articuloResource = new ArticuloResource(articuloRepositoryMock);
            when(articuloRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restArticuloMockMvc = MockMvcBuilders.standaloneSetup(articuloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restArticuloMockMvc.perform(get("/api/articulos?eagerload=true"))
        .andExpect(status().isOk());

            verify(articuloRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getArticulo() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        // Get the articulo
        restArticuloMockMvc.perform(get("/api/articulos/{id}", articulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articulo.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO));
    }

    @Test
    @Transactional
    public void getNonExistingArticulo() throws Exception {
        // Get the articulo
        restArticuloMockMvc.perform(get("/api/articulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticulo() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        int databaseSizeBeforeUpdate = articuloRepository.findAll().size();

        // Update the articulo
        Articulo updatedArticulo = articuloRepository.findById(articulo.getId()).get();
        // Disconnect from session so that the updates on updatedArticulo are not directly saved in db
        em.detach(updatedArticulo);
        updatedArticulo
            .fecha(UPDATED_FECHA)
            .titulo(UPDATED_TITULO);

        restArticuloMockMvc.perform(put("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticulo)))
            .andExpect(status().isOk());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeUpdate);
        Articulo testArticulo = articuloList.get(articuloList.size() - 1);
        assertThat(testArticulo.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testArticulo.getTitulo()).isEqualTo(UPDATED_TITULO);
    }

    @Test
    @Transactional
    public void updateNonExistingArticulo() throws Exception {
        int databaseSizeBeforeUpdate = articuloRepository.findAll().size();

        // Create the Articulo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticuloMockMvc.perform(put("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isBadRequest());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticulo() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        int databaseSizeBeforeDelete = articuloRepository.findAll().size();

        // Delete the articulo
        restArticuloMockMvc.perform(delete("/api/articulos/{id}", articulo.getId())
            .accept(TestUtil.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
