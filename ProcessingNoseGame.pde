import gab.opencv.*;
import processing.video.*;
import java.awt.*;

Capture video;
OpenCV opencv;

int hitbox = 50;

String accum = "";
int prevx = 0, prevy = 0;

void mouseWheel(MouseEvent event){
    if(event.getCount() > 0){
        hitbox--;
    }else {
        hitbox++;
    }
}

void mousePressed() {
    exit();
}

void setup() {
  size(640, 480);
  video = new Capture(this, 640/2, 480/2);
  opencv = new OpenCV(this, 640/2, 480/2);
  opencv.loadCascade("haarcascade_frontalface_alt.xml", true);  

  video.start();
}

void draw() {
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


void captureEvent(Capture c) {
  c.read();
}


