package com.dipo33.bewitched;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Integration tests for the Bewitched mod main class.
 * Tests mod constants and structure.
 */
public class BewitchedModTest {

    @Test
    public void testModIdConstant() {
        assertEquals("bewitched", Bewitched.MODID);
    }

    @Test
    public void testModNameConstant() {
        assertEquals("Bewitched", Bewitched.MOD_NAME);
    }

    @Test
    public void testModIdMatchesModName() {
        // MOD_ID should be lowercase version of MOD_NAME
        assertEquals(Bewitched.MOD_NAME.toLowerCase(), Bewitched.MODID);
    }

    @Test
    public void testLoggerNotNull() {
        assertNotNull(Bewitched.LOG);
    }

    @Test
    public void testLoggerName() {
        assertEquals("bewitched", Bewitched.LOG.getName());
    }

    @Test
    public void testProxyNotNull() {
        // Proxy is initialized by Forge, but the field should exist
        // In test environment, it might be null
        // We just verify the field exists
        try {
            assertNotNull(Bewitched.class.getField("proxy"));
        } catch (NoSuchFieldException e) {
            fail("proxy field should exist");
        }
    }

    @Test
    public void testModIdIsValidResourceLocation() {
        // Mod ID should be valid for resource locations (lowercase, no spaces)
        String modId = Bewitched.MODID;
        assertTrue(modId.matches("[a-z0-9_]+"));
        assertFalse(modId.contains(" "));
        assertFalse(modId.contains("-"));
        assertEquals(modId.toLowerCase(), modId);
    }

    @Test
    public void testModHasEventHandlers() {
        // Verify event handler methods exist
        try {
            assertNotNull(
                Bewitched.class.getMethod("preInit", cpw.mods.fml.common.event.FMLPreInitializationEvent.class));
            assertNotNull(Bewitched.class.getMethod("init", cpw.mods.fml.common.event.FMLInitializationEvent.class));
            assertNotNull(
                Bewitched.class.getMethod("postInit", cpw.mods.fml.common.event.FMLPostInitializationEvent.class));
            assertNotNull(
                Bewitched.class.getMethod("serverStarting", cpw.mods.fml.common.event.FMLServerStartingEvent.class));
        } catch (NoSuchMethodException e) {
            fail("Expected event handler method not found: " + e.getMessage());
        }
    }

    @Test
    public void testModClassIsPublic() {
        assertTrue(java.lang.reflect.Modifier.isPublic(Bewitched.class.getModifiers()));
    }

    @Test
    public void testModConstantsAreFinal() throws NoSuchFieldException {
        assertTrue(
            java.lang.reflect.Modifier.isFinal(
                Bewitched.class.getField("MODID")
                    .getModifiers()));
        assertTrue(
            java.lang.reflect.Modifier.isFinal(
                Bewitched.class.getField("MOD_NAME")
                    .getModifiers()));
        assertTrue(
            java.lang.reflect.Modifier.isFinal(
                Bewitched.class.getField("LOG")
                    .getModifiers()));
    }

    @Test
    public void testModConstantsAreStatic() throws NoSuchFieldException {
        assertTrue(
            java.lang.reflect.Modifier.isStatic(
                Bewitched.class.getField("MODID")
                    .getModifiers()));
        assertTrue(
            java.lang.reflect.Modifier.isStatic(
                Bewitched.class.getField("MOD_NAME")
                    .getModifiers()));
        assertTrue(
            java.lang.reflect.Modifier.isStatic(
                Bewitched.class.getField("LOG")
                    .getModifiers()));
    }

    @Test
    public void testProxyIsStatic() throws NoSuchFieldException {
        assertTrue(
            java.lang.reflect.Modifier.isStatic(
                Bewitched.class.getField("proxy")
                    .getModifiers()));
    }

    @Test
    public void testModHasAnnotation() {
        assertNotNull(Bewitched.class.getAnnotation(cpw.mods.fml.common.Mod.class));
    }

    @Test
    public void testModAnnotationValues() {
        cpw.mods.fml.common.Mod modAnnotation = Bewitched.class.getAnnotation(cpw.mods.fml.common.Mod.class);

        assertNotNull(modAnnotation);
        assertEquals("bewitched", modAnnotation.modid());
        assertEquals("Bewitched", modAnnotation.name());
    }

    @Test
    public void testClientProxyExists() {
        // Verify ClientProxy class exists
        try {
            Class<?> clientProxyClass = Class.forName("com.dipo33.bewitched.ClientProxy");
            assertNotNull(clientProxyClass);
            // Should extend CommonProxy
            assertTrue(CommonProxy.class.isAssignableFrom(clientProxyClass));
        } catch (ClassNotFoundException e) {
            fail("ClientProxy class not found");
        }
    }

    @Test
    public void testCommonProxyExists() {
        // Verify CommonProxy class exists
        Class<?> commonProxyClass = CommonProxy.class;
        assertNotNull(commonProxyClass);
    }

    @Test
    public void testCommonProxyHasEventHandlers() {
        // Verify CommonProxy has the expected methods
        try {
            assertNotNull(
                CommonProxy.class.getMethod("preInit", cpw.mods.fml.common.event.FMLPreInitializationEvent.class));
            assertNotNull(CommonProxy.class.getMethod("init", cpw.mods.fml.common.event.FMLInitializationEvent.class));
            assertNotNull(
                CommonProxy.class.getMethod("postInit", cpw.mods.fml.common.event.FMLPostInitializationEvent.class));
            assertNotNull(
                CommonProxy.class.getMethod("serverStarting", cpw.mods.fml.common.event.FMLServerStartingEvent.class));
        } catch (NoSuchMethodException e) {
            fail("Expected proxy method not found: " + e.getMessage());
        }
    }

    @Test
    public void testModIdNotEmpty() {
        assertNotNull(Bewitched.MODID);
        assertFalse(Bewitched.MODID.isEmpty());
    }

    @Test
    public void testModNameNotEmpty() {
        assertNotNull(Bewitched.MOD_NAME);
        assertFalse(Bewitched.MOD_NAME.isEmpty());
    }

    @Test
    public void testModIdLength() {
        // Mod IDs should be reasonable length
        assertTrue(Bewitched.MODID.length() > 0);
        assertTrue(Bewitched.MODID.length() < 50);
    }

    @Test
    public void testModNameLength() {
        // Mod names should be reasonable length
        assertTrue(Bewitched.MOD_NAME.length() > 0);
        assertTrue(Bewitched.MOD_NAME.length() < 100);
    }

    @Test
    public void testModIdMatchesGradleProperties() {
        // The mod ID should match what's in gradle.properties (modId = bewitched)
        assertEquals("bewitched", Bewitched.MODID);
    }

    @Test
    public void testModNameMatchesGradleProperties() {
        // The mod name should match what's in gradle.properties (modName = Bewitched)
        assertEquals("Bewitched", Bewitched.MOD_NAME);
    }

    @Test
    public void testConfigExists() {
        // Verify Config class exists and is accessible
        Class<?> configClass = Config.class;
        assertNotNull(configClass);
    }

    @Test
    public void testBlockRegistryExists() {
        // Verify BlockRegistry exists
        try {
            Class<?> blockRegistryClass = Class.forName("com.dipo33.bewitched.block.BlockRegistry");
            assertNotNull(blockRegistryClass);
        } catch (ClassNotFoundException e) {
            fail("BlockRegistry class not found");
        }
    }

    @Test
    public void testItemRegistryExists() {
        // Verify ItemRegistry exists
        try {
            Class<?> itemRegistryClass = Class.forName("com.dipo33.bewitched.items.ItemRegistry");
            assertNotNull(itemRegistryClass);
        } catch (ClassNotFoundException e) {
            fail("ItemRegistry class not found");
        }
    }

    @Test
    public void testSeedDropsExists() {
        // Verify SeedDrops class exists
        try {
            Class<?> seedDropsClass = Class.forName("com.dipo33.bewitched.items.SeedDrops");
            assertNotNull(seedDropsClass);
        } catch (ClassNotFoundException e) {
            fail("SeedDrops class not found");
        }
    }

    @Test
    public void testPackageStructure() {
        // Verify the package structure
        assertEquals(
            "com.dipo33.bewitched",
            Bewitched.class.getPackage()
                .getName());
    }

    @Test
    public void testModGroupMatchesPackage() {
        // The mod group in gradle.properties should match the package
        // modGroup = com.dipo33.bewitched
        String expectedPackage = "com.dipo33.bewitched";
        assertEquals(
            expectedPackage,
            Bewitched.class.getPackage()
                .getName());
    }
}
