package com.tomatienda.myapp.web.rest;

import com.tomatienda.myapp.TomatiendaApp;

import com.tomatienda.myapp.domain.Conline;
import com.tomatienda.myapp.repository.ConlineRepository;
import com.tomatienda.myapp.service.ConlineService;
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
 * Test class for the ConlineResource REST controller.
 *
 * @see ConlineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TomatiendaApp.class)
public class ConlineResourceIntTest {

    private static final Integer DEFAULT_IMPORTE = 1;
    private static final Integer UPDATED_IMPORTE = 2;

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_DESCUENTO = false;
    private static final Boolean UPDATED_DESCUENTO = true;

    private static final Integer DEFAULT_CODIGO = 1;
    private static final Integer UPDATED_CODIGO = 2;

    @Autowired
    private ConlineRepository conlineRepository;

    @Autowired
    private ConlineService conlineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConlineMockMvc;

    private Conline conline;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConlineResource conlineResource = new ConlineResource(conlineService);
        this.restConlineMockMvc = MockMvcBuilders.standaloneSetup(conlineResource)
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
    public static Conline createEntity(EntityManager em) {
        Conline conline = new Conline()
            .importe(DEFAULT_IMPORTE)
            .fecha(DEFAULT_FECHA)
            .descuento(DEFAULT_DESCUENTO)
            .codigo(DEFAULT_CODIGO);
        return conline;
    }

    @Before
    public void initTest() {
        conline = createEntity(em);
    }

    @Test
    @Transactional
    public void createConline() throws Exception {
        int databaseSizeBeforeCreate = conlineRepository.findAll().size();

        // Create the Conline
        restConlineMockMvc.perform(post("/api/conlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conline)))
            .andExpect(status().isCreated());

        // Validate the Conline in the database
        List<Conline> conlineList = conlineRepository.findAll();
        assertThat(conlineList).hasSize(databaseSizeBeforeCreate + 1);
        Conline testConline = conlineList.get(conlineList.size() - 1);
        assertThat(testConline.getImporte()).isEqualTo(DEFAULT_IMPORTE);
        assertThat(testConline.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testConline.isDescuento()).isEqualTo(DEFAULT_DESCUENTO);
        assertThat(testConline.getCodigo()).isEqualTo(DEFAULT_CODIGO);
    }

    @Test
    @Transactional
    public void createConlineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conlineRepository.findAll().size();

        // Create the Conline with an existing ID
        conline.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConlineMockMvc.perform(post("/api/conlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conline)))
            .andExpect(status().isBadRequest());

        // Validate the Conline in the database
        List<Conline> conlineList = conlineRepository.findAll();
        assertThat(conlineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConlines() throws Exception {
        // Initialize the database
        conlineRepository.saveAndFlush(conline);

        // Get all the conlineList
        restConlineMockMvc.perform(get("/api/conlines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conline.getId().intValue())))
            .andExpect(jsonPath("$.[*].importe").value(hasItem(DEFAULT_IMPORTE)))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].descuento").value(hasItem(DEFAULT_DESCUENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)));
    }

    @Test
    @Transactional
    public void getConline() throws Exception {
        // Initialize the database
        conlineRepository.saveAndFlush(conline);

        // Get the conline
        restConlineMockMvc.perform(get("/api/conlines/{id}", conline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conline.getId().intValue()))
            .andExpect(jsonPath("$.importe").value(DEFAULT_IMPORTE))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.descuento").value(DEFAULT_DESCUENTO.booleanValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO));
    }

    @Test
    @Transactional
    public void getNonExistingConline() throws Exception {
        // Get the conline
        restConlineMockMvc.perform(get("/api/conlines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConline() throws Exception {
        // Initialize the database
        conlineService.save(conline);

        int databaseSizeBeforeUpdate = conlineRepository.findAll().size();

        // Update the conline
        Conline updatedConline = conlineRepository.findOne(conline.getId());
        // Disconnect from session so that the updates on updatedConline are not directly saved in db
        em.detach(updatedConline);
        updatedConline
            .importe(UPDATED_IMPORTE)
            .fecha(UPDATED_FECHA)
            .descuento(UPDATED_DESCUENTO)
            .codigo(UPDATED_CODIGO);

        restConlineMockMvc.perform(put("/api/conlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConline)))
            .andExpect(status().isOk());

        // Validate the Conline in the database
        List<Conline> conlineList = conlineRepository.findAll();
        assertThat(conlineList).hasSize(databaseSizeBeforeUpdate);
        Conline testConline = conlineList.get(conlineList.size() - 1);
        assertThat(testConline.getImporte()).isEqualTo(UPDATED_IMPORTE);
        assertThat(testConline.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testConline.isDescuento()).isEqualTo(UPDATED_DESCUENTO);
        assertThat(testConline.getCodigo()).isEqualTo(UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void updateNonExistingConline() throws Exception {
        int databaseSizeBeforeUpdate = conlineRepository.findAll().size();

        // Create the Conline

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConlineMockMvc.perform(put("/api/conlines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conline)))
            .andExpect(status().isCreated());

        // Validate the Conline in the database
        List<Conline> conlineList = conlineRepository.findAll();
        assertThat(conlineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConline() throws Exception {
        // Initialize the database
        conlineService.save(conline);

        int databaseSizeBeforeDelete = conlineRepository.findAll().size();

        // Get the conline
        restConlineMockMvc.perform(delete("/api/conlines/{id}", conline.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Conline> conlineList = conlineRepository.findAll();
        assertThat(conlineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Conline.class);
        Conline conline1 = new Conline();
        conline1.setId(1L);
        Conline conline2 = new Conline();
        conline2.setId(conline1.getId());
        assertThat(conline1).isEqualTo(conline2);
        conline2.setId(2L);
        assertThat(conline1).isNotEqualTo(conline2);
        conline1.setId(null);
        assertThat(conline1).isNotEqualTo(conline2);
    }
}
