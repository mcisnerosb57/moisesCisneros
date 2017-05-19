package app.web.rest;

import app.ArtefactsCheckApp;

import app.domain.Artefacto;
import app.domain.Version;
import app.repository.ArtefactoRepository;
import app.service.ArtefactoService;

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
 * Test class for the ArtefactoResource REST controller.
 *
 * @see ArtefactoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtefactsCheckApp.class)
public class ArtefactoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_VERSIONA = "AAAAAAAAAA";
    private static final String UPDATED_VERSIONA = "BBBBBBBBBB";

    private static final String DEFAULT_REPOSITORIO = "AAAAAAAAAA";
    private static final String UPDATED_REPOSITORIO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_COMPROBADO = false;
    private static final Boolean UPDATED_COMPROBADO = true;

    private static final Boolean DEFAULT_EXISTE = false;
    private static final Boolean UPDATED_EXISTE = true;

    private static final String DEFAULT_GRUPO = "AAAAAAAAAA";
    private static final String UPDATED_GRUPO = "BBBBBBBBBB";

    @Inject
    private ArtefactoRepository artefactoRepository;

    @Inject
    private ArtefactoService artefactoService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restArtefactoMockMvc;

    private Artefacto artefacto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ArtefactoResource artefactoResource = new ArtefactoResource();
        ReflectionTestUtils.setField(artefactoResource, "artefactoService", artefactoService);
        this.restArtefactoMockMvc = MockMvcBuilders.standaloneSetup(artefactoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Artefacto createEntity(EntityManager em) {
        Artefacto artefacto = new Artefacto()
                .nombre(DEFAULT_NOMBRE)
                .versiona(DEFAULT_VERSIONA)
                .repositorio(DEFAULT_REPOSITORIO)
                .comprobado(DEFAULT_COMPROBADO)
                .existe(DEFAULT_EXISTE)
                .grupo(DEFAULT_GRUPO);
        // Add required entity
        Version version = VersionResourceIntTest.createEntity(em);
        em.persist(version);
        em.flush();
        artefacto.setVersion(version);
        return artefacto;
    }

    @Before
    public void initTest() {
        artefacto = createEntity(em);
    }

    @Test
    @Transactional
    public void createArtefacto() throws Exception {
        int databaseSizeBeforeCreate = artefactoRepository.findAll().size();

        // Create the Artefacto

        restArtefactoMockMvc.perform(post("/api/artefactos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(artefacto)))
                .andExpect(status().isCreated());

        // Validate the Artefacto in the database
        List<Artefacto> artefactos = artefactoRepository.findAll();
        assertThat(artefactos).hasSize(databaseSizeBeforeCreate + 1);
        Artefacto testArtefacto = artefactos.get(artefactos.size() - 1);
        assertThat(testArtefacto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testArtefacto.getVersiona()).isEqualTo(DEFAULT_VERSIONA);
        assertThat(testArtefacto.getRepositorio()).isEqualTo(DEFAULT_REPOSITORIO);
        assertThat(testArtefacto.isComprobado()).isEqualTo(DEFAULT_COMPROBADO);
        assertThat(testArtefacto.isExiste()).isEqualTo(DEFAULT_EXISTE);
        assertThat(testArtefacto.getGrupo()).isEqualTo(DEFAULT_GRUPO);
    }

    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = artefactoRepository.findAll().size();
        // set the field null
        artefacto.setNombre(null);

        // Create the Artefacto, which fails.

        restArtefactoMockMvc.perform(post("/api/artefactos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(artefacto)))
                .andExpect(status().isBadRequest());

        List<Artefacto> artefactos = artefactoRepository.findAll();
        assertThat(artefactos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVersionaIsRequired() throws Exception {
        int databaseSizeBeforeTest = artefactoRepository.findAll().size();
        // set the field null
        artefacto.setVersiona(null);

        // Create the Artefacto, which fails.

        restArtefactoMockMvc.perform(post("/api/artefactos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(artefacto)))
                .andExpect(status().isBadRequest());

        List<Artefacto> artefactos = artefactoRepository.findAll();
        assertThat(artefactos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRepositorioIsRequired() throws Exception {
        int databaseSizeBeforeTest = artefactoRepository.findAll().size();
        // set the field null
        artefacto.setRepositorio(null);

        // Create the Artefacto, which fails.

        restArtefactoMockMvc.perform(post("/api/artefactos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(artefacto)))
                .andExpect(status().isBadRequest());

        List<Artefacto> artefactos = artefactoRepository.findAll();
        assertThat(artefactos).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArtefactos() throws Exception {
        // Initialize the database
        artefactoRepository.saveAndFlush(artefacto);

        // Get all the artefactos
        restArtefactoMockMvc.perform(get("/api/artefactos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(artefacto.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].versiona").value(hasItem(DEFAULT_VERSIONA.toString())))
                .andExpect(jsonPath("$.[*].repositorio").value(hasItem(DEFAULT_REPOSITORIO.toString())))
                .andExpect(jsonPath("$.[*].comprobado").value(hasItem(DEFAULT_COMPROBADO.booleanValue())))
                .andExpect(jsonPath("$.[*].existe").value(hasItem(DEFAULT_EXISTE.booleanValue())))
                .andExpect(jsonPath("$.[*].grupo").value(hasItem(DEFAULT_GRUPO.toString())));
    }

    @Test
    @Transactional
    public void getArtefacto() throws Exception {
        // Initialize the database
        artefactoRepository.saveAndFlush(artefacto);

        // Get the artefacto
        restArtefactoMockMvc.perform(get("/api/artefactos/{id}", artefacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(artefacto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.versiona").value(DEFAULT_VERSIONA.toString()))
            .andExpect(jsonPath("$.repositorio").value(DEFAULT_REPOSITORIO.toString()))
            .andExpect(jsonPath("$.comprobado").value(DEFAULT_COMPROBADO.booleanValue()))
            .andExpect(jsonPath("$.existe").value(DEFAULT_EXISTE.booleanValue()))
            .andExpect(jsonPath("$.grupo").value(DEFAULT_GRUPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArtefacto() throws Exception {
        // Get the artefacto
        restArtefactoMockMvc.perform(get("/api/artefactos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArtefacto() throws Exception {
        // Initialize the database
        artefactoService.save(artefacto);

        int databaseSizeBeforeUpdate = artefactoRepository.findAll().size();

        // Update the artefacto
        Artefacto updatedArtefacto = artefactoRepository.findOne(artefacto.getId());
        updatedArtefacto
                .nombre(UPDATED_NOMBRE)
                .versiona(UPDATED_VERSIONA)
                .repositorio(UPDATED_REPOSITORIO)
                .comprobado(UPDATED_COMPROBADO)
                .existe(UPDATED_EXISTE)
                .grupo(UPDATED_GRUPO);

        restArtefactoMockMvc.perform(put("/api/artefactos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedArtefacto)))
                .andExpect(status().isOk());

        // Validate the Artefacto in the database
        List<Artefacto> artefactos = artefactoRepository.findAll();
        assertThat(artefactos).hasSize(databaseSizeBeforeUpdate);
        Artefacto testArtefacto = artefactos.get(artefactos.size() - 1);
        assertThat(testArtefacto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testArtefacto.getVersiona()).isEqualTo(UPDATED_VERSIONA);
        assertThat(testArtefacto.getRepositorio()).isEqualTo(UPDATED_REPOSITORIO);
        assertThat(testArtefacto.isComprobado()).isEqualTo(UPDATED_COMPROBADO);
        assertThat(testArtefacto.isExiste()).isEqualTo(UPDATED_EXISTE);
        assertThat(testArtefacto.getGrupo()).isEqualTo(UPDATED_GRUPO);
    }

    @Test
    @Transactional
    public void deleteArtefacto() throws Exception {
        // Initialize the database
        artefactoService.save(artefacto);

        int databaseSizeBeforeDelete = artefactoRepository.findAll().size();

        // Get the artefacto
        restArtefactoMockMvc.perform(delete("/api/artefactos/{id}", artefacto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Artefacto> artefactos = artefactoRepository.findAll();
        assertThat(artefactos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
