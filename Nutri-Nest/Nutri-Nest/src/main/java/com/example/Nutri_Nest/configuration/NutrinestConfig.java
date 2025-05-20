package com.example.Nutri_Nest.configuration;


import com.example.Nutri_Nest.dto.*;
import com.example.Nutri_Nest.entity.*;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NutrinestConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper Mmapper = new ModelMapper();
        Mmapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(AccessLevel.PRIVATE) // required for Lombok-generated fields
                .setSkipNullEnabled(false);

        Mmapper.typeMap(UserDTO.class, User.class).addMappings(mapper -> mapper.skip(User::setId)); // Ignore 'id' during mapping
        Mmapper.typeMap(FitnessActivityDTO.class, FitnessActivity.class).addMappings(mapper -> mapper.skip(FitnessActivity::setId)); // Ignore 'id' during mapping
        Mmapper.typeMap(DietPlanDTO.class, DietPlan.class).addMappings(mapper -> mapper.skip(DietPlan::setId)); // Ignore 'id' during mapping
        Mmapper.typeMap(UserProgressDTO.class, UserProgress.class).addMappings(mapper -> mapper.skip(UserProgress::setId)); // Ignore 'id' during mapping
        Mmapper.typeMap(GoalReportDTO.class, GoalReport.class).addMappings(mapper -> mapper.skip(GoalReport::setId)); // Ignore 'id' during mapping


        return Mmapper;
    }
}
