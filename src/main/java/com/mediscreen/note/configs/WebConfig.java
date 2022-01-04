package com.mediscreen.note.configs;

import com.mediscreen.note.models.Note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;

@Configuration
public class WebConfig {

    @Autowired
    private MongoOperations mongoOperations;

    // https://www.baeldung.com/spring-data-jpa-repository-populators
    // This bean is needed to populate the DB when the application starts.
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean getRespositoryPopulator() {
        mongoOperations.dropCollection(Note.class); // Dropping the existing DB collection when the app starts.
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] { new ClassPathResource("note-data.json") });
        return factory;
    }   
}
