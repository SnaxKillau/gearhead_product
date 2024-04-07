package ite.product.gearheadproduct.service;

import ite.product.gearheadproduct.entity.Transmission;
import lombok.extern.java.Log;

import java.util.List;

public interface TransmissionService {
    public List<Transmission> getTransmission();
    public Transmission postTransmission(Transmission transmission);
    public String deleteTransmission(Long id);

    public Transmission findById(Long id);
}
