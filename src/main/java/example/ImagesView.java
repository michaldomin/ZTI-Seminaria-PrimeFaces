package example;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class ImagesView {

    @Getter
    private List<String> images;

    @PostConstruct
    public void init() {
        images = new ArrayList<String>();
        for (int i = 1; i <= 10; i++) {
            images.add("natura" + i + ".jpg");
        }
    }
}