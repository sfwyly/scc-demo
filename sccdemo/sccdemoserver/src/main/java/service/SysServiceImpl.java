package service;

import bean.Param;
import bean.SCCSystem;
import bean.SCCUser;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import util.RedisStringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SysServiceImpl
 * @Description TODO
 * @Author 逝风无言
 * @Data 2019/9/14 11:14
 * @Version 1.0
 **/
@Service
public class SysServiceImpl implements SysService{


    @Autowired
    private RedisStringUtils redisStringUtils;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private Gson gson;

    @Override
    public SCCUser queryUserInfo(String token) {

        String value = redisStringUtils.get(RedisStringUtils.USER_CACHE_PREFIX+token);
        SCCUser sccUser = gson.fromJson(value,SCCUser.class);

        return sccUser;
    }

    @Override
    public List<SCCSystem> querySysInfo() {

        List<Map<String,Object>> objects = sqlSessionTemplate.selectList("sysMapper.querySysInfo");
        return convertdb2Obj(objects);
    }

    private List<SCCSystem> convertdb2Obj(List<Map<String,Object>> dbRes){

        if(CollectionUtils.isEmpty(dbRes)){
            return null;
        }

        List<SCCSystem> result = Lists.newArrayList();
        dbRes.forEach(k ->{
            result.add(new SCCSystem(((Integer)k.get("sysid")).intValue(),k.get("sysname").toString()));
        });
            return result;
    }
    @Override
    public List<Param> queryParamInfo(String sysId, String envName) {
        List<Map<String,Object>> res = sqlSessionTemplate.selectList("sysMpper.queryParamInfo", ImmutableMap.of("SysId",sysId,"EnvName",envName));
        if(CollectionUtils.isEmpty(res)){
            return null;
        }
        List<Param> result = Lists.newArrayList();

        res.forEach(k->{
            int id= ((Integer)k.get("id")).intValue();
            String paramKey = (String) k.get("paramKey");
            String paramValue = (String) k.get("paramValue") ;
            result.add(new Param(id,paramKey,paramValue));
        });
        return result;
    }

    @Override
    public int updateParam(String paramId, String paramValue) {

        int affectCount =sqlSessionTemplate.update("sysMapper.updateParam",ImmutableMap.of("Id",paramId,"ParamValue",paramValue));


        return affectCount;
    }
}
