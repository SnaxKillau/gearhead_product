package ite.product.gearheadproduct.service;

import ite.product.gearheadproduct.entity.Color;

import java.util.List;

public interface ColorService {
    public List<Color> getColor();
    public Color postColor(Color color);
    public Color updateColor(Long id, Color color);

    public String deleteColor(Long id);

    public Color getColorById(Long id);

}
