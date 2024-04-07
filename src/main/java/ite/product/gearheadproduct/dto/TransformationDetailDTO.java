package ite.product.gearheadproduct.dto;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface TransformationDetailDTO {
    Long getId();
    String getModel();
    Integer getYear();
    Integer getAvailable_Unit();
    Integer getPrice();
     String getDescriptions();
     Integer getMpg();
     Integer getCo2();
     Integer getPower();
     Integer getAcceleration();
     Integer getTop_Speed();

     Set<String> getImageNames();  // Assuming each transformation can have one image
     String getBrandDescription();
     String getConditionDescriptions();
     String getTransmissionDescriptions();
     String getColorDescription();
}
