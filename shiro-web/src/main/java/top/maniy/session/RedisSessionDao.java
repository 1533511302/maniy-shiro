package top.maniy.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.util.CollectionUtils;
import org.springframework.util.SerializationUtils;
import top.maniy.util.JedisUtil;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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

    private void saveSession(Session session){

        if(session !=null && session.getId()!=null) {
            byte[] key = getKey(session.getId().toString());
            byte[] value = SerializationUtils.serialize(session);
            jedisUtil.set(key, value);
            jedisUtil.expire(key, 600);
        }
    }


    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session,sessionId);
        saveSession(session);

        return sessionId;
    }

    protected Session doReadSession(Serializable serializable) {
        System.out.println("read session");
        if(serializable ==null){
            return null;
        }
        byte[] key =getKey(serializable.toString());
        byte[] value = jedisUtil.get(key);
        return (Session) SerializationUtils.deserialize(value);
    }

    public void update(Session session) throws UnknownSessionException {
       saveSession(session);
    }

    public void delete(Session session) {
        if(session ==null || session.getId()==null){
            return;
        }
        byte[] key =getKey(session.getId().toString());
        jedisUtil.del(key);
    }

    public Collection<Session> getActiveSessions() {
        Set<byte[]> keys =jedisUtil.keys(SHIRO_SEESION_PREFIX);
        Set<Session> sessions =new HashSet<Session>();
        if(CollectionUtils.isEmpty(keys)){
            return sessions;
        }
        for(byte[] key:keys){
            Session session =(Session) SerializationUtils.deserialize(jedisUtil.get(key));
            sessions.add(session);
        }
        return sessions;
    }
}
