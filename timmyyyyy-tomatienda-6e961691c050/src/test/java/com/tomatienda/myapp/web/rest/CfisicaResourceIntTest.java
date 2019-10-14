package com.tomatienda.myapp.web.rest;

import com.tomatienda.myapp.TomatiendaApp;

import com.tomatienda.myapp.domain.Cfisica;
import com.tomatienda.myapp.repository.CfisicaRepository;
import com.tomatienda.myapp.service.CfisicaService;
import com.tomatienda.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.tomatienda.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CfisicaResource REST controller.
 *
 * @see CfisicaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TomatiendaApp.class)
public class CfisicaResourceIntTest {

    private static final Double DEFAULT_IMPORTE = 1D;
    private static final Double UPDATED_IMPORTE = 2D;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    @Autowired
    private CfisicaRepository cfisicaRepository;

    @Autowired
    private CfisicaService cfisicaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCfisicaMockMvc;

    private Cfisica cfisica;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CfisicaResource cfisicaResource = new CfisicaResource(cfisicaService);
        this.restCfisicaMockMvc = MockMvcBuilders.standaloneSetup(cfisicaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cfisica createEntity(EntityManager em) {
        Cfisica cfisica = new Cfisica()
            .importe(DEFAULT_IMPORTE)
            .fecha(DEFAULT_FECHA)
            .codigo(DEFAULT_CODIGO);
        return cfisica;
    }

    @Before
    public void initTest() {
        cfisica = createEntity(em);
    }

    @Test
    @Transactional
    public void createCfisica() throws Exception {
        int databaseSizeBeforeCreate = cfisicaRepository.findAll().size();

        // Create the Cfisica
        restCfisicaMockMvc.perform(post("/api/cfisicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfisica)))
            .andExpect(status().isCreated());

        // Validate the Cfisica in the database
        List<Cfisica> cfisicaList = cfisicaRepository.findAll();
        assertThat(cfisicaList).hasSize(databaseSizeBeforeCreate + 1);
        Cfisica testCfisica = cfisicaList.get(cfisicaList.size() - 1);
        assertThat(testCfisica.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testCfisica.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCfisica.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void createCfisicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cfisicaRepository.findAll().size();

        // Create the Cfisica with an existing ID
        cfisica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCfisicaMockMvc.perform(post("/api/cfisicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfisica)))
            .andExpect(status().isBadRequest());

        // Validate the Cfisica in the database
        List<Cfisica> cfisicaList = cfisicaRepository.findAll();
        assertThat(cfisicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCfisicas() throws Exception {
        // Initialize the database
        cfisicaRepository.saveAndFlush(cfisica);

        // Get all the cfisicaList
        restCfisicaMockMvc.perform(get("/api/cfisicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cfisica.getId().intValue())))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE.doubleValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }

    @Test
    @Transactional
    public void getCfisica() throws Exception {
        // Initialize the database
        cfisicaRepository.saveAndFlush(cfisica);

        // Get the cfisica
        restCfisicaMockMvc.perform(get("/api/cfisicas/{id}", cfisica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cfisica.getId().intValue()))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE.doubleValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }

    @Test
    @Transactional
    public void getNonExistingCfisica() throws Exception {
        // Get the cfisica
        restCfisicaMockMvc.perform(get("/api/cfisicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCfisica() throws Exception {
        // Initialize the database
        cfisicaService.save(cfisica);

        int databaseSizeBeforeUpdate = cfisicaRepository.findAll().size();

        // Update the cfisica
        Cfisica updatedCfisica = cfisicaRepository.findOne(cfisica.getId());
        // Disconnect from session so that the updates on updatedCfisica are not directly saved in db
        em.detach(updatedCfisica);
        updatedCfisica
            .importe(UPDATED_IMPORTE)
            .fecha(UPDATED_FECHA)
            .codigo(UPDATED_CODIGO);

        restCfisicaMockMvc.perform(put("/api/cfisicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCfisica)))
            .andExpect(status().isOk());

        // Validate the Cfisica in the database
        List<Cfisica> cfisicaList = cfisicaRepository.findAll();
        assertThat(cfisicaList).hasSize(databaseSizeBeforeUpdate);
        Cfisica testCfisica = cfisicaList.get(cfisicaList.size() - 1);
        assertThat(testCfisica.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testCfisica.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCfisica.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void updateNonExistingCfisica() throws Exception {
        int databaseSizeBeforeUpdate = cfisicaRepository.findAll().size();

        // Create the Cfisica

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCfisicaMockMvc.perform(put("/api/cfisicas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cfisica)))
            .andExpect(status().isCreated());

        // Validate the Cfisica in the database
        List<Cfisica> cfisicaList = cfisicaRepository.findAll();
        assertThat(cfisicaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCfisica() throws Exception {
        // Initialize the database
        cfisicaService.save(cfisica);

        int databaseSizeBeforeDelete = cfisicaRepository.findAll().size();

        // Get the cfisica
        restCfisicaMockMvc.perform(delete("/api/cfisicas/{id}", cfisica.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cfisica> cfisicaList = cfisicaRepository.findAll();
        assertThat(cfisicaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cfisica.class);
        Cfisica cfisica1 = new Cfisica();
        cfisica1.setId(1L);
        Cfisica cfisica2 = new Cfisica();
        cfisica2.setId(cfisica1.getId());
        assertThat(cfisica1).isEqualTo(cfisica2);
        cfisica2.setId(2L);
        assertThat(cfisica1).isNotEqualTo(cfisica2);
        cfisica1.setId(null);
        assertThat(cfisica1).isNotEqualTo(cfisica2);
    }
}
