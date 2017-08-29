
package flertrådatProgrammeringL3V2;

import sun.awt.Mutex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;

/**
 * The GUI for assignment 3:
 *
 * Additional methods and variables are added to complete the assignment.
 *
 * the main-method is in this class.
 *
 */
public class GUISemaphore
{

    //Additional components to complete assigment 3
    private FactoryThread[] producers = new FactoryThread[3];
    private TruckThread[] consumers = new TruckThread[3];
    private Storage buffer;
    private ProducerButtonListener prodListener = new ProducerButtonListener();
    private ConsumerButtonListener consListener = new ConsumerButtonListener();




    /**
     * These are the components you need to handle.
     * You have to add listeners and/or code
     * Static controls are defined inline
     */
    private JFrame frame;				// The Main window
    private JProgressBar bufferCapacity;	// The progressbar, showing content in buffer
    private JProgressBar bufferVolume, bufferWeight;

    // Data for Producer Scan
    private JButton btnStartS;			// Button start Scan
    private JButton btnStopS;			// Button stop Scan
    private JLabel lblStatusS;			// Status Scan
    // DAta for producer Arla
    private JButton btnStartA;			// Button start Arla
    private JButton btnStopA;			// Button stop Arla
    private JLabel lblStatusA;			// Status Arla
    //Data for producer AxFood
    private JButton btnStartX;			// Button start AxFood
    private JButton btnStopX;			// Button stop AxFood
    private JLabel lblStatusX;			// Status AxFood

    // Data for consumer ICA
    private JLabel lblIcaItems;			// Ica limits
    private JLabel lblIcaWeight;
    private JLabel lblIcaVolume;
    private JLabel lblIcaStatus;		// load status
    private JTextArea lstIca;			// The cargo list
    private JButton btnIcaStart;		// The buttons
    private JButton btnIcaStop;
    private JCheckBox chkIcaCont;		// Continue checkbox
    //Data for consumer COOP
    private JLabel lblCoopItems;
    private JLabel lblCoopWeight;
    private JLabel lblCoopVolume;
    private JLabel lblCoopStatus;		// load status
    private JTextArea lstCoop;			// The cargo list
    private JButton btnCoopStart;		// The buttons
    private JButton btnCoopStop;
    private JCheckBox chkCoopCont;		// Continue checkbox
    // Data for consumer CITY GROSS
    private JLabel lblCGItems;
    private JLabel lblCGWeight;
    private JLabel lblCGVolume;
    private JLabel lblCGStatus;			// load status
    private JTextArea lstCG;			// The cargo list
    private JButton btnCGStart;			// The buttons
    private JButton btnCGStop;
    private JCheckBox chkCGCont;		// Continue checkbox

    /**
     * Constructor, creates the window
     */
    public GUISemaphore()
    {
    }

    /**
     * Starts the application
     */
    public void Start()
    {
        frame = new JFrame();
        frame.setBounds(0, 0, 730, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setTitle("Food Supply System");
        InitializeGUI();					// Fill in components

        //Additional!
        buffer = new Storage(this, 100, 200, 400);
        addListeners();

        frame.setVisible(true);
        frame.setResizable(false);			// Prevent user from change size
        frame.setLocationRelativeTo(null);	// Start middle screen
    }

    private void addListeners() {

        System.out.println("addListeners()");

        //Producers
        btnStartS.addActionListener(prodListener);
        btnStopS.addActionListener(prodListener);
        btnStartA.addActionListener(prodListener);
        btnStopA.addActionListener(prodListener);
        btnStartX.addActionListener(prodListener);
        btnStopX.addActionListener(prodListener);

        //Consumers
        btnCGStart.addActionListener(consListener);
        btnCGStop.addActionListener(consListener);
        btnCoopStart.addActionListener(consListener);
        btnCoopStop.addActionListener(consListener);
        btnIcaStart.addActionListener(consListener);
        btnIcaStop.addActionListener(consListener);

    }

    /**
     * Sets up the GUI with components
     */
    private void InitializeGUI()
    {
        // First create the three main panels
        JPanel pnlBuffer = new JPanel();
        pnlBuffer.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Storage"));
        pnlBuffer.setBounds(13, 403, 693, 200);
        pnlBuffer.setLayout(null);
        // Then create the progressbar, only component in buffer panel
        bufferCapacity = new JProgressBar();
        bufferCapacity.setBounds(155, 37, 500, 23);
        bufferCapacity.setBorder(BorderFactory.createLineBorder(Color.black));
        bufferCapacity.setForeground(Color.GREEN);
        bufferCapacity.setString("Capacity: ");
        bufferCapacity.setStringPainted(true);
        bufferWeight = new JProgressBar();
        bufferWeight.setBounds(155, 60, 500, 23);
        bufferWeight.setBorder(BorderFactory.createLineBorder(Color.black));
        bufferWeight.setForeground(Color.BLUE);
        bufferWeight.setString("Weight: ");
        bufferWeight.setStringPainted(true);
        bufferVolume = new JProgressBar();
        bufferVolume.setBounds(155, 83, 500, 23);
        bufferVolume.setBorder(BorderFactory.createLineBorder(Color.black));
        bufferVolume.setForeground(Color.RED);
        bufferVolume.setString("Volume: ");
        bufferVolume.setStringPainted(true);
        pnlBuffer.add(bufferCapacity);
        pnlBuffer.add(bufferWeight);
        pnlBuffer.add(bufferVolume);
        JLabel lblmax = new JLabel("Max capacity (items):");
        lblmax.setBounds(10, 42, 126,13);
        pnlBuffer.add(lblmax);
        frame.add(pnlBuffer);

        JPanel pnlProd = new JPanel();
        pnlProd.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Producers"));
        pnlProd.setBounds(13, 13, 229, 379);
        pnlProd.setLayout(null);

        JPanel pnlCons = new JPanel();
        pnlCons.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Consumers"));
        pnlCons.setBounds(266, 13, 440, 379);
        pnlCons.setLayout(null);

        // Now add the three panels to producer panel
        JPanel pnlScan = new JPanel();
        pnlScan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Producer: Scan"));
        pnlScan.setBounds(6, 19, 217, 100);
        pnlScan.setLayout(null);

        // Content Scan panel
        btnStartS = new JButton("Start Producing");
        btnStartS.setBounds(10, 59, 125, 23);
        pnlScan.add(btnStartS);
        btnStopS = new JButton("Stop");
        btnStopS.setBounds(140, 59, 65, 23);
        pnlScan.add(btnStopS);
        lblStatusS = new JLabel("Staus Idle/Stop/Producing");
        lblStatusS.setBounds(10, 31, 200, 13);
        pnlScan.add(lblStatusS);
        // Add Scan panel to producers
        pnlProd.add(pnlScan);

        // The Arla panel
        JPanel pnlArla = new JPanel();
        pnlArla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Producer: Arla"));
        pnlArla.setBounds(6, 139, 217, 100);
        pnlArla.setLayout(null);

        // Content Arla panel
        btnStartA = new JButton("Start Producing");
        btnStartA.setBounds(10, 59, 125, 23);
        pnlArla.add(btnStartA);
        btnStopA = new JButton("Stop");
        btnStopA.setBounds(140, 59, 65, 23);
        pnlArla.add(btnStopA);
        lblStatusA = new JLabel("Staus Idle/Stop/Producing");
        lblStatusA.setBounds(10, 31, 200, 13);
        pnlArla.add(lblStatusA);
        // Add Arla panel to producers
        pnlProd.add(pnlArla);

        // The AxFood Panel
        JPanel pnlAxfood = new JPanel();
        pnlAxfood.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Producer: AxFood"));
        pnlAxfood.setBounds(6, 262, 217, 100);
        pnlAxfood.setLayout(null);

        // Content AxFood Panel
        btnStartX = new JButton("Start Producing");
        btnStartX.setBounds(10, 59, 125, 23);
        pnlAxfood.add(btnStartX);
        btnStopX = new JButton("Stop");
        btnStopX.setBounds(140, 59, 65, 23);
        pnlAxfood.add(btnStopX);
        lblStatusX = new JLabel("Staus Idle/Stop/Producing");
        lblStatusX.setBounds(10, 31, 200, 13);
        pnlAxfood.add(lblStatusX);
        // Add Axfood panel to producers
        pnlProd.add(pnlAxfood);
        // Producer panel done, add to frame
        frame.add(pnlProd);

        // Next, add the three panels to Consumer panel
        JPanel pnlICA = new JPanel();
        pnlICA.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Consumer: ICA"));
        pnlICA.setBounds(19, 19,415, 100);
        pnlICA.setLayout(null);

        // Content ICA panel
        // First the limits panel
        JPanel pnlLim = new JPanel();
        pnlLim.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Package Limits"));
        pnlLim.setBounds(6, 19, 107, 75);
        pnlLim.setLayout(null);
        JLabel lblItems = new JLabel("Items:");
        lblItems.setBounds(7, 20, 50, 13);
        pnlLim.add(lblItems);
        JLabel lblWeight = new JLabel("Weight:");
        lblWeight.setBounds(7, 35, 50, 13);
        pnlLim.add(lblWeight);
        JLabel lblVolume = new JLabel("Volume:");
        lblVolume.setBounds(7, 50, 50, 13);
        pnlLim.add(lblVolume);
        lblIcaItems = new JLabel("Data");
        lblIcaItems.setBounds(60, 20, 47, 13);
        pnlLim.add(lblIcaItems);
        lblIcaWeight = new JLabel("Data");
        lblIcaWeight.setBounds(60, 35, 47, 13);
        pnlLim.add(lblIcaWeight);
        lblIcaVolume = new JLabel("Data");
        lblIcaVolume.setBounds(60, 50, 47, 13);
        pnlLim.add(lblIcaVolume);
        pnlICA.add(pnlLim);
        // Then rest of controls
        lstIca = new JTextArea();
        lstIca.setEditable(false);
        JScrollPane spane = new JScrollPane(lstIca);
        spane.setBounds(307, 16, 102, 69);
        spane.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlICA.add(spane);
        btnIcaStart = new JButton("Start Loading");
        btnIcaStart.setBounds(118, 64, 120, 23);
        pnlICA.add(btnIcaStart);
        btnIcaStop = new JButton("Stop");
        btnIcaStop.setBounds(240, 64, 60, 23);
        pnlICA.add(btnIcaStop);
        lblIcaStatus = new JLabel("Ica stop status here");
        lblIcaStatus.setBounds(118, 16, 150, 23);
        pnlICA.add(lblIcaStatus);
        chkIcaCont = new JCheckBox("Continue load");
        chkIcaCont.setBounds(118, 39, 130, 17);
        pnlICA.add(chkIcaCont);
        // All done, add to consumers panel
        pnlCons.add(pnlICA);

        JPanel pnlCOOP = new JPanel();
        pnlCOOP.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Consumer: COOP"));
        pnlCOOP.setBounds(19, 139, 415, 100);
        pnlCOOP.setLayout(null);
        pnlCons.add(pnlCOOP);

        // Content COOP panel
        // First the limits panel
        JPanel pnlLimC = new JPanel();
        pnlLimC.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Package Limits"));
        pnlLimC.setBounds(6, 19, 107, 75);
        pnlLimC.setLayout(null);
        JLabel lblItemsC = new JLabel("Items:");
        lblItemsC.setBounds(7, 20, 50, 13);
        pnlLimC.add(lblItemsC);
        JLabel lblWeightC = new JLabel("Weight:");
        lblWeightC.setBounds(7, 35, 50, 13);
        pnlLimC.add(lblWeightC);
        JLabel lblVolumeC = new JLabel("Volume:");
        lblVolumeC.setBounds(7, 50, 50, 13);
        pnlLimC.add(lblVolumeC);
        lblCoopItems = new JLabel("Data");
        lblCoopItems.setBounds(60, 20, 47, 13);
        pnlLimC.add(lblCoopItems);
        lblCoopWeight = new JLabel("Data");
        lblCoopWeight.setBounds(60, 35, 47, 13);
        pnlLimC.add(lblCoopWeight);
        lblCoopVolume = new JLabel("Data");
        lblCoopVolume.setBounds(60, 50, 47, 13);
        pnlLimC.add(lblCoopVolume);
        pnlCOOP.add(pnlLimC);
        // Then rest of controls
        lstCoop = new JTextArea();
        lstCoop.setEditable(false);
        JScrollPane spaneC = new JScrollPane(lstCoop);
        spaneC.setBounds(307, 16, 102, 69);
        spaneC.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlCOOP.add(spaneC);
        btnCoopStart = new JButton("Start Loading");
        btnCoopStart.setBounds(118, 64, 120, 23);
        pnlCOOP.add(btnCoopStart);
        btnCoopStop = new JButton("Stop");
        btnCoopStop.setBounds(240, 64, 60, 23);
        pnlCOOP.add(btnCoopStop);
        lblCoopStatus = new JLabel("Coop stop status here");
        lblCoopStatus.setBounds(118, 16, 150, 23);
        pnlCOOP.add(lblCoopStatus);
        chkCoopCont = new JCheckBox("Continue load");
        chkCoopCont.setBounds(118, 39, 130, 17);
        pnlCOOP.add(chkCoopCont);
        // All done, add to consumers panel
        pnlCons.add(pnlCOOP);

        JPanel pnlCG = new JPanel();
        pnlCG.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Consumer: CITY GROSS"));
        pnlCG.setBounds(19, 262, 415, 100);
        pnlCG.setLayout(null);
        pnlCons.add(pnlCG);

        // Content CITY GROSS panel
        // First the limits panel
        JPanel pnlLimG = new JPanel();
        pnlLimG.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Package Limits"));
        pnlLimG.setBounds(6, 19, 107, 75);
        pnlLimG.setLayout(null);
        JLabel lblItemsG = new JLabel("Items:");
        lblItemsG.setBounds(7, 20, 50, 13);
        pnlLimG.add(lblItemsG);
        JLabel lblWeightG = new JLabel("Weight:");
        lblWeightG.setBounds(7, 35, 50, 13);
        pnlLimG.add(lblWeightG);
        JLabel lblVolumeG = new JLabel("Volume:");
        lblVolumeG.setBounds(7, 50, 50, 13);
        pnlLimG.add(lblVolumeG);
        lblCGItems = new JLabel("Data");
        lblCGItems.setBounds(60, 20, 47, 13);
        pnlLimG.add(lblCGItems);
        lblCGWeight = new JLabel("Data");
        lblCGWeight.setBounds(60, 35, 47, 13);
        pnlLimG.add(lblCGWeight);
        lblCGVolume = new JLabel("Data");
        lblCGVolume.setBounds(60, 50, 47, 13);
        pnlLimG.add(lblCGVolume);
        pnlCG.add(pnlLimG);
        // Then rest of controls
        lstCG = new JTextArea();
        lstCG.setEditable(false);
        JScrollPane spaneG = new JScrollPane(lstCG);
        spaneG.setBounds(307, 16, 102, 69);
        spaneG.setBorder(BorderFactory.createLineBorder(Color.black));
        pnlCG.add(spaneG);
        btnCGStart = new JButton("Start Loading");
        btnCGStart.setBounds(118, 64, 120, 23);
        pnlCG.add(btnCGStart);
        btnCGStop = new JButton("Stop");
        btnCGStop.setBounds(240, 64, 60, 23);
        pnlCG.add(btnCGStop);
        lblCGStatus = new JLabel("CITY GROSS stop status here");
        lblCGStatus.setBounds(118, 16, 150, 23);
        pnlCG.add(lblCGStatus);
        chkCGCont = new JCheckBox("Continue load");
        chkCGCont.setBounds(118, 39, 130, 17);
        pnlCG.add(chkCGCont);
        // All done, add to consumers panel
        pnlCons.add(pnlCOOP);

        // Add consumer panel to frame
        frame.add(pnlCons);
    }

    /**Checks which thread calls on this method and updates the labels for the current thread's volume, weight and
     * capacity.
     *
     * @param itemName : String
     * @param groceryName : String
     * @param currentItems : int
     * @param currentVolume : double
     * @param currentWeight : double
     */
    public void updateConsumerCargo(String itemName, String groceryName, int currentItems, double currentVolume, double currentWeight) {

        if(groceryName.equalsIgnoreCase("ICA")){
            lblIcaItems.setText(""+currentItems);
            lblIcaVolume.setText(""+currentVolume);
            lblIcaWeight.setText(""+currentWeight);
            if(itemName.equalsIgnoreCase("clear list")){
                lstIca.setText("");
            }else{
                lstIca.append(itemName+"\n");
            }


        }
        else if(groceryName.equalsIgnoreCase("CITY GROSS")){

            lblCGItems.setText(""+currentItems);
            lblCGVolume.setText(""+currentVolume);
            lblCGWeight.setText(""+currentWeight);
            if(itemName.equalsIgnoreCase("clear list")){
                lstCG.setText("");
            }else{
                lstCG.append(itemName+"\n");
            }
        }
        else if(groceryName.equalsIgnoreCase("COOP")){
            lblCoopItems.setText(""+currentItems);
            lblCoopVolume.setText(""+currentVolume);
            lblCoopWeight.setText(""+currentWeight);
            if(itemName.equalsIgnoreCase("clear list")){
                lstCoop.setText("");
            }else{
                lstCoop.append(itemName+"\n");
            }
        }
    }

    /**Checks which thread calls on the method and updates it's status.
     *
     * @param consumerName : String
     * @param s : String
     */
    public void updateConsumerStatus(String consumerName, String s) {
        if(consumerName.equalsIgnoreCase("Ica")){
            lblIcaStatus.setText(consumerName+s);

        }
        if(consumerName.equalsIgnoreCase("Coop")){
            lblCoopStatus.setText(consumerName+s);

        }
        if(consumerName.equalsIgnoreCase("City gross")){
            lblCGStatus.setText(consumerName+s);

        }
    }

    /**Updates the progressbars that represent the storage and it's capacity (weight, items, volume).
     *
     * @param currentCapacity : int
     * @param currentVolume : double
     * @param currentWeight : double
     */
    public void updateStorage(int currentCapacity, double currentVolume, double currentWeight) {
        bufferCapacity.setValue(currentCapacity);
        bufferCapacity.setString("Capacity: "+currentCapacity);

        bufferWeight.setValue((int) currentWeight);
        bufferWeight.setString("Weight: "+String.format("%.2f", currentWeight));

        bufferVolume.setValue((int) currentVolume);
        bufferVolume.setString("Volume: "+String.format("%.2f", currentVolume));
    }

    /**Configures the Jprogressbars to correspond to the buffer's maximum capacity.
     *
     * @param capacity : int
     * @param weight : double
     * @param volume : double
     */
    public void setStorageCapacity(int capacity, double weight, double volume) {
        bufferCapacity.setMaximum(capacity);
        bufferVolume.setMaximum((int) volume);
        bufferWeight.setMaximum((int) weight);
    }

    public void updateProducerStatus(String factoryName, String status) {
        if(factoryName.equalsIgnoreCase("Arla")){
            lblStatusA.setText(status);
        }
        if(factoryName.equalsIgnoreCase("AxFood")){
            lblStatusX.setText(status);
        }
        if(factoryName.equalsIgnoreCase("Scan")){
            lblStatusS.setText(status);
        }
    }

    public void changeButtonAvaibility(String consumerName) {
        if(consumerName.equalsIgnoreCase("ICA")){
            btnIcaStart.setEnabled(true);
            btnIcaStop.setEnabled(false);
        }
        if(consumerName.equalsIgnoreCase("COOP")){
            btnCoopStart.setEnabled(true);
            btnCoopStop.setEnabled(false);
        }
        if(consumerName.equalsIgnoreCase("CITY GROSS")){
            btnCGStart.setEnabled(true);
            btnCGStop.setEnabled(false);
        }


    }

    /**This class is responsible for interaction with the buttons belonging to the producer threads. The buttons
     * instantiate and terminate threads.
     *
     */
    private class ProducerButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            //Scan
            if(e.getSource()==btnStartS){
                if(producers[0]==null){
                    producers[0] = new FactoryThread("Scan",buffer, GUISemaphore.this);
                    System.out.println("Försöker starta tråd Scan...");
                    producers[0].startProducing();
                }
                btnStartS.setEnabled(false);
                btnStopS.setEnabled(true);
            }
            if(e.getSource()==btnStopS){
                if(producers[0]!=null){
                    producers[0].stopProducing();
                    producers[0] = null;
                }
                btnStartS.setEnabled(true);
                btnStopS.setEnabled(false);

            }

            //Arla
            if(e.getSource()==btnStartA){
                if(producers[1]==null){
                    producers[1] = new FactoryThread("Arla", buffer, GUISemaphore.this);
                    System.out.println("Försöker starta tråd Arla...");

                    producers[1].startProducing();
                }
                btnStartA.setEnabled(false);
                btnStopA.setEnabled(true);

            }
            if(e.getSource()==btnStopA){
                if(producers[1]!=null){
                    producers[1].stopProducing();
                    producers[1] = null;

                }
                btnStartA.setEnabled(true);
                btnStopA.setEnabled(false);
            }

            //Axe-food
            if(e.getSource()==btnStartX){
                if(producers[2]==null){
                    producers[2] = new FactoryThread("AxFood", buffer, GUISemaphore.this);
                    producers[2].startProducing();
                }
                btnStartX.setEnabled(false);
                btnStopX.setEnabled(true);
            }
            if(e.getSource()==btnStopX){
                if(producers[2]!=null){
                    producers[2].stopProducing();
                    producers[2] = null;

                }
                btnStartX.setEnabled(true);
                btnStopX.setEnabled(false);
            }
        }
    }

    /**This class is responsible for interaction with buttons belonging to consumer threads. The buttons instantiate
     * and terminate threads.
     *
     */
    private class ConsumerButtonListener implements  ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            //Ica
            if(e.getSource()==btnIcaStart){
                System.out.println("Försöker starta Ica");
                boolean continious = false;
                if(chkIcaCont.isSelected()){
                    continious = true;
                }
                consumers[0]= new TruckThread("Ica", buffer, GUISemaphore.this, 100, 100, 50, continious);
                consumers[0].startConsuming();
                btnIcaStart.setEnabled(false);
                btnIcaStop.setEnabled(true);

            }
            if(e.getSource()==btnIcaStop){
                if(consumers[0]!=null){
                    consumers[0].stopConsuming();
                    lstIca.setText("");
                }
                btnIcaStart.setEnabled(true);
                btnIcaStop.setEnabled(false);

            }

            //COOP
            if(e.getSource()==btnCoopStart){
                System.out.println("Försöker starta Coop");

                boolean continious = false;
                if(chkCoopCont.isSelected()){
                    continious = true;
                }
                consumers[1]= new TruckThread("Coop", buffer, GUISemaphore.this, 120, 120, 60, continious);
                consumers[1].startConsuming();
                btnCoopStart.setEnabled(false);
                btnCoopStop.setEnabled(true);

            }
            if(e.getSource()==btnCoopStop){
                if(consumers[1]!=null){
                    consumers[1].stopConsuming();
                    lstCoop.setText("");
                }
                btnCoopStart.setEnabled(true);
                btnCoopStop.setEnabled(false);
            }

            //City-Gross
            if(e.getSource()==btnCGStart){
                System.out.println("Försöker starta City gross");

                boolean continious = false;

                if(chkCGCont.isSelected()){
                    continious = true;
                }
                consumers[2]= new TruckThread("City Gross", buffer, GUISemaphore.this, 200,200, 100, continious);
                consumers[2].startConsuming();
                btnCGStart.setEnabled(false);
                btnCGStop.setEnabled(true);

            }
            if(e.getSource()==btnCGStop){
                if(consumers[2]!=null){
                    consumers[2].stopConsuming();
                    lstCG.setText("");
                }
                btnCGStart.setEnabled(true);
                btnCGStop.setEnabled(false);
            }
        }
    }

    public static void main(String[] args) {
        new GUISemaphore().Start();
    }
}
