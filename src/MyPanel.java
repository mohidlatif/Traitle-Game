package src;
import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
public class MyPanel extends JPanel {
//Variables which control spacing
private static final double DESIGN_W = 3500.0;
private static final double DESIGN_H = 2000.0;
private static final double TOP_Y = 100; // top y of shape when line==null
private static final double VSTEP = 150; // your downLine vertical step length
private static final double STEM_H = 30;  // child stem height
private static final double WIDTH_G1 = 660;  //Gap between G1 line
private static final double WIDTH_G2 = 280; 
private static final double WIDTH_G3_PLUS = 220;  //Changes the length of the G2 line
private static final double MIN_GAP = 80; //Is the smallest gap between siblings
Graphics2D g2;
Disease d;
Heiarchy heiarchy;
    //Code to pull up JPanel
    MyPanel(Heiarchy h) {
        heiarchy=h;
        d = h.getGeneration(0).getGeneration().get(0).getCouple().getHusband().getDisease();
        setLayout(new BorderLayout());
        add(new JScrollPane(new PedigreeCanvas()), BorderLayout.CENTER);
    }
   
    private class PedigreeCanvas extends JPanel {

        private static final int DESIGN_W = 3500;
        private static final int DESIGN_H = 2000;

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(DESIGN_W, DESIGN_H);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);

            renderPedigree(g2, null, 0);
        }
    }

    @Override
    public Dimension getPreferredSize() {
    return new Dimension((int) DESIGN_W, (int) DESIGN_H);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Arial", 1, 10));
        g.drawString("Traitle 2026 Mohid Latif" + " This software is an original work developed by Mohid Latif." +  " It is provided for educational and research purposes only." + " Redistribution, modification, or reuse of this code, in whole or in part,  " + "is not permitted without explicit permission from the author." , 103, 692);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        renderPedigree(g2, null, 0);

        g2.dispose();
    }

    public void renderPedigree(Graphics2D g2,  Line2D line, int n){
        Rectangle clipBounds = g2.getClipBounds();
        int width = (int) clipBounds.getWidth();
        int size = 32;
        Font font = new Font("Apotos", Font.BOLD, size);
        g2.setFont(font);
        String str = "Guess the Pedigree Type!";
        int middleString =width/2-str.length()*12+150;
        g2.drawString(str, middleString , 30);
        font = new Font("Apotos", Font.BOLD, 28);
        g2.setFont(font);
        String[] strList = "Possible Diseases:;1: Mitochondrial;2: X-Linked Dominant;3: X-Linked Recessive;4: Y-Linked;5: Autosomal Dominant;6: Autosomal Recessive".split(";");
        for(int i=0; i<strList.length; i++){
            g2.drawString(strList[i],50, (50+40*i));
        }
            g2.setFont(new Font("Arial", Font.BOLD, 15));
            g2.drawString("Please Note: there may be several inheritance types to any pedigree, the intended answer is merely the disease type the pedigree was made on",100, (675));
        Rectangle2D father0 = null;
        Ellipse2D mother0 = null;
            Line2D[] lines= null;
            Line2D[] lines1= null;
            Line2D[] lines2= null;
            Line2D[] lines3= null;
             for (int gen = 0; gen < heiarchy.getHeiarchy().size(); gen++) {   
              for (int c = 0; c < heiarchy.getHeiarchy().get(gen).getGeneration().size(); c++) {
                    Couple ct = heiarchy.getHeiarchy().get(gen).getGeneration().get(c);
                    Person husband = ct.getHusband();
                    Person wife = ct.getWife();
                    father0=null;
                    mother0=null;
                    if(gen==0){
                        int upperOffest = 100;
                        father0 = husband.isAffected() ? drawMaleAffected(g2, 722-150, upperOffest): drawMaleUnaffected(g2, 722-150, upperOffest);
                        mother0 = wife.isAffected() ? drawFemaleAffected(g2, 722+150, upperOffest): drawFemaleUnaffected(g2, 722+150, upperOffest);
                        lines = lineSplits(g2, downLine(g2, linkShapes(g2, father0, mother0)) , husband.getChildren().size());
                    }
                    else if(gen==1){
                    if(heiarchy.getHeiarchy().get(0).getGeneration().get(0).getHusband().getChildren().indexOf(husband)>=0){
                        father0 = husband.isAffected() ? drawMaleAffected(g2, lines[c].getX2()-35, lines[c].getY2()): drawMaleUnaffected(g2, lines[c].getX2()-35, lines[c].getY2());
                        mother0 = wife.isAffected() ? drawFemaleAffected(g2, lines[c].getX2()+150, lines[c].getY2()): drawFemaleUnaffected(g2, lines[c].getX2()+150, lines[c].getY2()); 
                        switch(c){
                            case 0:
                                lines1 = lineSplits(g2, downLine(g2, linkShapes(g2, father0, mother0)) , husband.getChildren().size());
                                break;
                            case 1:
                                lines2 = lineSplits(g2, downLine(g2, linkShapes(g2, father0, mother0)) , husband.getChildren().size());
                                break;
                            case 2:
                                lines3 = lineSplits(g2, downLine(g2, linkShapes(g2, father0, mother0)) , husband.getChildren().size());
                                break;
                        }  
                    }
                    else{
                        father0 = husband.isAffected() ? drawMaleAffected(g2, lines[c].getX2()+150, lines[c].getY2()): drawMaleUnaffected(g2, lines[c].getX2()+150, lines[c].getY2());
                        mother0 = wife.isAffected() ? drawFemaleAffected(g2, lines[c].getX2()-35, lines[c].getY2()): drawFemaleUnaffected(g2, lines[c].getX2()-35, lines[c].getY2());  
                        switch(c){
                            case 0:
                                lines1 = lineSplits(g2, downLine(g2, linkShapes(g2, father0, mother0)) , husband.getChildren().size());
                                break;
                            case 1:
                                lines2 = lineSplits(g2, downLine(g2, linkShapes(g2, father0, mother0)) , husband.getChildren().size());
                                break;
                            case 2:
                                lines3 = lineSplits(g2, downLine(g2, linkShapes(g2, father0, mother0)) , husband.getChildren().size());
                                break;
                        } 
                    }
                    }
                  else if (gen == 2) {
                    Person child = (husband != null ? husband : wife);
                    int real;
                    real = heiarchy.getHeiarchy().get(1).getGeneration().get(0).getHusband().getChildren().indexOf(child);
                    if (real != -1) {
                    if (child.getGender()) {
                        father0 = child.isAffected() ? drawMaleAffected(g2, lines1[real].getX2() - 35, lines1[real].getY2()) : drawMaleUnaffected(g2, lines1[real].getX2() - 35, lines1[real].getY2());
                    } 
                    else {
                        mother0 = child.isAffected() ? drawFemaleAffected(g2, lines1[real].getX2() - 35, lines1[real].getY2()) : drawFemaleUnaffected(g2, lines1[real].getX2() - 35, lines1[real].getY2());
                    }
                    continue;
                    }
                    real = heiarchy.getHeiarchy().get(1).getGeneration().get(1).getHusband().getChildren().indexOf(child);
                    if (real != -1) {
                    if (child.getGender()) {
                        father0 = child.isAffected() ? drawMaleAffected(g2, lines2[real].getX2() - 35, lines2[real].getY2()) : drawMaleUnaffected(g2, lines2[real].getX2() - 35, lines2[real].getY2());
                    }
                    else {
                        mother0 = child.isAffected() ? drawFemaleAffected(g2, lines2[real].getX2() - 35, lines2[real].getY2()) : drawFemaleUnaffected(g2, lines2[real].getX2() - 35, lines2[real].getY2());
                    }
                    continue;
                    }
                    real = heiarchy.getHeiarchy().get(1).getGeneration().get(2).getHusband().getChildren().indexOf(child);
                    if (real != -1) {
                    if (child.getGender()) {
                        father0 = child.isAffected() ? drawMaleAffected(g2, lines3[real].getX2() - 35, lines3[real].getY2()) : drawMaleUnaffected(g2, lines3[real].getX2() - 35, lines3[real].getY2());
                    } 
                    else {
                        mother0 = child.isAffected() ? drawFemaleAffected(g2, lines3[real].getX2() - 35, lines3[real].getY2()) : drawFemaleUnaffected(g2, lines3[real].getX2() - 35, lines3[real].getY2());
                    }
                }
            }             
        }
    }
    
    }

    public Rectangle2D drawMaleUnaffected(Graphics2D g2, double x, double y) {
        g2.setColor(Color.BLACK);
        Rectangle2D rect = new Rectangle2D.Double(x, y, 70, 70);
        g2.draw(rect);
        return rect;
    }

    public Rectangle2D drawMaleAffected(Graphics2D g2, double x, double y) {
        g2.setColor(Color.BLACK);
        Rectangle2D rect = new Rectangle2D.Double(x, y, 70, 70);
        g2.fill(rect);
        return rect;
    }
    //Future Feature 
    /*
    public Rectangle2D drawMaleCarrier(Graphics2D g2, double x, double y){
   		Rectangle2D rect = new Rectangle2D.Double(x, y, 70, 70);
	    g2.setClip(new Rectangle2D.Double(x, y, 35, 70)); 
	    g2.setColor(Color.BLACK);
	    g2.fill(rect);
	   
	    //g2.setClip(new Rectangle2D.Double(x+35, y, 35, 70)); 
	    //g2.setColor(Color.WHITE);
	    //g2.fill(rect);
	    
	    g2.setClip(null);
	    g2.setColor(Color.BLACK);
	    g2.draw(rect);
	    return rect;
	}
`	*/  
    public Ellipse2D drawFemaleUnaffected(Graphics2D g2, double x, double y) {
        g2.setColor(Color.BLACK);
        Ellipse2D circle = new Ellipse2D.Double(x, y, 70, 70);
        g2.draw(circle);
        return circle;
    }

    public Ellipse2D drawFemaleAffected(Graphics2D g2, double x, double y) {
        g2.setColor(Color.BLACK);
        Ellipse2D circle = new Ellipse2D.Double(x, y, 70, 70);
        g2.fill(circle);
        return circle;
    }
    //Future Feature 
    /*
    public Ellipse2D drawFemaleCarrier(Graphics2D g2, double x, double y){
		Ellipse2D circle = new Ellipse2D.Double(x, y, 70, 70);
	    g2.setClip(new Rectangle2D.Double(x, y, 35, 40)); 
	    g2.setColor(Color.BLACK);
	    g2.fill(circle);
	    
	    //g2.setClip(new Rectangle2D.Double(x+35, y, 35, 70)); 
	    //g2.setColor(Color.WHITE);
	    //g2.fill(circle);
	    
	    g2.setClip(null);
	    g2.setColor(Color.BLACK);
	    g2.draw(circle);
	    return circle;	
	}
    */  
    public Line2D linkShapes(Graphics2D g2, Rectangle2D r, Ellipse2D e) {
        Line2D line;

        if (r.getMinX() < e.getMinX()) {
            line = new Line2D.Double(r.getMaxX(), r.getCenterY(), e.getMinX(), e.getCenterY());
        } 
        else {
            line = new Line2D.Double(r.getMinX(), r.getCenterY(), e.getMaxX(), e.getCenterY());
        }

        g2.draw(line);
        return line;
    }

    public Line2D downLine(Graphics2D g2, Line2D l) {
        double midX = (l.getX1() + l.getX2()) / 2;
        Line2D down = new Line2D.Double(midX, l.getY1(), midX, l.getY1() + 150);
        g2.draw(down);
        return down;
    }

    public Line2D[] lineSplits(Graphics2D g2, Line2D parentDown, int numKids) {
    Line2D[] lines = new Line2D[numKids];
    if (numKids == 1) {
        lines[0] = new Line2D.Double(parentDown.getX2(), parentDown.getY2(), parentDown.getX2(), parentDown.getY2() + STEM_H);
        g2.draw(lines[0]);
        return lines;
    }
   int genDepth = (int) Math.round((parentDown.getY2() - TOP_Y) / VSTEP);
	double targetWidth = 0.0;
	if(genDepth<=1){
		targetWidth = WIDTH_G1;
	}
	else if(genDepth==2){
		targetWidth = WIDTH_G2;
	}
	else{
		targetWidth = WIDTH_G3_PLUS;
	}
    double step = Math.max(MIN_GAP, targetWidth / (numKids - 1));
    double width = step * (numKids - 1);
    double mid = parentDown.getX2();
    double left = mid - width / 2.0;
    double right = mid + width / 2.0;

    Line2D horiz = new Line2D.Double(left, parentDown.getY2(), right, parentDown.getY2());
    g2.draw(horiz);

    for (int i = 0; i < numKids; i++) {
        double x = left + step * i;
        lines[i] = new Line2D.Double(x, horiz.getY1(), x, horiz.getY1() + STEM_H);
        g2.draw(lines[i]);
    }
    return lines;
}	
} 