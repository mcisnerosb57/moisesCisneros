package app.web.rest;

import app.ArtefactsCheckApp;

import app.domain.Aplicacion;
import app.repository.AplicacionRepository;
import app.service.AplicacionService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AplicacionResource REST controller.
 *
 * @see AplicacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtefactsCheckApp.class)
public class AplicacionResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Inject
    private AplicacionRepository aplicacionRepository;

    @Inject
    private AplicacionService aplicacionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restAplicacionMockMvc;

    private Aplicacion aplicacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AplicacionResource aplicacionResource = new AplicacionResource();
        ReflectionTestUtils.setField(aplicacionResource, "aplicacionService", aplicacionService);
        this.restAplicacionMockMvc = MockMvcBuilders.standaloneSetup(aplicacionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aplicacion createEntity(EntityManager em) {
        Aplicacion aplicacion = new Aplicacion()
                .nombre(DEFAULT_NOMBRE);
        return aplicacion;
    }

    @Before
    public void initTest() {
        aplicacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createAplicacion() throws Exception {
        int databaseSizeBeforeCreate = aplicacionRepository.findAll().size();

        // Create the Aplicacion

        restAplicacionMockMvc.perform(post("/api/aplicacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aplicacion)))
                .andExpect(status().isCreated());

        // Validate the Aplicacion in the database
        List<Aplicacion> aplicacions = aplicacionRepository.findAll();
        assertThat(aplicacions).hasSize(databaseSizeBeforeCreate + 1);
        Aplicacion testAplicacion = aplicacions.get(aplicacions.size() - 1);
        assertThat(testAplicacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = aplicacionRepository.findAll().size();
        // set the field null
        aplicacion.setNombre(null);

        // Create the Aplicacion, which fails.

        restAplicacionMockMvc.perform(post("/api/aplicacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(aplicacion)))
                .andExpect(status().isBadRequest());

        List<Aplicacion> aplicacions = aplicacionRepository.findAll();
        assertThat(aplicacions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAplicacions() throws Exception {
        // Initialize the database
        aplicacionRepository.saveAndFlush(aplicacion);

        // Get all the aplicacions
        restAplicacionMockMvc.perform(get("/api/aplicacions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(aplicacion.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getAplicacion() throws Exception {
        // Initialize the database
        aplicacionRepository.saveAndFlush(aplicacion);

        // Get the aplicacion
        restAplicacionMockMvc.perform(get("/api/aplicacions/{id}", aplicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aplicacion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAplicacion() throws Exception {
        // Get the aplicacion
        restAplicacionMockMvc.perform(get("/api/aplicacions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAplicacion() throws Exception {
        // Initialize the database
        aplicacionService.save(aplicacion);

        int databaseSizeBeforeUpdate = aplicacionRepository.findAll().size();

        // Update the aplicacion
        Aplicacion updatedAplicacion = aplicacionRepository.findOne(aplicacion.getId());
        updatedAplicacion
                .nombre(UPDATED_NOMBRE);

        restAplicacionMockMvc.perform(put("/api/aplicacions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedAplicacion)))
                .andExpect(status().isOk());

        // Validate the Aplicacion in the database
        List<Aplicacion> aplicacions = aplicacionRepository.findAll();
        assertThat(aplicacions).hasSize(databaseSizeBeforeUpdate);
        Aplicacion testAplicacion = aplicacions.get(aplicacions.size() - 1);
        assertThat(testAplicacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void deleteAplicacion() throws Exception {
        // Initialize the database
        aplicacionService.save(aplicacion);

        int databaseSizeBeforeDelete = aplicacionRepository.findAll().size();

        // Get the aplicacion
        restAplicacionMockMvc.perform(delete("/api/aplicacions/{id}", aplicacion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Aplicacion> aplicacions = aplicacionRepository.findAll();
        assertThat(aplicacions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
