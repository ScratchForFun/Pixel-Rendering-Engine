package net.scratchforfun;

import net.scratchforfun.gfx.Display;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

/**
 * Created by Magnus on 24/07/2015.
 */
public class Window implements Runnable {

    // Actual window
    private JFrame frame;
    // Display
    private Display display;

    // Window size
    public int WIDTH, HEIGHT;
    // Window title
    public String TITLE;

    // How often we want the game to tick per second
    public int TARGET_TPS;

    // Is this window thread running?
    public boolean isRunning;

    public Window(String TITLE, int WIDTH, int HEIGHT){
        // Set the variables
        this.TITLE = TITLE;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;

        // Set the target TPS
        this.TARGET_TPS = 30;

        // Create the window
        this.frame = new JFrame(TITLE);
        // Correctly terminate the program
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Sets the size of the content pane's window
        this.frame.getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // Make sure the window is not resizable
        this.frame.setResizable(false); // TODO
        // Updates the window with our specified size
        this.frame.pack();
        // Centers our window
        this.frame.setLocationRelativeTo(null);
        // Makes the frame visible
        this.frame.setVisible(true);

        // Create the display
        this.display = new Display(this);
        // Add the display to the frame
        this.frame.add(this.display);
    }

    public void start(){
        // Allow the gameloop to work
        isRunning = true;
        // Start the gameloop
        new Thread(this).start();
    }

    public void stop(){
        // Stop the gameloop
        isRunning = false;
    }

    public void tick(){

    }

    public void render(){
        // Buffer Strategy
        BufferStrategy bs = this.display.getBufferStrategy();
        if(bs == null){
            this.display.createBufferStrategy(2);
            this.display.requestFocus();
            return;
        }

        // Grab the buffer graphics
        Graphics g = bs.getDrawGraphics();

        // Clear the graphics
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // Draw the display
        this.display.render(g);

        // Dispose of graphics
        g.dispose();
        // Flipping
        bs.show();
    }

    @Override
    public void run() {
        // The fps and tick counter
        int fps = 0, tick = 0;
        // Keeps track of the last time you calculated and printed fps and tps to the console
        double fpsTimer = System.currentTimeMillis();

        // The amount of ticks per second.
        double secondsPerTick = 1D / TARGET_TPS;
        // The amount of nanoseconds per tick
        double nsPerTick = secondsPerTick * 1000000000D;
        // Keeps track of the last 'unprocessed' calculation
        double then = System.nanoTime();
        // The current time
        double now;
        // Whenever this exceeds 1, call the tick() method
        double unprocessed = 0;

        // The actual game loop
        while(isRunning){
            // Stores current time
            now = System.nanoTime();
            // The amount of time since last call divided by the amount nanoseconds per tick
            unprocessed += (now - then) / nsPerTick;
            // Update the timer
            then = now;

            // To keep track of all the unprocessed frames we tick the game till we are on track again
            while(unprocessed >= 1){
                // Tick the game
                tick();
                // Add to the tick counter (TPS)
                tick++;
                // You have processed one of the unprocessed frames, removed it from the calculation
                unprocessed--;
            }

            // This is NOT to sleep, but to limit the game loop
            try {
                // Sleeps 1 ms
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Render the window
            render();
            // Add to the fps counter (FPS)
            fps++;

            // If the current time is 1 second greater than the last time we printed
            if(System.currentTimeMillis() - fpsTimer >= 1000){
                // Print out FPS and TPS to console
                System.out.printf("FPS: %d, TPS: %d%n", fps, tick);
                // Reset the fps and tick counter
                fps = 0; tick = 0;
                // Add 1 second to the 'last time printed'
                // This will make sure the 'if' statement fires next second
                fpsTimer += 1000;
            }
        }

        // When the gameloop is finished running, close the program
        this.frame.dispatchEvent(new WindowEvent(this.frame, WindowEvent.WINDOW_CLOSING));
    }
}
