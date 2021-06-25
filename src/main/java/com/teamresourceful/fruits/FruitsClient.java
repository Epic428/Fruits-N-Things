package com.teamresourceful.fruits;

import com.teamresourceful.fruits.client.ModClient;
import net.fabricmc.api.ClientModInitializer;

public class FruitsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModClient.onInitialize();
    }
}
