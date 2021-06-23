package com.teamresourceful.fruits;

import com.teamresourceful.fruits.common.registry.ModClient;
import net.fabricmc.api.ClientModInitializer;

public class FruitsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModClient.onInitialize();
    }
}
