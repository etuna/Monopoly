package gui;



/*
 * TITLE: Program 7.3b
 *
 * @(#)Animator 2002/07/21
 * @author Charles W. Kann III
 *
 * Copyright (c) 2002 CRC Press
 * All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this
 * software and its documentation for NON-COMMERCIAL purposes
 * and without fee is hereby granted provided that this
 * copyright notice appears in all copies.
 *
 * THE AUTHOR MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGEMENT. THE AUTHOR SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

/** 
 *    Purpose:  This class creates a generic animator.  Any 
 *    		object that implements the Drawable interface
 *		can be added to this animator, and will be called
 *		at regular intervals from the paint method.
 *
 *		This animator only provides the mechanism for a
 *		call back.  Actually drawing and moving the objects
 *		is the responsibility of the object registering with
 *		this animator.
 */

@SuppressWarnings("serial")
public final class Animator extends JPanel implements Runnable {

    @SuppressWarnings("rawtypes")
	Vector elementsToDraw = new Vector();
    long sleepTime = 17;
    boolean animatorStopped = true, firstTime = true;
    //JFrame animFrame;
    
    private Point mPos;

    /**
     *  Constructor that creates the JFrame for the animator.  Note
     *  the JFrame is shown in the show() method because this starts
     *  the GUI thread.  Starting the thread in the constructor
     *  can lead to a race condition.
     */
    public Animator() {
       // animFrame = new JFrame("Generic Animator");
      //  Container animContainer = animFrame.getContentPane();
       // animContainer.add(this);
       // animFrame.setSize(700, 450);
      //  animFrame.setLocation(0,100);
        
        this.addMouseMotionListener(new MouseMotionAdapter() {
        	public void mouseMoved(MouseEvent evt) {
        		mPos = evt.getPoint();
        		//System.out.println("MOUSE POS: "+ mPos.getX()+", "+mPos.getY());
        	}
        });
    }

    /**
     *  setVisible is called to display or hide the animator.
     *  Note that only display = true is implemented, and this
     *  function only works at this point if it is called once.
     *  It is left as an exercise to implement it correctly.
     *  If display = false, the Control Thread needs to be
     *  suspended.  If display = true, the control thread
     *  should be started only the first time, after that it
     *  should be unsuspended.  This can be accomplished as
     *  using control variables in the paint method for Program
     *  7.4 and after, and should not be done using the suspend
     *  and resume methods.
     */
    public void setVisible(boolean display) {
        if (display == true) {
            if (firstTime) {
                firstTime = false;
                // Show the animator.  This starts the GUI thread.
                //animFrame.setVisible(true);

                // Put the animator in another thread so that the
                // calling object can continue.
                (new Thread(this)).start();
            }
        }
    }


    /** Thread that runs the animator.  This thread sleeps for some 
     *  amount of time specified by sleepTime, then calls repaint 
     *  which forces a call to the paint method, but in the GUI thread.
     *  Note that the animatorStopped button allows the animator to 
     *  single step and pause the animation.  The notify is done in 
     *  the control frame from the button.
    */

    public void run() {
        while (true) {
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                System.out.println("Program Interrupted");
                System.exit(0);
            }
            repaint();
        }
    }

    /**  
     *  The paint method redraws all the objects registered with this
     *  animator.  It is run in the GUI thread.  The repaint 
     *  command causes this to be called, and it passes the graphics 
     *  object g to each of the registered objects to have them draw 
     *  themselves.
    */
    public void paint(Graphics g) {
        super.paint(g);
        @SuppressWarnings("rawtypes")
		Enumeration e = elementsToDraw.elements();
        while (e.hasMoreElements())
            ((Drawable) e.nextElement()).draw(g);
    } 

    /** 
     *  addElement adds each drawable to the vector for use by the
     *   DrawElements method.
     */
    @SuppressWarnings("unchecked")
	public void addDrawable(Drawable d) {
        elementsToDraw.addElement(d);
    }

    /** 
     *  removeElement is used to remove drawables from the vector.  
     */
    public void removeDrawable(Drawable d) {
        elementsToDraw.removeElement(d);
    }

	public Point getmPos() {
		return mPos;
	}
	
}
