package com.br.security_system_backend.service;

import org.springframework.scheduling.annotation.Scheduled;

public class eventAutomation {

    private final SensorEventService sensorEventService;

    public eventAutomation(SensorEventService sensorEventService) {
        this.sensorEventService = sensorEventService;
    }


    @Scheduled(fixedRate = 1000)
    public void sendEvent(){
        int random = (int) (Math.random() * 10);

        if (random == 1){
            sensorEventService.saveEvent("sensorMovimento", "motion", "detected");
        } else if (){
            
        }

    }


}
