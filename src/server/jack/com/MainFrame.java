package server.jack.com;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JTextArea;

/**
 * @author jack
 * @ClassName: MainFrame.class
 * @Description:
 * @Date 2017年10月13日 下午10:48:07
 */
public class MainFrame extends JFrame {

    private JPanel contentPane;
    private JPanel pl_server, pl_ready, pl_arrive, pl_stop;
    private JTextArea ta_state;
    private JScrollPane jsp;
    private SimulatedCPU cpu = null;

    /**
     * @param cpu the cpu to set
     */
    public void setCpu(SimulatedCPU cpu) {
        this.cpu = cpu;
    }

    /**
     * @return the tv_state
     */
    public JTextArea getTa_state() {
        return ta_state;
    }

    /**
     * @return the pl_server
     */
    public JPanel getPl_server() {
        return pl_server;
    }

    /**
     * @return the pl_ready
     */
    public JPanel getPl_ready() {
        return pl_ready;
    }

    /**
     * @return the pl_arrive
     */
    public JPanel getPl_arrive() {
        return pl_arrive;
    }

    /**
     * @return the pl_stop
     */
    public JPanel getPl_stop() {
        return pl_stop;
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame frame = new MainFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainFrame() {
        setType(Type.UTILITY);
        setTitle("\u6A21\u62DF\u5148\u6765\u5148\u670D\u52A1\u7B97\u6CD5");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 685, 469);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setLocationRelativeTo(null);

        JPanel pl_content = new JPanel();
        pl_content.setBackground(new Color(70, 130, 180));
        pl_content.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u6A21\u62DFCPU",
                TitledBorder.CENTER, TitledBorder.TOP, null, null));
        pl_content.setBounds(5, 5, 670, 295);
        contentPane.add(pl_content);
        pl_content.setLayout(null);

        pl_server = new JPanel();
        pl_server.setBackground(Color.GRAY);
        pl_server.setBorder(new LineBorder(new Color(0, 0, 0)));
        pl_server.setBounds(364, 48, 238, 237);
        pl_content.add(pl_server);

        pl_ready = new JPanel();
        pl_ready.setBorder(new LineBorder(new Color(255, 255, 0)));
        pl_ready.setBackground(Color.GREEN);
        pl_ready.setForeground(Color.GREEN);
        pl_ready.setBounds(306, 48, 48, 237);
        pl_content.add(pl_ready);

        pl_stop = new JPanel();
        pl_stop.setBackground(Color.CYAN);
        pl_stop.setBounds(612, 48, 48, 237);
        pl_content.add(pl_stop);

        pl_arrive = new JPanel();
        pl_arrive.setBorder(new LineBorder(new Color(255, 255, 0)));
        pl_arrive.setBackground(new Color(70, 130, 180));
        pl_arrive.setBounds(10, 48, 286, 237);
        pl_content.add(pl_arrive);

        JPanel pl_title = new JPanel();
        pl_title.setBounds(10, 20, 650, 25);
        pl_content.add(pl_title);
        pl_title.setLayout(null);

        JLabel lblNewLabel_2 = new JLabel("\u6A21\u62DFCPU\u670D\u52A1");
        lblNewLabel_2.setFont(new Font("楷体", Font.ITALIC, 15));
        lblNewLabel_2.setBounds(420, 0, 100, 25);
        pl_title.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("\u8FDB\u7A0B\u7ED3\u675F");
        lblNewLabel_3.setFont(new Font("楷体", Font.ITALIC, 15));
        lblNewLabel_3.setBounds(582, 0, 68, 25);
        pl_title.add(lblNewLabel_3);

        JLabel lblNewLabel = new JLabel("\u6A21\u62DF\u8FDB\u7A0B\u5230\u8FBE");
        lblNewLabel.setFont(new Font("楷体", Font.ITALIC, 15));
        lblNewLabel.setBounds(91, 0, 118, 25);
        pl_title.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("\u963B\u585E\u961F\u5217");
        lblNewLabel_1.setFont(new Font("楷体", Font.ITALIC, 15));
        lblNewLabel_1.setBounds(284, 0, 74, 25);
        pl_title.add(lblNewLabel_1);

        JPanel pl_state = new JPanel();
        pl_state.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u72B6\u6001\u4FE1\u606F",
                TitledBorder.CENTER, TitledBorder.TOP, null, null));
        pl_state.setBounds(5, 311, 670, 119);
        contentPane.add(pl_state);
        pl_state.setLayout(null);

        JLabel lblNewLabel_4 = new JLabel("\u4FE1\u606F\u79D1\u5B66\u4E0E\u5DE5\u7A0B\u5B66\u9662");
        lblNewLabel_4.setFont(new Font("黑体", Font.BOLD, 15));
        lblNewLabel_4.setBounds(10, 10, 179, 37);
        pl_state.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("\u8BA1\u79D11502\u7EA7 \u5411\u6797");
        lblNewLabel_5.setFont(new Font("黑体", Font.ITALIC, 13));
        lblNewLabel_5.setBounds(62, 51, 127, 31);
        pl_state.add(lblNewLabel_5);

        JLabel lb_time = new JLabel("New label");
        lb_time.setFont(new Font("宋体", Font.BOLD, 14));
        lb_time.setBounds(109, 92, 179, 23);
        pl_state.add(lb_time);
        // timer显示时间
        setTime(lb_time);

        ta_state = new JTextArea("  这里信息运行信息运行信息...");
        ta_state.setFont(new Font("黑体", Font.BOLD, 15));
        ta_state.setLineWrap(true);
        ta_state.setEnabled(false);
        ta_state.setWrapStyleWord(true);
        ta_state.setBackground(Color.black);
        jsp = new JScrollPane(ta_state);
        jsp.setBounds(392, 10, 268, 99);
        pl_state.add(jsp);

        JButton btn_start = new JButton("\u5F00\u59CB");
        btn_start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // 开始
                new Thread((() -> {
                    event_btn_start();
                })).start();

            }
        });
        btn_start.setFont(new Font("黑体", Font.BOLD, 15));
        btn_start.setBounds(289, 24, 93, 23);
        pl_state.add(btn_start);

        JButton btn_exit = new JButton("\u9000\u51FA");
        btn_exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
        btn_exit.setFont(new Font("黑体", Font.BOLD, 13));
        btn_exit.setBounds(289, 59, 93, 23);
        pl_state.add(btn_exit);
    }

    private void event_btn_start() {
        Process[] processes = new Process[]{
                new Process(1, "Process1", (long) (Math.random() * 5 + 4), (long) (Math.random() * 5 + 4)),
                new Process(2, "Process2", (long) (Math.random() * 5 + 4), (long) (Math.random() * 5 + 4)),
                new Process(3, "Process3", (long) (Math.random() * 5 + 4), (long) (Math.random() * 5 + 4)),
                new Process(4, "Process4", (long) (Math.random() * 5 + 4), (long) (Math.random() * 5 + 4)),};
        if (cpu == SimulatedCPU.getSimulatedCPU(0, 4, 11000, this)) {
            return;
        } else {
            cpu = SimulatedCPU.getSimulatedCPU(0, 4, 9000, this);
            ta_state.setText("  这里显示运行信息...");
            cpu.addProcessList(processes);
            cpu.Server();
        }
    }

    private void setTime(JLabel jLabel) {
        final JLabel varTime = jLabel;
        Timer timeAction = new Timer(1000, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                long timemillis = System.currentTimeMillis();
                // 转换日期显示格式
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                varTime.setText(df.format(new Date(timemillis)));
            }
        });
        timeAction.start();
    }
}
