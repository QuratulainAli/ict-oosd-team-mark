//File Created by Mark Southwell

package ca.sait.oosd.travel.agents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.toedter.calendar.JDateChooser;

import ca.sait.oosd.SpringUtilities;
import ca.sait.oosd.TravelParts;
import ca.sait.oosd.business.TEBusinessDelegate;
import ca.sait.oosd.business.TEBusinessDelegateImpl;
import ca.sait.oosd.business.TEBusinessException;
import ca.sait.oosd.components.NavigationButtonPanel;
import ca.sait.oosd.components.TEJFrame;
import ca.sait.oosd.hibernate.Agencies;
import ca.sait.oosd.hibernate.Agents;
import ca.sait.oosd.hibernate.Packages;
import ca.sait.oosd.logger.LogLevel;
import ca.sait.oosd.logger.LoggerHelper;
import ca.sait.oosd.travel.packages.PackageGUI;
import ca.sait.oosd.travel.products.ProductsGUI;

public class AgentGUI extends TEJFrame {

	private static final long serialVersionUID = 1L;
	private LoggerHelper helper = new LoggerHelper(PackageGUI.class.getName());
    private Collection<Agents> agentCollection;  
    private Collection<Agencies> agencyCollection;
    private TEBusinessDelegate delegate;
    private Agents agents;
    private DefaultListModel model;

    private final JTextField agentIdTextField= new JTextField(15);
    private final JTextField agentFirstNameTextField = new JTextField(15);
    private final JTextField agentMiddleInitialTextField = new JTextField(2);
    private final JTextField agentLastNameTextField = new JTextField(15);
    private final JTextField agentBusPhoneTextField = new JTextField(15);
    private final JTextField agentEmailTextField = new JTextField(15);
    private  final JTextField agentPositionTextField = new JTextField(15);
    private JComboBox agencyIdCboBox;
    
	public AgentGUI() {
		super();
		 delegate = new TEBusinessDelegateImpl();
	       agentCollection = delegate.getAgentCollection();
	       agencyCollection = delegate.getAgenciesCollection();
	       
	        model = new DefaultListModel();

			initGUI();
			super.alignFrameOnScreen(this);
	}

	@Override
	protected void adjustSize(int width, int height) {
		
		
	}
	
	 private void clearComponents()
     {
     	agentIdTextField.setText("");
     	agentFirstNameTextField.setText("");
     	agentMiddleInitialTextField.setText("");
     	agentLastNameTextField.setText("");
     	agentBusPhoneTextField.setText("");
     	agentEmailTextField.setText("");
     	agentPositionTextField.setText("");
     	agencyIdCboBox.setSelectedIndex(0);
     }
	
	@Override
	protected void initGUI() {

        helper.log(LogLevel.INFO, "Start initlializing Package user interface...");

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel dataEntryPane = new JPanel(new SpringLayout());
        dataEntryPane.add(new JLabel("Agent ID", JLabel.TRAILING));
        agentIdTextField.setEditable(false);
        dataEntryPane.add(agentIdTextField);
        dataEntryPane.add(new JLabel("First Name", JLabel.TRAILING));
        dataEntryPane.add(agentFirstNameTextField);
        dataEntryPane.add(new JLabel("Middle Initial", JLabel.TRAILING));
        dataEntryPane.add(agentMiddleInitialTextField);
        dataEntryPane.add(new JLabel("Last Name", JLabel.TRAILING));
        dataEntryPane.add(agentLastNameTextField);
        dataEntryPane.add(new JLabel("Business Phone", JLabel.TRAILING));
        dataEntryPane.add(agentBusPhoneTextField);
        dataEntryPane.add(new JLabel("Email Address", JLabel.TRAILING));
        dataEntryPane.add(agentEmailTextField);
        dataEntryPane.add(new JLabel("Position", JLabel.TRAILING));
        dataEntryPane.add(agentPositionTextField);
        dataEntryPane.add(new JLabel("Agency ID", JLabel.TRAILING));
        agencyIdCboBox = new JComboBox();	
        for (Agencies agency : agencyCollection) {
        	
        	agencyIdCboBox.addItem(agency);			
		}
        dataEntryPane.add(agencyIdCboBox);
       

       SpringUtilities.makeCompactGrid(dataEntryPane,
				8, 2,
				6, 6,
				6, 6);


        //list to display the data
              for(Agents agent : agentCollection) {
            model.addElement(agent);
        }
        
		final JList list = new JList(model);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);

		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));

		//button panel
		JPanel southPane = new JPanel();
		JButton addButton = new JButton("Add");
		JButton updateButton = new JButton("Update");
		JButton clearButton = new JButton("Clear");
		JButton deleteButton = new JButton("Delete");
		southPane.add(addButton);
		southPane.add(updateButton);
		southPane.add(clearButton);
		southPane.add(deleteButton);

		GridLayout grid = new GridLayout(2, 1);
		JPanel centerPane = new JPanel();
		centerPane.setLayout(grid);

		centerPane.add(dataEntryPane);
		centerPane.add(southPane, JPanel.BOTTOM_ALIGNMENT);

        //add the listener to the list
        ListSelectionModel selectionModel = list.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                agents = (Agents)list.getSelectedValue();
                agentIdTextField.setText(Long.toString(agents.getAgentid()));
                agentFirstNameTextField.setText(agents.getAgtfirstname());
                agentMiddleInitialTextField.setText(agents.getAgtmiddleinitial());
                agentLastNameTextField.setText(agents.getAgtlastname());
                agentBusPhoneTextField.setText(agents.getAgtbusphone());
                agentEmailTextField.setText(agents.getAgtemail());
                agentPositionTextField.setText(agents.getAgtposition());
                
            }
        });

        //clear the content of the data entry screen
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               clearComponents();

            }			
        });
       
        //add new record to the database
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    agents = new Agents();
                    agents = (Agents)list.getSelectedValue();
                    agents.setAgtfirstname(agentFirstNameTextField.getText());
                    agents.setAgtmiddleinitial(agentMiddleInitialTextField.getText());
                    agents.setAgtmiddleinitial(agentMiddleInitialTextField.getText());
                    agents.setAgtlastname(agentLastNameTextField.getText());
                    agents.setAgtbusphone(agentBusPhoneTextField.getText());
                    agents.setAgtemail(agentEmailTextField.getText());
                    agents.setAgtposition(agentPositionTextField.getText());
                    agents.setAgencies((Agencies)agencyIdCboBox.getSelectedItem());                    

                    agents = (Agents) delegate.save(agents);                   
                    model.addElement(agents);
                    clearComponents();
                    
                } catch (TEBusinessException ex) {
                    helper.log(LogLevel.ERROR, "Exception occured while saving data..." + ex.getMessage());
                    
                }
            }
        });

        //set user to innactive
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int selection = JOptionPane.showConfirmDialog(AgentGUI.this, "Selecting this option will " +
               		"de-activate this agent." +
               		" You will be required to assign this " +
               		"agents clients to a new agent. Would you" +
               		" like to continue?", "Warning", JOptionPane.OK_CANCEL_OPTION);
               if (selection==0)
            	   
               {
            	   System.out.print("open the new window");  
            	   new ProductsGUI();
               }
               
            	
            	if(agents != null) {
                    try {
                        int position = model.indexOf(agents, 0);
                        delegate.delete(agents);

                        clearComponents();
                //        removeFromListModel(position);

                    } catch (TEBusinessException ex) {
                        helper.log(LogLevel.ERROR, "Exception occured while deleting data..." + ex.getMessage());
                        
                    }
                }
            }
        });

       //update a record from the user interface
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                	agents = (Agents)list.getSelectedValue();
                    agents.setAgentid(Long.parseLong(agentIdTextField.getText()));
                    agents.setAgtfirstname(agentFirstNameTextField.getText());
                    agents.setAgtmiddleinitial(agentMiddleInitialTextField.getText());
                    agents.setAgtmiddleinitial(agentMiddleInitialTextField.getText());
                    agents.setAgtlastname(agentLastNameTextField.getText());
                    agents.setAgtbusphone(agentBusPhoneTextField.getText());
                    agents.setAgtemail(agentEmailTextField.getText());
                    agents.setAgtposition(agentPositionTextField.getText());
                    agents.setAgencies((Agencies)agencyIdCboBox.getSelectedItem());                    

                    agents = (Agents) delegate.update(agents);
//                    updateListModel(agents);

                } catch (TEBusinessException ex) {
                    helper.log(LogLevel.ERROR, "Exception occured while saving data..." + ex.getMessage());

                }
            }
        });
                
        //pass the product type enum to the NavigationButtonPanel, so that it disable the particular button
        this.getContentPane().add(new NavigationButtonPanel(TravelParts.PACKAGE), BorderLayout.NORTH);
        this.getContentPane().add(listScroller, BorderLayout.EAST);
        this.getContentPane().add(centerPane, BorderLayout.CENTER);

		
		
	}

}
