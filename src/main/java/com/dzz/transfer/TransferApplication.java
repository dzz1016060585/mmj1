package com.dzz.transfer;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.EventListener;

import javax.annotation.Resource;
import java.util.List;

@EnableProcessApplication
@MapperScan(basePackages = "com.dzz.transfer.model.dao")
@SpringBootApplication
public class TransferApplication{
    public static void main(String[] args) {
        SpringApplication.run(TransferApplication.class, args);
    }

    @Autowired
    private RuntimeService runtimeService;

//    @EventListener
//    private void processPostDeploy(PostDeployEvent event) {
//        runtimeService.startProcessInstanceByKey("localApproval");
//    }

}
