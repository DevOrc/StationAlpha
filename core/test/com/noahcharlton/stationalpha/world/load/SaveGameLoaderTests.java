package com.noahcharlton.stationalpha.world.load;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.XmlReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class SaveGameLoaderTests {

    @Test
    void verifyVersionSameVersionTest() {
        XmlReader.Element element = LoadTestUtils.asChild("<SaveInfo GameVersion=\"0.2.0\"/>");

        SaveGameLoader.verifyVersion(element);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "<SaveInfo GameVersion=\"1.2.1\"/>",
            "<SaveInfo GameVersion=\"Foo1\"/>",
            "<SaveInfo/>"})
    void verifyVersionNotSameVersionFailsTest(String xml) {
        XmlReader.Element element = LoadTestUtils.asChild(xml);

        Assertions.assertThrows(GdxRuntimeException.class, () -> SaveGameLoader.verifyVersion(element));
    }
}
