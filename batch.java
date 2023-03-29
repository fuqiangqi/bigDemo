import java.util.ArrayList;
import java.util.List;
/**
 * 抽象策略接口
 */
interface Strategy {
    boolean execute(Object data);
}
/**
 * 具体策略A
 */
class ConcreteStrategyA implements Strategy {
    @Override
    public boolean execute(Object data) {
        System.out.println("执行策略A");
        // do something
        return true;
    }
}
/**
 * 具体策略B
 */
class ConcreteStrategyB implements Strategy {
    @Override
    public boolean execute(Object data) {
        System.out.println("执行策略B");
        // do something
        return true;
    }
}
/**
 * 抽象处理器
 */
abstract class Handler {
    protected Handler nextHandler;
    protected List<Strategy> strategies;
    public Handler(List<Strategy> strategies) {
        this.strategies = strategies;
    }
    public void setNextHandler(Handler handler) {
        this.nextHandler = handler;
    }
    public boolean handleRequest(Object data) {
        boolean result = false;
        if (strategies != null && strategies.size() > 0) {
            for (Strategy strategy : strategies) {
                result = strategy.execute(data);
                if (!result) {
                    break;
                }
            }
        }
        if (result && nextHandler != null) {
            result = nextHandler.handleRequest(data);
        }
        return result;
    }
}
/**
 * 具体处理器A
 */
class ConcreteHandlerA extends Handler {
    public ConcreteHandlerA(List<Strategy> strategies) {
        super(strategies);
    }
}
/**
 * 具体处理器B
 */
class ConcreteHandlerB extends Handler {
    public ConcreteHandlerB(List<Strategy> strategies) {
        super(strategies);
    }
}
/**
 * 客户端代码
 */
public class Client {
    public static void main(String[] args) {
        List<Strategy> strategies = new ArrayList<>();
        strategies.add(new ConcreteStrategyA());
        strategies.add(new ConcreteStrategyB());
        Handler handlerA = new ConcreteHandlerA(strategies);
        Handler handlerB = new ConcreteHandlerB(strategies);
        handlerA.setNextHandler(handlerB);
        Object data = new Object(); // 待处理数据
        boolean result = handlerA.handleRequest(data);
        if (result) {
            // 处理成功
        } else {
            // 处理失败
        }
    }
}