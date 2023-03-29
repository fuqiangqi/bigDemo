// Handler接口定义了处理请求的方法
public interface Handler {
    void handle(Request request);
}
// 具体的Handler实现类
public class HandlerA implements Handler {
    private Handler next;
    private TransactionManager transactionManager;
    public HandlerA(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    @Override
    public void handle(Request request) {
        // 处理请求
        // ...
        // 如果需要，向下传递请求
        if (next != null) {
            next.handle(request);
        }
        // 如果需要提交事务
        transactionManager.commit();
    }
    public void setNext(Handler next) {
        this.next = next;
    }
}
public class HandlerB implements Handler {
    private Handler next;
    private TransactionManager transactionManager;
    public HandlerB(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }
    @Override
    public void handle(Request request) {
        // 处理请求
        // ...
        // 如果需要，向下传递请求
        if (next != null) {
            next.handle(request);
        }
        // 如果需要提交事务
        transactionManager.commit();
    }
    public void setNext(Handler next) {
        this.next = next;
    }
}
// 事务管理器
public class TransactionManager {
    public void begin() {
        // 开始事务
        // ...
    }
    public void commit() {
        // 提交事务
        // ...
    }
    public void rollback() {
        // 回滚事务
        // ...
    }
}
// 客户端代码
public class Client {
    public static void main(String[] args) {
        TransactionManager transactionManager = new TransactionManager();
        HandlerA handlerA = new HandlerA(transactionManager);
        HandlerB handlerB = new HandlerB(transactionManager);
        handlerA.setNext(handlerB);
        // 启动事务
        transactionManager.begin();
        // 处理请求
        Request request = new Request();
        handlerA.handle(request);
        // 提交事务
        transactionManager.commit();
    }
}