package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.entity.Transmission;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.repository.TransmissionRepository;
import ite.product.gearheadproduct.service.TransmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransmissionServiceImpl implements TransmissionService {

    private final TransmissionRepository transmissionRepository;
    @Override
    public List<Transmission> getTransmission() {
        return transmissionRepository.findAll();
    }

    @Override
    public Transmission postTransmission(Transmission transmission) {
        return transmissionRepository.save(transmission);
    }

    @Override
    public String deleteTransmission(Long id) {
        Transmission transmission = transmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(id , "Transmission"));
        transmissionRepository.delete(transmission);
        String message = "Delete Successfully";
        return message;
    }

    @Override
    public Transmission findById(Long id) {
        Transmission transmission = transmissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(id , "Transmission"));
        return transmission;
    }
}
