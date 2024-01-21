import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


public class ToDoListGui extends JFrame implements ActionListener {

    private JPanel taskPanel, taskComponentPanel;


    public ToDoListGui() {
        super("TO-DO List");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(CommonConstants.GUI_SIZE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        addGuiComponents();
    }

    private void addGuiComponents(){
        JLabel bannerLabel = new JLabel("TO-DO List");
        bannerLabel.setFont(createFont("resources/LEMONMILK-Light.otf", 36f));
        bannerLabel.setBounds(
            (CommonConstants.GUI_SIZE.width - bannerLabel.getPreferredSize().width)/2, 
            15, 
            CommonConstants.BANNER_SIZE.width,
            CommonConstants.BANNER_SIZE.height
        );

        // task Panel
        taskPanel = new JPanel();

        // taskComponent Panel
        taskComponentPanel = new JPanel();
        taskComponentPanel.setLayout(new BoxLayout(taskComponentPanel, BoxLayout.Y_AXIS));
        taskPanel.add(taskComponentPanel);

        // Adding Scrolling to the task panel 
        JScrollPane scrollPane = new JScrollPane(taskPanel);
        scrollPane.setBounds(8, 70, CommonConstants.TASKPANEL_SIZE.width, CommonConstants.TASKPANEL_SIZE.height);
        scrollPane.setMaximumSize(CommonConstants.TASKPANEL_SIZE);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        // changing the speed of the scroll bar 
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUnitIncrement(20);


 
        // Task Button
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setFont(createFont("resources/LEMONMILK-Light.otf", 18f));
        addTaskButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addTaskButton.setBounds(-5, CommonConstants.GUI_SIZE.height - 88,
         CommonConstants.ADDTASK_BUTTON_SIZE.width,
          CommonConstants.ADDTASK_BUTTON_SIZE.height);
         addTaskButton.addActionListener(this);
 

        this.getContentPane().add(bannerLabel);
        this.getContentPane().add(scrollPane);
        this.getContentPane().add(addTaskButton);
  
    }

    private Font createFont(String resources, float size) {

        String filePath = getClass().getClassLoader().getResource(resources).getPath();

        // check to see if the path contains a folder with spaces in them 
        if (filePath.contains("%20")) {
            filePath = getClass().getClassLoader().getResource(resources).getPath()
            
            .replaceAll("%20", " ");

        } 
        try {
            File customFontFile = new File(filePath);
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, customFontFile).deriveFont(size);
            return customFont;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equalsIgnoreCase("Add Task")) {
            // Create a task Compnent
            TaskComponenet taskComponenet = new TaskComponenet(taskComponentPanel);
            taskComponentPanel.add(taskComponenet);

        if (taskComponentPanel.getComponentCount()> 1) {
            TaskComponenet previousTask = (TaskComponenet) taskComponentPanel.getComponent(
                 taskComponentPanel.getComponentCount() - 2);
            previousTask.getTaskField().setBackground(null);
        }

            // make the task field request focus after creation 
            taskComponenet.getTaskField().requestFocus();
            repaint();
            revalidate();
        }
    }
}