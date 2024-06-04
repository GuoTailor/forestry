package org.gyh.forestry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gyh.forestry.domain.Animal;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.JsonPoint;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddAnimalReq;
import org.gyh.forestry.dto.req.AnimalPageReq;
import org.gyh.forestry.dto.resp.AnimalResp;
import org.gyh.forestry.mapper.UserMapper;
import org.gyh.forestry.service.AnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.DelayQueue;

@SpringBootTest
class ForestryApplicationTests {
    @Autowired
    private AnimalService animalService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper json;

    @Test
    void testQuery() throws InterruptedException {
        DelayQueue<MyDelayed> queue = new DelayQueue<>();
        queue.add(new MyDelayed(3000, "11"));
        Thread.ofPlatform().start(() -> {
            while (true) {
                try {
                    MyDelayed take = queue.take();
                    System.out.println(take.time + "  " + take.name);
                    queue.removeIf(it -> it.name.equals("11"));
                    queue.add(take);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Thread.sleep(1000);
        queue.add(new MyDelayed(1000, "22"));
        Thread.sleep(3000);
    }

    @Test
    void testAdd() {
        User user = new User();
        user.setUsername("gyh");
        user.setPassword("123456");
        user.setEnable(true);
        user.setCreateTime(LocalDateTime.now());
        userMapper.insertSelective(user);
        System.out.println(user.getId());
    }

    @Test
    void contextLoads() {
        User user = userMapper.selectByPrimaryKey(1);
        System.out.println(user);
        JsonPoint point = new JsonPoint();
        point.setX(142.01);
        point.setY(23.01);
        point.setZ(12.0);

        AddAnimalReq animal = new AddAnimalReq();
        animal.setLocation(point);
        animal.setLocationZh("nmka");
        List<Animal> animals = animalService.addAnimal(List.of(animal));

        AnimalResp animal1 = animalService.selectById(animals.getFirst().getId());
        System.out.println(animal1);
    }

    @Test
    void testPage() throws JsonProcessingException {
        AnimalPageReq pageReq = new AnimalPageReq();
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
