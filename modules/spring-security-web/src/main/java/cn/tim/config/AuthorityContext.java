package cn.tim.config;

/**
 * Created by LuoLiBing on 15/9/1.
 */
/*@Component
public class AuthorityContext implements InitializingBean {

    @Autowired
    private AuthorityJpaRepository authorityJpaRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<Authority> authorities = authorityJpaRepository.findAll();
        initContext(authorities);
    }

    private final static Map<String, Authority> context = new HashMap<>();

    public static void initContext(List<Authority> authorities) {
        if(authorities != null) {
            for(Authority authority: authorities) {
                context.put(authority.getUrl(), authority);
            }
        }
    }

    public static Authority getAuthority(String uri) {
        return context.get(uri);
    }

}*/
