package ite.product.gearheadproduct.service.impl;

import ite.product.gearheadproduct.entity.Color;
import ite.product.gearheadproduct.exception.ResourceNotFound;
import ite.product.gearheadproduct.repository.ColorRepository;
import ite.product.gearheadproduct.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;
    @Override
    public List<Color> getColor() {
        return colorRepository.findAll();
    }
    @Override
    public Color postColor(Color color) {
        return colorRepository.save(color);
    }
    @Override
    public Color updateColor(Long id ,Color color) {
        Color findColor = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(id,"Color"));
        findColor.setCreated(color.getCreated());
        findColor.setDescription(color.getDescription());
        findColor.setColorCode(color.getColorCode());
        return colorRepository.save(findColor);
    }
    @Override
    public Color getColorById(Long id) {
        Color findColor = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(id,"Color"));
        return findColor;
    }

    @Override
    public String deleteColor(Long id) {
        Color findColor = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(id,"Color"));
        colorRepository.delete(findColor);
        return "Delete Successfully";
    }
}
