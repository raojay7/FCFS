package server.jack.com;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * @ClassName: SimulatedCPU.class
 * @Description: 模拟CPU
 * @author jack
 * @Date 2017年10月13日 上午10:25:15
 * @ClassName: SimulatedCPU.class
 * @Description: 提取方法
 * @author jack
 * @Date 2017年10月15日 下午2:51:50
 * @ClassName: SimulatedCPU.class
 * @Description: 提取方法
 * @author jack
 * @Date 2017年10月15日 下午2:51:50
 */
/**
 * @ClassName: SimulatedCPU.class
 * @Description: 提取方法
 *
 * @author jack
 * @Date 2017年10月15日 下午2:51:50
 */

/**
 * @ClassName: SimulatedCPU.class
 * @Description: 添加注释
 *
 * @author jack
 * @Date 2017年10月15日 下午4:28:04
 */
public class SimulatedCPU {
    private volatile static SimulatedCPU simulatedCPU;

    /**
     * 窗体
     */
    private MainFrame mf;
    /**
     * 随机颜色
     */
    private Color[] colors;
    /**
     * 容器的统一高度
     */
    private int panelHeight;
    /**
     * 椭圆大小
     */
    private int ovalSize;
    /**
     * 细分单位
     */
    private int dh;
    /**
     * 移动速度
     */
    private int dx;
    /**
     * 文本区域
     */
    private JTextArea ta_state;
    /**
     * cpu标号
     */
    // private int cId;
    /**
     * 最大等待进程数
     */
    // private int waitingNumber;
    /**
     * cpu是否空闲
     */
    // private boolean isFree;
    /**
     * CPU等待延时
     */
    private long timeOut;
    /**
     * 进程集合
     */
    private List<Process> processList;
    /**
     * 等待阻塞队列
     */
    private BlockingQueue<Process> waitingQueue;

    /**
     * @param cId
     * @param waitingNumber
     */
    private SimulatedCPU(int cId, int waitingNumber, long timeOut, MainFrame mf) {
        super();
        // this.cId = cId;
        // this.waitingNumber = waitingNumber;
        this.timeOut = timeOut;
        // isFree = true;
        this.mf = mf;
        ta_state = mf.getTa_state();
        processList = new ArrayList<>();
        waitingQueue = new LinkedBlockingQueue<>(waitingNumber);

        initParameter();
    }

    /**
     * 添加进程到进程数组中
     *
     * @param processes
     */
    public void addProcessList(Process[] processes) {
        // 清除原有的进程
        processList.clear();
        for (Process process : processes) {
            if (!isRepeat(process)) {
                processList.add(process);
            }
        }
    }

    /**
     * 判断进程体是否重复
     *
     * @param process
     * @return
     */
    private boolean isRepeat(Process process) {
        for (Process pro : processList) {
            if (pro.equals(process)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 模拟进程到达CPU
     */
    private void imitateProcessArrive() {
        // 模拟进程同步到达CPU
        for (Process process : processList) {
            new Thread(new addWaitingQueueThread(process)).start();
        }
    }

    /**
     * 进程加入等待队列
     *
     * @param process
     */
    private void addWaitingQueue(Process process) {
        // 得到进程信息 id、名称、准备时间
        int processId;
        String processName;
        long readyTime;
        processId = process.getProcessId();
        processName = process.getProcessName();
        readyTime = process.getReadyTime();
        System.out.println("进程 " + processName + "就绪... 准备时间  " + readyTime);
        setText("进程" + processName + "就绪... 准备时间  " + readyTime);
        // 用线程睡眠模拟进程到达CPU的过程
        try {
            // do other GUI
            SwingUtilities.invokeLater(() -> {
                new Thread(() -> {
                    drawDynamicPanel(mf.getPl_arrive(), processId, readyTime);
                }).start();

            });
            Thread.sleep(readyTime * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 更新完界面后执行添加操作
        waitingQueue.add(process);
        System.out.println("进程 " + processName + "到达CPU！");
        setText("进程 " + processName + "到达CPU！");
        SwingUtilities.invokeLater(() -> {
            new Thread(() -> {
                rawStaticPanel(mf.getPl_ready(), colors[processId - 1], processId);
            }).start();
        });
    }

    /**
     * 模拟CPU运行状态
     */
    private void imitateCpuRunning() {
        while (true) {
            Process process = getReadyProcess();
            if (process == null) {
                System.out.println("\n\n等待超时...模拟终止！");
                setText("\n\n等待超时...模拟终止！");
                SwingUtilities.invokeLater(() -> {
                    drawStopPanel();
                });
                mf.setCpu(null);
                break;
            }
            imitateProcessRunning((process));
        }
    }

    /**
     * 清除进程结束面板
     */
    private void drawStopPanel() {
        JPanel pl = mf.getPl_stop();
        Graphics graphics = pl.getGraphics();
        Color color = pl.getBackground();
        int currentWidth = pl.getWidth();
        graphics.setColor(color);
        graphics.fillRect(0, 0, currentWidth, panelHeight);
        graphics.setColor(Color.black);
        graphics.drawRect(0, 0, currentWidth, panelHeight);
    }

    /**
     * 模拟运行进程
     *
     * @param readyProcess
     */
    private void imitateProcessRunning(Process readyProcess) {
        // 得到进程信息 名称、服务时间
        int processId;
        String processName;
        long serverTime;
        processId = readyProcess.getProcessId();
        processName = readyProcess.getProcessName();
        serverTime = readyProcess.getReadyTime();

        System.out.println("进程 " + processName + "开始执行... 服务时间 " + serverTime);
        setText("进程 " + processName + "开始执行... 服务时间  " + serverTime);

        // 用线程睡眠模拟进程CPU服务时间
        try {
            SwingUtilities.invokeLater(() -> {
                new Thread(() -> {
                    rawStaticPanel(mf.getPl_ready(), mf.getPl_ready().getBackground(), processId);
                    drawDynamicPanel(mf.getPl_server(), processId, serverTime);
                }).start();
            });
            Thread.sleep(serverTime * 1000);
            // do other GUI
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("进程 " + processName + "完成服务！");
        setText("进程 " + processName + " 完成服务！");

        SwingUtilities.invokeLater(() -> {
            new Thread(() -> {
                rawStaticPanel(mf.getPl_stop(), colors[processId - 1], processId);
            }).start();

        });
    }

    /**
     * 得到可运行的进程
     *
     * @return
     */
    private Process getReadyProcess() {
        try {
            // 等待timeout时间，超出时间表示已无进程存在
            return waitingQueue.poll(timeOut, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 模拟服务
     */
    public void Server() {
        setText("模拟开始...");
        imitateProcessArrive();
        imitateCpuRunning();
    }

    /**
     * @param i
     * @param j
     * @param k
     * @param mainFrame
     * @return
     */
    public static SimulatedCPU getSimulatedCPU(int i, int j, int k, MainFrame mainFrame) {
        if (simulatedCPU == null) {
            synchronized (SimulatedCPU.class) {
                if (simulatedCPU == null) {
                    simulatedCPU = new SimulatedCPU(i, j, k, mainFrame);
                }
            }
        }
        return simulatedCPU;
    }

    /**
     * @ClassName: addWaitingQueueThread.class
     * @Description: 内部类 模拟CPU到达CPU的线程
     *
     * @author jack
     * @Date 2017年10月13日 上午10:52:03
     */
    private class addWaitingQueueThread implements Runnable {

        /**
         * 进程体
         */
        private Process process;

        /**
         * @param process
         */
        public addWaitingQueueThread(Process process) {
            super();
            this.process = process;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Runnable#run()
         */
        @Override
        public void run() {
            addWaitingQueue(process);
        }
    }

    /**
     * 初始化与窗体有关的参数
     */
    private void initParameter() {
        // 使用随机数生成颜色
        colors = new Color[]{
                // 使用随机颜色效果不好
                // new Color((int) (Math.random() * 128 + 128), (int) (Math.random() * 128 +
                // 128),
                // (int) (Math.random() * 128 + 128)),
                // new Color((int) (Math.random() * 128 + 128), (int) (Math.random() * 128 +
                // 128),
                // (int) (Math.random() * 128 + 128)),
                // new Color((int) (Math.random() * 128 + 128), (int) (Math.random() * 128 +
                // 128),
                // (int) (Math.random() * 128 + 128)),
                // new Color((int) (Math.random() * 128 + 128), (int) (Math.random() * 128 +
                // 128),
                // (int) (Math.random() * 128 + 128))
                Color.blue, Color.yellow, Color.pink, Color.red};
        // 容器的统一高度
        panelHeight = mf.getPl_arrive().getHeight();
        // 细分单位
        dh = (panelHeight / 4) / 4;
        // 椭圆大小
        ovalSize = dh * 2;
        // 默认值
        dx = 1;
    }

    /**
     * 绘制动态容器
     *
     * @param jPanel
     * @param id
     * @param time
     */
    private void drawDynamicPanel(JPanel jPanel, int id, long time) {
        int X, Y, currentWidth;
        currentWidth = jPanel.getWidth();
        Graphics graphics = jPanel.getGraphics();
        Color currentColor = jPanel.getBackground();
        X = ovalSize / 9;
        Y = dh * (4 * (id - 1) + 1);

        while (X <= currentWidth) {
            // 清除上一次作图
            graphics.setColor(currentColor);
            graphics.fillRect(X - dx, Y, ovalSize, ovalSize);
            graphics.drawRect(X - dx, Y, ovalSize, ovalSize);
            // 新绘图
            graphics.setColor(colors[id - 1]);
            graphics.fillOval(X, Y, ovalSize, ovalSize);
            graphics.setColor(Color.black);
            graphics.drawOval(X, Y, ovalSize, ovalSize);
            //
            X = X + dx;
            try {
                Thread.sleep((1000 * time) / (currentWidth / dx) - 1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 绘制队列容器
     *
     * @param jPanel
     * @param currentColor
     * @param id
     */
    private void rawStaticPanel(JPanel jPanel, Color currentColor, int id) {
        int x, y;
        Graphics graphics = jPanel.getGraphics();
        x = (jPanel.getWidth() - ovalSize) / 2;
        y = dh * (4 * (id - 1) + 1);
        graphics.setColor(currentColor);
        graphics.fillOval(x, y, ovalSize, ovalSize);
        graphics.drawOval(x, y, ovalSize, ovalSize);
    }

    /**
     * 更新状态信息
     *
     * @param str
     */
    private void setText(String str) {
        SwingUtilities.invokeLater(() -> {
            new Thread(() -> {
                ta_state.append("\n\n" + str);
            }).start();
        });
    }
}
