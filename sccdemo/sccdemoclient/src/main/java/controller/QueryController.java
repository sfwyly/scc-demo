package controller;

import com.google.common.collect.ImmutableMap;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName QueryController
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/14 14:21
 * @Version 1.0
 **/
@RestController
public class QueryController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    @GetMapping("write/cache/{key}/{value}")
    public String writeCache(@PathVariable String key ,@PathVariable String value){
        stringRedisTemplate.opsForValue().set(key,value);
        return "success";
    }

    @GetMapping("/write/db/{value}")
    public int writeDb(@PathVariable  String value){
        int affectCount = sqlSessionTemplate.insert("testMapper.testInsert",
                ImmutableMap.of("Value",value));

        return affectCount;
    }


}
