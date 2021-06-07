import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import javax.swing.border.EtchedBorder;
import java.awt.Color;

import javax.swing.ImageIcon;
import java.awt.Toolkit;


public class MainWindow {

	private JPanel panelStart;
	private JButton btnSingle,btnDual;
	private JComboBox cboxDifficultySelector;
	private JFrame frame;
	final private String[] Difficulty = {"关卡1","关卡2","关卡3","关卡4","关卡5","关卡6","关卡7","关卡8","关卡9","关卡10","关卡11","关卡12","关卡13","关卡14","关卡15","关卡16","关卡17","关卡18","关卡19","关卡20","关卡21","关卡22","关卡23","关卡24","关卡25"};
	private PillarGenerator pGenerator;
	private Pillar[] pillars;

	private Boolean bKeyChangeFlag = false;
	private int SingleKey = 32;

	private Boolean bKeyChangeLeftFlag = false;
	private Boolean bKeyChangeRightFlag = false;
	private int dualLeftKey = 32;
	private int dualRightKey = 40;

	/* single play*/

	private long timeStart;
	private long timeEnd;
	private long timeInterval;
	private Boolean btnPush = false;
	/*	Dual play */


	private JButton btnSingleStart;
	private JPanel panelDualSetting;
	private JButton btnDualStart;
	private JComboBox cboxDifficultySelector_1;
	private JLabel lblDualKeyBindTooltipLeft;
	private JButton btnDualKeyBindLeft;
	private JButton btnDualSettingReturn;
	private SinglePlay panelSinglePlay;
	private JButton singlePushbtn;
	private JProgressBar singleProgressBar;
	private Monitor monitor = new Monitor();
	JPanel panelSingleSetting;
	private JPanel panelDualPlay;
	private JLabel score_1;
	private JLabel useTime_1;
	private JLabel score_2;
	private JLabel useTime_2;
	private JPanel windowPanelLeft;
	private JPanel windowPanelRight;
	private JButton btnNewButton_1;
	private JButton SingleLife1;
	private JButton SingleLife2;
	private JButton SingleLife3;

	private JButton SingleLife4;
	private JButton SingleLife5;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
					window.frame.setFocusable(true);
					window.frame.setTitle("Jump Game @copyright by Team X");
					//window.frame.addKeyListener(window);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		MusicThread mThread = new MusicThread(Data.backgroungMusic.getPath(), 1);
		mThread.start();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("unchecked")
	private void initialize() {
		CardLayout cl = new CardLayout(0, 0);
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainWindow.class.getResource("/source/window.png")));
		frame.setResizable(false);
		frame.setBounds(100, 100, 1242, 770);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panelStart = new JPanel();	
		btnSingle = new JButton("\u5355\u4EBA\u6A21\u5F0F");
		btnSingle.setBounds(232, 387, 301, 127);
		btnSingle.setIcon(new ImageIcon(MainWindow.class.getResource("/source/single.png")));
		btnSingle.setBackground(Color.PINK);
		btnSingle.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				MusicThread mThread = new MusicThread(Data.buttonPushMusic.getPath(), 0);
				mThread.start();
				cl.show(panel, "panel3");
			}
		});
		btnSingle.setFont(new Font("宋体", Font.PLAIN, 20));

		btnDual = new JButton("\u53CC\u4EBA\u6A21\u5F0F");
		btnDual.setBounds(669, 387, 301, 127);
		btnDual.setIcon(new ImageIcon(MainWindow.class.getResource("/source/double2.png")));
		btnDual.setBackground(new Color(135, 206, 235));
		btnDual.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) { 
				MusicThread mThread = new MusicThread(Data.buttonPushMusic.getPath(), 0);
				mThread.start();
				cl.show(panel, "panelDualSetting");
			}
		});
		btnDual.setFont(new Font("宋体", Font.PLAIN, 20));

		panelSingleSetting = new JPanel();

		JButton btnSingleSettingReturn = new JButton("");
		btnSingleSettingReturn.setBackground(Color.WHITE);
		btnSingleSettingReturn.setIcon(new ImageIcon(MainWindow.class.getResource("/source/return.png")));
		btnSingleSettingReturn.setBounds(737, 444, 290, 126);
		btnSingleSettingReturn.setFont(new Font("宋体", Font.PLAIN, 16));
		btnSingleSettingReturn.setBorder(null);
		btnSingleSettingReturn.setOpaque(false);
		btnSingleSettingReturn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				MusicThread mThread = new MusicThread(Data.buttonPushMusic.getPath(), 0);
				mThread.start();
				cl.show(panel, "panelStart");
			}
		});
		panel.setLayout(cl);

		panelSinglePlay = new SinglePlay();
		panelSinglePlay.addKeyListener(monitor);
		panelSinglePlay.setName("panelSinglePlay");
		panelDualPlay = new DualPlay();
		panelDualPlay.addKeyListener(monitor);
		panel.add(panelDualPlay, "panelDualPlay");

		JProgressBar dualProgressBar1 = new JProgressBar();
		dualProgressBar1.setForeground(Color.ORANGE);
		dualProgressBar1.setBounds(1119, 96, 61, 271);
		dualProgressBar1.setName("dualProgressBar1");
		dualProgressBar1.setValue(0);
		dualProgressBar1.setOrientation(SwingConstants.VERTICAL);

		JProgressBar dualProgressBar2 = new JProgressBar();
		dualProgressBar2.setForeground(Color.GREEN);
		dualProgressBar2.setBounds(1115, 462, 61, 271);
		dualProgressBar2.setName("dualProgressBar2");
		dualProgressBar2.setValue(0);
		dualProgressBar2.setOrientation(SwingConstants.VERTICAL);

		score_1 = new JLabel("SCOREplay1: 0");
		score_1.setBounds(1067, 21, 154, 23);
		score_1.setFont(new Font("宋体", Font.BOLD, 15));
		score_1.setName("score_1");

		useTime_1 = new JLabel("Time : 0");
		useTime_1.setBounds(1067, 50, 154, 23);
		useTime_1.setFont(new Font("宋体", Font.BOLD, 15));
		useTime_1.setName("useTime_1");

		score_2 = new JLabel("SCOREplay2: 0");
		score_2.setBounds(1067, 400, 154, 23);
		score_2.setFont(new Font("宋体", Font.BOLD, 15));
		score_2.setName("score_2");

		useTime_2 = new JLabel("Time : 0");
		useTime_2.setBounds(1067, 429, 154, 23);
		useTime_2.setFont(new Font("宋体", Font.BOLD, 15));
		useTime_2.setName("useTime_2");

		windowPanelLeft = new PlayWindow();
		windowPanelLeft.setBounds(10, 10, 1033, 357);
		windowPanelLeft.setName("windowPanelLeft");
		windowPanelRight = new PlayWindow();
		windowPanelRight.setBounds(10, 373, 1033, 369);
		windowPanelRight.setName("windowPanelRight");

		btnNewButton_1 = new JButton("\u8FD4\u56DE\u83DC\u5355");
		btnNewButton_1.setBounds(1095, 373, 98, 23);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MusicThread mThread = new MusicThread(Data.buttonPushMusic.getPath(), 0);
				mThread.start();
				cl.show(panel, "panelStart");
			}
		});
		panelDualPlay.setLayout(null);
		panelDualPlay.add(windowPanelLeft);
		panelDualPlay.add(score_1);
		panelDualPlay.add(useTime_1);
		panelDualPlay.add(dualProgressBar1);
		panelDualPlay.add(windowPanelRight);
		panelDualPlay.add(btnNewButton_1);
		panelDualPlay.add(score_2);
		panelDualPlay.add(useTime_2);
		panelDualPlay.add(dualProgressBar2);

		panel.add(panelSinglePlay, "panelSinglePlay");
		singlePushbtn = new JButton("");
		singlePushbtn.setForeground(Color.WHITE);
		singlePushbtn.setBackground(Color.WHITE);
		singlePushbtn.setIcon(new ImageIcon(MainWindow.class.getResource("/source/button.png")));
		singlePushbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		singlePushbtn.setBorder(null);
		singlePushbtn.setOpaque(false);
		singlePushbtn.setFocusable(false);
		singlePushbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(panelSinglePlay.getState() == false) {
					if(btnPush == false) {
						timeStart = System.currentTimeMillis();
						panelSinglePlay.setFlag();
						btnPush = true;
					}
				}
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(panelSinglePlay.getGameIsOver() == true) {
					cl.show(panel, "panel3");
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if(panelSinglePlay.getState() == false) {
					MusicThread mThread = new MusicThread(Data.jumpMusic.getPath(), 0);
					mThread.start();
					btnPush = false;
					timeEnd = System.currentTimeMillis();
					timeInterval = timeEnd - timeStart;
					panelSinglePlay.resetFlag();
					panelSinglePlay.setTimeInterval(timeInterval);
				}
			}
		});
		singlePushbtn.setFont(new Font("宋体", Font.PLAIN, 16));

		singleProgressBar = new JProgressBar();
		singleProgressBar.setOrientation(SwingConstants.VERTICAL);
		singleProgressBar.setValue(0);

		panelDualSetting = new JPanel();
		panelDualSetting.setBorder(null);
		panel.add(panelDualSetting, "panelDualSetting");
		
		btnDualStart = new JButton(" ");
		btnDualStart.setBackground(Color.WHITE);
		btnDualStart.setIcon(new ImageIcon(MainWindow.class.getResource("/source/begin.png")));
		btnDualStart.setBounds(754, 190, 424, 220);
		btnDualStart.setBorder(null);
		btnDualStart.setOpaque(false);
		btnDualStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MusicThread mThread = new MusicThread(Data.buttonPushMusic.getPath(), 0);
				mThread.start();
				monitor.setLeftKey(dualLeftKey);
				monitor.setRightKey(dualRightKey);
				pGenerator = new PillarGenerator(cboxDifficultySelector.getSelectedIndex());
				pGenerator.setmuHeight(125, 25);
				pGenerator.setmuDist(330, 30);
				pillars = pGenerator.RandomGenerator();
				((PlayWindow) windowPanelLeft).setPillarNumber(pGenerator.getPillarNumber());
				((PlayWindow) windowPanelLeft).SinglePillarSetting(pillars);

				((PlayWindow) windowPanelRight).setPillarNumber(pGenerator.getPillarNumber());
				((PlayWindow) windowPanelRight).SinglePillarSetting(pillars);
				((DualPlay) panelDualPlay).inicial();
				((DualPlay) panelDualPlay).setTotalTime(cboxDifficultySelector.getSelectedIndex()*10+60);
				cl.show(panel, "panelDualPlay");
				panelDualPlay.requestFocus();

			}
		});

		btnDualStart.setFont(new Font("宋体", Font.PLAIN, 16));

		cboxDifficultySelector_1 = new JComboBox(Difficulty);
		cboxDifficultySelector_1.setBounds(430, 136, 114, 23);

		lblDualKeyBindTooltipLeft = new JLabel("\u5DE6\u4FA7\u73A9\u5BB6\u6309\u952E\u7ED1\u5B9A\uFF1A");
		lblDualKeyBindTooltipLeft.setBounds(805, 82, 144, 19);
		lblDualKeyBindTooltipLeft.setFont(new Font("宋体", Font.BOLD, 16));

		btnDualKeyBindLeft = new JButton("Space");
		btnDualKeyBindLeft.setBounds(1027, 65, 107, 44);
		btnDualKeyBindLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bKeyChangeLeftFlag = true;
			}
		});
		btnDualKeyBindLeft.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(bKeyChangeLeftFlag) {
					dualLeftKey = e.getKeyCode();
					btnDualKeyBindLeft.setText(KeyEvent.getKeyText(dualLeftKey));
				}
				bKeyChangeLeftFlag = false;
			}
		});
		btnDualKeyBindLeft.setFont(new Font("宋体", Font.PLAIN, 16));

		btnDualSettingReturn = new JButton("");
		btnDualSettingReturn.setBackground(Color.WHITE);
		btnDualSettingReturn.setIcon(new ImageIcon(MainWindow.class.getResource("/source/return.png")));
		btnDualSettingReturn.setBounds(754, 441, 424, 220);
		btnDualSettingReturn.setBorder(null);
		btnDualSettingReturn.setOpaque(false);
		btnDualSettingReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MusicThread mThread = new MusicThread(Data.buttonPushMusic.getPath(), 0);
				mThread.start();
				cl.show(panel, "panelStart");
			}
		});
		btnDualSettingReturn.setFont(new Font("宋体", Font.PLAIN, 16));

		JLabel lblDualKeyBindTooltipRight = new JLabel("\u53F3\u4FA7\u73A9\u5BB6\u6309\u952E\u7ED1\u5B9A\uFF1A");
		lblDualKeyBindTooltipRight.setBounds(805, 140, 144, 19);
		lblDualKeyBindTooltipRight.setFont(new Font("宋体", Font.BOLD, 16));

		JButton btnDualKeyBindRight = new JButton("down");
		btnDualKeyBindRight.setBounds(1027, 119, 107, 44);
		btnDualKeyBindRight.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(bKeyChangeRightFlag) {
					dualRightKey = e.getKeyCode();
					btnDualKeyBindRight.setText(KeyEvent.getKeyText(dualRightKey));
				}
				bKeyChangeRightFlag = false;
			}
		});
		btnDualKeyBindRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				bKeyChangeRightFlag = true;
			}
		});
		btnDualKeyBindRight.setFont(new Font("宋体", Font.PLAIN, 16));

		panel.add(panelStart, "panelStart");
		panelStart.setLayout(null);
		panelStart.add(btnSingle);
		panelStart.add(btnDual);
		
		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setBackground(Color.WHITE);
		btnNewButton_2.setBorder(null);
		btnNewButton_2.setOpaque(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_2.setIcon(new ImageIcon(MainWindow.class.getResource("/source/title2.png")));
		btnNewButton_2.setBounds(293, 136, 625, 142);
		panelStart.add(btnNewButton_2);
		panel.add(panelSingleSetting, "panel3");
		panel.add(panelDualSetting,"panelDualSetting");
		panelDualSetting.setLayout(null);
		panelDualSetting.add(cboxDifficultySelector_1);
		panelDualSetting.add(lblDualKeyBindTooltipRight);
		panelDualSetting.add(lblDualKeyBindTooltipLeft);
		panelDualSetting.add(btnDualKeyBindRight);
		panelDualSetting.add(btnDualKeyBindLeft);
		panelDualSetting.add(btnDualSettingReturn);
		panelDualSetting.add(btnDualStart);
		
		JLabel lblNewLabel_1_1 = new JLabel("Please Choose Diffivulty");
		lblNewLabel_1_1.setFont(new Font("Bookman Old Style", Font.ITALIC, 29));
		lblNewLabel_1_1.setBounds(224, 41, 424, 112);
		panelDualSetting.add(lblNewLabel_1_1);
		
		JButton dp1 = new JButton("");
		dp1.setIcon(new ImageIcon(MainWindow.class.getResource("/source/1.png")));
		dp1.setBackground(Color.WHITE);
		dp1.setOpaque(false);
		dp1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡1");
			}
		});
		dp1.setBounds(166, 190, 64, 64);
		panelDualSetting.add(dp1);
		
		JButton dp2 = new JButton("");
		dp2.setIcon(new ImageIcon(MainWindow.class.getResource("/source/2.png")));
		dp2.setBackground(Color.WHITE);
		dp2.setOpaque(false);
		dp2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡2");
			}
		});
		dp2.setBounds(260, 190, 64, 64);
		panelDualSetting.add(dp2);
		
		JButton dp3 = new JButton("");
		dp3.setIcon(new ImageIcon(MainWindow.class.getResource("/source/3.png")));
		dp3.setBackground(Color.WHITE);
		dp3.setOpaque(false);
		dp3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡3");
			}
		});
		dp3.setBounds(358, 190, 64, 64);
		panelDualSetting.add(dp3);
		
		JButton dp4 = new JButton("");
		dp4.setIcon(new ImageIcon(MainWindow.class.getResource("/source/4.png")));
		dp4.setBackground(Color.WHITE);
		dp4.setOpaque(false);
		dp4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡4");
			}
		});
		dp4.setBounds(466, 190, 64, 64);
		panelDualSetting.add(dp4);
		
		JButton dp5 = new JButton("");
		dp5.setSelectedIcon(null);
		dp5.setIcon(new ImageIcon(MainWindow.class.getResource("/source/5.png")));
		dp5.setBackground(Color.WHITE);
		dp5.setOpaque(false);
		dp5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡5");
			}
		});
		dp5.setBounds(568, 190, 64, 64);
		panelDualSetting.add(dp5);
		
		JButton dp10 = new JButton("");
		dp10.setIcon(new ImageIcon(MainWindow.class.getResource("/source/10.png")));
		dp10.setBackground(Color.WHITE);
		dp10.setOpaque(false);
		dp10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡10");
			}
		});
		dp10.setBounds(568, 286, 64, 64);
		panelDualSetting.add(dp10);
		
		JButton dp9 = new JButton("");
		dp9.setIcon(new ImageIcon(MainWindow.class.getResource("/source/9.png")));
		dp9.setBackground(Color.WHITE);
		dp9.setOpaque(false);
		dp9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡9");
			}
		});
		dp9.setBounds(466, 286, 64, 64);
		panelDualSetting.add(dp9);
		
		JButton dp8 = new JButton("");
		dp8.setIcon(new ImageIcon(MainWindow.class.getResource("/source/8.png")));
		dp8.setBackground(Color.WHITE);
		dp8.setOpaque(false);
		dp8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡8");
			}
		});
		dp8.setBounds(358, 286, 64, 64);
		panelDualSetting.add(dp8);
		
		JButton dp15 = new JButton("");
		dp15.setIcon(new ImageIcon(MainWindow.class.getResource("/source/15.png")));
		dp15.setBackground(Color.WHITE);
		dp15.setOpaque(false);
		dp15.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡15");
			}
		});
		dp15.setBounds(568, 378, 64, 64);
		panelDualSetting.add(dp15);
		
		JButton dp20 = new JButton("");
		dp20.setIcon(new ImageIcon(MainWindow.class.getResource("/source/20.png")));
		dp20.setBackground(Color.WHITE);
		dp20.setOpaque(false);
		dp20.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡20");
			}
		});
		dp20.setBounds(568, 479, 64, 64);
		panelDualSetting.add(dp20);
		
		JButton dp25 = new JButton("");
		dp25.setIcon(new ImageIcon(MainWindow.class.getResource("/source/25.png")));
		dp25.setBackground(Color.WHITE);
		dp25.setOpaque(false);
		dp25.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡25");
			}
		});
		dp25.setBounds(568, 577, 64, 64);
		panelDualSetting.add(dp25);
		
		JButton dp24 = new JButton("");
		dp24.setIcon(new ImageIcon(MainWindow.class.getResource("/source/24.png")));
		dp24.setBackground(Color.WHITE);
		dp24.setOpaque(false);
		dp24.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡24");
			}
		});
		dp24.setBounds(466, 577, 64, 64);
		panelDualSetting.add(dp24);
		
		JButton dp19 = new JButton("");
		dp19.setIcon(new ImageIcon(MainWindow.class.getResource("/source/19.png")));
		dp19.setBackground(Color.WHITE);
		dp19.setOpaque(false);
		dp19.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡19");
			}
		});
		dp19.setBounds(466, 479, 64, 64);
		panelDualSetting.add(dp19);
		
		JButton dp14 = new JButton("");
		dp14.setIcon(new ImageIcon(MainWindow.class.getResource("/source/14.png")));
		dp14.setBackground(Color.WHITE);
		dp14.setOpaque(false);
		dp14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡14");
			}
		});
		dp14.setBounds(466, 378, 64, 64);
		panelDualSetting.add(dp14);
		
		JButton dp13 = new JButton("");
		dp13.setIcon(new ImageIcon(MainWindow.class.getResource("/source/13.png")));
		dp13.setBackground(Color.WHITE);
		dp13.setOpaque(false);
		dp13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡13");
			}
		});
		dp13.setBounds(358, 378, 64, 64);
		panelDualSetting.add(dp13);
		
		JButton dp18 = new JButton("");
		dp18.setIcon(new ImageIcon(MainWindow.class.getResource("/source/18.png")));
		dp18.setBackground(Color.WHITE);
		dp18.setOpaque(false);
		dp18.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡18");
			}
		});
		dp18.setBounds(358, 479, 64, 64);
		panelDualSetting.add(dp18);
		
		JButton dp23 = new JButton("");
		dp23.setIcon(new ImageIcon(MainWindow.class.getResource("/source/23.png")));
		dp23.setBackground(Color.WHITE);
		dp23.setOpaque(false);
		dp23.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡23");
			}
		});
		dp23.setBounds(358, 578, 64, 64);
		panelDualSetting.add(dp23);
		
		JButton dp22 = new JButton("");
		dp22.setIcon(new ImageIcon(MainWindow.class.getResource("/source/22.png")));
		dp22.setBackground(Color.WHITE);
		dp22.setOpaque(false);
		dp22.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡22");
			}
		});
		dp22.setBounds(260, 577, 64, 64);
		panelDualSetting.add(dp22);
		
		JButton dp17 = new JButton("");
		dp17.setIcon(new ImageIcon(MainWindow.class.getResource("/source/17.png")));
		dp17.setBackground(Color.WHITE);
		dp17.setOpaque(false);
		dp17.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡17");
			}
		});
		dp17.setBounds(260, 479, 64, 64);
		panelDualSetting.add(dp17);
		
		JButton dp12 = new JButton("");
		dp12.setIcon(new ImageIcon(MainWindow.class.getResource("/source/12.png")));
		dp12.setBackground(Color.WHITE);
		dp12.setOpaque(false);
		dp12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡12");
			}
		});
		dp12.setBounds(260, 378, 64, 64);
		panelDualSetting.add(dp12);
		
		JButton dp7 = new JButton("");
		dp7.setIcon(new ImageIcon(MainWindow.class.getResource("/source/7.png")));
		dp7.setBackground(Color.WHITE);
		dp7.setOpaque(false);
		dp7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡7");
			}
		});
		dp7.setBounds(260, 286, 64, 64);
		panelDualSetting.add(dp7);
		
		JButton dp6 = new JButton("");
		dp6.setIcon(new ImageIcon(MainWindow.class.getResource("/source/6.png")));
		dp6.setBackground(Color.WHITE);
		dp6.setOpaque(false);
		dp6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡6");
			}
		});
		dp6.setBounds(166, 286, 64, 64);
		panelDualSetting.add(dp6);
		
		JButton dp11 = new JButton("");
		dp11.setIcon(new ImageIcon(MainWindow.class.getResource("/source/11.png")));
		dp11.setBackground(Color.WHITE);
		dp11.setOpaque(false);
		dp11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡11");
			}
		});
		dp11.setBounds(166, 378, 64, 64);
		panelDualSetting.add(dp11);
		
		JButton dp16 = new JButton("");
		dp16.setIcon(new ImageIcon(MainWindow.class.getResource("/source/16.png")));
		dp16.setBackground(Color.WHITE);
		dp16.setOpaque(false);
		dp16.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡16");
			}
		});
		dp16.setBounds(166, 479, 64, 64);
		panelDualSetting.add(dp16);
		
		JButton dp21 = new JButton("");
		dp21.setIcon(new ImageIcon(MainWindow.class.getResource("/source/21.png")));
		dp21.setBackground(Color.WHITE);
		dp21.setOpaque(false);
		dp21.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector_1.setSelectedItem("关卡21");
			}
		});
		dp21.setBounds(166, 577, 64, 64);
		panelDualSetting.add(dp21);
		
		JLabel lblNewLabel_2 = new JLabel("\u60A8\u5DF2\u9009\u62E9\u5173\u5361:");
		lblNewLabel_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(247, 136, 120, 23);
		panelDualSetting.add(lblNewLabel_2);
		panel.add(panelSinglePlay,"panelSinglePlay");

		JLabel useTime = new JLabel("Time: 0");
		useTime.setName("useTime");
		useTime.setFont(new Font("宋体", Font.BOLD, 15));

		JLabel score = new JLabel("SCORE: 0");
		score.setName("score");
		score.setFont(new Font("宋体", Font.BOLD, 15));

		JButton btnNewButton = new JButton("\u8FD4\u56DE\u83DC\u5355");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MusicThread mThread = new MusicThread(Data.buttonPushMusic.getPath(), 0);
				mThread.start();
				cl.show(panel, "panelStart");
			}
		});

		SingleLife1 = new JButton("");
		SingleLife1.setIcon(new ImageIcon(MainWindow.class.getResource("/source/lif.png")));
		SingleLife1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		SingleLife1.setBorder(null);
		SingleLife1.setOpaque(false);
		SingleLife1.setBackground(Color.WHITE);
		SingleLife1.setForeground(Color.WHITE);
		SingleLife1.setName("SingleLife1");

		SingleLife2 = new JButton("");
		SingleLife2.setBorder(null);
		SingleLife2.setOpaque(false);
		SingleLife2.setIcon(new ImageIcon(MainWindow.class.getResource("/source/lif.png")));
		SingleLife2.setForeground(Color.WHITE);
		SingleLife2.setBackground(Color.WHITE);
		SingleLife2.setName("SingleLife2");

		SingleLife3 = new JButton("");
		SingleLife3.setBorder(null);
		SingleLife3.setOpaque(false);
		SingleLife3.setIcon(new ImageIcon(MainWindow.class.getResource("/source/lif.png")));
		SingleLife3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		SingleLife3.setForeground(Color.WHITE);
		SingleLife3.setBackground(Color.WHITE);
		SingleLife3.setName("SingleLife3");

		SingleLife4 = new JButton("");
		SingleLife4.setBorder(null);
		SingleLife4.setOpaque(false);
		SingleLife4.setIcon(new ImageIcon(MainWindow.class.getResource("/source/lif.png")));
		SingleLife4.setForeground(Color.WHITE);
		SingleLife4.setBackground(Color.WHITE);
		SingleLife4.setName("SingleLife4");

		SingleLife5 = new JButton("");
		SingleLife5.setBorder(null);
		SingleLife5.setOpaque(false);
		SingleLife5.setIcon(new ImageIcon(MainWindow.class.getResource("/source/lif.png")));
		SingleLife5.setForeground(Color.WHITE);
		SingleLife5.setBackground(Color.WHITE);
		SingleLife5.setName("SingleLife5");

		GroupLayout gl_panelSinglePlay = new GroupLayout(panelSinglePlay);
		gl_panelSinglePlay.setHorizontalGroup(
			gl_panelSinglePlay.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelSinglePlay.createSequentialGroup()
					.addGap(1024)
					.addGroup(gl_panelSinglePlay.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelSinglePlay.createSequentialGroup()
							.addGap(41)
							.addGroup(gl_panelSinglePlay.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelSinglePlay.createSequentialGroup()
									.addGroup(gl_panelSinglePlay.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelSinglePlay.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(useTime, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
										.addComponent(singlePushbtn, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
										.addComponent(score, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE))
									.addGap(18))
								.addGroup(gl_panelSinglePlay.createSequentialGroup()
									.addGroup(gl_panelSinglePlay.createParallelGroup(Alignment.LEADING)
										.addComponent(SingleLife1, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addComponent(SingleLife2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addComponent(SingleLife3, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addComponent(SingleLife4, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
										.addComponent(SingleLife5, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
									.addGap(41)
									.addComponent(singleProgressBar, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())))
						.addGroup(gl_panelSinglePlay.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addGap(52))))
		);
		gl_panelSinglePlay.setVerticalGroup(
			gl_panelSinglePlay.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSinglePlay.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(score, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(useTime, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(singlePushbtn, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panelSinglePlay.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSinglePlay.createSequentialGroup()
							.addGap(32)
							.addComponent(singleProgressBar, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
							.addGap(27))
						.addGroup(gl_panelSinglePlay.createSequentialGroup()
							.addGap(59)
							.addComponent(SingleLife1, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(SingleLife2, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(SingleLife3, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(SingleLife4, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(SingleLife5, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		panelSinglePlay.setLayout(gl_panelSinglePlay);
		cboxDifficultySelector = new JComboBox(Difficulty);
		cboxDifficultySelector.setEditable(true);
		cboxDifficultySelector.setBounds(379, 156, 114, 33);
		JLabel lblSingleKeyBindTooltip = new JLabel("\u6309\u952E\u7ED1\u5B9A\uFF1A");
		lblSingleKeyBindTooltip.setBounds(762, 137, 106, 52);
		lblSingleKeyBindTooltip.setFont(new Font("宋体", Font.BOLD, 16));

		JButton btnSingleKeyBind = new JButton("Space");
		btnSingleKeyBind.setBackground(Color.PINK);
		btnSingleKeyBind.setBounds(853, 147, 130, 33);
		btnSingleKeyBind.setToolTipText("\u5355\u51FB\u4FEE\u6539\u6309\u952E");
		btnSingleKeyBind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnSingleKeyBind.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (bKeyChangeFlag) {
					SingleKey = e.getKeyCode();
					btnSingleKeyBind.setText(KeyEvent.getKeyText(SingleKey));
					bKeyChangeFlag = false;
				}
			}
		});
		btnSingleKeyBind.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				bKeyChangeFlag = true;
			}
		});
		btnSingleKeyBind.setFont(new Font("宋体", Font.PLAIN, 16));

		btnSingleStart = new JButton(" ");
		btnSingleStart.setBackground(Color.WHITE);
		btnSingleStart.setIcon(new ImageIcon(MainWindow.class.getResource("/source/begin.png")));
		btnSingleStart.setBounds(762, 247, 255, 100);
		btnSingleStart.setBorder(null);
		btnSingleStart.setOpaque(false);
		btnSingleStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MusicThread mThread = new MusicThread(Data.buttonPushMusic.getPath(), 0);
				mThread.start();
				monitor.setLeftKey(SingleKey);
				pGenerator = new PillarGenerator(cboxDifficultySelector.getSelectedIndex());
				pillars = pGenerator.RandomGenerator();
				panelSinglePlay.setPillarNumber(pGenerator.getPillarNumber());
				panelSinglePlay.SinglePillarSetting(pillars);
				panelSinglePlay.Inicial();
				cl.show(panel, "panelSinglePlay");	
				panelSinglePlay.requestFocus();	

			}
		});
		btnSingleStart.setFont(new Font("宋体", Font.PLAIN, 16));
		panelSingleSetting.setLayout(null);
		panelSingleSetting.add(cboxDifficultySelector);
		panelSingleSetting.add(lblSingleKeyBindTooltip);
		panelSingleSetting.add(btnSingleKeyBind);
		panelSingleSetting.add(btnSingleStart);
		panelSingleSetting.add(btnSingleSettingReturn);

		JButton p1 = new JButton("");
		p1.setIcon(new ImageIcon(MainWindow.class.getResource("/source/1.png")));
		p1.setBackground(Color.WHITE);
		p1.setOpaque(false);
		p1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡1");
			}
		});
		p1.setBounds(147, 217, 64, 64);
		panelSingleSetting.add(p1);

		JButton p2 = new JButton("");
		p2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		p2.setIcon(new ImageIcon(MainWindow.class.getResource("/source/2.png")));
		p2.setBackground(Color.WHITE);
		p2.setOpaque(false);
		p2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡2");
			}
		});
		p2.setBounds(241, 217, 64, 64);
		panelSingleSetting.add(p2);

		JButton p3 = new JButton("");
		p3.setIcon(new ImageIcon(MainWindow.class.getResource("/source/3.png")));
		p3.setBackground(Color.WHITE);
		p3.setOpaque(false);
		p3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡3");
			}
		});
		p3.setBounds(339, 217, 64, 64);
		panelSingleSetting.add(p3);

		JButton p4 = new JButton("");
		p4.setIcon(new ImageIcon(MainWindow.class.getResource("/source/4.png")));
		p4.setBackground(Color.WHITE);
		p4.setOpaque(false);
		p4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡4");
			}
		});
		p4.setBounds(447, 217, 64, 64);
		panelSingleSetting.add(p4);

		JButton p5 = new JButton("");
		p5.setIcon(new ImageIcon(MainWindow.class.getResource("/source/5.png")));
		p5.setBackground(Color.WHITE);
		p5.setOpaque(false);
		p5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡5");
			}
		});
		p5.setBounds(549, 217, 64, 64);
		panelSingleSetting.add(p5);

		JButton p6 = new JButton("");
		p6.setIcon(new ImageIcon(MainWindow.class.getResource("/source/6.png")));
		p6.setBackground(Color.WHITE);
		p6.setOpaque(false);
		p6.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡6");
			}
		});
		p6.setBounds(147, 313, 64, 64);
		panelSingleSetting.add(p6);

		JButton p7 = new JButton("");
		p7.setIcon(new ImageIcon(MainWindow.class.getResource("/source/7.png")));
		p7.setBackground(Color.WHITE);
		p7.setOpaque(false);
		p7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡7");
			}
		});
		p7.setBounds(241, 313, 64, 64);
		panelSingleSetting.add(p7);

		JButton p8 = new JButton("");
		p8.setIcon(new ImageIcon(MainWindow.class.getResource("/source/8.png")));
		p8.setBackground(Color.WHITE);
		p8.setOpaque(false);
		p8.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡8");
			}
		});
		p8.setBounds(339, 313, 64, 64);
		panelSingleSetting.add(p8);

		JButton p9 = new JButton("");
		p9.setIcon(new ImageIcon(MainWindow.class.getResource("/source/9.png")));
		p9.setBackground(Color.WHITE);
		p9.setOpaque(false);
		p9.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡9");
			}
		});
		p9.setBounds(447, 313, 64, 64);
		panelSingleSetting.add(p9);

		JButton p10 = new JButton("");
		p10.setIcon(new ImageIcon(MainWindow.class.getResource("/source/10.png")));
		p10.setBackground(Color.WHITE);
		p10.setOpaque(false);
		p10.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡10");
			}
		});
		p10.setBounds(549, 313, 64, 64);
		panelSingleSetting.add(p10);

		JButton p11 = new JButton("");
		p11.setIcon(new ImageIcon(MainWindow.class.getResource("/source/11.png")));
		p11.setBackground(Color.WHITE);
		p11.setOpaque(false);
		p11.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡11");
			}
		});
		p11.setBounds(147, 405, 64, 64);
		panelSingleSetting.add(p11);

		JButton p12 = new JButton("");
		p12.setIcon(new ImageIcon(MainWindow.class.getResource("/source/12.png")));
		p12.setBackground(Color.WHITE);
		p12.setOpaque(false);
		p12.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡12");
			}
		});
		p12.setBounds(241, 405, 64, 64);
		panelSingleSetting.add(p12);

		JButton p13 = new JButton("");
		p13.setIcon(new ImageIcon(MainWindow.class.getResource("/source/13.png")));
		p13.setBackground(Color.WHITE);
		p13.setOpaque(false);
		p13.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡13");
			}
		});
		p13.setBounds(339, 405, 64, 64);
		panelSingleSetting.add(p13);

		JButton p14 = new JButton("");
		p14.setIcon(new ImageIcon(MainWindow.class.getResource("/source/14.png")));
		p14.setBackground(Color.WHITE);
		p14.setOpaque(false);
		p14.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡14");
			}
		});
		p14.setBounds(447, 405, 64, 64);
		panelSingleSetting.add(p14);

		JButton p15 = new JButton("");
		p15.setIcon(new ImageIcon(MainWindow.class.getResource("/source/15.png")));
		p15.setBackground(Color.WHITE);
		p15.setOpaque(false);
		p15.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡15");
			}
		});
		p15.setBounds(549, 405, 64, 64);
		panelSingleSetting.add(p15);

		JButton p16 = new JButton("");
		p16.setIcon(new ImageIcon(MainWindow.class.getResource("/source/16.png")));
		p16.setBackground(Color.WHITE);
		p16.setOpaque(false);
		p16.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡16");
			}
		});
		p16.setBounds(147, 506, 64, 64);
		panelSingleSetting.add(p16);

		JButton p17 = new JButton("");
		p17.setIcon(new ImageIcon(MainWindow.class.getResource("/source/17.png")));
		p17.setBackground(Color.WHITE);
		p17.setOpaque(false);
		p17.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡17");
			}
		});
		p17.setBounds(241, 506, 64, 64);
		panelSingleSetting.add(p17);

		JButton p18 = new JButton("");
		p18.setIcon(new ImageIcon(MainWindow.class.getResource("/source/18.png")));
		p18.setBackground(Color.WHITE);
		p18.setOpaque(false);
		p18.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡18");
			}
		});
		p18.setBounds(339, 506, 64, 64);
		panelSingleSetting.add(p18);

		JButton p19 = new JButton("");
		p19.setIcon(new ImageIcon(MainWindow.class.getResource("/source/19.png")));
		p19.setBackground(Color.WHITE);
		p19.setOpaque(false);
		p19.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡19");
			}
		});
		p19.setBounds(447, 506, 64, 64);
		panelSingleSetting.add(p19);

		JButton p20 = new JButton("");
		p20.setIcon(new ImageIcon(MainWindow.class.getResource("/source/20.png")));
		p20.setBackground(Color.WHITE);
		p20.setOpaque(false);
		p20.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡20");
			}
		});
		p20.setBounds(549, 506, 64, 64);
		panelSingleSetting.add(p20);

		JButton p21 = new JButton("");
		p21.setIcon(new ImageIcon(MainWindow.class.getResource("/source/21.png")));
		p21.setBackground(Color.WHITE);
		p21.setOpaque(false);
		p21.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡21");
			}
		});
		p21.setBounds(147, 604, 64, 64);
		panelSingleSetting.add(p21);

		JButton p22 = new JButton("");
		p22.setIcon(new ImageIcon(MainWindow.class.getResource("/source/22.png")));
		p22.setBackground(Color.WHITE);
		p22.setOpaque(false);
		p22.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡22");
			}
		});
		p22.setBounds(241, 604, 64, 64);
		panelSingleSetting.add(p22);

		JButton p23 = new JButton("");
		p23.setIcon(new ImageIcon(MainWindow.class.getResource("/source/23.png")));
		p23.setBackground(Color.WHITE);
		p23.setOpaque(false);
		p23.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡23");
			}
		});
		p23.setBounds(339, 604, 64, 64);
		panelSingleSetting.add(p23);

		JButton p24 = new JButton("");
		p24.setIcon(new ImageIcon(MainWindow.class.getResource("/source/24.png")));
		p24.setBackground(Color.WHITE);
		p24.setOpaque(false);
		p24.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡24");
			}
		});
		p24.setBounds(447, 604, 64, 64);
		panelSingleSetting.add(p24);

		JButton p25 = new JButton("");
		p25.setIcon(new ImageIcon(MainWindow.class.getResource("/source/25.png")));
		p25.setBackground(Color.WHITE);
		p25.setOpaque(false);
		p25.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				cboxDifficultySelector.setSelectedItem("关卡25");
			}
		});
		p25.setBounds(549, 605, 64, 64);
		panelSingleSetting.add(p25);
		
		JLabel lblNewLabel = new JLabel("\u60A8\u5DF2\u9009\u62E9\u5173\u5361:");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 16));
		lblNewLabel.setBounds(225, 157, 115, 29);
		panelSingleSetting.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Please Choose Difficulty");
		lblNewLabel_1.setFont(new Font("Bookman Old Style", Font.ITALIC, 29));
		lblNewLabel_1.setBounds(196, 51, 424, 112);
		panelSingleSetting.add(lblNewLabel_1);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGap(1)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 795, Short.MAX_VALUE))
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
				);
		frame.getContentPane().setLayout(groupLayout);
		cl.show(panel, "panelStart");
	}
}
