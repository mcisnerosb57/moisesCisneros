package app.web.rest;

import app.ArtefactsCheckApp;

import app.domain.Version;
import app.domain.Aplicacion;
import app.repository.VersionRepository;
import app.service.VersionService;

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
 * Test class for the VersionResource REST controller.
 *
 * @see VersionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ArtefactsCheckApp.class)
public class VersionResourceIntTest {

    private static final String DEFAULT_VERSIONAPP = "AAAAAAAAAA";
    private static final String UPDATED_VERSIONAPP = "BBBBBBBBBB";

    @Inject
    private VersionRepository versionRepository;

    @Inject
    private VersionService versionService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restVersionMockMvc;

    private Version version;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VersionResource versionResource = new VersionResource();
        ReflectionTestUtils.setField(versionResource, "versionService", versionService);
        this.restVersionMockMvc = MockMvcBuilders.standaloneSetup(versionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Version createEntity(EntityManager em) {
        Version version = new Version()
                .versionapp(DEFAULT_VERSIONAPP);
        // Add required entity
        Aplicacion aplicacion = AplicacionResourceIntTest.createEntity(em);
        em.persist(aplicacion);
        em.flush();
        version.setAplicacion(aplicacion);
        return version;
    }

    @Before
    public void initTest() {
        version = createEntity(em);
    }

    @Test
    @Transactional
    public void createVersion() throws Exception {
        int databaseSizeBeforeCreate = versionRepository.findAll().size();

        // Create the Version

        restVersionMockMvc.perform(post("/api/versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(version)))
                .andExpect(status().isCreated());

        // Validate the Version in the database
        List<Version> versions = versionRepository.findAll();
        assertThat(versions).hasSize(databaseSizeBeforeCreate + 1);
        Version testVersion = versions.get(versions.size() - 1);
        assertThat(testVersion.getVersionapp()).isEqualTo(DEFAULT_VERSIONAPP);
    }

    @Test
    @Transactional
    public void checkVersionappIsRequired() throws Exception {
        int databaseSizeBeforeTest = versionRepository.findAll().size();
        // set the field null
        version.setVersionapp(null);

        // Create the Version, which fails.

        restVersionMockMvc.perform(post("/api/versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(version)))
                .andExpect(status().isBadRequest());

        List<Version> versions = versionRepository.findAll();
        assertThat(versions).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVersions() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get all the versions
        restVersionMockMvc.perform(get("/api/versions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(version.getId().intValue())))
                .andExpect(jsonPath("$.[*].versionapp").value(hasItem(DEFAULT_VERSIONAPP.toString())));
    }

    @Test
    @Transactional
    public void getVersion() throws Exception {
        // Initialize the database
        versionRepository.saveAndFlush(version);

        // Get the version
        restVersionMockMvc.perform(get("/api/versions/{id}", version.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(version.getId().intValue()))
            .andExpect(jsonPath("$.versionapp").value(DEFAULT_VERSIONAPP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVersion() throws Exception {
        // Get the version
        restVersionMockMvc.perform(get("/api/versions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVersion() throws Exception {
        // Initialize the database
        versionService.save(version);

        int databaseSizeBeforeUpdate = versionRepository.findAll().size();

        // Update the version
        Version updatedVersion = versionRepository.findOne(version.getId());
        updatedVersion
                .versionapp(UPDATED_VERSIONAPP);

        restVersionMockMvc.perform(put("/api/versions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedVersion)))
                .andExpect(status().isOk());

        // Validate the Version in the database
        List<Version> versions = versionRepository.findAll();
        assertThat(versions).hasSize(databaseSizeBeforeUpdate);
        Version testVersion = versions.get(versions.size() - 1);
        assertThat(testVersion.getVersionapp()).isEqualTo(UPDATED_VERSIONAPP);
    }

    @Test
    @Transactional
    public void deleteVersion() throws Exception {
        // Initialize the database
        versionService.save(version);

        int databaseSizeBeforeDelete = versionRepository.findAll().size();

        // Get the version
        restVersionMockMvc.perform(delete("/api/versions/{id}", version.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Version> versions = versionRepository.findAll();
        assertThat(versions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
