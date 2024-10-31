package tn.esprit.tpfoyer;


import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.control.EtudiantRestController;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EtudiantRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IEtudiantService etudiantService;

    @InjectMocks
    private EtudiantRestController etudiantRestController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(etudiantRestController).build();
    }

    @Test
    public void testGetEtudiants() throws Exception {
        List<Etudiant> etudiants = Arrays.asList(
                new Etudiant(1, "John", "Doe", 12345678, new Date(), null),
                new Etudiant(2, "Jane", "Doe", 87654321, new Date(), null)
        );
        when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiants);

        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nomEtudiant").value("John"))
                .andExpect(jsonPath("$[1].nomEtudiant").value("Jane"));
    }

    @Test
    public void testRetrieveEtudiantParCin() throws Exception {
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 12345678, new Date(), null);
        when(etudiantService.recupererEtudiantParCin(12345678L)).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/retrieve-etudiant-cin/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"));
    }

    @Test
    public void testRetrieveEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 12345678, new Date(), null);
        when(etudiantService.retrieveEtudiant(1L)).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/retrieve-etudiant/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"));
    }

    @Test
    public void testAddEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 12345678, new Date(), null);
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(post("/etudiant/add-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nomEtudiant\": \"John\", \"prenomEtudiant\": \"Doe\", \"cinEtudiant\": 12345678}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"));
    }

    @Test
    public void testRemoveEtudiant() throws Exception {
        doNothing().when(etudiantService).removeEtudiant(1L);

        mockMvc.perform(delete("/etudiant/remove-etudiant/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testModifyEtudiant() throws Exception {
        Etudiant etudiant = new Etudiant(1, "John", "Doe", 12345678, new Date(), null);
        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(put("/etudiant/modify-etudiant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"idEtudiant\": 1, \"nomEtudiant\": \"John\", \"prenomEtudiant\": \"Doe\", \"cinEtudiant\": 12345678}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomEtudiant").value("John"));
    }
}
