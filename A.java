import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        // 创建责任链
        TaskHandler handler = createChain();
        // 创建任务
        Task task1 = new Task("吃饭");
        Task task2 = new Task("学习");
        // 处理任务
        handler.handle(task1, 1);
        handler.handle(task2, 2);
    }
    // 创建责任链
    private static TaskHandler createChain() {
        // 创建吃饭、睡觉、打游戏这一组策略
        List<Strategy> strategies1 = new ArrayList<>();
        strategies1.add(new EatStrategy());
        strategies1.add(new SleepStrategy());
        strategies1.add(new GameStrategy());
        List<Integer> order1 = new ArrayList<>();
        order1.add(1);
        order1.add(2);
        order1.add(3);
        StrategyGroup group1 = new StrategyGroup(strategies1, order1);
        // 创建学习、听音乐、吃饭这一组策略
        List<Strategy> strategies2 = new ArrayList<>();
        strategies2.add(new StudyStrategy());
        strategies2.add(new MusicStrategy());
        strategies2.add(new EatStrategy());
        List<Integer> order2 = new ArrayList<>();
        order2.add(1);
        order2.add(2);
        order2.add(3);
        StrategyGroup group2 = new StrategyGroup(strategies2, order2);
        // 创建责任链
        TaskHandler handler = new TaskHandler();
        handler.setNextHandler(group1.init());
        handler.setNextHandler(group2.init());
        return handler;
    }
}
// 策略接口
interface Strategy {
    void execute(Task task, int param);
}
// 吃饭策略
class EatStrategy implements Strategy {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    @Override
    public void execute(Task task, int param) {
        try {
            if (task.getTaskName().equals("吃饭")) {
                System.out.println("Handler1: 吃饭，参数为：" + threadLocal.get());
            }
            threadLocal.set("eat");
        } catch (Exception e) {
            System.out.println("Handler1: 吃饭任务异常，退出整个策略链路");
            throw new RuntimeException("Handler1: 吃饭任务异常，退出整个策略链路", e);
        }
    }
}
// 睡觉策略
class SleepStrategy implements Strategy {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    @Override
    public void execute(Task task, int param) {
        try {
            if (task.getTaskName().equals("睡觉")) {
                System.out.println("Handler2: 睡觉，参数为：" + threadLocal.get());
            }
            threadLocal.set("sleep");
        } catch (Exception e) {
            System.out.println("Handler2: 睡觉任务异常，退出整个策略链路");
            throw new RuntimeException("Handler2: 睡觉任务异常，退出整个策略链路", e);
        }
    }
}
// 打游戏策略
class GameStrategy implements Strategy {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    @Override
    public void execute(Task task, int param) {
        try {
            if (task.getTaskName().equals("打游戏")) {
                System.out.println("Handler3: 打游戏，参数为：" + threadLocal.get());
            }
            threadLocal.set("game");
        } catch (Exception e) {
            System.out.println("Handler3: 打游戏任务异常，退出整个策略链路");
            throw new RuntimeException("Handler3: 打游戏任务异常，退出整个策略链路", e);
        }
    }
}
// 学习策略
class StudyStrategy implements Strategy {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    @Override
    public void execute(Task task, int param) {
        try {
            if (task.getTaskName().equals("学习")) {
                System.out.println("Handler1: 学习，参数为：" + threadLocal.get());
            }
            threadLocal.set("study");
        } catch (Exception e) {
            System.out.println("Handler1: 学习任务异常，退出整个策略链路");
            throw new RuntimeException("Handler1: 学习任务异常，退出整个策略链路", e);
        }
    }
}
// 听音乐策略
class MusicStrategy implements Strategy {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    @Override
    public void execute(Task task, int param) {
        try {
            if (task.getTaskName().equals("听音乐")) {
                System.out.println("Handler2: 听音乐，参数为：" + threadLocal.get());
            }
            threadLocal.set("music");
        } catch (Exception e) {
            System.out.println("Handler2: 听音乐任务异常，退出整个策略链路");
            throw new RuntimeException("Handler2: 听音乐任务异常，退出整个策略链路", e);
        }
    }
}
// 任务类
class Task {
    private final String taskName;
    public Task(String taskName) {
        this.taskName = taskName;
    }
    public String getTaskName() {
        return taskName;
    }
}
// 策略组类
class StrategyGroup {
    private final List<Strategy> strategies;
    private final List<Integer> order;
    public StrategyGroup(List<Strategy> strategies, List<Integer> order) {
        this.strategies = strategies;
        this.order = order;
    }
    // 初始化责任链
    public TaskHandler init() {
        // 对策略按照顺序排序
        Collections.sort(strategies, (o1, o2) -> {
            int index1 = strategies.indexOf(o1);
            int index2 = strategies.indexOf(o2);
            return order.get(index1) - order.get(index2);
        });
        // 将策略依次连接起来
TaskHandler handler = new TaskHandler();
        TaskHandler currentHandler = handler;
        for (Strategy strategy : strategies) {
            currentHandler.setStrategy(strategy);
            currentHandler.setNextHandler(new TaskHandler());
            currentHandler = currentHandler.getNextHandler();
        }
        return handler;
    }
}
// 责任链处理器类
class TaskHandler {
    private Strategy strategy;
    private TaskHandler nextHandler;
    // 设置策略
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }
    // 设置下一个处理器
    public void setNextHandler(TaskHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
    // 处理任务
    public void handle(Task task, int param) {
        try {
            strategy.execute(task, param);
            if (nextHandler != null) {
                nextHandler.handle(task, param);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}