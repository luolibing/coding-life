package cn.tim;

/**
 * Created by LuoLiBing on 15/8/8.
 */
public class ExecutorImpl extends AbstractExecutor<Entity> {

    @Override
    Entity callDatabase(String msg) {
        try {
            Thread.sleep(1000*1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Entity entity = new Entity();
        entity.setId((int) System.currentTimeMillis());
        entity.setMsg(msg + System.currentTimeMillis());
        return entity;
    }
}

class Entity {
    int id;
    String msg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
