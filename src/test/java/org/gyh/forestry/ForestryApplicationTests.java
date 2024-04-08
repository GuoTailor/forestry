package org.gyh.forestry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.postgis.jdbc.geometry.Point;
import org.gyh.forestry.domain.Animal;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.PageReq;
import org.gyh.forestry.dto.resp.AnimalResp;
import org.gyh.forestry.mapper.UserMapper;
import org.gyh.forestry.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ForestryApplicationTests {
    @Autowired
    private AnimalService animalService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper json;


    @Test
    void contextLoads() {
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
        Point point = new Point();
        point.setSrid(4326);
        point.setX(142.01);
        point.setY(23.01);
        point.setZ(12);

        Animal animal = new Animal();
        animal.setLocation(point);
        animal.setLocationZh("nmka");
        animalService.addAnimal(animal);

        AnimalResp animal1 = animalService.selectById(animal.getId());
        System.out.println(animal1);
    }

    @Test
    void testPage() throws JsonProcessingException {
        PageReq pageReq = new PageReq();
        pageReq.setPage(0);
        pageReq.setPageSize(2);
        PageInfo<AnimalResp> animalPageInfo = animalService.selectByPage(pageReq);
        String x = json.writeValueAsString(animalPageInfo);
        System.out.println(x);
        PageInfo<Animal> animalPageInfo1 = json.readValue(x, new TypeReference<PageInfo<Animal>>() {
        });
        System.out.println(animalPageInfo1.getData().get(0).getLocation().getX());
    }

}
