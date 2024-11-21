package org.gyh.forestry.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.WeatherData;
import org.gyh.forestry.mapper.WeatherDataMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;

/**
 * create by GYH on 2024/11/21
 */
@Slf4j
@Service
public class WeatherDataService {
    @Resource
    private WeatherDataMapper weatherDataMapper;
    @Value("${spring.datasource.password}")
    private String password;

    public WeatherData getTodayWeather(String name) {
        WeatherData weatherData = weatherDataMapper.selectByName(name);
        if (weatherData == null) {
            weatherData = new WeatherData();
            weatherData.setName(name);
            weatherData.setUpdateTime(LocalDateTime.now().minusHours(2));
            weatherDataMapper.insertSelective(weatherData);
        }
        if (weatherData.getUpdateTime().isBefore(LocalDateTime.now().minusHours(1))) {
            String[] command = new String[]{"python3.8", "./pydir/weather.py", "forestry", "postgres", password, name, name};
            try {
                log.info("开始执行命令");
                Process process = Runtime.getRuntime().exec(command);
                // 读取输出流
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder sb = new StringBuilder();
                for (String line; (line = reader.readLine()) != null; log.info(line)) {
                    sb.append(line).append("\n");
                }
                for (String line; (line = errReader.readLine()) != null; log.error(line)) {
                    sb.append(line).append("\n");
                }
                reader.close();
                reader.close();
                int exitVal = process.waitFor();
                if (exitVal == 0) {
                    log.info("命令执行成功，{}", sb);
                    weatherData = weatherDataMapper.selectByName(name);
                } else {
                    log.info("执行命令出错，{}", sb);
                }
            } catch (IOException | InterruptedException e) {
                log.error("执行命令出错", e);
            }
        }
        return weatherData;
    }
}
