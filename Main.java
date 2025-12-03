package application;
	
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.TextArea;
import java.util.function.UnaryOperator;
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			TextField BHNfield = new TextField();

	       
	        BHNfield.setPromptText("Enter Brinell Hardness");
	        
	        TextField calibers = new TextField();

	       
	        calibers.setPromptText("Shell Caliber (mm)");
	        
	        TextField thickness = new TextField();

	       
	        thickness.setPromptText("Armor Thickness (mm)");
	        
	        TextField velocity = new TextField();

	        
	        velocity.setPromptText("Velocity (m/s)");
	       
	        TextField impactangle = new TextField();

	        
	        impactangle.setPromptText("Impact Angle");
	        
	        
	        TextField shellmass = new TextField();

	      
	        shellmass.setPromptText("Shell Mass (KG)");
	       
	        
	        TextField coremass = new TextField();

	      
	        coremass.setPromptText("APCR Core Mass (KG)");
	        
	        
	        TextField tnteq = new TextField();

	       
	        tnteq.setPromptText("TNT (KG)");
	     
	        ObservableList<String> options = FXCollections.observableArrayList(
	                "AP",
	                "APBC",
	                "APC",
	                "APCR",
	                "APDS (Early)",
	                "APDS (Late)",
	                "HEAT (Early)",
	                "HEAT",
	                "AP (Soviet)",
	                "APBC (Soviet)"
	                
	                
	        );

		 ObservableList<String> options2 = FXCollections.observableArrayList(
	                "RHA",
	                "CHA",
	                "Composite Armor",
	                "NERA",
	                "Wood",
	                "Steel",
	                "Concrete",
	                "Aluminium"
	                
	                
	        );

		
	        ComboBox<String> shells = new ComboBox<>(options);
	        shells.setOnAction(event -> {
	            String s = shells.getValue();
	            System.out.println("Selected: " + s);
	        });
	        ComboBox<String> materials = new ComboBox<>(options2);
	        materials.setOnAction(event -> {
	             String sw = materials.getValue();
	            System.out.println("Selected: " + sw);
	        });
	        TextArea area = new TextArea();
	        Button calculate = new Button("Calculate");
	        calculate.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                System.out.println("Button Clicked!");
	                if ((shells.getValue().equals("HEAT")) || ((shells.getValue().equals("HEAT (Early)")))){
	                	shapedcharge(shells.getValue(),Double.parseDouble(calibers.getText()), Double.parseDouble(tnteq.getText()), Double.parseDouble(thickness.getText()), Double.parseDouble(impactangle.getText()), materials.getValue(), area );
	                }
	                else {
	                	fullcalculation(shells.getValue(),Double.parseDouble(calibers.getText()),Double.parseDouble(thickness.getText()), Double.parseDouble(impactangle.getText()), Double.parseDouble(shellmass.getText()), Double.parseDouble(velocity.getText()), materials.getValue(), Double.parseDouble(coremass.getText()),Double.parseDouble(BHNfield.getText()), area,  Double.parseDouble(tnteq.getText())  );
	                }
	            }
	        });
	        
		        //VBox root2 = new VBox(comboBox);
		        shells.setPromptText("Shell Type");
		        materials.setPromptText("Select a Material");
			Pane root = new Pane(shells,BHNfield,calibers,materials,thickness,velocity,impactangle,shellmass,coremass, tnteq,calculate,area);
			shells.relocate(0, 0);
			BHNfield.relocate(110,0);
			BHNfield.setFocusTraversable(false);
			calibers.relocate(255,0);
			materials.relocate(390,0);
			thickness.relocate(500, 0);
			velocity.relocate(640, 0);
			impactangle.relocate(760, 0);
			shellmass.relocate(875, 0);
			coremass.relocate(990, 0);
			tnteq.relocate(1120, 0);
			calculate.relocate(0,200);
			area.relocate(0, 300);
		    
			Scene scene = new Scene(root,1400,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setTitle("Mark IX \"Sandwichinator\" Infinite Sandwich Generator");
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
		String shelltype = "";
		double shellcaliber =0;
		double shelllength =0;
		double shellvelocity=0;
		double platethickness=0;
		double impactangle=0;
		double shellmass=0;
		double coremass =0;
		double hardness=0;
		//System.out.println(Math.toDegrees(Math.cos(60)));
		//System.out.println(calculateAP(76.2, 792.0,6.935));
		//fullcalculation("HEAT (Early)",105, 63.5,47,6.53, 618, "RHA",1.8,310);
		//shapedcharge("HEAT",105,1.01,63.5,47,"RHA");
	/*JFrame patrick = new JFrame("Mark IX \"Sandwichinator\" Infinite Sandwich Generator");
	 patrick.getContentPane().setBackground(Color.black);
	patrick.setSize(1000,1000);
	patrick.setVisible(true);
    patrick.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JTextField input = new JTextField("Enter Text Here",8);
    JPanel panel = new JPanel();
    panel.add(input);
    input.setVisible(true);
    patrick.add(input, BorderLayout.CENTER);
    input.setBackground(Color.white) ;
    input.setSize(50,50);*/
	}
	public static void shapedcharge(String shelltype, double caliber, double tnteq, double thickness, double impactangle, String material,TextArea area) {
		  double pen =0;
		  
		  double slopemulti =calculateslope(caliber,thickness,impactangle,shelltype);
			if (shelltype.equals("HEAT (Early)")) {
			  pen = calculateheatearly(caliber,tnteq);
		  }
			else {
				pen = calculateheat(caliber,tnteq);
			}
	Map<String, Double> materials = new HashMap<String, Double>();
			materials.put("RHA", 1.00);
		    materials.put("Composite Armor", 2.0);
			materials.put("Steel", .45);
			materials.put("Wood", .05);
			materials.put("CHA", .94);
			materials.put("Concrete", .35 );
			materials.put("Aluminium", .20);
				materials.put("NERA", 1.70);
				double materialmultiplier = materials.get(material);
				String result = "";
				if (pen >= (((thickness) * slopemulti) * materialmultiplier)){
					result = "Success";
				}
				if (pen < ((thickness * slopemulti) * materialmultiplier)){
					result = "Non-Pen";
				}
				/*if (impactangle > ricochetangle) {
					System.out.println("Ricochet");
				}*/
				if (thickness*7 <= caliber) {
					result  = "Overmatch";
				}
				
				area.setText("Penetration: " + (int)pen + "mm" + "\n"+
				"Impact Angle: " + impactangle + "\n"+
				"Material: " + material + "\n"+
				"Plate Thickness: " + thickness + "\n" +
				"Effective Thickness: " + ((int)(thickness*slopemulti)*materialmultiplier) + "\n" +
				result);
				if (pen >= (((thickness) * slopemulti) * materialmultiplier)){
					System.out.println("Success");
				}
				if (pen < ((thickness * slopemulti) * materialmultiplier)){
					System.out.println("Non-Pen");
				}
				/*if (impactangle > ricochetangle) {
					System.out.println("Ricochet");
				}*/
				if (thickness*7 <= caliber) {
					System.out.println("Overmatch");
				}
		}
		public static void fullcalculation(String shelltype, double caliber, double thickness, double impactangle, double shellmass, double shellvelocity, String material,  double coremass, double hardness, TextArea area, double tnteq) {
			double slopemulti =calculateslope(caliber,thickness,impactangle,shelltype);
			double totalpen = 0;
			double prepen =0;
			shellmass -= tnteq;
			double between1 =37;
			double between2 = 52;
			double currentap =0;
			double BHNmult = 0.01 * 977.07 * Math.pow(caliber, 0.06111) * Math.pow(thickness/caliber, 0.2821) * Math.pow(hardness, -0.4363);
			//double ricochetangle = 0;
			System.out.println(BHNmult + "BH");
			if (shelltype.equals("AP") || shelltype.equals("APBC")) {
				 totalpen = calculateAP(caliber, shellvelocity,shellmass);
				 System.out.println(totalpen);
				 prepen = calculateAPpre(caliber, shellvelocity, shellmass);
				 if (impactangle<between1) {
					 currentap=prepen;
				 }
				 if (impactangle>between2) {
					 currentap=totalpen;
				 }
				 if ((impactangle>= between1) && (impactangle <=between2)){
					 //double t = 1- ((between2-impactangle)/(between2-between1));// ((impactangle-between1)/ (between2-between1));
					 //System.out.println("t " + t);
					 currentap =prepen+((totalpen-prepen)/ (between2-between1)) * (impactangle -between1);
				 }
			}
			if (shelltype.equals("AP (Soviet)") || shelltype.equals("APBC (Soviet)")) {
				 totalpen = calculateAPsovpre(caliber, shellvelocity,shellmass);
				 System.out.println(totalpen);
				 prepen = calculateAPsov(caliber, shellvelocity, shellmass);
				 if (impactangle<between1) {
					 currentap=prepen;
				 }
				 if (impactangle>between2) {
					 currentap=totalpen;
				 }
				 if ((impactangle>= between1) && (impactangle <=between2)){
					 //double t = 1- ((between2-impactangle)/(between2-between1));// ((impactangle-between1)/ (between2-between1));
					 //System.out.println("t " + t);
					 currentap =prepen+((totalpen-prepen)/ (between2-between1)) * (impactangle -between1);
				 }
			}
			if (shelltype.equals("APC")) {
				 totalpen = calculateAPC(caliber, shellvelocity,shellmass);
			}
			if (shelltype.equals("APDS (Late)") || (shelltype.equals("APDS (Early)"))) {
				 totalpen = calculateAPDS(caliber, shellvelocity,shellmass);
			}
			if (shelltype.equals("APCR")) {
				totalpen = calculateAPCR(caliber,shellvelocity,shellmass,coremass);
			}
			Map<String, Double> materials = new HashMap<String, Double>();
			materials.put("RHA", 1.00);
		    materials.put("Composite Armor", .50);
			materials.put("Steel", .45);
			materials.put("Wood", .05);
			materials.put("CHA", .94);
			materials.put("Concrete", .35 );
			materials.put("Aluminium", .20);
			materials.put("NERA", .10);
			double materialmultiplier = materials.get(material);
			String result = "";
			if (totalpen >= (((thickness) * slopemulti) * materialmultiplier)*BHNmult){
				result = "Success";
			}
			if (totalpen < ((thickness * slopemulti) * materialmultiplier)* BHNmult){
				result ="Non-Pen";
			}
			/*if (impactangle > ricochetangle) {
				System.out.println("Ricochet");
			}*/
			if (thickness*7 <= caliber) {
				result = "Overmatch";
			}
			String penthingy = "";
			if (shelltype.equals("AP") || (shelltype.equals("APBC")) || (shelltype.equals("APBC (Soviet)")) || (shelltype.equals("AP (Soviet)")) ){
				penthingy = "Penetration: " + (int)totalpen + "mm" + " (" + (int)prepen + "mm Pre-Deformation)" + "\n" + 
				"Flat Penetration at angle w/ deformation: " + (int)currentap + "mm" + "\n";
			}
			else {
				penthingy = "Penetration: " + (int)totalpen + "mm" + "\n";
			}
			area.setText(penthingy + "Impact Angle: " + impactangle + "\n" +
			"Material: " + material + "\n" + 
			"Plate Thickness: " + thickness + "\n" + 
			"Effective Thickness: " + (int)((thickness*slopemulti)*materialmultiplier)*BHNmult + "\n"+
			result);
			
		}
		public static double calculateslope(double caliber, double thickness, double impactangle, String shelltype) {
			Map<Integer, Double> APFSDS_long = new HashMap<Integer, Double>(20);
			APFSDS_long.put(0, 1.0);
			APFSDS_long.put(5, 0.0);
			APFSDS_long.put(10, 0.0);
			APFSDS_long.put(15, 0.0);
			APFSDS_long.put(20, 1.064);
			APFSDS_long.put(25, 0.0);
			APFSDS_long.put(30, 1.185);
			APFSDS_long.put(35, 0.0);
			APFSDS_long.put(40, 1.305);
			APFSDS_long.put(45, 0.0);
			APFSDS_long.put(50, 1.55);
			APFSDS_long.put(55, 0.0);
			APFSDS_long.put(60, 1.73);
			APFSDS_long.put(65, 0.0);
			APFSDS_long.put(70, 2.4);
			APFSDS_long.put(75, 0.0);
			APFSDS_long.put(80, 5.3);
			APFSDS_long.put(85, 0.0);
			APFSDS_long.put(90, 20.0);
			Map<Integer, Double> apfsdsShort = new HashMap<Integer, Double>(20);
	        apfsdsShort.put(0, 1.0);
	        apfsdsShort.put(5, 0.0);
	        apfsdsShort.put(10, 1.06);
	        apfsdsShort.put(15, 0.0);
	        apfsdsShort.put(20, 1.17);
	        apfsdsShort.put(25, 0.0);
	        apfsdsShort.put(30, 1.22);
	        apfsdsShort.put(35, 0.0);
	        apfsdsShort.put(40, 1.64);
	        apfsdsShort.put(45, 0.0);
	        apfsdsShort.put(50, 1.88);
	        apfsdsShort.put(55, 0.0);
	        apfsdsShort.put(60, 2.6);
	        apfsdsShort.put(65, 0.0);
	        apfsdsShort.put(70, 3.5);
	        apfsdsShort.put(75, 0.0);
	        apfsdsShort.put(80, 6.7);
	        apfsdsShort.put(85, 0.0);
	        apfsdsShort.put(90, 20.0);
			double slopemultiplier = 0;
			double TD = thickness/caliber;
			if ((shelltype.equals("AP") || (shelltype.equals("APC"))  || (shelltype.equals("AP (Soviet)"))) && (impactangle>=0) && (impactangle<40)) {
				double a = .95 * Math.pow(2.71828, 0.0000539 * Math.pow(impactangle, 2.5));
			System.out.println(a);
				double b2 = 0.04433 * Math.pow(2.71828, 0.04687*impactangle);
	            slopemultiplier = a* Math.pow(TD, b2);
	            System.out.println("Slope mult: " + slopemultiplier);
			}
			if ((shelltype.equals("AP") || shelltype.equals("APC")  || (shelltype.equals("AP (Soviet)")))&& (impactangle>=40) && (impactangle<55)) {
				
				slopemultiplier = 0.04754 * Math.pow(impactangle, 0.953) * Math.pow(TD, 0.02047164 * Math.pow(impactangle, 0.46471));
				System.out.println("Slope mult: " + slopemultiplier);
			}
			if ((shelltype.equals("AP") || shelltype.equals("APC")  || (shelltype.equals("AP (Soviet)"))) && (impactangle>=55)) {
				slopemultiplier = 0.0001675 * Math.pow(impactangle, 2.3655) * Math.pow(TD, 0.02047164 * Math.pow(impactangle, 0.46471));
				System.out.println("Slope mult: " + slopemultiplier);
			}
			if ((shelltype.equals("APBC") ||  (shelltype.equals("APBC (Soviet)")))  && (impactangle<55)) {
				double a = Math.pow(2.71828, 0.019925*Math.pow(1.06758, impactangle));
				double b = 0.007012 * Math.pow(1.08289, impactangle);
				slopemultiplier = a* Math.pow(TD, b);
			}
			if ((shelltype.equals("APBC") ||  (shelltype.equals("APBC (Soviet)"))) && (impactangle>=55) && (impactangle<60)) {
				double a = Math.pow(2.71828, 0.002542*Math.pow(1.1089, impactangle));
				double b = 0.0004673 * Math.pow(1.1373, impactangle);
				slopemultiplier = a* Math.pow(TD, b);
			}
			if ((shelltype.equals("APBC") ||  (shelltype.equals("APBC (Soviet)"))) && (impactangle>=60)) {
				double a = Math.pow(2.71828, 0.03723*Math.pow(1.06033, impactangle));
				double b = -3.367 + (0.07411* impactangle);
				slopemultiplier = a* Math.pow(TD, b);
			}
			if (((shelltype.equals("APDS (Early)")))) {
				slopemultiplier = Math.pow(2.17828, (Math.pow(impactangle, 2.60)*0.00003011));
				System.out.println(slopemultiplier);
			}
			if ((shelltype.equals("APDS (Late)"))) {
				slopemultiplier = Math.pow(2.17828, Math.pow(impactangle/1.2025, 2.60)*0.00003011);
				System.out.println(slopemultiplier);
			}
			if ((shelltype.equals("APCR")) && (impactangle>=0) && (impactangle<=25)) {
	            slopemultiplier = 2.71828* (Math.pow(impactangle,2.20) * 0.0001727);
			}
			if ((shelltype.equals("APCR")) && (impactangle>25)) {
	            slopemultiplier = 0.7277* 2.71828* (Math.pow(impactangle,1.50) * 0.003787);
			}
			if (shelltype.equals("HEAT") || (shelltype.equals("HEAT (Early)"))) {
				slopemultiplier = 1/Math.cos(Math.toRadians(impactangle));
				System.out.println(slopemultiplier);
			}
			System.out.println(impactangle);
			if(shelltype.equals("APFSDS (Long Rod)")) {
				if ("APFSDS (Long Rod)".equals(shelltype)) {
		            int dif = (int)(impactangle % 5);
		            int ceil = (int)Math.ceil(impactangle - dif + 5);
		            int floor = (int)Math.floor(impactangle - dif);
		            Double sCeil = APFSDS_long.get(ceil);
		            Double sFloor = APFSDS_long.get(floor);
		            int difJum = 1;

		            // Find next non-zero sCeil
		            while (sCeil != null && sCeil == 0) {
		                ceil += 5;
		                sCeil = APFSDS_long.get(ceil);
		                difJum++;
		            }

		            // Find previous non-zero sFloor
		            while (sFloor != null && sFloor == 0) {
		                floor -= 5;
		                sFloor = APFSDS_long.get(floor);
		                difJum++;
		            }

		            // Only calculate multiplier if both are not null
		            if (sCeil != null && sFloor != null) {
		                slopemultiplier = sFloor + (((sCeil - sFloor) / difJum) * ((impactangle - floor) / 5));
		            } else {
		                slopemultiplier = 0;
		            }
		        }

		   


			}
			return slopemultiplier;
		}
		public static double calculateAP(double caliber, double velocity, double mass) {
			double totalpen = 90 * Math.pow(velocity/792, 1.4283) * Math.pow(caliber/40, 1.0714) * Math.pow(mass/Math.pow(caliber,3), 0.7143) / Math.pow(1.08/Math.pow(40,3), 0.7143);
			return totalpen;
		}
		public static double calculateAPDS(double caliber, double velocity, double mass) {
			double totalpen = 275 * Math.pow(velocity/1204, 1.4283) * Math.pow(caliber/38.1, 1.0714) * Math.pow(mass/Math.pow(caliber,3), 0.7143) / Math.pow(3.5/Math.pow(38.1,3), 0.7143);
			return totalpen;
		}
		public static double calculateAPsov(double caliber, double velocity, double mass) {
			double totalpen = 148 * Math.pow(velocity/792, 1.4283) * Math.pow(caliber/85, 1.0714) * Math.pow(mass/Math.pow(caliber,3), 0.7143) / Math.pow(9.1033/Math.pow(85,3), 0.7143);
			return totalpen;
		}
		
		public static double calculateAPsovpre(double caliber, double velocity, double mass) {
			double totalpen = 184 * Math.pow(velocity/792, 1.4283) * Math.pow(caliber/85, 1.0714) * Math.pow(mass/Math.pow(caliber,3), 0.7143) / Math.pow(9.1033/Math.pow(85,3), 0.7143);
			return totalpen;
		}
		
	static double calculateAPpre(double caliber, double velocity, double mass) {
			double totalpen = 165 * Math.pow(velocity/853, 1.4283) * Math.pow(caliber/90, 1.0714) * Math.pow(mass/Math.pow(caliber,3), 0.7143) / Math.pow(10.61/Math.pow(90,3), 0.7143);
			return totalpen;
		}
		public static double calculateAPC(double caliber, double velocity, double mass) {
			double totalpen = 192 * Math.pow(velocity/935, 1.4283) * Math.pow(caliber/74.5, 1.0714) * Math.pow(mass/Math.pow(caliber,3), 0.7143) / Math.pow(6.782/Math.pow(74.5,3), 0.7143);
			return totalpen;
		}
		public static double calculateheatearly(double caliber, double tnteq) {
			double pen = (caliber)*75/caliber * 1.55 * Math.pow(tnteq/.876, .3);
			return pen;
		}
		public static double calculateheat(double caliber, double tnteq) {
			double pen =  caliber * 3.1 * Math.pow(tnteq/.876, .3);
			return pen;
		}
		public static double calculateAPCR(double corecaliber, double velocity, double shellmass, double coremass) {
			 double pallet_mass = shellmass - coremass;
		        double kfbr = 3000;
		        double part_pallet_mass = (coremass / shellmass) * 100;
		        double kf_pallet_mass = (part_pallet_mass > 36.0) ? 0.5 : 0.4;
		        double calculated_mass = coremass + (kf_pallet_mass * pallet_mass);
		        
			double totalpen = ((Math.pow(velocity, 1.43) * Math.pow(calculated_mass, 0.71)) / (Math.pow(kfbr, 1.43) * Math.pow(corecaliber / 10000, 1.07)));
			return totalpen;
		}
}
