package org.gyh.forestry;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.gyh.forestry.domain.AnimalRecognition;
import org.gyh.forestry.domain.Moxing;
import org.gyh.forestry.domain.User;
import org.gyh.forestry.dto.JsonPoint;
import org.gyh.forestry.dto.PageInfo;
import org.gyh.forestry.dto.req.AddAnimalRecognitionReq;
import org.gyh.forestry.dto.req.AnimalRecognitionPageReq;
import org.gyh.forestry.dto.resp.AnimalRecognitionResp;
import org.gyh.forestry.mapper.MoxingMapper;
import org.gyh.forestry.mapper.UserMapper;
import org.gyh.forestry.service.AnimalRecognitionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.DelayQueue;

@SpringBootTest
class ForestryApplicationTests {
    @Autowired
    private AnimalRecognitionService animalRecognitionService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper json;
    @Resource
    private MoxingMapper moxingMapper;

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
    void testMoxing() {
        Moxing moxing = moxingMapper.selectByPrimaryKey(1);
        System.out.println(moxing);
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

        AddAnimalRecognitionReq animal = new AddAnimalRecognitionReq();
        animal.setLocation(point);
        animal.setLocationZh("nmka");
        List<AnimalRecognitionResp> animals = animalRecognitionService.addAnimal(List.of(animal));

        AnimalRecognitionResp animal1 = animalRecognitionService.selectById(animals.getFirst().getId());
        System.out.println(animal1);
    }

    @Test
    void testPage() throws JsonProcessingException {
        AnimalRecognitionPageReq pageReq = new AnimalRecognitionPageReq();
        pageReq.setPage(0);
        pageReq.setPageSize(2);
        PageInfo<AnimalRecognitionResp> animalPageInfo = animalRecognitionService.selectByPage(pageReq);
        String x = json.writeValueAsString(animalPageInfo);
        System.out.println(x);
        PageInfo<AnimalRecognition> animalPageInfo1 = json.readValue(x, new TypeReference<PageInfo<AnimalRecognition>>() {
        });
        System.out.println(animalPageInfo1.getData().get(0).getLocation().getX());
    }

}
