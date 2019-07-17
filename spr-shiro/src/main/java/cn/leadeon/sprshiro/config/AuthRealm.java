package cn.leadeon.sprshiro.config;

import cn.leadeon.sprshiro.entity.Permission;
import cn.leadeon.sprshiro.entity.Role;
import cn.leadeon.sprshiro.entity.User;
import cn.leadeon.sprshiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @description: 自定义realm
 *      用于进行权限信息的验证，我们自己实现。Realm 本质上是一个特定的安全 DAO：它封装与数据源连接的细节，得到Shiro
 *      所需的相关的数据。在配置 Shiro 的时候，你必须指定至少一个Realm 来实现认证（authentication）和/或授权（authorization）
 * @author: he.l
 * @create: 2019-05-22 17:01
 **/
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    /**
     * 授权访问控制
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取前端输入的用户信息，封装为User对象
        User userweb = (User) principals.getPrimaryPrincipal();
        //获取前端输入的用户名
        String username = userweb.getUsername();
        //根据前端输入的用户名查询数据库中对应的记录
        User user = userService.findByUsername(username);
        //如果数据库中有该用户名对应的记录，就进行授权操作
        if (user != null){
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //因为addRoles和addStringPermissions方法需要的参数类型是Collection
            //所以先创建两个collection集合
            Collection<String> rolesCollection = new HashSet<String>();
            Collection<String> perStringCollection = new HashSet<String>();
            //获取user的Role的set集合
            Set<Role> roles = user.getRoles();
            //遍历集合
            for (Role role : roles){
                //将每一个role的name装进collection集合
                rolesCollection.add(role.getName());
                //获取每一个Role的permission的set集合
                Set<Permission> permissionSet =  role.getPermissions();
                //遍历集合
                for (Permission permission : permissionSet){
                    //将每一个permission的name装进collection集合
                    perStringCollection.add(permission.getName());
                }
                //为用户授权
                info.addStringPermissions(perStringCollection);
            }
            //为用户授予角色
            info.addRoles(rolesCollection);
            return info;
        }else{
            return null;
        }

    }



    /**
     * 验证用户身份
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token携带了用户信息
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取前端输入的用户名
        String userName  = usernamePasswordToken.getUsername();
        //根据用户名查询数据库中对应的记录
        User user = userService.findByUsername(userName);
        //当前realm对象的name
        String realmName = getName();
        //盐值
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
        //封装用户信息，构建AuthenticationInfo对象并返回
        AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPassword(),
                credentialsSalt, realmName);
        return authcInfo;
    }
}
