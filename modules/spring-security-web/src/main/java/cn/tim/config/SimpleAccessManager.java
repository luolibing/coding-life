package cn.tim.config;

/**
 * Created by LuoLiBing on 15/8/31.
 * 自定义的访问权限控制manager
 */
/*
public class SimpleAccessManager implements AccessDecisionManager {


    *//**
     * authentication包含了当前用户信息
     * 当前正在请求的受保护对象
     * 当前正在访问的受保护对象的配置信息
     *
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     *//*
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        System.out.println(authentication);
        System.out.println(configAttributes);

        System.out.println(" ---------------  MyAccessDecisionManager --------------- ");
        if(configAttributes == null) {
            return;
        }

        // 登录用户权限认证
        if(authentication.getPrincipal() instanceof CurrentUser) {

            FilterInvocation filter = (FilterInvocation) object;
            String uri = filter.getRequest().getRequestURI();
            //Authority authority = AuthorityContext.getAuthority(uri);
            CurrentUser currentUser = (CurrentUser) authentication.getPrincipal();
            if (currentUser.getUser().accessCheck(uri) || checkPermitAll(configAttributes)) {
                return;
            }

        } else {
            // 匿名用户，所访问的资源是否可以匿名
            if(checkPermitAll(configAttributes)) {
                return;
            }
        }


        *//**
         * if( configAttributes == null ) {
            return ;
         }

         Iterator<ConfigAttribute> ite = configAttributes.iterator();

         while( ite.hasNext()){

             ConfigAttribute ca = ite.next();
             String needRole = ((SecurityConfig)ca).getAttribute();

             //ga 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
             for( GrantedAuthority ga: authentication.getAuthorities()){

                 if(needRole.trim().equals(ga.getAuthority().trim())){

                 return;
                 }

             }

         }
         *//*
        //没有权限
        throw new AccessDeniedException(" 没有权限访问！ ");
    }


    private boolean checkPermitAll(Collection<ConfigAttribute> configAttributes) {
        if(configAttributes == null) {
            return true;
        }
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            //访问所请求资源所需要的权限
            String needPermission = configAttribute.toString();
            //System.out.println("needPermission is " + needPermission);
            //用户所拥有的权限authentication
            if ("permitAll".equals(needPermission)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}*/
