package tn.esprit.tpfoyer;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.control.EtudiantRestController;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EtudiantRestControllerTest {

    @Mock
    private IEtudiantService etudiantService;

    @InjectMocks
    private EtudiantRestController etudiantRestController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetEtudiants() {
        // Arrange
        List<Etudiant> etudiants = new ArrayList<>();
        etudiants.add(new Etudiant(1L, "John", "Doe", 12345678L, new Date(), new HashSet<>()));
        etudiants.add(new Etudiant(2L, "Jane", "Doe", 87654321L, new Date(), new HashSet<>()));

        when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiants);

        // Act
        List<Etudiant> result = etudiantRestController.getEtudiants();

        // Assert
        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getNomEtudiant());
    }

    @Test
    public void testRetrieveEtudiantParCin() {
        // Arrange
        Long cin = 12345678L;
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", cin, new Date(), new HashSet<>());

        when(etudiantService.recupererEtudiantParCin(cin)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantRestController.retrieveEtudiantParCin(cin);

        // Assert
        assertNotNull(result);
        assertEquals(cin, result.getCinEtudiant());
    }

    @Test
    public void testRetrieveEtudiant() {
        // Arrange
        Long id = 1L;
        Etudiant etudiant = new Etudiant(id, "John", "Doe", 12345678L, new Date(), new HashSet<>());

        when(etudiantService.retrieveEtudiant(id)).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantRestController.retrieveEtudiant(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getIdEtudiant());
    }

    @Test
    public void testAddEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant(0L, "John", "Doe", 12345678L, new Date(), new HashSet<>());

        // Simulate the service behavior
        when(etudiantService.addEtudiant(any(Etudiant.class)))
                .thenReturn(new Etudiant(1L, "John", "Doe", 12345678L, new Date(), new HashSet<>()));

        // Act
        Etudiant result = etudiantRestController.addEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
        assertEquals(1L, result.getIdEtudiant());  // Ensure the returned ID is correct
    }


    @Test
    public void testRemoveEtudiant() {
        // Arrange
        Long id = 1L;

        // Act
        etudiantRestController.removeEtudiant(id);

        // Assert
        verify(etudiantService, times(1)).removeEtudiant(id);
    }

    @Test
    public void testModifyEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant(1L, "John", "Doe", 12345678L, new Date(), new HashSet<>());

        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantRestController.modifyEtudiant(etudiant);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
    }
}
