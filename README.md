# scc-demo 
## 慕课网  应用SpringCloud完成自定义配置中心开发实践

### 1.介绍
> 对于分布式微服务，各个服务之间需要单独的配置文件，通过开发配置中心，进行开发配置的统一管理，并且可以方便的实现实现动态的更新

### 2.主要实现
> *子系统配置配置中心的远程主机地址*
```
system:
 id: 1
 name: 订单系统
 env: online
 scc-server: localhost:8020
 redis-enable: true
 db-enable: true
```
> 其中scc-server配置远程主机地址，**redis-enable**与**db-enable**项表示是否需要连接使用redis与database，后续会通过配置来决定是否注册对应bean

> *子系统配置项注册机制*
>> *其中DB的配置注册中心实现如下*
```
@Configuration
@ConditionalOnProperty(name="system.db-enable",havingValue="true")
@AutoConfigureAfter(ParamConfiguration.class)
public class DBConfiguration {

    @Autowired
    ParamConfiguration paramConfiguration;

    @Bean
    public DataSource dataSource(){
        DBParam dbParam = paramConfiguration.getDbParam();
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbParam.getUrl());
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername(dbParam.getUsername());
        dataSource.setPassword(dbParam.getPassword());
        dataSource.setInitialSize(1);
        dataSource.setMaxActive(20);

    }
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception{
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);

        List<Resource> resources = Lists.newArrayList();
        resources.addAll(Arrays.asList(
                new PathMatchingResourcePatternResolver().getResource("classpath:mapper/*.xml")
        ));

        sessionFactoryBean.setMapperLocations(resources.toArray(
                new Resource[resources.size()]
        ));
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
```
> 其中 **@ConditionalOnProperty(name="system.db-enable",havingValue="true")** 注解将根据配置项system.db-enable是否为true进行决定是否加载该配置类， **@AutoConfigureAfter(ParamConfiguration.class)** 注解将该配置类的spring容器加载时间放在 **ParamConfiguration** 类之后，因为 **ParamConfiguration** 配置类需要从注册中心先动态获取DB与Redis的必要信息

> *ParamConfiguration配置类实现如下*
```
@Configuration
public class ParamConfiguration {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Gson gson;

    @Value("${system.id}")
    private int sysId;
    @Value("${system.name}")
    private String sysName;
    @Value("${system.env}")
    private String envName;
    @Value("${system.scc-server}")
    private String sccServer;

    private RedisParam redisParam;

    private DBParam dbParam;

    @PostConstruct
    public void init() throws Exception{
        //读取远程配置
        Map<String,String> result = initParams();

        //初始化缓存连接
        initRedisParam(result);
        //初始化数据库连接
        initDbParam(result);
    }

    public RedisParam getRedisParam() {
        return redisParam;
    }

    public DBParam getDbParam() {
        return dbParam;
    }

    private DBParam initDbParam(Map<String,String> result) throws Exception{
        for (int i=0;i<DB_KEY_TMPL.length;i++){
            if(StringUtils.isEmpty(result.get(DB_KEY_TMPL[i]))){
                throw new Exception("必要参数："+DB_KEY_TMPL[i]);
            }
        }
        String url = result.get(DB_KEY_TMPL[0]);
        String username = result.get(DB_KEY_TMPL[1]);
        String password = result.get(DB_KEY_TMPL[2]);
       return new DBParam(url,username,password);
    }
    private static final String[] DB_KEY_TMPL={
            "scc.db.url","scc.db.user","scc.db.password"
    };
    private static final String[] REDIS_KEY_TMPL={
            "scc.redis.ip","scc.redis.port","scc.redis.timeout"
    };

    private RedisParam initRedisParam(Map<String,String> result) throws Exception{
        for (int i=0;i<REDIS_KEY_TMPL.length;i++){
            if(StringUtils.isEmpty(result.get(REDIS_KEY_TMPL[i]))){
                throw new Exception("必要参数："+REDIS_KEY_TMPL[i]);
            }
        }
        String host = result.get(REDIS_KEY_TMPL[0]);
        int port = Integer.parseInt(result.get(REDIS_KEY_TMPL[1]));
        long timeout = Long.parseLong(result.get(REDIS_KEY_TMPL[2]));

        return new RedisParam(host,port, Duration.ofMillis(timeout));
    }

    private Map<String,String> initParams() throws Exception{

       RequestResult requestResult = restTemplate.postForObject("http://"+sccServer+"/queryparaminfo/"+sysId+"/"+envName,null, RequestResult.class);
       if(requestResult ==null){
           throw new Exception("初始化配置中心连接出错");
       }
       if("0".equals(requestResult.getStatus())){
           throw new Exception("初始化配置中心连接出错");
       }
       Map<String,String> paramMap = Maps.newHashMap();

        List<Param> params = Lists.newArrayList();
       new JsonParser().parse(requestResult.getData().toString())
               .getAsJsonArray().forEach(jsonElement -> {
                   params.add(gson.fromJson(jsonElement,Param.class));
       });

       params.forEach(param->{
           paramMap.put(param.getKey(),param.getValue());

       });
       return paramMap;
    }

}
```
> 当spring容器记载该配置类时，通过 **@PostConstruct** 注解将首先执行**init（）**方法,通过远程请求注册中心api接口，通过sysId与sysName字段查询DB与Redis参数

> 上述过程实现了客户端动态调用远程中心API进行动态连接数据库进行操作
