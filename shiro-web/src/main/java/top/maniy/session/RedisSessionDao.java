package top.maniy.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.SerializationUtils;
import top.maniy.util.JedisUtil;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author liuzonghua
 * @Package top.maniy.session
 * @Description:
 * @date 2018/10/9 14:56
 */
public class RedisSessionDao extends AbstractSessionDAO{
    @Resource
    private JedisUtil jedisUtil;

    private final String SHIRO_SEESION_PREFIX="maniy-session:";

    private byte[] getKey (String key){
        return (SHIRO_SEESION_PREFIX+key).getBytes();
    }

    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);

        byte[] key = getKey(session.getId().toString());
        byte[] value = SerializationUtils.serialize(session);
        jedisUtil.set(key,value);
        jedisUtil.expire(key,600);
        return sessionId;
    }

    protected Session doReadSession(Serializable serializable) {
        if(serializable ==null){
            return null;
        }
        byte[] key =getKey(serializable.toString());
        byte[] value = jedisUtil.get(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    public void update(Session session) throws UnknownSessionException {

    }

    public void delete(Session session) {

    }

    public Collection<Session> getActiveSessions() {
        return null;
    }
}
