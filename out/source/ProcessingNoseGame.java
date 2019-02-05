import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import gab.opencv.*; 
import processing.video.*; 
import java.awt.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ProcessingNoseGame extends PApplet {





Capture video;
OpenCV opencv;

int hitbox = 50;

String accum = "";
int prevx = 0, prevy = 0;

public void mouseWheel(MouseEvent event){
    if(event.getCount() > 0){
        hitbox--;
    }else {
        hitbox++;
    }
}

public void mousePressed() {
    exit();
}

public void setup() {
  
  video = new Capture(this, 640/2, 480/2);
  opencv = new OpenCV(this, 640/2, 480/2);
  opencv.loadCascade("haarcascade_frontalface_alt.xml", true);  

  video.start();
}

public void draw() {
    scale(2);
    opencv.loadImage(video);

    image(video, 0, 0 );

    noFill();
    stroke(0, 255, 0);
    strokeWeight(3);
    Rectangle[] faces = opencv.detect();
    println(faces.length);


    if(faces.length == 0) {
        rect(prevx, prevy, hitbox, hitbox);
    } else {
        
        for (int i = 0; i < faces.length; i++) {
            println(faces[i].x + "," + faces[i].y);
            stroke(0,255,0);
            rect(faces[i].x, faces[i].y, faces[i].width, faces[i].height);
            //let's say ship has hitbox of 100x100
            stroke(255,0 ,0 );
            prevx =faces[i].x + faces[i].width/2 - hitbox/2;
            prevy =  faces[i].y + faces[i].height/2 - hitbox/2;
            rect(prevx, prevy, hitbox, hitbox);
            
        }

    }
    text(hitbox, 20, 20);
}


public void captureEvent(Capture c) {
  c.read();
}


  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ProcessingNoseGame" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
