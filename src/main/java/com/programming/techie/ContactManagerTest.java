package com.programming.techie;

import jdk.jfr.Enabled;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {

    ContactManager contactManager;

    @BeforeAll
    public void mensajeInicial() {
        System.out.println("Iniciando los test...");
    }

    @BeforeEach
    public void crearContacto() {
        contactManager = new ContactManager();

    }


    @Test /* CREAMOS EL TEST */
    @DisplayName("Contacto creado corretamente") /* NOMBRE DEL TEST DINÁMICO*/
    public void shouldCreateContact() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());

    }

    @Test
    @DisplayName("Nombre nulo")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null, "Doe", "0123456789");
        });
    }

    @Test
    @DisplayName("Apellido nulo")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", null, "0123456789");
        });
    }

    @Test
    @DisplayName("Teléfono nulo")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John", "Doe", null);
        });
    }

    @AfterEach
    public void ejecucionIndividual() {
        System.out.println("Ejecutando después de cada clase...");

    }

    @AfterAll
    public void ejecucionalFinal() {
        System.out.println("Ejecutando al final de la clase.");
    }


    @Test /* CREAMOS EL TEST */
    @DisplayName("Habilitamos el test en MAC") /* NOMBRE DEL TEST DINÁMICO*/
    @EnabledOnOs(value = OS.MAC, disabledReason = "Este test solo se puede ejecutar en Mac")
    public void shouldCreateContactOnlyMac() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());

    }

    @Test /* CREAMOS EL TEST */
    @DisplayName("Deshabilitamos el test en Windows") /* NOMBRE DEL TEST DINÁMICO*/
    @EnabledOnOs(value = OS.WINDOWS, disabledReason = "Habilitamos el test en Windows")
    public void shouldCreateContactOnlyWindows() {
        contactManager.addContact("John", "Doe", "0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1, contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());

    }

    @Test /* CREAMOS EL TEST */
    @DisplayName("Creamos el test en la máquina del desarrollador") /* NOMBRE DEL TEST DINÁMICO*/
    public void creamosenDispositivo() {
        Assumptions.assumeTrue("TEST".equals(System.getProperty("ENV")));
        contactManager.addContact("John", "Doe", "0123456789");
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());


    }





    @Nested
    class TestsAnidadosRepetidos{
        @DisplayName("Creamos el contacto 5 veces") /* NOMBRE DEL TEST DINÁMICO*/
        @RepeatedTest(value = 5, name = "Repetir la creación del contacto {currentRepetition} of {totalRepetitions}")
        public void creamosContactoVariasVeces() {
            contactManager.addContact("John", "Doe", "0123456789");
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());


        }



    }

    @Nested
    class TestsAnidadosParametrizados{
        @DisplayName("Tests parametrizados") /* NOMBRE DEL TEST DINÁMICO*/
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0654587456", "0123456789"})
        public void creamosTestParametrizado(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }



        @DisplayName("Tests parametrizados con CSV") /* NOMBRE DEL TEST DINÁMICO*/
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456781", "0468798162"})
        public void testFormatoNumeroCSV(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }

        @DisplayName("Tests parametrizados con CSV") /* NOMBRE DEL TEST DINÁMICO*/
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void testFormatoNumeroArchivoCSV(String phoneNumber) {
            contactManager.addContact("John", "Doe", phoneNumber);
            assertFalse(contactManager.getAllContacts().isEmpty());
            assertEquals(1, contactManager.getAllContacts().size());
        }

    }


}
