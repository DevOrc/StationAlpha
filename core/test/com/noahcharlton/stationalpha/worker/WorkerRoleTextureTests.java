package com.noahcharlton.stationalpha.worker;

import com.badlogic.gdx.Gdx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class WorkerRoleTextureTests {

    @ParameterizedTest
    @EnumSource(WorkerRole.class)
    public void workerTextureExistsTest(WorkerRole workerRole){
        Assertions.assertTrue(Gdx.files.internal(workerRole.getWorkerTexture().getPath()).exists());
    }
}
